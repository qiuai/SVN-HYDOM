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
 * Controller - 用户车辆信息
 * 
 * 
 * 
 */
@Controller("carApi")
@RequestMapping("/api/car")
public class CarController extends BaseController{
	
	/**
	 * 用户车辆列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	public void list(HttpServletRequest request,HttpServletResponse response,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 保存车辆     1是添加车的时候 carId是车型ID 
				2是用户编辑已有车辆是 carId是用户该车辆的ID
	 * @param request
	 * @param response
	 * @param id
	 * @param carId
	 * @param trip
	 * @param roadTime
	 * @param type
	 */
	@RequestMapping("/save")
	public void list(HttpServletRequest request,HttpServletResponse response,Long id,
			Long carId,String trip,String roadTime,String type){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 车辆信息详情
	 * @param request
	 * @param response
	 * @param id
	 * @param carId
	 */
	@RequestMapping("/detail")
	public void detail(HttpServletRequest request,HttpServletResponse response,Long id,
			Long carId){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 车辆信息删除
	 * @param request
	 * @param response
	 * @param id
	 * @param carId
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response,Long id,
			Long carId){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 设置默认车辆
	 * @param request
	 * @param response
	 * @param id
	 * @param carId
	 */
	@RequestMapping("/setDefault")
	public void setDefault(HttpServletRequest request,HttpServletResponse response,Long id,
			Long carId){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 选择品牌
	 * @param request
	 * @param response
	 */
	@RequestMapping("/chooseBrand")
	public void chooseBrand(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 选择车系
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/chooseCarSeries")
	public void chooseCarSeries(HttpServletRequest request,HttpServletResponse response,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
	
	/**
	 * 选择车型
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/chooseCar")
	public void chooseCar(HttpServletRequest request,HttpServletResponse response,Long id){
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put(CODE, SUCCESS);
		jsonObject.put(DATA, "");
		ajaxSuccess(jsonObject, response);
	}
}