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
import javax.servlet.http.HttpSession;

import com.hydom.util.bean.MemberBean;

public class userFilter implements Filter {
	
	private static final String[] EXP_ACTIONS = new String[] {
		"/user/myCoupon/list"
	};
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String path = request.getServletPath();
		
		if (isException(path)) {
			HttpSession session = request.getSession();
			// 用户超时或没有登陆时跳转到登陆页面
			if (session.getAttribute(MemberBean.MEMBER_SESSION) == null || session.isNew()) {
				response.sendRedirect(servletRequest.getServletContext().getContextPath()+"/web/index");
				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	/**
	 * 检测是否需呀登录.
	 * @param path
	 * @return true[无需登录], false[需要登录]
	 */
	private boolean isException(String path) {
		boolean bFlg = false;
		
		for (String str : EXP_ACTIONS) {
			if (path.contains(str)) {
				bFlg = true;
			}
		}
		
		return true;
	}
}
