package com.carinsurance.infos;

import java.io.Serializable;

public class YTimeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YTimeModel()
	{
		
	}
	
	public YTimeModel(String stime, String etime) {
//		super();
		this.stime = stime;
		this.etime = etime;
	}

	private String stime;// 开始时间
	private String etime;// 结束时间

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
