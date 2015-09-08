package com.hydom.common.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * ��ҳ - bean��
 * @author Holen
 * @version 1.0.0 2013-8-5 �½�
 */
public class Pager implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 7343703255664740446L;

	// ����ʽ
	public enum OrderType{
		asc, desc
	}
	
	public static final Integer MAX_PAGE_SIZE = 500;// ÿҳ����¼������

	private Integer pageNumber = 1;// ��ǰҳ��
	private Integer pageSize = 15;// ÿҳ��¼��
	private Integer totalCount = 0;// �ܼ�¼��
	private Integer pageCount = 0;// ��ҳ��
	private String property;// �����������
	private String keyword;// ���ҹؼ���
	private String orderBy = "createDate";// �����ֶ�
	private OrderType orderType = OrderType.desc;// ����ʽ
	private List<?> list;// ���List

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}