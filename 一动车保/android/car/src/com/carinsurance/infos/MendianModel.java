package com.carinsurance.infos;

import java.io.Serializable;
//“img”:”门店图片”,
//“address” : ”地址”，
//“distance”:”距离”,
//“id”:”门店ID”,
//“score”:“门店评分”,
//“amount” : ”订单数量”
public class MendianModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String img;
	private String address;
	private String distance;
	private String id;
	private String score;
	private String amount;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
