package com.hydom.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONObject;

/**
 * Web Controller基类.
 * 
 * @author Holen
 * @version 1.0.0 2014.11.24 创建
 */
public abstract class AbstractController implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8305105971106087065L;
	/** 查看详情. */
	protected final static String VIEW = "view";
	/** 跳转到新建/编辑页面. */
	protected final static String INPUT = "input";
	/** 跳转到列表页面. */
	protected final static String LIST = "list";
	/** 跳转到登录页面. */
	protected final static String LOGIN = "login";
	/** 统一的AJAX返回页面. */
	protected final static String AJAX = "ajax";

	/** 用于统一的状态返回. */
	protected final static String STATUS = "status";
	/** 成功. */
	protected final static String SUCCESS = "success";
	/** 统一的消息码返回 . */
	protected final static String MSG = "msg";
	/** 统一的成功状态码. */
	protected final static String CODE_SUCCESS = "0";
	/** 统一的失败状态码. */
	protected final static String CODE_FAILD = "-1";
	/** 返回的数据. */
	protected final static String DATA = "data";
	
	protected HttpServletRequest request;

	protected HttpServletResponse response;

	/**
	 * 获取请求的Session.
	 * 
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return request.getSession();
	}

	

	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			// HttpServletResponse response =
			// ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	public String ajaxJSONObject(JSONObject json) {
		return ajax(json.toString(), "text/html");
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}

	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, Object> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MSG, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json");
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, "-1");
		jsonMap.put(MSG, message);
		jsonMap.put("login", "yes");
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json");
	}



	public HttpServletRequest getRequest() {
		return request;
	}



	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



	public HttpServletResponse getResponse() {
		return response;
	}



	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 获取登录的用户信息.
	 */
	/*
	 * public UserInfo getUserInfo() { return (UserInfo)
	 * getSession().getAttribute(UserInfo.SESSION_USER); }
	 */
	/*@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}*/
	
}
