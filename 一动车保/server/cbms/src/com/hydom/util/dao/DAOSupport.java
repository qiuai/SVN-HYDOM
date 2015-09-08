package com.hydom.util.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hydom.account.ebean.ProductCategory;

/**
 * DAO实现
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
	public void refresh(T entity) {
		if(entity != null){
			em.refresh(entity);
		}
	}

	@Override
	@Transactional
	public void delete(Serializable... ids) {
		for (Serializable id : ids) {
			this.remove(this.find(id));
		}
	}

	
	@Override
	@Transactional
	public void deleteBySql(String... ids) {
		
		if(ids.length > 0){
			List<String> params = new ArrayList<String>();
			for(String id : ids){
				params.add(id);
			}
			String sql = "delete "+buildEntityName()+" o where 1=1 and o.id in (:ids)";
			Query query = em.createQuery(sql);
			query.setParameter("ids", params);
			int i = query.executeUpdate();
			System.out.println(i);
		}
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public T find(Serializable id) {
		return em.find(entityClazz, id);
	}

	@Override
	public void save(T entity) {
		Assert.notNull(entity);
		em.persist(entity);
	}

	@Override
	public T update(T entity) {
		Assert.notNull(entity);
		return em.merge(entity);
	}
	
	/**
	 * 删除对象
	 */
	@Override
	public void removeById(String id) {
		if (StringUtils.isNotEmpty(id)) {
			em.remove(this.find(id));
		}
	}
	
	@Override
	public void remove(T entity) {
		if (entity!=null) {
			em.remove(entity);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params) {
		return this.getScrollData(startIndex, maxresult, jpql, params, null);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			LinkedHashMap<String, String> orderby) {
		return this.getScrollData(startIndex, maxresult, null, null, orderby);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(String jpql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return this.getScrollData(-1, -1, jpql, params, orderby);
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params, LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityName = buildEntityName();

		String whereJpql = "";
		if (jpql != null && !"".equals(jpql)) {
			whereJpql = "where 1=1 and " + jpql;
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

		query = em.createQuery("select count("
				+ this.getCountField(this.entityClazz) + ") from " + entityName
				+ " o " + whereJpql);
		if (jpql != null && !"".equals(jpql)) {
			this.setParms(query, params);
		}
		qr.setTotalrecords((Long) query.getSingleResult());
		return qr;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public QueryResult<T> getScrollData() {
		return this.getScrollData(-1, -1, null, null, null);
	}
	
	@Override
	public PageView<T> getPage(PageView<T> pageView) {
		if(pageView == null){
			pageView = new PageView<T>();
		}
		int startIndex = pageView.getFirstResult();
		int maxresult = pageView.getMaxresult();
		String sql = pageView.getJpql();
		QueryResult<T> result = getScrollData(startIndex,maxresult,sql,pageView.getParams(),pageView.getOrderby());
		pageView.setQueryResult(result);
		return pageView;
	}
	
	
	
	@Override
	public List<T> getList(String jpql, Object[] params, LinkedHashMap<String, String> orderby) {
		return getScrollData(jpql,params,orderby).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getList(Serializable... ids) {
		
		/*String m = "";
		for(Serializable id : ids){
			if(m!=""){
				m+=",";
			}
			m += id;
		}
		List<T> list = new ArrayList<T>();
		for(Serializable id : ids){
			T t = find(id);
			list.add(t);
		}*/
		
		/*String jqpl = "o.id in ?1";
		List<Object> params = new ArrayList<Object>();
		params.add(ids);
		
		return list;
		*/
		List<Serializable> list = new ArrayList<Serializable>();
		for(Serializable id : ids){
			list.add(id);
		}
		String entityName = buildEntityName();
		Query query = em.createQuery("select o from " + entityName + " o where 1=1 and o.id in (:list)");
		query.setParameter("list", list);
		return query.getResultList();
	}

	
	@Override
	public List<T> getListByHql(String hql) {
		TypedQuery<T> query = em.createQuery(hql, entityClazz).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	@Override
	public T findByHql(String hql) {
		TypedQuery<T> query = em.createQuery(hql, entityClazz).setFlushMode(FlushModeType.COMMIT);
		List<T> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(String jpql, Object[] params) {
		
		String entityName = buildEntityName();
		String whereJpql = "";
		if (jpql != null && !"".equals(jpql)) {
			whereJpql = "where " + jpql;
		}
		Query query = em.createQuery("select o from " + entityName + " o " + whereJpql, entityClazz);
		setParms(query, params);
		List<T> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
