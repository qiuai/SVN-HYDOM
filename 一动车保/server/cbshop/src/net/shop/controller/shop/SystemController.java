/*
 * 
 * 
 * 
 */
package net.shop.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 系统
 * 
 * 
 * 
 */
@Controller("shopSystemController")
@RequestMapping("/system")
public class SystemController {

	/**
	 * 信息
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

}