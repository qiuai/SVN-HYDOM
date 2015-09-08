package com.hydom.common;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import lombok.Getter;
import lombok.Setter;

import com.hydom.common.bean.UserInfo;

/**
 * Web Controller基类.
 * 
 * @author Holen
 * @version 1.0.0 2014.11.24 创建
 */
public abstract class AbstractController implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6420632244186657154L;

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

	/** 客户端返回JSON */
	protected JSONObject json;
	/** 返回客户端结果 0:成功 1:失败 */
	protected String CODE = "code";
	/** 返回客户端错误消息 */
	protected String MESSAGE = "message";
	/** 成功 */
	protected Integer CLIENT_SUCCESS = 0;
	/** 失败 */
	protected Integer CLIENT_ERROR = -1;
	
	@Getter
	@Setter
	protected HttpServletRequest request;
	@Getter
	@Setter
	protected HttpServletResponse response;

	/**
	 * 获取请求的Session.
	 * 
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return request.getSession();
	}

	// 数据库操作日志.
	/** 操作类型:增、删、改. */
	protected String operationtype;
	/** 操作表. */
	protected String operationtable;
	/** 操作字段. */
	protected String operationfield;
	/** 记录的ID. */
	protected String recordpkid;
	/** 原值. */
	protected String oldvalue;
	/** 新值. */
	protected String newvalue;

	/** 操作内容. */
	protected String operationtext;
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

	/**
	 * 获取登录的用户信息.
	 */
	public UserInfo getUserInfo() {
		return (UserInfo) getSession().getAttribute(UserInfo.SESSION_USER);
	}
	
	public String getRoot(){
		return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath() + "/";
	}
	/**
	 * 客户端发送成功信息
	 */
	public void sendSuccess(JSONObject json){
		try {
			json.put(CODE, CLIENT_SUCCESS);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 客户端发送错误信息
	 */
	public void sendError(String message){
		try {
			json = new JSONObject();
			json.put(CODE, CLIENT_ERROR);
			json.put(MESSAGE, message);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
