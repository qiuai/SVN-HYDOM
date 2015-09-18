package com.hydom.core.ebean;

import java.util.Date;

public class Room {
	private String id;

	/** 课程名称 **/
	private String name;

	/** 开始时间 **/
	private Date startDate;

	/** 房间密码 **/
	private String password;

	/** 最多人数 **/
	private Integer maxStuNum;

	/** 收费？？？ **/
	private Float money;

	/** 房间总收入 **/
	private Float countMoney;

	/** 房间模式 **/
	private Integer types;

	/** 备注 **/
	private String remark;

	/** 主要内容（知识点） **/
	private String mainContent;

	/** 地址 **/
	private String url;

	/** 是否热门 **/
	private Boolean isHot = false;

	/** 课程背景图片 **/
	private String image;

	/** 观看人数 */
	private Integer number = 0;

	/** 所属用户ID **/
	private String userId;

	/** 当前直播视频流名 **/
	private String nowFlow;

	/** 状态 1正在上课 0未上课 **/
	private Integer status = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxStuNum() {
		return maxStuNum;
	}

	public void setMaxStuNum(Integer maxStuNum) {
		this.maxStuNum = maxStuNum;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Float getCountMoney() {
		return countMoney;
	}

	public void setCountMoney(Float countMoney) {
		this.countMoney = countMoney;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNowFlow() {
		return nowFlow;
	}

	public void setNowFlow(String nowFlow) {
		this.nowFlow = nowFlow;
	}

}
