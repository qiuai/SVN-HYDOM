package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class MyOrderModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String result;
	private String pages;
	List<OrderModel> list;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public List<OrderModel> getList() {
		return list;
	}

	public void setList(List<OrderModel> list) {
		this.list = list;
	}

}
