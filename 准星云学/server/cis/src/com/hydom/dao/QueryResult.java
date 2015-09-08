package com.hydom.dao;

import java.util.List;

/**
 * 查询结果封装类:可以把总的记录数及结果集封装到此bean类中
 * 
 * @author www.hydom.cn [heou]
 * 
 * @param <T>
 */
public class QueryResult<T> {
	private List<T> resultList; // 查询到的实体记录
	private long totalrecords; // 查询到的总的记录数

	/**
	 * @return：返回实体List集合
	 */
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/**
	 * @return：总的记录数
	 */

	public long getTotalrecords() {
		return totalrecords;
	}

	public void setTotalrecords(long totalrecords) {
		this.totalrecords = totalrecords;
	}
}
