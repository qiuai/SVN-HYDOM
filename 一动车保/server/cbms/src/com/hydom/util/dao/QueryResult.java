package com.hydom.util.dao;

import java.util.List;

/**
 * 查询结果封装类:可以把总的记录数及结果集封装到此bean类中
 * 
 * @author www.hydom.cn [heou]
 * 
 * @param <T>
 */
public class QueryResult<T> {
	private List<T> resultList;
	private long totalrecords;

	/**
	 * 获取实体列表
	 * 
	 * @return
	 */
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 得到总的记录数
	 * 
	 * @return
	 */
	public long getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(long totalrecords) {
		this.totalrecords = totalrecords;
	}
}
