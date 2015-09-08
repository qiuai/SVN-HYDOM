package com.carinsuran.car.model;

import java.io.Serializable;

public class OrderBuss implements Serializable{

	   private String result;
	   private String ordernum;
	   private String orderId;
	   private String contact;
	   private String phone;
	   private String car;
	   private String carNum;
	   private String carColor;
	   private Integer cleanType;
	   private Double mlng;
	   private Double mlat;
	   private String startDate;
	   private Double distance;
//	   Afterimgs afterimgs;
//	   beforeImgs beforeimage;
	   private String imageUrl1;
	   private String imageUrl2;
	   private String imageUrl3;
	   private String imageUrl4;
	   private String imageUrl5;
	   private String imageUrl6;
	   
	   
	
	public String getImageUrl1() {
		return imageUrl1;
	}
	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}
	public String getImageUrl2() {
		return imageUrl2;
	}
	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}
	public String getImageUrl3() {
		return imageUrl3;
	}
	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}
	public String getImageUrl4() {
		return imageUrl4;
	}
	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}
	public String getImageUrl5() {
		return imageUrl5;
	}
	public void setImageUrl5(String imageUrl5) {
		this.imageUrl5 = imageUrl5;
	}
	public String getImageUrl6() {
		return imageUrl6;
	}
	public void setImageUrl6(String imageUrl6) {
		this.imageUrl6 = imageUrl6;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	   
}
