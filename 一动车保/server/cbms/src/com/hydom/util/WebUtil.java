package com.hydom.util;

import javax.servlet.http.HttpServletRequest;

import com.hydom.util.bean.AdminBean;

public class WebUtil {

	/**
	 * 获取当前登录后台帐户相关数据
	 * 
	 * @param request
	 * @return
	 */
	public static AdminBean getlogonAccount(HttpServletRequest request) {
		return (AdminBean) request.getSession().getAttribute(
				AdminBean.ADMIN_SESSION);
	}

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
