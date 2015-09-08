package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class BaiduWeatheritem1Model implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String currentCity;
	private String pm25;
	private List<BaiduWeatheritem1_1> index;
	
	private List<BaiduWeatheritem1_2> weather_data;
	public List<BaiduWeatheritem1_2> getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(List<BaiduWeatheritem1_2> weather_data) {
		this.weather_data = weather_data;
	}
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public List<BaiduWeatheritem1_1> getIndex() {
		return index;
	}
	public void setIndex(List<BaiduWeatheritem1_1> index) {
		this.index = index;
	}
	
	
}
