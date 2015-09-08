package com.hydom.dao;

import java.util.List;

/**
 * 使用此封装对象的模板: (1)构造对象后便可以获取记录的开始索引,为获取总的记录数及实体集成做准备 (2)设置总的记录数(3)设置实体集合
 * (2)(3)步也可以直接使用setQueryResult方法代替. 解析:明确此类主要封装实体记录集合List<T>
 * 可有可无的第(4)步:如果想设置分布条上显示的页码数,可以setPageBarSize,但是也可不设置,因为默认设置为8
 * records及分页javeBean
 * :PageIndex.第(2)步设置总的记录数,引发设置总页数,进而完成PageIndex对象的封装.第(3)步设置实体集合完成List
 * <T>recodrs的封装
 * 补充说明:此类还特别提供为项目提供两个帮助的方法:getFirstResult:在完成第(1)步后,可以为查询实体时设置起始索引服务
 * ;setQueryResult方法可以直接把项目中的QueryResult对象直接封装到F此类,这样就可以省掉(2)(3)步
 * 
 * @author www.hydom.cn [heou]
 * @param <T>
 */
public class PageView<T> {
	private List<T> records; // 查询到的实体记录数
	private long totalrecord;// 总记录数

	private PageIndex pageIndex; // 分页索引：开始索引 结束索引
	private long pageBarSize = 5; // 分页条上的页码数
	private int currentPage; // 当前页
	private long totalPage = 1; // 总页数

	private int maxresult = 10; // 每页显示记录数

	/**
	 * 唯一的构造方法
	 * 
	 * @param maxresult
	 *            :每页显示的最大记录数
	 * @param currenPage
	 *            :当前页
	 */
	public PageView(int maxresult, int currenPage) {
		this.maxresult = maxresult;
		this.currentPage = currenPage;
	}

	/**
	 * 获取开始记录的索引:备用
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxresult;
	}

	/**
	 * 针对QueryResutl对象提供一个快速设置实体集合及总的记录数的方法
	 * 
	 * @param qr
	 */
	public void setQueryResult(QueryResult<T> qr) {
		setTotalrecord(qr.getTotalrecords());
		setRecords(qr.getResultList());
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
		// 如果设置总记录数，应相应地修改总的页数
		setTotalPage(this.totalPage = this.totalrecord % this.maxresult == 0 ? this.totalrecord
				/ this.maxresult
				: this.totalrecord / this.maxresult + 1);
	}

	public long getPageBarSize() {
		return pageBarSize;
	}

	public void setPageBarSize(long pageBarSize) {
		this.pageBarSize = pageBarSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		pageIndex = PageIndex.getPageIndex(pageBarSize, currentPage, totalPage);
	}

	public int getMaxresult() {
		return maxresult;
	}

	public void setMaxresult(int maxresult) {
		this.maxresult = maxresult;
	}
}
