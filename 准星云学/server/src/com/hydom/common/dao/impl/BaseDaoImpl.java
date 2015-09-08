package com.hydom.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hydom.common.bean.Pager;
import com.hydom.common.bean.Pager.OrderType;
import com.hydom.common.dao.BaseDao;

/**
 * DAO实现类 - 基类实现类
 * @author Holen
 * @version 1.0.0 2014.6.8 新建
 *
 * @param <T> - 实体类
 * @param <PK> - 主键类型
 */
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {

	private Class<T> entityClass;
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = null;
        Class<?> c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id, "id is required");
		return (T) getSession().load(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		String hql = "from " + entityClass.getName() + " as model where model.id in(:ids)";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public T get(String[] propertyNames, Object[] values) {
		StringBuffer buff = new StringBuffer("from ");
		buff.append(entityClass.getName()).append(" as model where ");
		for (int i = 0; i < propertyNames.length; i ++) {
			Assert.hasText(propertyNames[i], "propertyName must not be empty");
			Assert.notNull(values[i], "value is required");
			if (i != 0) {
				buff.append(" and ");
			}
			buff.append(" model.").append(propertyNames[i]).append(" = ?");
		}
		
		Query query = getSession().createQuery(buff.toString());
		for (int i = 0; i < propertyNames.length; i ++) {
			query.setParameter(i, values[i]);
		}
		return (T) query.setMaxResults(1).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称集合
	 * @param value
	 *            属性值集合
	 * @return 实体对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> getList(String[] propertyNames, Object[] values) {
		Assert.notNull(propertyNames, "propertys is not null");
		Assert.notNull(values, "valuesis no empty");
		
		// 属性值和Value值长度判断是否一致
		if (propertyNames.length != values.length) {
			return null;
		}
		
		String hql = "from " + entityClass.getName() + " as model";
		
		for (int i = 0; i < propertyNames.length; i ++) {
			if (0 == i) {
				if(values[i]==null)
				{
					hql += " where model." + propertyNames[i] + " is null";
				}
				else
				{
					hql += " where model." + propertyNames[i] + "= ?";
				}
			} else {
				if(values[i]==null)
				{
					hql += " and model." + propertyNames[i] + " is null";
				}
				else
				{
					hql += " and model." + propertyNames[i] + "= ?";
				}
			}
		}
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < propertyNames.length; i ++) {
			query.setParameter(i, values[i]);
		}
		return (List<T>) query.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		String hql = "from " + entityClass.getName();
		return getSession().createQuery(hql).setCacheable(true).list();
	}
	
	@SuppressWarnings("unchecked")
	public PK getTotalCount() {
		String hql = "select count(*) from " + entityClass.getName();
		return (PK) getSession().createQuery(hql).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public PK getTotalCount(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "select count(*) from " + entityClass.getName() + " as model where model." + propertyName + " = ?";
		return (PK) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(newValue, "newValue is required");
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && StringUtils.equalsIgnoreCase((String) oldValue, (String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		List<T> objs = getList(propertyName, value);
		return (objs.size() != 0);
	}

	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		Assert.notNull(entity, "entity is required");
		return (PK) getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().update(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity is required");
		getSession().delete(entity);
	}

	public void delete(PK id) {
		Assert.notNull(id, "id is required");
		T entity = load(id);
		getSession().delete(entity);
	}

	public void delete(PK[] ids) {
		Assert.notEmpty(ids, "ids must not be empty");
		for (PK id : ids) {
			T entity = load(id);
			getSession().delete(entity);
			
		}
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(Object object) {
		Assert.notNull(object, "object is required");
		getSession().evict(object);
	}
	
	public Pager findByPager(Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		return findByPager(pager, detachedCriteria);
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}

		Projection projection = ((CriteriaImpl) criteria).getProjection();
		Integer totalCount = Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		
		criteria.setProjection(projection);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
		
	}

	@SuppressWarnings("unchecked")
	public List<T> getList(DetachedCriteria detachedCriteria, int maxResults) {
		// 无返回数据多少限制
		if (maxResults == -1) {
			return detachedCriteria.getExecutableCriteria(getSession()).list();
		}
		return detachedCriteria.getExecutableCriteria(getSession()).setMaxResults(maxResults).list();
	}
	
	/**
	 * DetachedCriteria对象进行查询
	 * @param detachedCriteria
	 * @param startIdx - 开始位置
	 * @param limit - 最大返回数
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> getList(DetachedCriteria detachedCriteria, int start, int limit) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	public boolean isExist(String[] propertyNames, Object[] values) {
		Assert.notNull(propertyNames, "propertyNames must not be empty");
		Assert.notNull(values, "value is required");
		List<T> objs = getList(propertyNames, values);
		return (objs.size() != 0);
	}
	
	public String[] getPropertyNames() {
		String[] properties = getSession().getSessionFactory().getClassMetadata(entityClass).getPropertyNames();
		return properties;
	}
	
	public Projection formProjection(String[] properties) {
		ProjectionList list = Projections.projectionList();
        for (int i = 0; i < properties.length; ++i)
            list.add(Projections.property(properties[i]), properties[i]);
        return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListBySql(String sql){
		SQLQuery query = getSession().createSQLQuery(sql).addEntity(entityClass);
		List<T> list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public Integer countBySql(String sql) {
		// TODO Auto-generated method stub
		Integer totalCount = 0;
		try {
			SQLQuery countQuery = getSession().createSQLQuery(sql);
			
			
			
			totalCount = Integer.valueOf( countQuery.uniqueResult().toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return totalCount;
	}

	public Double distanceBySql(String sql) {
		Double totalCount = (double) 0;
		try {
			SQLQuery countQuery = getSession().createSQLQuery(sql);
			totalCount = (Double) countQuery.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return totalCount;
	}
	

	   //不返回对象集合（分组统计查询等）
	@SuppressWarnings("unchecked")
	public List<Object[]> getList(DetachedCriteria detachedCriteria) {
		
		return detachedCriteria.getExecutableCriteria(getSession()).list();
	}
	
	/**
	 * 根据属性删除数据.
	 * @param propertie
	 * @param value
	 * @return 删除结果
	 */
	public boolean delete(String table, String propertie, String value) {
		try {
			String sql = "DELETE FROM " + table + " WHERE " + propertie + "='" + value + "'";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}