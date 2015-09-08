package com.carinsurance.infos;

import java.io.Serializable;

public class LocationInfos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Longitude;//经度
	private String Latitude;//纬度
	private String addressName;//地址名称
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	
}
