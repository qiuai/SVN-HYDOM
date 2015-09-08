package com.hydom.util.sfl;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hydom.api.service.TokenService;

@Component
public class ApiInteceptor extends HandlerInterceptorAdapter {
	private Log log = LogFactory.getLog("apiURILog");
	@Resource
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String, String[]> kvMap = request.getParameterMap();
		StringBuffer param = new StringBuffer();
		String uid = "";
		String authId = "";
		for (String key : kvMap.keySet()) {
			String[] values = kvMap.get(key);
			for (String value : values) {
				if (param.length() == 0) {
					param.append("?" + key + "=" + value);
				} else {
					param.append("&" + key + "=" + value);
				}
			}
			if (key.equals("uid")) {
				uid = values[0];
			}
			if (key.equals("token")) {
				authId = values[0];
			}
		}
		log.info("【API原始请求数据：】" + request.getRequestURI() + param);

		if (request.getRequestURI().contains("/api/user/")
				&& !request.getRequestURI().contains("/api/user/signin")) { // 对用户操作数据(排除登录)进行拦截响应
			try {
				boolean validToken = tokenService.findToken(uid, authId) != null ? true
						: false;
				if (!validToken) {// token失效
					PrintWriter out = response.getWriter();
					out.print("{\"result\":\"102\"}");
					return false;
				}
			} catch (Exception e) {
				PrintWriter out = response.getWriter();
				out.print("{\"result\":\"102\"}");
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
}
