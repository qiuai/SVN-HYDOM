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
@Controller("viewApi")
@RequestMapping("/api/views")
public class ViewController extends BaseController{
	

	/**
	 * 资讯列表
	 * @param response
	 * @param request
	 * @param pageNubmer
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response,HttpServletRequest request,String pageNubmer){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 资讯详情
	 * @param response
	 * @param request
	 * @param id 资讯ID
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletResponse response,HttpServletRequest request,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 资讯 喜欢
	 */
	@RequestMapping("/likeView")
	public void likeView(HttpServletResponse response,HttpServletRequest request,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
}