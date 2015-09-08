package com.hydom.user.action;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.service.CouponService;
import com.hydom.util.BaseAction;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.dao.PageView;

/**
 * @Description 我的优惠券控制层
 * @author WY
 * @date 2015年7月31日 下午2:53:17
 */

@RequestMapping("/user/myCoupon")
@Controller
public class MyCouponAction extends BaseAction{

	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private CouponService couponService;
	@Resource
	private MemberService memberService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private int maxresult = 10;
	/**优惠券列表*/
	@RequestMapping("/list")
	public @ResponseBody ModelAndView list() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("type", "desc");
		String jpql = "o.visible=?1 and o.isExchange=?2";
		Object[] params = new Object[]{true,true};
		
		ModelAndView mav = new ModelAndView("web/center/myCoupon");
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		if(memberId!=null){
			mav.addObject("amount", memberService.find(memberId).getAmount());
		}else{
			mav.addObject("amount", "请登录");
		}
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		return mav;
	}
	
	/**兑换优惠券*/
	@RequestMapping("/convert")
	public @ResponseBody String convert(String id) {
		MemberBean bean = getMemberBean(request);
		if(bean.getMember().getId()!=null){
			String memberId = bean.getMember().getId();
			Member member = memberService.find(memberId);
			Coupon coupon = couponService.find(id);
			if(coupon.getIsExchange()){
				Float point = (float) 0;
				if(coupon.getPoint()!=null){
					point = Float.parseFloat(coupon.getPoint()+"");
				}
				Float amount = member.getAmount();
				if(amount==null) amount = (float) 0;
				if(point <= amount){
					MemberCoupon memberCoupon = new MemberCoupon();
					memberCoupon.setName(coupon.getName());
					memberCoupon.setBeginDate(coupon.getBeginDate());
					memberCoupon.setEndDate(coupon.getEndDate());
					memberCoupon.setIntroduction(coupon.getIntroduction());
					memberCoupon.setType(coupon.getType());
					memberCoupon.setUseType(coupon.getUseType());
					memberCoupon.setDiscount(coupon.getDiscount());
					memberCoupon.setRate(coupon.getRate());
					memberCoupon.setMinPrice(coupon.getMinPrice());
					memberCoupon.setImgPath(coupon.getImgPath());
					memberCoupon.setGainWay(1);
					memberCoupon.setPoint(0);
					memberCoupon.setMember(member);
					memberCoupon.setCoupon(coupon);
					memberCoupon.setReceiveDate(new Date());
					memberCouponService.save(memberCoupon);
					member.setAmount(amount-point);
					memberService.update(member);
					return ajaxSuccess("领取成功！", response);
				}else{
					return ajaxError("积分不足！", response);
				}
			}
		}
		return ajaxError("领取失败！", response);
	}
	
	/**优惠券历史记录*/
	@RequestMapping("/history")
	public @ResponseBody ModelAndView history(@RequestParam(defaultValue="1") Integer page) {
		
		MemberBean bean = getMemberBean(request);
		if(bean!=null && bean.getMember().getId()!=null){
			String memberId = bean.getMember().getId();
			
			PageView<MemberCoupon> pageView = new PageView<MemberCoupon>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("receiveDate", "desc");
			String jpql = "o.member.id = ?1";
			Object[] params = new Object[]{memberId};
//			pageView.setHql(jpql);
//			pageView.setParams(params);
//			pageView.setOrderby(orderby);
//			pageView = memberCouponService.getPage(pageView);
			
			pageView.setQueryResult(memberCouponService.getScrollData(pageView.getFirstResult(), maxresult, jpql, params, orderby));
			
			ModelAndView mav = new ModelAndView("web/center/myCoupon_history");
			mav.addObject("pageView", pageView);
			return mav;
		}
		return null;
	}
}
