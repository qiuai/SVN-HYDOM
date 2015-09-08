package com.hydom.core.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonUtil;
import com.hydom.util.dao.PageView;

/**
 * @Description 汽车品牌控制层
 * @author WY
 * @date 2015年6月26日 下午5:36:05
 */

@RequestMapping("/manage/carBrand")
@Controller
public class CarBrandAction extends BaseAction{
	
	@Resource
	private CarBrandService carBrandService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI() {
		ModelAndView mav = new ModelAndView("/carBrand/carBrand_add");
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public ModelAndView save(@ModelAttribute CarBrand carBrand) {
		carBrandService.save(carBrand);
		ModelAndView mav = new ModelAndView("redirect:list");
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String id) {
		CarBrand carBrand = carBrandService.find(id);
		ModelAndView mav = new ModelAndView("/carBrand/carBrand_edit");
		mav.addObject("carBrand", carBrand);
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page, String queryContent) {
		PageView<CarBrand> pageView = new PageView<CarBrand>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible = 1";
		Object[] params = new Object[]{};
		if(queryContent!=null){
			jpql+=" and (o.name like ?1 or o.jp like ?2 or o.qp like ?3)";
			params = new Object[]{"%"+queryContent+"%",queryContent+"%",queryContent+"%"};
		}
		pageView.setQueryResult(carBrandService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/carBrand/carBrand_list");
		mav.addObject("paveView", pageView);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 2);
		return mav;
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute CarBrand carBrand) {
		CarBrand entity = carBrandService.find(carBrand.getId());
		entity.setImgPath(carBrand.getImgPath());
		entity.setInitial(carBrand.getInitial());
		entity.setName(carBrand.getName());
		entity.setJp(carBrand.getJp());
		entity.setQp(carBrand.getQp());
		entity.setModifyDate(new Date());
		
		carBrandService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}
	
/*	*//**
	 * 删除
	 *//*
	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {*/
		
		/**
		 * 删除
		 */
		@RequestMapping("/delete")
		public @ResponseBody
		String delete(@RequestParam String ids) {
			try{
				CarBrand entity = carBrandService.find(ids);
				if(entity.getCarTypeList().size() > 0){
					return ajaxError("该品牌下有可使用的车系，请先删除车系", response);
				}
				entity.setVisible(false);
				carBrandService.update(entity);
				return ajaxSuccess("成功", response);
			}catch(Exception e){
				
			}
			return ajaxError("删除失败", response);
		}
		
		
		/*for(String id : ids){
			CarBrand entity = carBrandService.find(id);
			entity.setVisible(false);
			carBrandService.update(entity);
		}
		return ajaxSuccess("成功", response);}*/
	
	
	/**
	 * 验证
	 */
	@RequestMapping("/check")
	public @ResponseBody
	boolean check(@RequestParam String name) {
		return carBrandService.isExist(name);
	}
	
	/**
	 * 返回汉字拼音首字母
	 */
	@RequestMapping("/getAbridge")
	public @ResponseBody
	String getAbridge(@RequestParam String name) {
		if(null == name) return null;
		String abridge = "";
		for(int i=0;i<name.length();i++){
			if(CommonUtil.getCharacterPinYin(name.charAt(i))==null){
				abridge += name.charAt(i);
			}else{
				abridge += CommonUtil.getCharacterPinYin(name.charAt(i)).substring(0,1);
			}
		}
		return abridge;
	}
	
	/**
	 * 返回汉字拼音缩写
	 */
	@RequestMapping("/getInitial")
	public @ResponseBody
	String getInitial(@RequestParam String name) {
		if(null == name) return null;
		String initial = CommonUtil.getCharacterPinYin(name.charAt(0));
		if(initial==null) return name.substring(0,1);
		return initial.substring(0,1);
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
