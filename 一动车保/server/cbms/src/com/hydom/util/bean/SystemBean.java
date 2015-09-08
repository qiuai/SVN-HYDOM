package com.hydom.util.bean;

import java.io.Serializable;

import com.hydom.account.ebean.Account;

/**
 * 后台用户登录bean
 * @author Administrator
 *
 */
public class SystemBean implements Serializable{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2905280281273059940L;
	
	private String startDate;
	private String endDate;
	private String content;
	private String version;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
