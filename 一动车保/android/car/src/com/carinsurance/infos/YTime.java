package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class YTime implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String id;
	private String result;
	private List<YTimeModel> list;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public List<YTimeModel> getList() {
		return list;
	}
	public void setList(List<YTimeModel> list) {
		this.list = list;
	}
	
	
	
	
}