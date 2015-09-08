package com.hydom.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;


/**
 * @author www.hydom.cn [heou]
 * 
 * @param <T>
 */
public interface DAO<T> {
	public void save(T entity);

	public void update(T entity);

	public T find(Serializable id);

	public void delete(Serializable... ids);

	/**
	 * 获取总的记录数
	 */
	public long getCount();

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
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params);

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
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			LinkedHashMap<String, String> orderby);

	/**
	 * where+排序--->3
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
	public QueryResult<T> getScrollData(int startIndex, int maxresult,
			String jpql, Object[] params, LinkedHashMap<String, String> orderby);

	/**
	 * 获取全部数据
	 * 
	 * @return
	 */
	public QueryResult<T> getScrollData();
}
