/*
 * 
 * 
 * 
 */
package net.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * api - 优惠券
 * 
 * 
 * 
 */
@Controller("servicesApi")
@RequestMapping("/api/services")
public class ServicesController extends BaseController{
	

	/**
	 * 获取顶置服务 热门服务 普通服务
	 * @param response
	 * @param request
	 */
	@RequestMapping("/getServices")
	public void getServices(HttpServletResponse response,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 获取顶置服务 热门服务 普通服务
	 * @param response
	 * @param request
	 */
	@RequestMapping("/getMoreServices")
	public void getMoreServices(HttpServletResponse response,HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	
	
}