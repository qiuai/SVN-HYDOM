package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class CarXModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<CarXitemModel> list;
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
	public List<CarXitemModel> getList() {
		return list;
	}
	public void setList(List<CarXitemModel> list) {
		this.list = list;
	}

	
	
}
