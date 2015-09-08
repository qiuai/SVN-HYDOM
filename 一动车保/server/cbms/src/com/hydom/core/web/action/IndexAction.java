package com.hydom.core.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.Member;
import com.hydom.account.service.MemberService;
import com.hydom.core.server.service.CarBrandService;
import com.hydom.core.server.service.CarService;
import com.hydom.core.server.service.CarTypeService;
import com.hydom.util.BaseAction;
import com.hydom.util.IDGenerator;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.cache.CachedManager;
import com.hydom.util.duanxin.SendMessage;

/**
 * web首页
 * 
 * @author liudun
 * 
 */

@RequestMapping("/web")
@Controller
public class IndexAction extends BaseAction {

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
	@RequestMapping("/index")
	public String index() {

		// MemberBean bean =

		return base + "/index";
	}

	/**
	 * 用户登录
	 * @param mobile
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(String mobile, String code, HttpServletRequest request) {
		//登录流程
		//1.存在该电话 则将其用户放入session 
		//2.不存在该电话 ，则新创建一个用户，然后将其放入session
		
		Object verifyCode = CachedManager.getObjectCached("phonecode", mobile);
	//	System.out.println(verifyCode);
		if(verifyCode == null){
			return ajaxError("无效的验证码", response);
		}
		
		Member member = memberService.findByHql("select o from com.hydom.account.ebean.Member o where o.mobile='" + mobile+"' and o.visible = true");
		if (member != null) {
			if (code.equals(verifyCode.toString())) {
				
				request.getSession().setAttribute(MemberBean.MEMBER_SESSION, MemberBean.convert2MemberBean(member));
				CachedManager.remove("phonecode", mobile);
				
				return ajaxSuccess("已创建新用户，登录成功", response);
			} else {
				return ajaxError("验证码错误", response);
			}
		} else {
			if (code.equals(verifyCode.toString())) {
				member = new Member();
				member.setMobile(mobile);
				memberService.save(member);
				
				request.getSession().setAttribute(MemberBean.MEMBER_SESSION, MemberBean.convert2MemberBean(member));
				CachedManager.remove("phonecode", mobile);
				return ajaxSuccess("已创建新用户，登录成功", response);
			} else {
				return ajaxError("验证码错误", response);
			}
		}
	}
	
	/**
	 * 根据电话号码获取验证码
	 * @param mobile
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getcode")
	@ResponseBody
	public void getcode(String mobile, HttpServletRequest request,
			HttpServletResponse response) {
		
		String code = IDGenerator.getRandomString(6, 1);
		String str = "【一动车保】您本次登录的验证码为：" + code + "，验证码5分钟内有效。";
		boolean b = SendMessage.sendMessage(mobile, str);
		if(b){
			//发送成功,将当前的code 存入cached中
			CachedManager.putObjectCached("phonecode", mobile, code);
			
			ajaxSuccess(code, response);
		}else{
			ajaxError("获取验证码失败,稍后在试", response);
		}
	}
	
	/**
	 * 跳转到个人中心
	 * @param mobile
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/gotoMemberCenter")
	public String gotoMemberCenter(String mobile,HttpServletRequest request,ModelMap model){
		MemberBean bean = getMemberBean(request);
		model.addAttribute("bean", bean);
		return "redirect:/user/order/list";
	}
	
	/**
	 * 销毁session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/destory")
	@ResponseBody
	public String destory(HttpServletRequest request, HttpServletResponse response){
		
		request.getSession().invalidate();
		
		return ajaxSuccess("成功", response);
	}
	
	
}
