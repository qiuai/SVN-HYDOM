package com.hydom.core.ebean;

public class User {

	private String id;

	private String nickname;

	private Boolean isteacher;

	/** 麦克风是否可用：来源于RTMP请求 设置 **/
	private Boolean microphoneenable;

	/** 屏幕共享是否可用：来源于RTMP请求 设置 **/
	private Boolean screenshareenable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getIsteacher() {
		return isteacher;
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

	public void setIsteacher(Boolean isteacher) {
		this.isteacher = isteacher;
	}

}
