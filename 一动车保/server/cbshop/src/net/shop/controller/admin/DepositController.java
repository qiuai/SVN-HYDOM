/*
 * 
 * 
 * 
 */
package net.shop.controller.admin;

import javax.annotation.Resource;

import net.shop.Pageable;
import net.shop.entity.Member;
import net.shop.service.DepositService;
import net.shop.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 预存款
 * 
 * 
 * 
 */
@Controller("adminDepositController")
@RequestMapping("/admin/deposit")
public class DepositController extends BaseController {

	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long memberId, Pageable pageable, ModelMap model) {
		Member member = memberService.find(memberId);
		if (member != null) {
			model.addAttribute("member", member);
			model.addAttribute("page", depositService.findPage(member, pageable));
		} else {
			model.addAttribute("page", depositService.findPage(pageable));
		}
		return "/admin/deposit/list";
	}

}