package com.hydom.dao;

/**
 * 
 * @author www.hydom.cn [heou]
 * 
 */
public class PageIndex {
	private long startindex;
	private long endindex;

	public PageIndex(long startindex, long endindex) {
		this.startindex = startindex;
		this.endindex = endindex;
	}

	public long getStartindex() {
		return startindex;
	}

	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}

	public long getEndindex() {
		return endindex;
	}

	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}

	/**
	 * 分页条上的索引
	 * 
	 * @param pageBarSize
	 *            :分页条上显示的页码数
	 * @param currentPage
	 *            :当前页码
	 * @param totalpage
	 *            :总页数
	 * @return
	 */
	public static PageIndex getPageIndex(long pageBarSize, int currentPage,
			long totalPage) {
		long startPage = currentPage
				- (pageBarSize % 2 == 0 ? pageBarSize / 2 - 1 : pageBarSize / 2);
		long endPage = currentPage + pageBarSize / 2;
		if (startPage < 3) {
			startPage = 3;
			endPage = pageBarSize;
		}
		if (endPage > totalPage - 2) {
			endPage = totalPage - 2;
			startPage = endPage - pageBarSize + 1;
			if (startPage < 3) {
				startPage = 3;
			}
		}
		return new PageIndex(startPage, endPage);
	}
}
