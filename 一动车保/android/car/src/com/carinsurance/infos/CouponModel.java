package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class CouponModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String score;//用户当前积分
	private String result;
	private String pages;
	List<CouponItemModel> list;
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
	public List<CouponItemModel> getList() {
		return list;
	}
	public void setList(List<CouponItemModel> list) {
		this.list = list;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	
	
	
	
	
	
}