package com.hydom.parse.json;

import java.util.Date;

public class JsonVideo {
	private String id; // 视频文件ID
	private String name; // 视频名称
	private Boolean islive;// 是否正在直播
	private Date createDate; // 创建日期

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getIslive() {
		return islive;
	}

	public void setIslive(Boolean islive) {
		this.islive = islive;
	}

}
