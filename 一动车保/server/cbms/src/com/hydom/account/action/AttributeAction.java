package com.hydom.account.action;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.hydom.account.ebean.Attribute;
import com.hydom.account.service.AttributeService;
import com.hydom.account.service.ProductCategoryService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/productAttribute")
@Controller
public class AttributeAction extends BaseAction{
	
	private final static String basePath = "/account";
	private final static int mark = 1;
	
	@Resource
	private ProductCategoryService productCategoryService;
	
	@Resource
	private AttributeService attributeService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		
		PageView<Attribute> pageView = new PageView<Attribute>(null,page);
		
		StringBuffer jpql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append("o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("productCategory.name", "asc");
		orderby.put("order", "asc");
		pageView.setOrderby(orderby);
		
		pageView = attributeService.getPage(pageView);
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", pageView);
		model.addAttribute("m", mark);
		//mav.addAllObjects(model);
		return basePath+"/attribute_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model){
		model.addAttribute("m", mark);
		model.addAttribute("categorys", productCategoryService.findProductCategory(null));
		return  basePath+"/attribute_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		
		Attribute entity = attributeService.find(id);
		model.addAttribute("entity", entity);
		
		model.addAttribute("m", mark);
		
		return  basePath+"/attribute_edit";
	}
	
	@RequestMapping("/save")
	public String save(ModelMap model,Attribute entity,@RequestParam(required=false) String[] attributeValues){
		
		List<String> options = new ArrayList<String>();
		if(attributeValues!=null && attributeValues.length > 0){
			for(String attributeValue : attributeValues){
				if(StringUtils.isNotEmpty(attributeValue)){
					options.add(attributeValue);
				}
			}
		}
		
		if(StringUtils.isNotEmpty(entity.getId())){
			Attribute attribute = attributeService.find(entity.getId());
			attribute.setName(entity.getName());
			attribute.setOrder(entity.getOrder());
			attribute.setOptions(options);
			attributeService.update(attribute);
		}else{
			entity.setPropertyIndex(null);
			entity.setOptions(options);
			attributeService.save(entity);
		}
		return  "redirect:list";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			Attribute entity = attributeService.find(id);
			attributeService.remove(entity);
		}
		return ajaxSuccess("成功", response);
	}
}
