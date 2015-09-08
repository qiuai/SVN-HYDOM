package com.test;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.hydom.common.service.UserService;
import com.hydom.vt.api.ApiCommonController;

/**
 * @Description:功能测试
 * @author WY
 * @date 2015年6月2日 下午3:10:15
 */

public class TestWY extends BaseTestCase{

	@Resource
	private UserService memberService;
	
	/** 关注老师测试 */
	@org.junit.Test
	public void focusTeaTest(){
		JSONObject json = new JSONObject();
		json.put("type", "208");
		json.put("id", "D1F412C0268C4D8D84B4");
		System.out.println("\n关注老师");
		System.out.println(json);
		
	}
	
	/** 登录测试 */
	@org.junit.Test
	public void loginTest(){
		JSONObject json = new JSONObject();
		json.put("type", "201");
		json.put("username", "zs");
		json.put("password", "123");
		System.out.println("\n登录");
		System.out.println(json);
	}
	
	/** 注册测试 */
	@org.junit.Test
	public void registerTest(){
		JSONObject json = new JSONObject();
		json.put("type", "202");
		json.put("username", "sll");
		json.put("password", "123");
		json.put("nickname", "孙老师");
		json.put("isteacher", "1");
		json.put("sex", "1");
		System.out.println("\n注册");
		System.out.println(json);
	}

	/** 修改用户信息 */
	@org.junit.Test
	public void editUserInfo(){
		JSONObject json = new JSONObject();
		json.put("type", 223);
		json.put("id", 3);
		json.put("tel", "13823452456");
		json.put("city", "都江堰");
		json.put("email", "321@123.com");
		json.put("nickname", "");
		System.out.println("\n修改用户信息");
		System.out.println(json);
	}
}
