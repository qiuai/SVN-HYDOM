package com.hydom.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author www.hydom.cn [heou]
 * 
 * @param <T>
 */
@Transactional
public abstract class DAOSupport<T> implements DAO<T> {
	@PersistenceContext
	protected EntityManager em;
	private Class<T> entityClazz = this.getGenTypeParameter(this.getClass());

	@SuppressWarnings("static-access")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public long getCount() {
		return (Long) em.createQuery(
				"select count(" + this.getCountField(this.entityClazz)
						+ ") from " + buildEntityName() + " o")
				.getSingleResult();
	}

	@Override
	public void delete(Serializable... ids) {
		for (Serializable id : ids) {
			em.remove(em.getReference(entityClazz, id));
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public T find(Serializable id) {
		return em.find(entityClazz, id);
	}

	@Override
	public void save(T entity) {
		em.persist(entity);
	}

	@Override
	public void update(T entity) {
		em.merge(entity);
	}

	/**
	 * 分页+where--->1
	 * 
	 * @param startIndex
	 *            :分页的起始索引
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param jpql
	 *            :查询语句的where语句,形如:o.id=?1 and o.name=?2
	 * @param params
	 *            :查询语句的where语句参数,形如:Object[]{2,"zhangsan"}
	 * @return:封装后的查询结果集
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params) {
		return this.getScrollData(startIndex, maxresult, jpql, params, null);
	}

	/**
	 * 分页+排序--->2
	 * 
	 * @param startIndex
	 *            :分页的起始索引
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param orderby
	 *            :排序语句:orderby.add("id","desc");
	 * @return:封装后的查询结果集
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			LinkedHashMap<String, String> orderby) {
		return this.getScrollData(startIndex, maxresult, null, null, orderby);
	}

	/**
	 * where+排序--->3 r
	 * 
	 * @param startIndex
	 *            :分页的起始索引
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param jpql
	 *            :查询语句的where语句,形如:o.id=?1 and o.name=?2
	 * @param params
	 *            :查询语句的where语句参数,形如:Object[]{2,"zhangsan"}
	 * @param orderby
	 *            :排序语句:orderby.add("id","desc");
	 * @return:封装后的查询结果集
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(String jpql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return this.getScrollData(-1, -1, jpql, params, orderby);
	}

	/**
	 * 分页+where+排序--->4
	 * 
	 * @param startIndex
	 *            :分页的起始索引
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param jpql
	 *            :查询语句的where语句,形如:o.id=?1 and o.name=?2
	 * @param params
	 *            :查询语句的where语句参数,形如:Object[]{2,"zhangsan"}
	 * @param orderby
	 *            :排序语句:orderby.add("id","desc");
	 * @return:封装后的查询结果集
	 */
	@SuppressWarnings( { "unchecked", "static-access" })
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params, LinkedHashMap<String, String> orderby) {
		// SQL:select o from User o where |o.id=?1 and o.name=?2| ... order by
		// id desc,name asc;
		QueryResult<T> qr = new QueryResult<T>();
		String entityName = buildEntityName();

		String whereJpql = "";
		if (jpql != null && !"".equals(jpql)) {
			whereJpql = "where " + jpql;
		}

		Query query = em.createQuery("select o from " + entityName + " o "
				+ whereJpql + methodOrderby(orderby));
		if (jpql != null && !"".equals(jpql)) {
			this.setParms(query, params);
		}
		if (startIndex != -1 && maxresult != -1) {// 只有这两个参数都不等于-1,才进行分页
			query.setFirstResult(startIndex);
			query.setMaxResults(maxresult);
		}

		qr.setResultList(query.getResultList());

		// 统计查询记录数
		query = em.createQuery("select count("
				+ this.getCountField(this.entityClazz) + ") from " + entityName
				+ " o " + whereJpql);
		if (jpql != null && !"".equals(jpql)) {
			this.setParms(query, params);
		}
		qr.setTotalrecords((Long) query.getSingleResult());
		return qr;
	}

	/**
	 * 获取全部数据
	 * 
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData() {
		return this.getScrollData(-1, -1, null, null, null);
	}

	private String methodOrderby(LinkedHashMap<String, String> orderby) {
		StringBuilder sb = new StringBuilder();
		if (orderby != null && orderby.size() > 0) {
			sb.append(" order by ");
			for (String key : orderby.keySet()) {
				sb.append("o.").append(key).append(" ")
						.append(orderby.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);// 删除最后多余的逗号
		}
		return sb.toString();
	}

	/**
	 * 对query对象进行参数赋值
	 * 
	 * @param query
	 * @param params
	 */
	private void setParms(Query query, Object[] params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
	}

	/**
	 * 通过实体clazz对象,构建查询的实体名字
	 * 
	 * @return
	 */
	private String buildEntityName() {
		String name = entityClazz.getSimpleName();
		Entity entity = entityClazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name().trim())) {
			name = entity.name();
		}
		return name;
	}

	/**
	 * 通过泛型技术获取泛型类型参数:比如UserServiceBean extends DAOSupporet<User> 将能得知T代表User
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getGenTypeParameter(Class<? extends DAOSupport> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType genType = (ParameterizedType) type;
			return (Class<T>) genType.getActualTypeArguments()[0];
		} else {
			throw new RuntimeException(clazz + " not extends DAOSupport");
		}
	}

	protected static <E> String getCountField(Class<E> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null
						&& method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(
							propertydesc.getPropertyType())
							.getPropertyDescriptors();
					out = "o."
							+ propertydesc.getName()
							+ "."
							+ (!ps[1].getName().equals("class") ? ps[1]
									.getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

}
