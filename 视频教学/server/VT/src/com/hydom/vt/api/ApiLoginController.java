package com.hydom.vt.api;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.common.AbstractController;

/**
 * 用户登录处理类.
 * 
 * @author rl
 * @version 1.0.0 2015.5.20 新建
 */
@Controller
@RequestMapping("/api/user")
public class ApiLoginController extends AbstractController {


	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8157556087418455871L;

	// 登录
	@ResponseBody
	@RequestMapping("/login.do")
	public void login(String username, String password) {
		JSONObject result = new JSONObject();
		result.put(STATUS, CODE_SUCCESS);

		ajaxJSONObject(result);
	}

	//
	@ResponseBody
	@RequestMapping("/test.do")
	public void test(String username, String password) {
		JSONObject result = new JSONObject();
		result.put(STATUS, CODE_SUCCESS);

		ajaxJSONObject(result);
	}
}
