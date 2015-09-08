package com.hydom.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;

import com.hydom.common.bean.Pager;

/**
 * Service接口 - Service接口基类
 */
public interface BaseService<T, PK extends Serializable> {

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T get(PK id);

	/**
	 * 根据ID获取实体对象.
	 * 
	 * @param id
	 *            记录ID
	 * @return 实体对象
	 */
	public T load(PK id);

	/**
	 * 根据ID数组获取实体对象集合.
	 * 
	 * @param ids
	 *            ID对象数组
	 * 
	 * @return 实体对象集合
	 */
	public List<T> get(PK[] ids);

	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String propertyName, Object value);

	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String[] propertyName, Object[] value);

	/**
	 * 根据属性名和属性值获取实体对象集合.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象集合
	 */
	public List<T> getList(String propertyName, Object value);

	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称集合
	 * @param value
	 *            属性值集合
	 * @return 实体对象
	 */
	public List<T> getList(String[] propertyNames, Object[] values);
	
	/**
	 * detachedCriteria对象进行查询
	 * @param detachedCriteria
	 * @param maxResults 最大返回数, -1无限制
	 * @return List<T>
	 */
	public List<T> getList(DetachedCriteria detachedCriteria, int maxResults);
	
	/**
	 * DetachedCriteria对象进行查询
	 * @param detachedCriteria
	 * @param startIdx - 开始位置
	 * @param limit - 最大返回数
	 * @return List<T>
	 */
	public List<T> getList(DetachedCriteria detachedCriteria, Integer start, Integer limmit);

	/**
	 * 获取所有实体对象集合.
	 * 
	 * @return 实体对象集合
	 */
	public List<T> getAll();

	/**
	 * 获取所有实体对象总数.
	 * 
	 * @return 实体对象总数
	 */
	public PK getTotalCount();
	
	/**
	 * 
	 * @param propertyName 参数名
	 * @param value 参数值
	 * @return 实体对象总数
	 */
	public PK getTotalCount(String propertyName, Object value);

	/**
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param oldValue
	 *            修改前的属性值
	 * @param oldValue
	 *            修改后的属性值
	 * @return boolean
	 */
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue);

	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyNames
	 *            属性名称
	 * @param values
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String[] propertyNames, Object[] values);

	/**
	 * 保存实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return ID
	 */
	
	
	public PK save(T entity);

	/**
	 * 更新实体对象.
	 * 
	 * @param entity
	 *            对象
	 */
	public void update(T entity);

	/**
	 * 删除实体对象.
	 * 
	 * @param entity
	 *            对象
	 * @return
	 */
	public void delete(T entity);

	/**
	 * 根据ID删除实体对象.
	 * 
	 * @param id
	 *            记录ID
	 */
	public void delete(PK id);

	/**
	 * 根据ID数组删除实体对象.
	 * 
	 * @param ids
	 *            ID数组
	 */
	public void delete(PK[] ids);

	/**
	 * 刷新session.
	 * 
	 */
	public void flush();

	/**
	 * 清除Session.
	 * 
	 */
	public void clear();

	/**
	 * 清除某一对象.
	 * 
	 * @param object
	 *            需要清除的对象
	 */
	public void evict(Object object);

	/**
	 * 根据Page对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param page
	 *            Page对象
	 * @return Page对象
	 */
	public Pager findByPager(Pager pager);

	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 *            Pager对象
	 * @return Pager对象
	 */
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria);

	/**
	 * 根据ids更新集合
	 * 
	 * @param ids
	 * @param stauts
	 */
	public void updateBatch(String[] ids, String stauts);

	public String[] getPropertyNames();
	
	public Projection formProjection(String[] properties);
	
	/**
	 * 通过原生sql查询列表
	 * @param sql
	 * @return
	 */
	public List<T> findListBySql(String sql);
	
	/**
	 * 通过原生sql统计记录条数
	 * @param sql
	 * @return
	 */
	public Integer countBySql(String sql);
	/**
	 * 通过原生sql计算距离
	 * @param sql
	 * @return
	 */
	public Double distanceBySql(String sql);
	
	
	// 不返回对象集合（分组统计查询等）
		public List<Object[]> getList(DetachedCriteria detachedCriteria);
	/**
	 * 根据属性删除数据.
	 * @param propertie
	 * @param value
	 * @return 删除结果
	 */
	public boolean delete(String table, String propertie, String value);
}
