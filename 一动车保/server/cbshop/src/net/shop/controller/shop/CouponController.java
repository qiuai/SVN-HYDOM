/*
 * 
 * 
 * 
 */
package net.shop.controller.shop;

import javax.annotation.Resource;

import net.shop.Pageable;
import net.shop.service.CouponService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 会员登录
 * 
 * 
 * 
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
	
	/** 每页记录数 */
	private static final int PAGE_SIZE = 40;
	
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index(Long id,ModelMap model) {
		
		model.put("coupon", couponService.find(id));
		
		return "/shop/coupon/content";
	}
	
	
	@RequestMapping(value="/list/{pageNumber}",method = RequestMethod.GET)
	public String list(@PathVariable Integer pageNumber,ModelMap model) {
		
		Pageable pageable = new Pageable(pageNumber, PAGE_SIZE);
		
		model.put("page", couponService.findPage(pageable));
		
		return "/shop/coupon/list";
	}
	
}