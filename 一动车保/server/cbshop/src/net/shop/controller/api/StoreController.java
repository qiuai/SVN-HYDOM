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
@Controller("storeApi")
@RequestMapping("/api/store")
public class StoreController extends BaseController{
	

	/**
	 * 门店列表
	 * @param response
	 * @param request
	 * @param pageNubmer
	 * @param areaId
	 * @param orderType
	 * @param storeType
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response,HttpServletRequest request,String pageNubmer,Long areaId,String orderType,String storeType){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 门店详情
	 * @param response
	 * @param request
	 * @param id 门店ID
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
	 * 门店 查看更多评论
	 */
	@RequestMapping("/getMore")
	public void getMore(HttpServletResponse response,HttpServletRequest request,Long id,String pageNumber){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 门店预约
	 */
	@RequestMapping("/bespeak")
	public void bespeak(HttpServletResponse response,HttpServletRequest request,Long id,Long carId,String name,String phone){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	
	
}