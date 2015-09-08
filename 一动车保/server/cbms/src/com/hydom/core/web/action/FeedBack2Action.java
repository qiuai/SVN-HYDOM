package com.hydom.core.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Feedback;
import com.hydom.account.ebean.Member;
import com.hydom.account.service.FeedbackService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.MemberBean;
@Controller
@RequestMapping("/user/feedback")
public class FeedBack2Action extends BaseAction{
@Resource
private FeedbackService feedbackService;

@Autowired
private HttpServletRequest request;
@Autowired
private HttpServletResponse response;
/**跳转反馈页面*/
@RequestMapping("/add")
public ModelAndView add(){
	ModelAndView mav = new ModelAndView("/web/feedBack/myfeedback");
	return mav;
	
}
/**提交反馈信息*/
@RequestMapping("/savefeedback")
public ModelAndView savefeedback(@RequestParam() String Content){
	MemberBean bean = getMemberBean(request);
	//HttpSession session = request.getSession();
	Member member = bean.getMember();
	Feedback entity = new Feedback();
	entity.setMember(member);
	entity.setContent(Content);
	feedbackService.save(entity);
	ModelAndView mav = new ModelAndView("redirect:add");
	return mav;
	
}
}
