package net.shop.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shop.Message;
import net.shop.Pageable;
import net.shop.entity.Car;
import net.shop.entity.CarType;
import net.shop.service.CarBrandService;
import net.shop.service.CarService;
import net.shop.service.CarTypeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("adminCarController")
@RequestMapping("/admin/car")
public class CarController extends BaseController{
	
	@Resource(name = "carServiceImpl")
	private CarService carService;
	@Resource(name = "carBrandServiceImpl")
	private CarBrandService carBrandService;
	@Resource(name = "carTypeServiceImpl")
	private CarTypeService carTypeService;
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, Long carBrandId, String name) {
		model.addAttribute("brands", carBrandService.findAll());
		List<CarType> list = carTypeService.findAll();
		for(int i=0;i<list.size();){
			if(list.get(i).getLevel()==1 || list.get(i).getCarBrand().getId()!=carBrandId){
				list.remove(i);
				continue;
			}
			i++;
		}
		model.addAttribute("carTypes", list);
		model.addAttribute("carBrandId", carBrandId);
		model.addAttribute("name", name);
		return "/admin/car/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Long carBrandId, Long carTypeId, Car car, RedirectAttributes redirectAttributes) {
		if (!isValid(car)) {
			return ERROR_VIEW;
		}
		car.setBrand(carBrandService.find(carBrandId));
		car.setCarType(carTypeService.find(carTypeId));
		carService.save(car);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model, Long carBrandId) {
		Car car = new Car();
		car = carService.find(id);
		model.addAttribute("car", car);
		model.addAttribute("brands", carBrandService.findAll());
		List<CarType> list = carTypeService.findAll();
		for(int i=0;i<list.size();){
			if(list.get(i).getLevel()==1 || list.get(i).getCarBrand().getId()!=(carBrandId==null?car.getBrand().getId():carBrandId)){
				list.remove(i);
				continue;
			}
			i++;
		}
		model.addAttribute("carTypes", list);
		model.addAttribute("carBrandId", carBrandId);
		return "/admin/car/edit";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", carService.findPage(pageable));
		return "/admin/car/list";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Car car, Long carBrandId, Long carTypeId, RedirectAttributes redirectAttributes) {
		if (!isValid(car)) {
			return ERROR_VIEW;
		}
		car.setBrand(carBrandService.find(carBrandId));
		car.setCarType(carTypeService.find(carTypeId));
		carService.update(car);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		carService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
