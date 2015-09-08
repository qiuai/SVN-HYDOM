package com.hydom.user.action;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Member;
import com.hydom.account.service.MemberService;
import com.hydom.core.server.service.CarService;
import com.hydom.user.ebean.UserCar;
import com.hydom.user.service.UserCarService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.MemberBean;

/**
 * @Description 车管家控制层
 * @author WY
 * @date 2015年7月28日 下午4:49:04
 */

@RequestMapping("/user/carSteward")
@Controller
public class UserCarAction extends BaseAction{
	
	@Resource
	private CarService carService;
	@Resource
	private UserCarService userCarService;
	@Resource
	private MemberService memberService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	/**增加*/
	@RequestMapping("/add")
	public @ResponseBody ModelAndView add() {
		ModelAndView mav = new ModelAndView("web/center/carSteward_add");
		return mav;
	}
	
	/**保存*/
	@RequestMapping("/save")
	public @ResponseBody ModelAndView save(UserCar userCar, String carId) {
		MemberBean bean = getMemberBean(request);
		if(bean!=null && bean.getMember().getId()!=null){
			String s = userCar.getId();
			if(null==userCar.getId() || "".equals(userCar.getId())){
				userCar.setCar(carService.find(carId));
				userCar.setMember(bean.getMember());
				
				String jpql = "o.member.id=?1";
				Object[] params = new Object[]{bean.getMember().getId()};
				if(0==userCarService.getScrollData(-1, -1, jpql, params).getResultList().size()){
					userCar.setDefaultCar(true);
				}else{
					userCar.setDefaultCar(false);
				}
				
				userCarService.save(userCar);
			}else{
				userCar.setCar(carService.find(carId));
				userCar.setMember(bean.getMember());
				userCarService.update(userCar);
			}
			//刷新session数据
			Member member = memberService.find(bean.getId());
			request.getSession().setAttribute(MemberBean.MEMBER_SESSION, MemberBean.convert2MemberBean(member));
			
			ModelAndView mav = new ModelAndView("redirect:list");
			return  mav;
		}
		return null;
	}
	
	/**显示*/
	@RequestMapping("/list")
	public @ResponseBody ModelAndView list() {
		MemberBean bean = getMemberBean(request);
		if(bean!=null && bean.getMember().getId()!=null){
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("createDate", "desc");
			String jpql = "o.member.id=?1";
			Object[] params = new Object[]{bean.getMember().getId()};
			ModelAndView mav = new ModelAndView("web/center/carSteward_list");
			mav.addObject("userCars", userCarService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
			return mav;
		}
		return null;
	}
	
	/**修改*/
	@RequestMapping("/update")
	public @ResponseBody ModelAndView update(String userCarId) {
		ModelAndView mav = new ModelAndView("web/center/carSteward_add");
		mav.addObject("userCar", userCarService.find(userCarId));
		return mav;
	}
	
	/**设为默认车型*/
	@RequestMapping("/setDefaultCar")
	public @ResponseBody String setDefaultCar(String userCarId) {
		try {
			MemberBean bean = getMemberBean(request);
			if(bean!=null && bean.getMember().getId()!=null){
				userCarService.resetDefaultCar(bean.getMember().getId(), userCarId);
				//刷新session数据
				Member member = memberService.find(bean.getId());
				request.getSession().setAttribute(MemberBean.MEMBER_SESSION, MemberBean.convert2MemberBean(member));
				
				return ajaxSuccess("设置成功！", response);
			}
			return ajaxError("删除失败！", response);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxError("设置失败！", response);
		}
	}
	
	/**删除*/
	@RequestMapping("/del")
	public @ResponseBody String del(String userCarId) {
		try {
			MemberBean bean = getMemberBean(request);
			if(bean==null){
				return ajaxError("删除失败！", response);
			}
			String memberId = bean.getMember().getId();
			UserCar uc = userCarService.find(userCarId);
			if(uc.getDefaultCar()!=true){
				if(!uc.getMember().getId().equals(memberId)){
					return ajaxError("账户信息异常！", response);
				}
				if(userCarId!=null){
					userCarService.delete(userCarId);
					return ajaxSuccess("删除成功！", response);
				}
			}
			return ajaxError("不能删除默认车型！", response);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxError("删除失败！", response);
		}
	}
}
