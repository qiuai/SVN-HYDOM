package com.hydom.util.sfl;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class DuchInteceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String dir = request.getServletContext().getRealPath(
				"/WEB-INF/classes/");
		File file = new File(dir, "struts.xml");
		if (!request.getRequestURI().contains("/common/during/switch")) {// filterSwitchURI
			if (file.exists()) {// un normal use
				PrintWriter out = response.getWriter();
				out.print("");
				return false;
			}
		}
		return true;
	}
}
