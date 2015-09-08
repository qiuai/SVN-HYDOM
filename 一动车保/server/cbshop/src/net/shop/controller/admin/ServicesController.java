/*
 * 
 * 
 * 
 */
package net.shop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shop.Filter;
import net.shop.Page;
import net.shop.Pageable;
import net.shop.entity.ServicesType;
import net.shop.service.ServicesTypeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 销售统计
 * 
 * 
 * 
 */
@Controller("adminServiceController")
@RequestMapping("/admin/service")
public class ServicesController extends BaseController {
	
	@Resource(name="servicesTypeServiceImpl")
	private ServicesTypeService servicesTypeService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String view(Model model,Pageable pageable,Integer type) {
		
		List<Filter> filters = new ArrayList<Filter>();
		
		if(type != null){
			filters.add(Filter.eq("type", type));
		}
		
		Page<ServicesType> serviceTypes = servicesTypeService.findPage(pageable);
		model.addAttribute("page", serviceTypes);
		return "/admin/service/list";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		
		return "/admin/service/add";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String detial(Long id,Model model) {
		ServicesType serviceType = servicesTypeService.find(id);
		model.addAttribute("entity", serviceType);
		return "/admin/service/edit";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ServicesType serviceType , Model model) {
		servicesTypeService.save(serviceType);
		return "redirect:list.jhtml";
	}
}