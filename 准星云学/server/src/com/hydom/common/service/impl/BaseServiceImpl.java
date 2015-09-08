package com.hydom.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.springframework.transaction.annotation.Transactional;

import com.hydom.common.bean.Pager;
import com.hydom.common.dao.BaseDao;
import com.hydom.common.service.BaseService;


/**
 * Service实现类 - Service实现类基类
 */

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

    public BaseServiceImpl() {   
        super();
    }

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

	public T get(PK id) {
		return baseDao.get(id);
	}

	public T load(PK id) {
		return baseDao.load(id);
	}
	
	public List<T> get(PK[] ids) {
		return baseDao.get(ids);
	}
	 @Transactional
	public T get(String propertyName, Object value) {
		return baseDao.get(propertyName, value);
	}
	
	public List<T> getList(String propertyName, Object value) {
		return baseDao.getList(propertyName, value);
	}
	
	public List<T> getList(String[] propertyName, Object[] value) {
		return baseDao.getList(propertyName, value);
	}

	public List<T> getAll() {
		return baseDao.getAll();
	}
	
	public PK getTotalCount() {
		return baseDao.getTotalCount();
	}

	public PK getTotalCount(String propertyName, Object value) {
		return baseDao.getTotalCount(propertyName,value);
	}
	
	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		return baseDao.isUnique(propertyName, oldValue, newValue);
	}
	
	public boolean isExist(String propertyName, Object value) {
		return baseDao.isExist(propertyName, value);
	}

	public PK save(T entity) {
		return baseDao.save(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	public void delete(T entity) {
		baseDao.delete(entity);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}
	
	public void flush() {
		baseDao.flush();
	}

	public void clear() {
		baseDao.clear();
	}
	
	public void evict(Object object) {
		baseDao.evict(object);
	}

	public Pager findByPager(Pager pager) {
		return baseDao.findByPager(pager);
	}
	
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		return baseDao.findByPager(pager, detachedCriteria);
	}

	public T get(String[] propertyName, Object[] value) {
		return baseDao.get(propertyName, value);
	}

	public void updateBatch(String[] ids, String  stauts) {
		
	}

	public List<T> getList(DetachedCriteria detachedCriteria, int maxResults) {
		return baseDao.getList(detachedCriteria, maxResults);
	}
	
	public List<T> getList(DetachedCriteria detachedCriteria, Integer start, Integer limit) {
		return baseDao.getList(detachedCriteria, start, limit);
	}

	public boolean isExist(String[] propertyNames, Object[] values) {
		return baseDao.isExist(propertyNames, values);
	}
	
	public String[] getPropertyNames() {
		return baseDao.getPropertyNames();
	}
	
	public Projection formProjection(String[] properties) {
		return baseDao.formProjection(properties);
	}

	public List<T> findListBySql(String sql) {
		return baseDao.findListBySql(sql);
	}

	public Integer countBySql(String sql) {
		return baseDao.countBySql(sql);
	}

	public Double distanceBySql(String sql) {
		return baseDao.distanceBySql(sql);
	}
	
	/**
	 * 根据属性删除数据.
	 * @param propertie
	 * @param value
	 * @return 删除结果
	 */
	public boolean delete(String table, String propertie, String value) {
		return baseDao.delete(table, propertie, value);
	}
}
