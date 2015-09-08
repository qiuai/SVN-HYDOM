package com.hydom.core.server.action;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

/**
 * @Description 汽车品牌控制层
 * @author WY
 * @date 2015年8月18日 下午3:36:05
 */

@RequestMapping("/manage/sendCoupon")
@Controller
public class SendCouponAction extends BaseAction{
	
	@Resource
	private MemberService memberService;
	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private CouponService couponService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	/**
	 * 添加
	 */
	@RequestMapping("/add")
	public ModelAndView addUI() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible=?1 and o.useType=1";
		Object[] params = new Object[]{true};
		
		ModelAndView mav = new ModelAndView("/sendCoupon/sendCoupon_add");
		mav.addObject("m", 9);
		mav.addObject("coupons", couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		return mav;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public @ResponseBody String save(String type, String mobile, @RequestParam(defaultValue = "1") Integer num) {
		try {
			if(mobile!=null){
				LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
				orderby.put("createDate", "desc");
				String jpql = "o.mobile=?1";
				Object[] params = new Object[]{mobile};
				List<Member> mlist = memberService.getScrollData(-1, -1, jpql, params, orderby).getResultList();
				Member member = null;
				if(mlist.size()!=0) member = mlist.get(0);
				if(member!=null){
					for(int i=0;i<num;i++){
						Coupon coupon = couponService.find(type);
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
						memberCoupon.setGainWay(2);
						memberCoupon.setPoint(0);
						memberCoupon.setMember(member);
						memberCoupon.setCoupon(coupon);
						memberCoupon.setReceiveDate(new Date());
						memberCouponService.save(memberCoupon);
					}
					return ajaxSuccess("发放成功！", response);
				}
			}
			return ajaxError("发放失败！", response);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxError("发放失败！", response);
		}
		
	}
	
	/**
	 * 验证会员是否存在
	 */
	@RequestMapping("/checkMember")
	public @ResponseBody
	boolean checkMember(String mobile) {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.mobile=?1";
		Object[] params = new Object[]{mobile};
		List<Member> list = memberService.getScrollData(-1, -1, jpql, params, orderby).getResultList();
		if(list.size()!=0){
			Member member = list.get(0);
			if(member!=null) return true;
		}
		return false;
	}
	
	/**
	 * 返回优惠券种类
	 */
	@RequestMapping("/getCoupon")
	public @ResponseBody
	List<Coupon> getCoupon(@RequestParam Integer useTypeId) {
		if(null == useTypeId) return null;
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createDate", "desc");
		String jpql = "o.visible=?1 and o.useType=?2";
		Object[] params = new Object[]{true,useTypeId};
		return couponService.getScrollData(-1, -1, jpql, params, orderby).getResultList();
	}
}
