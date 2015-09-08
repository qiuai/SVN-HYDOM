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
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Area;
import com.hydom.account.ebean.ServiceType;
import com.hydom.account.service.ServiceTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/serviceType")
@Controller
public class ServiceTypeAction extends BaseAction{
	
	private final static String basePath = "/account";
	
	@Resource
	private ServiceTypeService serviceTypeService;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/list")
	public String list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		
		PageView<ServiceType> pageView = new PageView<ServiceType>(null,page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("type", "desc");
		orderby.put("order", "asc");
		
		StringBuffer jpql = new StringBuffer();
		List<String> params = new ArrayList<String>();
		jpql.append("o.visible = true ");
		String queryContent = request.getParameter("queryContent");
		if(StringUtils.isNotEmpty(queryContent)){
			jpql.append("and o.name like ?"+(params.size()+1));
			params.add("%"+queryContent+"%");
		}
		model.addAttribute("queryContent", queryContent);
		
		List<ServiceType> serviceTypes = serviceTypeService.getList(jpql.toString(), params.toArray(), orderby);
		
		/*pageView.setJpql(jpql.toString());
		pageView.setParams(params.toArray());
		pageView.setOrderby(orderby);
		pageView = serviceTypeService.getPage(pageView);*/
	
	//	ModelAndView mav = new ModelAndView(basePath+"/service_type_list");
		model.addAttribute("pageView", serviceTypes);
		model.addAttribute("m", 4);
		
		//mav.addAllObjects(model);
		return basePath+"/service_type_list";
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model,String id){
		model.addAttribute("m", 4);
		return  basePath+"/service_type_add";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model,String id){
		ServiceType serviceType = serviceTypeService.find(id);
		model.addAttribute("entity", serviceType);
		model.addAttribute("m", 4);
		return  basePath+"/service_type_edit";
	}
	
	
	@RequestMapping("/save")
	public String save(ModelMap model,ServiceType entity){
		if(StringUtils.isNotEmpty(entity.getId())){
			serviceTypeService.update(entity);
		}else{
			entity.setType(1);
			serviceTypeService.save(entity);
		}
		return  "redirect:list";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			ServiceType serviceType = serviceTypeService.find(id);
			if(serviceType.getType() == 2){
				continue;
			}
			serviceType.setVisible(false);
			serviceTypeService.update(serviceType);
		}
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/checkName")
	@ResponseBody
	public String checkName(String name){
		
		ServiceType serviceType = serviceTypeService.getEntitybyName(name);
		if(serviceType == null){
			return ajaxSuccess("", response);
		}
		
		return ajaxError("该名称已存在,请重新输入", response);
	}
}
