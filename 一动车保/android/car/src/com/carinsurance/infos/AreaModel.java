package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class AreaModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<AreaItemModel> list;

	public List<AreaItemModel> getList() {
		return list;
	}
	public void setList(List<AreaItemModel> list) {
		this.list = list;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	
}