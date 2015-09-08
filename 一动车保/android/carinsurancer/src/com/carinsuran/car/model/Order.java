package com.carinsuran.car.model;

public class Order {
       private String result;
	   private String orderId;
	   private String contact;
	   private String phone;
	   private String car;
	   private String carNum;
	   private String carColor;
	   private Integer cleanType;
	   private Double mlng;
	   private Double mlat;
	   private Boolean hasOrder;
	   private Boolean jobStatus;
	   private Double distance;
	   private String orderNum;
	   private Integer  stats;
	   
	public Integer getStats() {
		return stats;
	}
	public void setStats(Integer stats) {
		this.stats = stats;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Boolean getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(Boolean jobStatus) {
		this.jobStatus = jobStatus;
	}
	@Override
	public String toString() {
		return "Order [result=" + result + ", orderId=" + orderId
				+ ", contact=" + contact + ", phone=" + phone + ", car=" + car
				+ ", carNum=" + carNum + ", carColor=" + carColor
				+ ", cleanType=" + cleanType + ", mlng=" + mlng + ", mlat="
				+ mlat + ", hasOrder=" + hasOrder + "]";
	}
	public Boolean getHasOrder() {
		return hasOrder;
	}
	public void setHasOrder(Boolean hasOrder) {
		this.hasOrder = hasOrder;
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
	
	
	   
	   
	
}
