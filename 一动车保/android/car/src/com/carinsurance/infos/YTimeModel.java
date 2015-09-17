package com.carinsurance.infos;

import java.io.Serializable;

public class YTimeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YTimeModel() {

	}

	public YTimeModel(String stime, String etime) {
		// super();
		this.stime = stime;
		this.etime = etime;
	}

	public YTimeModel(String yuyuetime) {
		// super();
		this.yuyueyear_month_day = yuyuetime;
	}

	private String stime;// 开始时间
	private String etime;// 结束时间

	private String yuyueyear_month_day;// 预约的年月日

	public String getYuyueyear_month_day() {
		return yuyueyear_month_day;
	}

	public void setYuyueyear_month_day(String yuyueyear_month_day) {
		this.yuyueyear_month_day = yuyueyear_month_day;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

}
