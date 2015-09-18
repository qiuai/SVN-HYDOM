package com.hydom.parse.json;

public class JsonUser {

	private String nickname;
	private String userid;
	private Boolean microphoneenable;// 麦克风是否可用
	private Boolean screenshareenable;// 屏幕共享是否可用

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Boolean getMicrophoneenable() {
		return microphoneenable;
	}

	public void setMicrophoneenable(Boolean microphoneenable) {
		this.microphoneenable = microphoneenable;
	}

	public Boolean getScreenshareenable() {
		return screenshareenable;
	}

	public void setScreenshareenable(Boolean screenshareenable) {
		this.screenshareenable = screenshareenable;
	}

}
