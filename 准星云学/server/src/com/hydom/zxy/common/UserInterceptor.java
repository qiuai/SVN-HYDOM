package com.hydom.zxy.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.UserInfo;

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
	        	AbstractController bc = (AbstractController)hm.getBean();
	        	bc.setRequest(request);
	        	bc.setResponse(response);
	    	}
	    	
	    	// 请求的uri
	        String uri = request.getRequestURI();
	     // uri中包含background时才进行过滤
	        if (uri.indexOf("/api/") != -1 && uri.contains(".do")) {
	        	return true;
	        }
//	    	// 若是登录Controller则不进行验证是否登录
//	    	if (controller instanceof LoginController) {
//	    		return true;
//	    	}
	
	    	// 验证是否登录
	    	Object obj = request.getSession().getAttribute(UserInfo.SESSION_USER);
	    	if (null == obj) {
	    		response.sendRedirect("user/tologin.do");
	    		return false;
	    	}
    	}

		return true;
    }
    
}
