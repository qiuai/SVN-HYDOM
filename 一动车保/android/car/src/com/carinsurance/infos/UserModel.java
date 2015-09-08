package com.carinsurance.infos;

import java.io.Serializable;

public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickname;
	private String photo;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
