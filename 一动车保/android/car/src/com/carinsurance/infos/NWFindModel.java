package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class NWFindModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String result;
	private String pages;
	
	private List<NWFindItemModel> list;

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

	public List<NWFindItemModel> getList() {
		return list;
	}

	public void setList(List<NWFindItemModel> list) {
		this.list = list;
	}
	
	
}
