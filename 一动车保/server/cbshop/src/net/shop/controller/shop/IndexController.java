/*
 * 
 * 
 * 
 */
package net.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 会员登录
 * 
 * 
 * 
 */
@Controller
public class IndexController extends BaseController {

	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index(ModelMap model) {
	//	System.out.println("进入index方法");
		return "/shop/index";
	}

}