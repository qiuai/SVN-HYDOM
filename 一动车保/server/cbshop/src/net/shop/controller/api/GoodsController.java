/*
 * 
 * 
 * 
 */
package net.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * api - 商品
 * 
 * 
 * 
 */
@Controller("goodsApi")
@RequestMapping("/api/goods")
public class GoodsController extends BaseController{
	

	/**
	 * 商品分类列表
	 * @param response
	 * @param request
	 * @param id
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response,HttpServletRequest request,Long id,String type,String pageNumber){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 商品列表
	 * @param response
	 * @param request
	 * @param id
	 * @param pageNumber
	 */
	@RequestMapping("/getGoodsList")
	public void getGoodsList(HttpServletResponse response,HttpServletRequest request,Long id,String pageNumber){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER,"1");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 商品详情
	 * @param response
	 * @param request
	 * @param id
	 */
	@RequestMapping("/getGoodsDetail")
	public void getGoodsDetail(HttpServletResponse response,HttpServletRequest request,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
}