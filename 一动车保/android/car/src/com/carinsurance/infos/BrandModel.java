package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class BrandModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<BrandItemModel> list;
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
	public List<BrandItemModel> getList() {
		return list;
	}
	public void setList(List<BrandItemModel> list) {
		this.list = list;
	}

	
	
    
}