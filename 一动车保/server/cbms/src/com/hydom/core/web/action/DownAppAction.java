package com.hydom.core.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.report.MessagesResult.Android;

import com.hydom.account.service.MemberService;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;

/**
 * web首页
 * 
 * @author liudun
 * 
 */

@RequestMapping("/down")
@Controller
public class DownAppAction extends BaseAction {

	private static final String base = "/index";

	@Resource
	private CarService carService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarTypeService carTypeService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Resource
	private MemberService memberService;

	/**
	 * 首页信息
	 */
	@RequestMapping("/{type}")
	@ResponseBody
	public void index(@PathVariable String type) {
		String url = "";
		if("android".equals(type)){
			url = "down/android.apk";
		}else{
			url = "down/iphone.ipa";
		}
		download(url, request, response);
	}
	
}
