package com.hydom.core.server.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.Technician;
import com.hydom.account.service.AreaService;
import com.hydom.account.service.TechnicianService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.core.server.service.CouponService;
import com.hydom.core.server.service.FirstSpendSendCouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

/**
 * @Description 电子地图控制层
 * @author WY
 * @date 2015年9月7日 上午9:18:35
 */

@RequestMapping("/manage/electronicMap")
@Controller
public class ElectronicMapAction extends BaseAction {

	@Resource
	private TechnicianService technicianService;
	@Resource
	private AreaService areaService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(String queryContent) {
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1");
		Object[] params = new Object[] {};
		if (queryContent != null) {
			jpql.append(" and o.name like ?1 or o.phonenumber = ?2");
			params = new Object[] { "%" + queryContent + "%", queryContent};
		}
		ModelAndView mav = new ModelAndView("/electronicMap/electronicMap_list");
		mav.addObject("tech", technicianService.getScrollData(-1, -1, jpql.toString(), params, orderby).getResultList());
		
		orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		jpql = new StringBuffer("o.parent is null and o.visible = 1");
		List<Area> areas = areaService.getList(jpql.toString(), null, orderby);
		mav.addObject("rootArea", areas);
		
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 10);
		return mav;
	}
	
	/**
	 * 返回技师
	 */
	@RequestMapping("/getTech")
	public @ResponseBody
	JSONArray getTech(@RequestParam String queryContent) {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		StringBuffer jpql = new StringBuffer("o.visible = 1 and o.jobstatus is true");
		Object[] params = new Object[] {};
		if (StringUtils.isNotEmpty(queryContent)) {
			jpql.append(" and (o.name like ?1 or o.phonenumber like ?1)");
			params = new Object[] { "%" + queryContent + "%"};
		}
		List<Technician> l=technicianService.getScrollData(-1, -1, jpql.toString(), params, orderby).getResultList();
		JSONArray jsonArr = new JSONArray();
		JSONObject json = new JSONObject();
		for(Technician t : l){
			json.put("name", t.getName());
			json.put("phonenumber", t.getPhonenumber());
			json.put("lat", t.getLatitude());
			json.put("lng", t.getLongitude());
			json.put("area", t.getArea());
			jsonArr.add(json);
			json.clear();
		}
		return jsonArr;
	}
	
	/**
	 * 根据ID 获取 子地区
	 */
	@RequestMapping("/findAreaChildren")
	@ResponseBody
	public JSONArray findAreaChildren(String rootAreaId){
		
		Area entity = areaService.find(rootAreaId);
		Set<Area> areas = entity.getChildren();
		
		JSONArray jsonArray = new JSONArray();
		
		for(Area area : areas){
			JSONObject obj = new JSONObject();
			obj.put("areaId", area.getId());
			obj.put("areaName", area.getName());
			obj.put("hasNext", area.getChildren().size() > 0 ? "hasNext" : "");
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
}
