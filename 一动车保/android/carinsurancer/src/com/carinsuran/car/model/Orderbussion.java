package com.carinsuran.car.model;

public class Orderbussion {

	   private String result;
	   private String orderId;
	   private String ordernum;
	   private Integer cleanType;
	   private String startDate;
	   private Integer orderState;
	   
	   
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
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
	public Integer getCleanType() {
		return cleanType;
	}
	public void setCleanType(Integer cleanType) {
		this.cleanType = cleanType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	   
	   
	
}
