package net.shop.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shop.Message;
import net.shop.Pageable;
import net.shop.entity.CarBrand;
import net.shop.entity.CarType;
import net.shop.service.CarBrandService;
import net.shop.service.CarTypeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminCarTypeController")
@RequestMapping("/admin/carType")
public class CarTypeController extends BaseController{
	
	@Resource(name = "carTypeServiceImpl")
	private CarTypeService carTypeService;
	
	@Resource(name = "carBrandServiceImpl")
	private CarBrandService carBrandService;
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		List<CarType> list = carTypeService.findAll();
		for(int i=0;i<list.size();){
			if(list.get(i).getLevel()!=1){
				list.remove(i);
				continue;
			}
			i++;
		}
		List<CarBrand> carBrands = carBrandService.findAll();
		model.addAttribute("parents", list);
		model.addAttribute("carBrands", carBrands);
		return "/admin/carType/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String name, Long parentId, Long carBrandId, RedirectAttributes redirectAttributes) {
//		if (!isValid(carType)) {
//			return ERROR_VIEW;
//		}
		CarType carType = new CarType();
		carType.setName(name);
		CarType parent = carTypeService.find(parentId);
		carType.setParent(parent);
		carType.setCarBrand(carBrandService.find(carBrandId));
		if(parent==null){
			carType.setLevel(1);
		}else{
			carType.setLevel(parent.getLevel()+1);
		}
		carTypeService.save(carType);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("carType", carTypeService.find(id));
		List<CarType> list = carTypeService.findAll();
		for(int i=0;i<list.size();i++){
			if(list.get(i).getLevel()!=1){
				list.remove(i);
				i--;
			}
		}
		List<CarBrand> carBrands = carBrandService.findAll();
		model.addAttribute("carBrands", carBrands);
		model.addAttribute("parents", list);
		return "/admin/carType/edit";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", carTypeService.findPage(pageable));
		return "/admin/carType/list";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(CarType carType, Long parentId, RedirectAttributes redirectAttributes) {
		if (!isValid(carType)) {
			return ERROR_VIEW;
		}
		CarType parent = carTypeService.find(parentId);
		carType.setParent(parent);
		if(parent==null){
			carType.setLevel(1);
		}else{
			carType.setLevel(parent.getLevel()+1);
		}
		carTypeService.update(carType);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		carTypeService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
