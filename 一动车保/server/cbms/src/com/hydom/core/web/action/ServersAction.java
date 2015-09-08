package com.hydom.core.web.action;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hydom.account.ebean.Server;
import com.hydom.account.service.ServerService;
import com.hydom.util.BaseAction;

/**
 * @author liuyulin
 * @date 2015年7月14日下午2:44:13
 * @file NewsAction.java
 */
@Controller
@RequestMapping("web/server")
public class ServersAction extends BaseAction {

	@Resource
	private ServerService serverService;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/detail")
	public String detail(String id, ModelMap model) {
		Server server = serverService.find(id);
		model.put("server", server);
		return "/server/service";
	}

	@RequestMapping("/service")
	public String service() {
		return "/server/service";
	}

	@RequestMapping("/appdown")
	public String appDownlod() {
		// Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; MI 2S Build/LRX22G)
		// AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4
		// TBS/025440 Mobile Safari/533.1 MicroMessenger/6.2.4.54_r266a9ba.601
		// NetType/WIFI Language/zh_CN

		// Mozilla/5.0 (Linux; Android 5.0.2; MI 2 Build/LRX22G)
		// AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0
		// Mobile Safari/537.36 MicroMessenger/6.2.4.54_r266a9ba.601
		// NetType/WIFI Language/zh_CN
		// Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101
		// Firefox/39.0
		Enumeration<String> uagent = request.getHeaders("User-Agent");
		StringBuffer uagentValue = new StringBuffer();
		while (uagent.hasMoreElements()) {
			uagentValue.append(uagent.nextElement());
		}
		
		System.out.println(uagentValue);
		if (uagentValue.toString().contains("MQQBrowser")) { // 微信内置浏览器
			request.setAttribute("MQQBrowser", "true");
		}
		System.out.println(request.getAttribute("MQQBrowser"));
		return "/server/appDown";
	}
}
