package com.hydom.core.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.dao.PageView;

/**
 * @Description:车系控制层
 * @author WY
 * @date 2015年7月1日 上午10:05:05
 */

@RequestMapping("/manage/carType")
@Controller
public class CarTypeAction extends BaseAction{
	
	private static String base = "/carType";
	private static Integer mark = 2;
	
	@Resource
	private CarTypeService carTypeService;
	@Resource
	private CarBrandService carBrandService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	
	/**
	 * 根据车辆品牌获取大车系
	 */
	@RequestMapping("/getCarType")
	@ResponseBody
	public String getCarType(String carBrandId) {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible = true and o.carBrand.id=?1 and o.level = 1";
		List<Object> params = new ArrayList<Object>();
		params.add(carBrandId);
		List<CarType> carTypes = carTypeService.getList(jpql, params.toArray(), orderby);
		JSONArray array = new JSONArray();
		for(CarType entity:carTypes){
			JSONObject obj = new JSONObject();
			obj.put("id", entity.getId());
			obj.put("name", entity.getName());//entity.getName()
			array.add(obj);
		}
		return ajaxSuccess(array, response);
	}
	
	/**
	 * 跳转添加页面
	 */
	@RequestMapping("/add")
	public String addUI(ModelMap model) {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("initial", "asc");
		String jpql = "o.visible = true";
		List<CarBrand> carBrands = carBrandService.getList(jpql, null, orderby);
		model.addAttribute("carBrands", carBrands);
		model.addAttribute("m", mark);
		
		return base + "/carType_add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(String name, String qp, String jp, String parentId, String carBrandId) {
		CarType carType = new CarType();
		carType.setName(name);
		carType.setQp(qp);
		carType.setJp(jp);
		if(parentId!=null){
			CarType parent = carTypeService.find(parentId);
			carType.setParent(parent);
			carType.setLevel(2);
		}else{
			carType.setParent(null);
			carType.setLevel(1);
		}
		carType.setCarBrand(carBrandService.find(carBrandId));
		carTypeService.save(carType);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value="/edit")
	public String editUI(ModelMap model,String id) {
		
		CarType carType = carTypeService.find(id);
		
		//品牌
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("initial", "asc");
		String jpql = "o.visible = true";
		List<CarBrand> carBrands = carBrandService.getList(jpql, null, orderby);
		model.addAttribute("carBrands", carBrands);
		model.addAttribute("carType", carType);
		
		//顶级分类
		if(carType.getParent() != null || carType.getLevel() != 1){
			orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			jpql = "o.visible = true and o.carBrand.id=?1 and o.level = 1";
			List<Object> params = new ArrayList<Object>();
			params.add(carType.getCarBrand().getId());
			List<CarType> carTypes = carTypeService.getList(jpql, params.toArray(), orderby);
			model.addAttribute("carTypes", carTypes);
		}
		
		model.addAttribute("m", mark);
		
		return base + "/carType_edit";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent,ModelMap model) {
		
		
		PageView<CarType> pageView = new PageView<CarType>(maxresult, page);
		
		pageView = carTypeService.getPage(pageView,queryContent);
	
		model.addAttribute("pageView", pageView);
		model.addAttribute("queryContent", queryContent);
		model.addAttribute("m", mark);
		
		return base + "/carType_list";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(String id, String name, String qp, String jp, String parentId, String carBrandId) {
		CarType entity = carTypeService.find(id);
		entity.setName(name);
		entity.setQp(qp);
		entity.setJp(jp);
		CarType parent = null;
		if(parentId==null){
			entity.setLevel(1);
		}else{
			parent = carTypeService.find(parentId);
			entity.setLevel(parent.getLevel()+1);
		}
		entity.setParent(parent);
		entity.setCarBrand(carBrandService.find(carBrandId));
		entity.setModifyDate(new Date());
		carTypeService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String ids) {
		try{
			CarType entity = carTypeService.find(ids);
			
			List<Car> carList = new ArrayList<Car>();
			if(entity.getLevel() == 1){
				for(CarType carType : entity.getCarTypeSet()){
					carList.addAll(carType.getCarList());
				}
			}else{
				carList.addAll(entity.getCarList());
			}
			if(carList.size() > 0){
				return ajaxError("该车系下有关联车辆，请先删除车辆", response);
			}
			
			entity.setVisible(false);
			carTypeService.update(entity);
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			
		}
		return ajaxError("删除失败", response);
		/*for(String id : ids){
			CarType entity = carTypeService.find(id);
			entity.setVisible(false);
			carTypeService.update(entity);
		}
		return ajaxSuccess("成功", response);*/
	}
	
	/**
	 * 验证
	 */
	@RequestMapping("/check")
	public @ResponseBody
	boolean check(@RequestParam String name) {
		return carTypeService.isExist(name);
	}
	
	/**
	 * 返回字符串的全拼
	 */
	@RequestMapping("/getPinYin")
	public @ResponseBody
	String getPinYin(@RequestParam String name) {
		if(null == name) return null;
		String initial = CommonUtil.getStringPinYin(name);
		if(initial == null){
			return null;
		}else{
			return initial;
		}
	}
}
