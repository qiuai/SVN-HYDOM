package com.hydom.user.action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Member;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.MemberService;
import com.hydom.api.action.UnionPay;
import com.hydom.api.action.WexinPay;
import com.hydom.core.server.ebean.CouponPackage;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.core.server.service.CouponPackageRecordService;
import com.hydom.core.server.service.CouponPackageService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonAttributes;
import com.hydom.util.CommonUtil;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.payUtil.UnionPayUtil;
import com.hydom.util.payUtil.WeChatPayUtil;

/**
 * @Description 我的优惠券控制层
 * @author WY
 * @date 2015年9月15日 下午2:53:17
 */

@RequestMapping("/user/couponPackage")
@Controller
public class BuyCouponPackageAction extends BaseAction{

	@Resource
	private CouponPackageRecordService couponPackageRecordService;
	@Resource
	private FeeRecordService feeRecordService;
	@Resource
	private MemberService memberService;
	@Resource
	private CouponPackageService couponPackageService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private ObjectMapper mapper = new ObjectMapper();
//	private String path = request.getContextPath();
//	private String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	/**优惠券列表*/
	@RequestMapping("/list")
	public @ResponseBody ModelAndView list() {
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("modifyDate", "desc");
		String jpql = "o.visible=?1 and o.isEnabled=?2";
		Object[] params = new Object[]{true,true};
		
		ModelAndView mav = new ModelAndView("web/center/couponPackage");
		MemberBean bean = getMemberBean(request);
		String memberId = bean.getMember().getId();
		if(memberId!=null){
			mav.addObject("couponPackages", couponPackageService.getScrollData(-1, -1, jpql, params, orderby).getResultList());
		}
		return mav;
	}
	
	/**购买会员卡*/
	@RequestMapping("/buy")
	public @ResponseBody String buy(String cpId, int payWay) {
		MemberBean bean = getMemberBean(request);
		if(bean.getMember().getId()!=null){
			try {
				Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
				CouponPackage couponPackage = couponPackageService.find(cpId);
				Member member = memberService.find(bean.getMember().getId());
				FeeRecord feeRecord = new FeeRecord();
				feeRecord.setRechargeNo(CommonUtil.getOrderNum());
				feeRecord.setMember(member);
				feeRecord.setPhone(member.getPhone());
				feeRecord.setFee(couponPackage.getPrice());
				feeRecord.setType(3);// 购买会员卡
				feeRecord.setVisible(false);
				feeRecord.setPayWay(payWay);
				/** 优惠券包记录 */
				CouponPackageRecord record = new CouponPackageRecord();
				record.setCoupon(couponPackage.getCoupon());
				record.setCouponCount(couponPackage.getCouponCount());
				record.setFeeRecord(feeRecord);
				record.setImgPath(couponPackage.getImgPath());
				record.setIntroduction(couponPackage.getIntroduction());
				record.setName(couponPackage.getName());
				record.setPrice(couponPackage.getPrice());
				record.setMember(member); 
				feeRecord.setCouponPackageRecord(record);
				if (payWay == 1) {// 会员卡
					double cpprice = couponPackage.getPrice();
					double userMoney = member.getMoney();
					if (userMoney >= cpprice) {// 余额足以支付
						member.setMoney(CommonUtil.subtract(userMoney + "", cpprice + ""));
						couponPackageRecordService.memberCarBuy(feeRecord, member);
						dataMap.put("result", "001");
						dataMap.put("payWay", "1");
						dataMap.put("payAction", "2");// 支付完成
					} else {
						dataMap.put("result", "001");
						dataMap.put("payWay", "1");
						dataMap.put("payAction", "3");// 余额不足
					}
				} else if (payWay == 2) {// 支付宝
					feeRecordService.save(feeRecord);
					// couponPackageRecordService.save(record);
					dataMap.put("result", "001");
					dataMap.put("orderNum", feeRecord.getRechargeNo());
					dataMap.put("orderPrice", feeRecord.getFee());
					dataMap.put("payWay", "2");
				} else if (payWay == 3) {// 银联
					feeRecordService.save(feeRecord);
					// couponPackageRecordService.save(record);
					dataMap.put("result", "001");
//					dataMap.put("num", UnionPay.payNumber(
//							feeRecord.getRechargeNo(), feeRecord.getFee(),
//							UnionPayUtil.recharge_return));
					dataMap.put("payAction", "1");// 调用支付接口
					dataMap.put("payWay", "3");
					String notify_url = CommonAttributes.getInstance().getPayURL()+"/web/pay/"+UnionPayUtil.recharge_return;
					String frontUrl = CommonAttributes.getInstance().getPayURL()+"/user/couponPackage/list";
					String html = new UnionPayUtil().getParameterMapHTML(feeRecord.getRechargeNo(), 
							CommonUtil.getLong(feeRecord.getFee(), 100,0), "会员卡购买", notify_url, frontUrl);
					dataMap.put("html", html);
				} else if (payWay == 4) { // 微信
					feeRecordService.save(feeRecord);
					// couponPackageRecordService.save(record);
					dataMap.put("result", "001");
					dataMap.put("num", WexinPay.weixinOrder(
							feeRecord.getRechargeNo(), feeRecord.getFee(),
							WeChatPayUtil.recharge_return,
							"微信充值：" + feeRecord.getFee(), request));
					dataMap.put("payAction", "1");// 调用支付接口
					dataMap.put("payWay", "4");
				} else {
					dataMap.put("result", "不支持的购买方式");// 不支持的购买方式
				}
				String json = mapper.writeValueAsString(dataMap);
				return json;
			} catch (Exception e) {
				e.printStackTrace();
				return "{\"result\":\"购买失败！\"}";
			}
		}else{
			return "{\"result\":\"请登录！\"}";
		}
	}
}
