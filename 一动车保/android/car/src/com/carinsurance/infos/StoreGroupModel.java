package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class StoreGroupModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private String pages;// 页数
	private List<StoreChildModel> list;
	
	
	@Override
	public String toString() {
		return "StoreModel [id=" + id + ", result=" + result + ", pages=" + pages + ", list=" + list + "]";
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
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public List<StoreChildModel> getList() {
		return list;
	}
	public void setList(List<StoreChildModel> list) {
		this.list = list;
	}
    
}