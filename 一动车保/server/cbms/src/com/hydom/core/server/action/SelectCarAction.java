package com.hydom.core.server.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.QueryResult;

/**
 * @Description 车型选择控制层
 * @author WY
 * @date 2015年7月1日 下午5:49:04
 */

@RequestMapping("/web/car")
@Controller
public class SelectCarAction extends BaseAction{
	
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
	
	@RequestMapping("/getCar")
	public @ResponseBody String getCar(String carTypeId,String keyWord) {
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("name", "desc");
			StringBuffer jpql = new StringBuffer("o.visible = true and o.carType = ?1");
			CarType ct = carTypeService.find(carTypeId);
			Object[] params = new Object[]{ct};
//			if(!"null".equals(keyWord)){
//				jpql.append(" and o.qp like ?2");
//				params = new Object[]{ct,keyWord+"%"};
//			}
			QueryResult<Car> cars = carService.getScrollData(-1, -1, jpql.toString(), params, orderby);
			List<Car> list = new ArrayList<Car>(cars.getResultList());

			JSONArray jList = new JSONArray();
			for(Car c:list){
				JSONObject obj = new JSONObject();
				obj.put("id", c.getId());
				obj.put("name", c.getName());
				obj.put("imgPath", c.getImgPath());
				jList.add(obj);
			}
			ObjectMapper mapper = new ObjectMapper();
			String json  = mapper.writeValueAsString(jList);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping("/getCarType")
	public @ResponseBody String getCarType(String carBrandId,String keyWord) {
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("name", "asc");
			StringBuffer jpql = new StringBuffer("o.visible = true and o.level = 2 and o.carBrand = ?1");
			CarBrand cb = carBrandService.find(carBrandId);
			Object[] params = new Object[]{cb};
			
			if(StringUtils.isNotEmpty(keyWord)){
				jpql.append(" and (o.qp like ?2 or o.name like ?3 or o.jp like ?4)");
				params = new Object[]{cb,keyWord+"%","%"+keyWord+"%",keyWord+"%"};
			}
			QueryResult<CarType> carTypes = carTypeService.getScrollData(-1, -1, jpql.toString(), params, orderby);
			List<CarType> list = new ArrayList<CarType>(carTypes.getResultList());
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			JSONArray jList = new JSONArray();
			for(CarType ct:list){
				dataMap.put("id", ct.getId());
				dataMap.put("name", ct.getName());
				jList.add(dataMap);
				dataMap.clear();
			}
			ObjectMapper mapper = new ObjectMapper();
			String json  = mapper.writeValueAsString(jList);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@RequestMapping("/getCarBrand")
	public @ResponseBody String getCarBrand(String keyWord) {
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("name", "asc");
			String jpql = "o.visible = true";
			Object[] params = null;
			if(StringUtils.isNotEmpty(keyWord)){
				jpql+=" and o.qp like ?1 or o.name like ?2 or o.jp like ?3";
				params = new Object[]{keyWord+"%","%"+keyWord+"%",keyWord+"%"};
			}
			QueryResult<CarBrand> carBrands = carBrandService.getScrollData(-1, -1, jpql, params, orderby);
			List<CarBrand> list = new ArrayList<CarBrand>(carBrands.getResultList());
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			JSONArray jList = new JSONArray();
			for(CarBrand cb:list){
				dataMap.put("id", cb.getId());
				dataMap.put("name", cb.getName());
				dataMap.put("initial", cb.getInitial());
				dataMap.put("imgPath", cb.getImgPath()==null?"":cb.getImgPath());
				jList.add(dataMap);
			}
			dataMap.clear();
			ObjectMapper mapper = new ObjectMapper();
			String json  = mapper.writeValueAsString(jList);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
