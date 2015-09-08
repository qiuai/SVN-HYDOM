package com.hydom.vt.controller;

import java.io.Serializable;

import org.hibernate.engine.transaction.spi.TransactionContext;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.UserInfo;
import com.hydom.common.service.UserService;
import com.hydom.util.HydomUtil;
import com.hydom.vt.entity.User;
import com.hydom.vt.util.Constant;
import com.hydom.vt.util.session.SessionListener;

/**
 * 用户登录处理Handler
 * 
 * @author rl
 * @version 1.0.0 2015.6.4 新建
 */
@Controller
@RequestMapping("/user")
public class LoginController extends AbstractController implements Validator {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1718786787833928397L;
	@Resource
	private UserService userService;

	/**
	 * 进行登录处理.
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") LoginForm user,
			BindingResult result) {

		if (result.hasErrors()) {
			request.setAttribute("error", "用户名或密码错误!");
			return LOGIN;
		}

		User member = userService.get(
				new String[] { "username", "password" },
				new Object[] { user.getUsername(),
						HydomUtil.md5Hex(user.getPassword()) });
		if (member == null) {
			request.setAttribute("error", "用户名或密码错误!");
			return LOGIN;
		}

		if (member.getAdminType() == 0) {
			request.setAttribute("error", "你不是管理员!");
			return LOGIN;
		}
		getSession().setAttribute(Constant.SESSION_USER, member);
		
		SessionListener.sessionContext.AddSession(getSession());
		return "redirect:/main/main.do";
	}

	/**
	 * 登出处理.
	 * 
	 * @return 登录页面
	 */
	@RequestMapping("/logout.do")
	public String logout() {
		getSession().removeAttribute(UserInfo.SESSION_USER);
		return "redirect:/user/login.do";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(this);
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login() {
		return LOGIN;
	}

	@Getter
	@Setter
	public static class LoginForm implements Serializable {

		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = -8665641331917376803L;

		private String username;
		private String password;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (null == obj) {
			errors.reject("error", "用户名不能为空!");
		} else {
			LoginForm cmd = (LoginForm) obj;

			// 判断用户名
			if (StringUtils.isEmpty(cmd.getUsername())) {
				errors.reject("error", "用户名不能为空!");
			}

			// 判断密码
			if (StringUtils.isEmpty(cmd.getPassword())) {
				errors.reject("error", "密码不能为空!");
			}
		}
	}

}
