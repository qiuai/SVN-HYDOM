package com.hydom.core.web.action;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hydom.account.ebean.News;
import com.hydom.account.service.NewsService;
import com.hydom.util.BaseAction;

/**
 *@author liuyulin
 *@date 2015年7月14日下午2:44:13
 *@file NewsAction.java
 */
@Controller("news")
@RequestMapping("web/news")
public class NewsAction extends BaseAction {
	@Resource
	private NewsService newsService;
	
	@RequestMapping("list")
	public String list(ModelMap model){
		LinkedHashMap<String, String> order=new LinkedHashMap<String, String>();
		order.put("createDate", "desc");
		List<News> newsList=newsService.getScrollData(null, null, order).getResultList();
		model.addAttribute("list", newsList);
		return "web/news/news_list";
	}
	
	@RequestMapping("info")
	public String info(String id,ModelMap model){
		News news=newsService.find(id);
		model.addAttribute("news", news);
		return "web/news/news_info";
	}
	
}
