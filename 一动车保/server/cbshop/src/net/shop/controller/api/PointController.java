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
 * api - 积分
 */
@Controller("pointApi")
@RequestMapping("/api/point")
public class PointController extends BaseController{
	

	/**
	 * 积分
	 * @param response
	 * @param request
	 * @param id 用户ID
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response,HttpServletRequest request,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	
}