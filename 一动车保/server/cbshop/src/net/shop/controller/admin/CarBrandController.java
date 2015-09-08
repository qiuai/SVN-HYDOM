package net.shop.controller.admin;

import javax.annotation.Resource;

import net.shop.Message;
import net.shop.Order;
import net.shop.Order.Direction;
import net.shop.Pageable;
import net.shop.entity.Brand;
import net.shop.entity.CarBrand;
import net.shop.entity.Brand.Type;
import net.shop.service.CarBrandService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminCarBrandController")
@RequestMapping("/admin/carBrand")
public class CarBrandController extends BaseController{
	
	@Resource(name = "carBrandServiceImpl")
	private CarBrandService carBrandService;
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/carBrand/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(CarBrand carBrand, RedirectAttributes redirectAttributes) {
		if (!isValid(carBrand)) {
			return ERROR_VIEW;
		}
		carBrandService.save(carBrand);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("carBrand", carBrandService.find(id));
		return "/admin/carBrand/edit";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		pageable.setOrderProperty("jp");
		pageable.setOrderDirection(Direction.asc);
		model.addAttribute("page", carBrandService.findPage(pageable));
		return "/admin/carBrand/list";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(CarBrand carBrand, RedirectAttributes redirectAttributes) {
		if (!isValid(carBrand)) {
			return ERROR_VIEW;
		}
		carBrandService.update(carBrand);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		carBrandService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
