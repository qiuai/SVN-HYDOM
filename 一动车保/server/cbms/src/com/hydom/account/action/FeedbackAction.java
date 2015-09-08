package com.hydom.account.action;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Feedback;
import com.hydom.account.ebean.Technician;
import com.hydom.account.service.FeedbackService;
import com.hydom.util.BaseAction;
import com.hydom.util.dao.PageView;
@Controller
@RequestMapping("/manage/feedback")
public class FeedbackAction extends BaseAction{
	@Resource
	private FeedbackService feedbackService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	private int maxresult = 6;

	private final static int mark = 14;
	/**列表*/
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page) {
		PageView<Feedback> pageView = new PageView<Feedback>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		String jpql = "o.visible = 1";
		Object[] params = new Object[]{};
		pageView.setQueryResult(feedbackService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
		request.setAttribute("pageView", pageView);
		ModelAndView mav = new ModelAndView("/account/feedback_list");
		mav.addObject("paveView", pageView);
		mav.addObject("m", mark);
		return mav;
	}
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam() String id){
		
			Feedback entity = feedbackService.find(id);
			entity.setVisible(false);
			feedbackService.update(entity);
			ModelAndView mav = new ModelAndView("redirect:list");
			
		return mav;
		
	}
}
