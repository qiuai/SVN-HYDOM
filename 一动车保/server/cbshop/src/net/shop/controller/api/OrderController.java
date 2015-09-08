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
 * api - 订单
 * 
 * 
 * 
 */
@Controller("orderApi")
@RequestMapping("/api/order")
public class OrderController extends BaseController{
	

	/**
	 * 获取订单列表
	 * @param response
	 * @param request 
	 * @param id 当前用户ID
	 * @param type 订单类型  0 完结订单 1进行中订单 2 未支付订单 3退费订单
	 * @param pageNumber 当前页码
	 */
	@RequestMapping("/getOrders")
	public void getOrders(HttpServletResponse response,HttpServletRequest request,Long id,String type,String pageNumber){
		JSONObject jsonObject = new JSONObject();
		
		
		JSONArray array = new JSONArray();
		for(int i = 0; i < 2; i++ ){
			JSONObject obj = new JSONObject();
			obj.put("orderId", "1");
			obj.put("orderSn", "订单编号");
			obj.put("number", "数量");
			obj.put("total", "总计金额");
			obj.put("type", "1 实物订单 2服务订单");
			array.add(obj);
		}
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, array);
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	
	/**
	 * 订单详情
	 * @param id 订单ID
	 * @param response
	 */
	@RequestMapping("/getOrderDetail")
	public void getOrderDetail(Long id,HttpServletResponse response){
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
}