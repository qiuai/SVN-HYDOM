package com.hydom.account.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Account;
import com.hydom.account.service.AccountService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.AdminBean;

@RequestMapping("/account")
@Controller
public class LoginAndExitAction extends BaseAction{
	
	@Resource
	private AccountService accountService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/login")
	public ModelAndView signin() {
		ModelAndView mav = new ModelAndView("signin");
		return mav;
	}

	@RequestMapping("/signin")
	public ModelAndView signin(@RequestParam String username,
			@RequestParam String password) {
		ModelAndView mav = new ModelAndView("signin");
		Account account = accountService.findByUP(username, password);
		if (account == null) {
			request.setAttribute("error", "用户名或密码错误");
			return mav;
		}
		HttpSession session = request.getSession();
		accountService.update(account);
	//	session.setAttribute("loginAccount", account);
		
		AdminBean bean = AdminBean.convert2MemberBean(account);
		session.setAttribute(AdminBean.ADMIN_SESSION, bean);
		
		mav = new ModelAndView("redirect:/manage/main");
		return mav;
	}

	@RequestMapping("/signout")
	@ResponseBody
	public String signout(HttpServletResponse response) {
		removeMemberBean(request, AdminBean.ADMIN_SESSION);
		return ajaxSuccess("", response);
	}
	
	
}
