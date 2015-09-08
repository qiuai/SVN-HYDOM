package com.hydom.account.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.service.AccountService;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.util.BaseAction;
/**
 * 跳转到主页
 * @author Administrator
 *
 */
@RequestMapping("/manage")
@Controller
public class MainAction extends BaseAction{
	@Resource
	private AccountService accountService;
	@Resource
	private PrivilegeGroupService groupService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/main")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page) {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
}
