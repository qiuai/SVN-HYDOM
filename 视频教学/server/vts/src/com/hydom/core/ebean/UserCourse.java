package com.hydom.core.ebean;

import java.util.Date;

public class UserCourse {

	/** ID */
	protected String id;
	/** 创建时间 */
	protected Date createDate;
	/** 修改时间. */
	protected Date modifyDate;
	/** 0关注的课程 1观看的历史记录 2学生当前所在的房间 **/
	private Integer types = 0;
	/** 学生ID **/
	private String userId;
	/** 课程(房间ID) **/
	private String roomid;

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
