package com.hydom.account.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.ProductBrand;
import com.hydom.account.service.ProductBrandService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/productBrand")
@Controller
public class ProductBrandAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 1;
	
	@Resource
	private ProductBrandService productBrandService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model,String searchProp) {
		
		PageView<ProductBrand> pageView = new PageView<ProductBrand>(null,page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("order", "asc");
		
		StringBuffer jpql = new StringBuffer("o.visible = true");
		List<String> params = new ArrayList<String>();
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append(" and o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		
		pageView.setOrderby(orderby);
		pageView = productBrandService.getPage(pageView);
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		model.addAttribute("searchProp", searchProp);
		//mav.addAllObjects(model);
		return basePath+"/product_brand_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
	
		model.addAttribute("m", mark);
	
		return  basePath+"/product_brand_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		ProductBrand productBrand = productBrandService.find(id);
		model.addAttribute("entity", productBrand);
		
		model.addAttribute("m", mark);
	
		return  basePath+"/product_brand_add";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,ProductBrand entity){
		if(StringUtils.isNotEmpty(entity.getId())){
			ProductBrand productBrand = productBrandService.find(entity.getId());
			productBrand.setImgPath(entity.getImgPath());
			productBrand.setName(entity.getName());
			productBrand.setOrder(entity.getOrder());
			productBrand.setUrl(entity.getUrl());
			productBrand.setRemark(entity.getRemark());
			productBrand.setCommandBrand(entity.getCommandBrand());
			productBrandService.update(productBrand);
		}else{
			productBrandService.save(entity);
		}
		return  "redirect:list";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id){
		try{
			ProductBrand productBrand = productBrandService.find(id);
			if(productBrand.getProductSet().size() > 0){
				return ajaxError("该品牌下存在商品，无法删除", response);
			}
			productBrand.setVisible(false);
			productBrandService.update(productBrand);
			return ajaxSuccess("成功", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("删除失败", response);
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String name){
		try{
			ProductBrand productBrand = productBrandService.findbyName(name);
			if(productBrand != null){
				return ajaxError("该名称已存在",response);
			}
			return ajaxSuccess("", response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ajaxError("删除失败", response);
	}
	
}
