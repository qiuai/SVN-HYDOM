package com.hydom.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hydom.vt.util.Constant;

/**
 * 用户登录验证.
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 对Controller设置request与response

		if (handler instanceof HandlerMethod) {

			HandlerMethod hm = (HandlerMethod) handler;
			Object controller = hm.getBean();
			if (controller instanceof AbstractController) {
				AbstractController bc = (AbstractController) hm.getBean();
				bc.setRequest(request);
				bc.setResponse(response);
			}

			// 请求的uri
			String uri = request.getRequestURI();
			Object obj = request.getSession().getAttribute(
					Constant.SESSION_USER);

			if (obj == null) {
					if (!uri.contains("login.do")) {
						if (uri.indexOf("/api/common.do") != -1) {// 接口
							String httpdata = (String) request
									.getParameter("httpdata");
							JSONObject data = JSONObject.fromObject(httpdata);
							if (data.getString("type")
									.equals(Constant.TYPE_201)
									|| data.getString("type").equals( // 登录和注册
											Constant.TYPE_202)) {
								return true;
							} else {
								String type = "text/html";
								JSONObject result = new JSONObject();
								result.put("type", Constant.TYPE_99);
								result.put("error", "登录失效");
								response.setContentType(type + ";charset=UTF-8");
								response.setHeader("Pragma", "No-cache");
								response.setHeader("Cache-Control", "no-cache");
								response.setDateHeader("Expires", 0);
								response.getWriter().write(result.toString());
								response.getWriter().flush();

								return false;
							}
						} else {// 网页
							response.sendRedirect("../user/login.do");
							return false;
						}
				}
			}
		}

		return true;
	}
}
