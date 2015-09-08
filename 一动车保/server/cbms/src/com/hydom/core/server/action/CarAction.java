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
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;
import com.hydom.util.dao.QueryResult;

/**
 * @Description 车型控制层
 * @author WY
 * @date 2015年7月1日 下午5:49:04
 */

@RequestMapping("/manage/car")
@Controller
public class CarAction extends BaseAction{
	
	private static String base = "/car";
	private static Integer mark = 2;
	
	@Resource
	private CarService carService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarTypeService carTypeService;
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
		for(CarType entity : carTypes){
			JSONObject obj = new JSONObject();
			obj.put("id", entity.getId());
			obj.put("name", entity.getName());
			JSONArray subArray = new JSONArray();
			for(CarType subEntity12 : entity.getCarTypeSet()){
				JSONObject sbobj = new JSONObject();
				sbobj.put("id", subEntity12.getId());
				sbobj.put("name", subEntity12.getName());
				subArray.add(sbobj);
			}
			obj.put("subDate", subArray);
			array.add(obj);
		}
		return ajaxSuccess(array, response);
	}
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public String addUI(ModelMap model) {
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("initial", "asc");
		String jpql = "o.visible = true";
		List<CarBrand> carBrands = carBrandService.getList(jpql, null, orderby);
		model.addAttribute("carBrands", carBrands);
		model.addAttribute("m", mark);
		
		return base + "/car_add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(Car car,String name, String carTypeId, String carBrandId) {
		
		car.setName(name);
		car.setCarType(carTypeService.find(carTypeId));
		car.setCarBrand(carBrandService.find(carBrandId));
		car.setModifyDate(new Date());
		carService.save(car);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 2);
		return mav;
	}
		
	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public String editUI(ModelMap model,@RequestParam String id) {
		Car car = carService.find(id);
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("initial", "asc");
		String jpql = "o.visible = true";
		List<CarBrand> carBrands = carBrandService.getList(jpql, null, orderby);
		model.addAttribute("carBrands", carBrands);
		model.addAttribute("m", mark);
		model.addAttribute("car", car);
		//顶级分类
		orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		jpql = "o.visible = true and o.carBrand.id=?1 and o.level = 1";
		List<Object> params = new ArrayList<Object>();
		params.add(car.getCarBrand().getId());
		List<CarType> carTypes = carTypeService.getList(jpql, params.toArray(), orderby);
		model.addAttribute("carTypes", carTypes);
		
		return base + "/car_edit";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<Car> pageView = new PageView<Car>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible = 1";
		Object[] params = new Object[]{};
		if(queryContent!=null){
			jpql+=" and (o.name like ?1 or o.carType.name like ?2 or o.carBrand.name like ?3)";
			params = new Object[]{"%"+queryContent+"%","%"+queryContent+"%","%"+queryContent+"%"};
		}
		pageView.setQueryResult(carService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/car/car_list");
		mav.addObject("queryContent", queryContent);
		mav.addObject("paveView", pageView);
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(String id, Car car, String carTypeId, String carBrandId) {
		Car entity = carService.find(id);
		entity.setName(car.getName());
		entity.setImgPath(car.getImgPath());
		entity.setCarType(carTypeService.find(carTypeId));
		entity.setCarBrand(carBrandService.find(carBrandId));
		entity.setModifyDate(new Date());
		carService.update(entity);
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
			Car car = carService.find(ids);
			if(car.getUserCarSet().size() > 0){
				return ajaxError("该车型有用户在使用，无法删除", response);
			}
			car.setVisible(false);
			carService.update(car);
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("删除失败", response);
		
		/*for(String id : ids){
			Car entity = carService.find(id);
			entity.setVisible(false);
			carService.update(entity);
		}
		return ajaxSuccess("成功", response);*/
	}
	
	/**
	 * 验证
	 */
	@RequestMapping("/check")
	public @ResponseBody
	boolean check(@RequestParam String name) {
		return carService.isExist(name);
	}
}
