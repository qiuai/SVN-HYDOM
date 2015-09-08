package com.hydom.account.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.News;
import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.service.NewsService;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.util.BaseAction;

@RequestMapping("/manage/news")
@Controller
public class NewsAction extends BaseAction {
	@Resource
	private NewsService newsService;
	
	@Resource
	private PrivilegeGroupService groupService;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表
	 * 
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page,ModelMap model) {
		List<News> newsList =  newsService.getListByHql("FROM News ORDER BY createDate DESC");
		ModelAndView mv = new ModelAndView("/news/news_list");
		mv.addObject("m", 11);
		mv.addObject("newsList",newsList);
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @param response
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String[] ids){
		for(String id : ids){
			News entity = newsService.find(id);
			newsService.remove(entity);
		}
		return ajaxSuccess("成功", response);
	}
	
	@RequestMapping("/add")
	public ModelAndView addUI() {
		ModelAndView mav = new ModelAndView("/news/news_add");
		List<PrivilegeGroup> groups = groupService.getScrollData()
				.getResultList();
		mav.addObject("groups", groups);
		mav.addObject("m", 11);
		return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(News news) {
		if(StringUtils.isNotEmpty(news.getId())){
			newsService.update(news);
		}else{
			newsService.save(news);
		}
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editUI(String id) {
		News news = newsService.find(id);
		ModelAndView mav = new ModelAndView("/news/news_add");
		mav.addObject("news", news);
		return mav;
	}

}
