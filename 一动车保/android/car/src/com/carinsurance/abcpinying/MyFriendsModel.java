package com.carinsurance.abcpinying;

import java.io.Serializable;

public class MyFriendsModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 用户id
	private String is_firend;
	private String motto;
	private String head;
	private String nickName;
	private String sortLetters; // 显示数据拼音的首字母
	private boolean judge;//是否选中

	//浏览俱乐部会员加的字段
	private String uid;

	
	@Override
	public String toString() {
		return "MyFriendsModel [id=" + id + ", is_firend=" + is_firend
				+ ", motto=" + motto + ", head=" + head + ", nickName="
				+ nickName + ", sortLetters=" + sortLetters + ", judge="
				+ judge + ", uid=" + uid + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_firend() {
		return is_firend;
	}

	public void setIs_firend(String is_firend) {
		this.is_firend = is_firend;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isJudge() {
		return judge;
	}

	public void setJudge(boolean judge) {
		this.judge = judge;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


	
}
