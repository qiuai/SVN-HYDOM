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
@Controller("couponApi")
@RequestMapping("/api/coupon")
public class CouponController extends BaseController{
	

	/**
	 * 获取订单列表
	 * @param response
	 * @param request 
	 * @param id 当前用户ID
	 * @param type 订单类型  0 完结订单 1进行中订单 2 未支付订单 3退费订单
	 * @param pageNumber 当前页码
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response,HttpServletRequest request,Long id,String type,String pageNumber){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		jsonObject.put(PAGENUMBER, "1");
		ajaxSuccess(jsonObject, response);
	}
	
	
}