package com.hydom.core.ebean;

import java.util.Date;

/**
 * 视频列表
 * 
 * @author Administrator
 *
 */
public class Video {
	/** ID */
	private String id;

	/** 创建日期 **/
	private Date createDate;

	/** 房间ID **/
	private String roomId;

	/** 所属老师ID **/
	private String userId;

	/** 视频文件名称 **/
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
