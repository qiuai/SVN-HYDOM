package com.hydom.common.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * 用户信息 - bean类
 * @author Holen
 * @version 1.0.0 2014-12-05 新建
 */
@Getter
@Setter
public class UserInfo implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2821647076532414467L;
	
	/** Session存放时KEY. */
	public final static String SESSION_USER = "session_user";

	/** ID. */
	private String id;
	
	/** 工作编号. */
	private String workcode;
	
	/** 用户名称. */
	private String name;
	
	/** 头像. */
	private String header;
	
}
