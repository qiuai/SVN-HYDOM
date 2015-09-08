package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class ProductDefaultModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	private String pages;
	private String result;

	List<ProductDefaultItemModel> list;

	public String getPages() {
		return pages;
	}

	public void setPage(String pages) {
		this.pages = pages;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ProductDefaultItemModel> getList() {
		return list;
	}

	public void setList(List<ProductDefaultItemModel> list) {
		this.list = list;
	}

}