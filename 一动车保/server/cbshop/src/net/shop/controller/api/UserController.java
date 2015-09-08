/*
 * 
 * 
 * 
 */
package net.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller - 基类
 * 
 * 
 * 
 */
@Controller("userApi")
@RequestMapping("/api/user")
public class UserController extends BaseController{
	
	/**
	 * 用户登录
	 * @param response
	 * @param request
	 * @param username
	 * @param pwd
	 */
	@RequestMapping("/login")
	public void login(HttpServletResponse response,HttpServletRequest request,String username,String pwd){
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	
	/**
	 * 电话号码注册
	 * @param mobile 电话号码
	 * @param code 注册码
	 * @param response
	 */
	@RequestMapping("/register")
	public void register(String mobile,String code,HttpServletResponse response){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 注册 获取验证码
	 * @param mobile 手机
	 * @param code 验证码
	 * @param response
	 */
	@RequestMapping("/register_code")
	public void register_code(String mobile,String code,HttpServletResponse response){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 意见反馈
	 */
	@RequestMapping("/addOpinion")
	public void addOpinion(Long id, String remark, HttpServletResponse response){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 消息
	 */
	@RequestMapping("/view")
	public void view(Long id, String type, HttpServletResponse response){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
}