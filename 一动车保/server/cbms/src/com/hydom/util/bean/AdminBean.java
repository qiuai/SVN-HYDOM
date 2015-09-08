package com.hydom.util.bean;

import java.io.Serializable;

import com.hydom.account.ebean.Account;

/**
 * 后台用户登录bean
 * @author Administrator
 *
 */
public class AdminBean implements Serializable{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6371135870848674762L;

	public static final String ADMIN_SESSION = "admin_session";
	
	private String username;
	private String nickname;
	private Account member;
	
	
	public static AdminBean convert2MemberBean(Account member){
		if(member != null){
			AdminBean bean = new AdminBean();
			bean.username = member.getUsername();
			bean.member = member;
			bean.nickname = member.getNickname(); 
			return bean;
		}
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Account getMember() {
		return member;
	}

	public void setMember(Account member) {
		this.member = member;
	}
	
}
