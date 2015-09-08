package com.hydom.util.bean;

import java.util.Date;

import com.hydom.util.DateTimeHelper;

public class DateMapBean {
	
	/**
	 * 开始时间段
	 */
	private Date startDate;
	
	/**
	 * 结束时间段
	 */
	private Date endDate;
	
	/**
	 * 能够服务的车队数量
	 */
	private Integer carTeamCount;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getCarTeamCount() {
		return carTeamCount;
	}

	public void setCarTeamCount(Integer carTeamCount) {
		this.carTeamCount = carTeamCount;
	}

	/**
	 * 获取时间间隔区间字符串
	 * @return
	 */
	public String getMapDate(){
		String startDateTime = DateTimeHelper.formatDateTimetoString(this.getStartDate(), "HH:mm");
		String endDateTime = DateTimeHelper.formatDateTimetoString(this.getEndDate(), "HH:mm");
		return startDateTime + " - " + endDateTime;
	}
	
	public String getMap(){
		String startDateTime = DateTimeHelper.formatDateTimetoString(this.getStartDate(), "HH:mm");
		String endDateTime = DateTimeHelper.formatDateTimetoString(this.getEndDate(), "HH:mm");
		return startDateTime + "," + endDateTime;
	}
}
