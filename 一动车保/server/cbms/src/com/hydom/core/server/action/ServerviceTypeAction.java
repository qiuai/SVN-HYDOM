package com.hydom.core.server.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.bean.DateMapBean;

/**
 * 安装服务类型
 * 
 * @author 李晓波
 *
 */
@RequestMapping("/web/serverType")
@Controller
public class ServerviceTypeAction extends BaseAction {
	@Resource
	private ServiceTypeService serviceTypeService;

	@Resource
	AreaService areaService;

/*	@Resource
	ServiceStoreService serviceStoreService;*/

	@Resource
	OrderService orderService;

	@RequestMapping("/installService")
	public ModelAndView installService(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/index/maintain3");
		String serviceTypeId = request.getParameter("id");
		ServiceType serviceType = serviceTypeService.find(serviceTypeId);

		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		String jpql = "parent is null and o.visible = true";
		List<Area> areas = areaService.getList(jpql, null, orderby);
		mav.addObject(serviceType);
		mav.addObject("areas", areas);

		return mav;
	}

	/**
	 * 根据ID 获取 子地区
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findArea")
	@ResponseBody
	public String findArea(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");

		Area entity = areaService.find(id);
		Set<Area> areas = entity.getChildren();

		JSONArray jsonArray = new JSONArray();

		for (Area area : areas) {
			JSONObject obj = new JSONObject();
			obj.put("id", area.getId());
			obj.put("text", area.getName());
			obj.put("hasNext", area.getChildren().size() > 0 ? "hasNext" : "");
			jsonArray.add(obj);
		}

		return ajaxSuccess(jsonArray, response);
	}

	/*@RequestMapping("/findServiceStore")
	public String findServiceStore(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			Area area = areaService.find(id);
			Set<ServiceStore> serviceStores = area.getServiceStoreSet();
			JSONArray jsonArray = new JSONArray();
			for (ServiceStore serviceStore : serviceStores) {
				JSONObject obj = new JSONObject();
				obj.put("id", serviceStore.getId());
				obj.put("name", serviceStore.getName());
				obj.put("address", serviceStore.getAddress());
				jsonArray.add(obj);
			}
			return ajaxSuccess(jsonArray, response);
		} else {
			return null;
		}
	}*/
	/**
	 * 获取时间段
	 * @param areaId
	 * @param date
	 * @param serviceTypeId
	 * @return
	 */
	@RequestMapping("/getTimeMap")
	@ResponseBody
	public String getTimeMap(String areaId,Date date,String serviceTypeId,HttpServletResponse response){
		List<DateMapBean> beans = orderService.getDateMapBean(areaId, new Date(), serviceTypeId);
		JSONArray array = new JSONArray();
		for(DateMapBean bean : beans){
			if(bean.getCarTeamCount() > 0){//车队服务数量大于0时  返回该时间段
				JSONObject obj = new JSONObject();
				obj.put("date", bean.getMapDate());
				obj.put("dateMap", bean.getMap());
				obj.put("count", bean.getCarTeamCount());
				array.add(obj);
			}
		}
		return ajaxSuccess(array, response);
	}

	@RequestMapping("/saveOrder")
	public ModelAndView saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String areaName = request.getParameter("areaName");
		String serviceStoreId = request.getParameter("serviceStore.id");
		String serverWay = request.getParameter("serverWay");
		String registdate = request.getParameter("registdate");
		String timeMap = request.getParameter("timeMap");
		String userName = request.getParameter("user");
		String phone = request.getParameter("tel");
		String address = request.getParameter("address");
		String payWay = request.getParameter("pay");

		Order order = new Order();
		//ServiceStore serviceStore = serviceStoreService.find(serviceStoreId);

		String[] timeArr = timeMap.split(",");
		String startDateStr = registdate + " " + timeArr[0];
		String endDateStr = registdate + " " + timeArr[1];

		Date startDate = DateTimeHelper.parseToDate(startDateStr, DateTimeHelper.FMT_yyyyMMddHHmm);
		Date endDate = DateTimeHelper.parseToDate(endDateStr, DateTimeHelper.FMT_yyyyMMddHHmm);

		//order.setArea_name(areaName);
		//order.setServiceStore(serviceStore);
		order.setServerWay(Integer.parseInt(serverWay));
		order.setStartDate(startDate);
		order.setEndDate(endDate);
		order.setContact(userName);
		order.setPhone(phone);
		order.setAddress(address);
		order.setPayWay(Integer.parseInt(payWay));
		
		orderService.save(order);

		ModelAndView mav = new ModelAndView("/index/wash");
		return mav;
	}

}
