package com.hydom.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.hydom.util.bean.AdminBean;
import com.hydom.util.bean.MemberBean;

/**
 * Controller - 基类
 * 
 * 
 * 
 */
public class BaseAction {

	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "/admin/common/success";			// 成功页面
	public static final String ERROR = "/admin/common/error";				// 错误页面
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static String REDIRECT_URL = "redirectionUrl"; // 跳转页面
	
	/** 错误视图 */
	protected static final String ERROR_VIEW = "/admin/common/error";
	/** 成功视图 */
	protected static final String SUCCESS_VIEW = "/admin/common/success";
	
	/** "验证结果"参数名称 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	/** 登录保存SESSION名称 */
	public static final String USER_SESSION = "user_session";
	
	/** 文件路径 */
	private String url;

	@PersistenceContext
	protected EntityManager entityManager;
	
	public CriteriaBuilder getCriteria(){
		return entityManager.getCriteriaBuilder();
	}
	
	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}
	
	/**
	 * 异常控制
	 * @param runtimeException
	 * @return
	 * @throws ServletException 
	 * @throws IOException 
	 */
	@ExceptionHandler(Exception.class)
	public void runtimeExceptionHandler(HttpServletResponse response,HttpServletRequest request,Exception ex) throws ServletException, IOException {
		
		String path = request.getRequestURI();
		System.out.println(path);
		ex.printStackTrace();
		if(path.contains("/cbms/manage")){
			request.setAttribute("message", "服务器异常！");
			request.getRequestDispatcher("/WEB-INF/page/common/notfound.jsp").forward(request, response);
		}
		
	}
	
	/**
	 * 获取用户登录者信息
	 * @return
	 */
	protected MemberBean getMemberBean(HttpServletRequest request){
		return (MemberBean)request.getSession().getAttribute(MemberBean.MEMBER_SESSION);
	}
	
	
	protected void removeMemberBean(HttpServletRequest request,String session){
		request.getSession().removeAttribute(session);
	}
	

	/**
	 * 获取后台用户登录者信息
	 * @return
	 */
	protected AdminBean getAdminBean(HttpServletRequest request){
		return (AdminBean)request.getSession().getAttribute(AdminBean.ADMIN_SESSION);
	}
	
	protected void removeAdminBean(HttpServletRequest request,String session){
		request.getSession().removeAttribute(session);
	}
	

	// AJAX输出，返回null
	public String ajax(String content, String type, HttpServletResponse response) {
		try {
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

	// AJAX输出文本，返回null
	public String ajaxText(String text, HttpServletResponse response) {
		return ajax(text, "text/plain", response);
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml, HttpServletResponse response) {
		return ajax(xml, "text/xml", response);
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString, HttpServletResponse response) {
		return ajax(jsonString, "text/html", response);
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap, HttpServletResponse response) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message, HttpServletResponse response) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html", response);
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxSuccess(Object message, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, "success");
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json", response);
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxError(Object message, HttpServletResponse response) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, "error");
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "application/json", response);
	}
	
	
	public String getRoot(HttpServletRequest request){
		return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath() + "/";
	}

	/**
	 * 通用文件下载方法
	 */
	public String download(String url, HttpServletRequest request, HttpServletResponse response) {
//		if(StringUtils.isEmpty(url)){
//			addActionError("链接无效或不存在.");
//			return ERROR;
//		}
		try {
			url = new String(url.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
		try {
			response.setContentType("application/x-download; charset=utf-8");
			response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
			
			ServletOutputStream outStream = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(outStream);
			
			String realPath = request.getServletContext().getRealPath(url);
			File srcFile = new File(realPath);
			FileInputStream stream = new FileInputStream(srcFile);
			
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
	
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			outStream.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for" );  
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP" );  
		}  
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP" );  
		}  
		if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
		}
	 	return ip;
	}
}