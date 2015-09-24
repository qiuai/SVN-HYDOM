package com.hydom.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class AlipayURLFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String path = request.getServletPath();
		
		if (isMatching(request.getQueryString())) {
			System.out.println("已拦截一个非法请求！\n来源IP："+request.getRemoteAddr()+"\n路径："+path+"\n数据："+request.getQueryString());
			response.sendRedirect(servletRequest.getServletContext().getContextPath()+"errorPage.html");
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 是否包含违法字符><'"
	 * @param path
	 * @return
	 */
	private boolean isMatching(String path) {
		boolean flag = false;
		if(StringUtils.isEmpty(path)) return false;
		if (path.contains("%3C") || path.contains("%3E") || path.contains("%22") || path.contains("%27")) flag = true;
		return flag;
	}
}
