package com.hydom.core.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Member;
import com.hydom.account.service.MemberService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.MemberBean;
@Controller
@RequestMapping("/user/information")
public class BasicInformationAction extends BaseAction{
@Resource
private MemberService memberService;
@Autowired
private HttpServletRequest request;
@Autowired
private HttpServletResponse response;
/**跳转基本信息页面*/
@RequestMapping("/info")
public ModelAndView basicinfo(){
	MemberBean bean = getMemberBean(request);
	Member member = bean.getMember();
	//Member member = memberService.find(memberId);
	ModelAndView mav = new ModelAndView("/web/myInformation/basicinformation");
	mav.addObject("member", member);
	return mav;

}
@RequestMapping("/goup")
public ModelAndView goup(){
	ModelAndView mav = new ModelAndView("/web/updatePassword/updatepassword");
	return mav;
	
}
/**修改密码*/
@RequestMapping("/updatepassword")
public ModelAndView updatepassword(@RequestParam String newpassword){
	MemberBean bean = getMemberBean(request);
	Member entity = bean.getMember();
	entity.setPassword(newpassword);
	memberService.update(entity);
	ModelAndView mav = new ModelAndView("/web/myInformation/basicinformation");
	return mav;
	
}
}
