package com.hydom.util.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DAO层通用接口
 * 
 * @author www.hydom.cn [heou]
 * @param <T>
 */
public interface DAO<T> {
	public void save(T entity);

	public T update(T entity);
	
	public void delete(Serializable... ids);
	
	public void deleteBySql(String... ids);
	
	/**
	 * 获取总的记录数
	 */
	public long getCount();

	/**
	 * 获取查询结果集：【分页】【where】
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
	public QueryResult<T> getScrollData(int startIndex, int maxresult, String jpql, Object[] params);

	/**
	 *获取查询结果集：【分页】【排序】
	 * 
	 * @param startIndex
	 *            :分页的起始索引
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param orderby
	 *            :排序语句:orderby.add("id","desc");
	 * @return:封装后的查询结果集
	 */
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			LinkedHashMap<String, String> orderby);

	/**
	 * 获取查询结果集：【排序】【where】
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
	public QueryResult<T> getScrollData(String jpql, Object[] params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 获取查询结果集：【分页】【where】【排序】
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
	public QueryResult<T> getScrollData(int startIndex, int maxresult, String jpql,
			Object[] params, LinkedHashMap<String, String> orderby);

	/**
	 * 获取全部数据
	 * 
	 * @return
	 */
	public QueryResult<T> getScrollData();
	
	/**
	 * 根据pageView中数据获取数据
	 * @param pageView
	 * @return
	 */
	public PageView<T> getPage(PageView<T> pageView);
	
	/**
	 * 根据SQL语句获取列表
	 * @param jpql
	 * @param params
	 * @param orderby
	 * @return
	 */
	public List<T> getList(String jpql,Object[] params, LinkedHashMap<String, String> orderby);
	
	/**
	 * 根据ID 获取数据列表
	 * @param ids
	 * @return
	 */
	public List<T> getList(Serializable... ids);
	
	/**
	 * 根据hql获取列表
	 * @param hql
	 * @return
	 */
	public List<T> getListByHql(String hql);
	
	/**
	 * 根据ID 获取实体类
	 * @param id
	 * @return
	 */
	public T find(Serializable id);
	
	/**
	 * 根据jpql获取实体类
	 * @param jpql
	 * @param params
	 * @return
	 */
	public T find(String jpql,Object[] params);
	
	/**
	 * 根据hql获取实体对象
	 * @param hql
	 * @return
	 */
	public T findByHql(String hql);
	
	void remove(T entity);
	
	
	public void refresh(T entity);

	void removeById(String id);
	
}
