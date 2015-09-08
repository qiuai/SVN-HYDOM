package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class MyCarInfosGroupModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String id;
    private String result;
    private List<MyCarInfosModel> list;
    
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
	public List<MyCarInfosModel> getList() {
		return list;
	}
	public void setList(List<MyCarInfosModel> list) {
		this.list = list;
	}
    
}
