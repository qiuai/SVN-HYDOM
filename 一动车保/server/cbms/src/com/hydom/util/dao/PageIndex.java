package com.hydom.util.dao;

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

	public static PageIndex getPageIndex(long pageBarSize, int currentPage, long totalPage) {
		long startPage = currentPage
				- (pageBarSize % 2 == 0 ? pageBarSize / 2 - 1 : pageBarSize / 2);
		long endPage = currentPage + pageBarSize / 2;
		if (startPage < 2) {
			startPage = 2;
			endPage = pageBarSize;
		}
		if (endPage > totalPage - 1) {
			endPage = totalPage - 1;
			startPage = endPage - pageBarSize + 1;
			if (startPage < 2) {
				startPage = 2;
			}
		}
		return new PageIndex(startPage, endPage);
	}
}
