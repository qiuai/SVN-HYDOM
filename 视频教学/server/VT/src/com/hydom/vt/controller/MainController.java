package com.hydom.vt.controller;

import java.io.Serializable;

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

/**
 * 主页面处理Handler
 * 
 * @author rl
 * @version 1.0.0 2015.6.4 新建
 */
@Controller
@RequestMapping("/main")
public class MainController extends AbstractController {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1718786787833928397L;
	@Resource
	private UserService userService;

	/**
	 * 进入主页面.
	 * 
	 * @return 主页面
	 */
	@RequestMapping("/main.do")
	public String main() {

		return "main";
	}
	
	
	@RequestMapping("/index.do")
	public String index() {
		
		return "main_index";
	}

	/**
	 * 头部
	 * 
	 * @return 头部页码
	 */
	@RequestMapping("/header.do")
	public String header() {

		return "main_header";
	}

	/**
	 * 左边
	 * 
	 * @return 左边页码
	 */
	@RequestMapping("/left.do")
	public String left() {

		return "menu_left";
	}
	
	@RequestMapping("/middle.do")
	public String middle() {
		
		return "main_middle";
	}
}
