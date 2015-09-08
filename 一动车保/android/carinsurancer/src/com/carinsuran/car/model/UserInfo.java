package com.carinsuran.car.model;

import org.litepal.crud.DataSupport;

public class UserInfo extends DataSupport{

	private String techId;
	
	private String orderId;
	private String contact;
	   private String phone;
	   private String car;
	   private String carNum;
	   private String carColor;
	   private Integer cleanType;
	   private Double mlng;
	   private Double mlat;
	   private Integer state;
	   
	   

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public Integer getCleanType() {
		return cleanType;
	}

	public void setCleanType(Integer cleanType) {
		this.cleanType = cleanType;
	}

	public Double getMlng() {
		return mlng;
	}

	public void setMlng(Double mlng) {
		this.mlng = mlng;
	}

	public Double getMlat() {
		return mlat;
	}

	public void setMlat(Double mlat) {
		this.mlat = mlat;
	}

	public String getTechId() {
		return techId;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}
	
	
	
	
}
