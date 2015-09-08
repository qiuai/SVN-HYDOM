package com.hydom.util.sfl;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hydom.account.ebean.Technician;
import com.hydom.account.service.TechnicianService;

/**
 * @Description 技师App拦截器
 * @author WY
 * @date 2015年8月31日 下午12:09:04
 */

@Component
public class TechInteceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private TechnicianService technicianService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			if(request.getRequestURI().contains("/api/technician/") 
					&& !request.getRequestURI().contains("/api/technician/login")
					&& !request.getRequestURI().contains("/api/technician/getDate")){
				Technician tech = null;
				if(request.getParameter("techId")!=null){
					tech = technicianService.find(request.getParameter("techId"));
				}
				if(tech==null || tech.getVisible()==false){
					PrintWriter out = response.getWriter();
					out.print("{\"result\":\"1004\"}");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("账号已被删除");
			PrintWriter out = response.getWriter();
			out.print("{\"result\":\"1004\"}");
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
