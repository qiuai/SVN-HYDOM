package com.hydom.util.payUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Order;
import com.hydom.account.ebean.Product;
import com.hydom.account.ebean.RechargeBenefits;
import com.hydom.account.ebean.ServerOrderDetail;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.account.service.OrderService;
import com.hydom.account.service.ProductService;
import com.hydom.account.service.RechargeBenefitsService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.util.CommonUtil;
import com.hydom.util.PushUtil;
import com.hydom.util.SpringUtil;

@Component
public class PayCommonUtil {
	private Log log = LogFactory.getLog("payLog");
	
	/**
	 * 订单保存
	 * @param out_trade_no 订单编号
	 * @param trade_no 商家交易号
	 * @param total_fee 交易金额
	 * @param type 交易类型
	 * @return
	 */
	public String saveServiceOrder(String confimId,String tradeNum,String total_fee,Integer type){
		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		MemberCouponService memberCouponService = (MemberCouponService) SpringUtil.getBean("memberCouponService");
		FeeRecordService feeRecordService = (FeeRecordService) SpringUtil.getBean("feeRecordService");
		ProductService productService = (ProductService) SpringUtil.getBean("productService");
		MemberService memberService = (MemberService) SpringUtil.getBean("memberService");
		log.info("消费编号："+confimId + "进入保存流程");
		try {
			//查询该订单
			Order order = orderService.getOrderByOrderNum(confimId);
			
			if(order == null){
				return "fail";
			}
			
			if(order.getIsPay()){
				log.info("消费编号："+confimId + "以保存");
				return "";
			}
			
			//BigDecimal fb = new BigDecimal(0).setScale(2, BigDecimal.ROUND_DOWN);
			
			if(!CommonUtil.compareToFloat(total_fee, order.getPrice()+"")){
				return "fail";
			}
			
			if(order.getMemberCoupon() != null){
				MemberCoupon memberCoupon = order.getMemberCoupon();
				memberCoupon.setStatus(1);
				memberCouponService.update(memberCoupon);
			}
			
			//保存一条消费记录
			FeeRecord feeRecord = order.getFeeRecord();
			/*if(feeRecord == null){
				feeRecord = new FeeRecord();
			}*/
			feeRecord.setType(2);
			feeRecord.setOrder(order);
			feeRecord.setPayWay(order.getPayWay());
			feeRecord.setPhone(order.getPhone());
			feeRecord.setTradeNo(tradeNum);
			feeRecord.setFee(order.getPrice());
			feeRecord.setMember(order.getMember());
			feeRecord.setVisible(true);
			feeRecordService.update(feeRecord);
			/*if(StringUtils.isNotEmpty(feeRecord.getId())){
				feeRecordService.update(feeRecord);
			}else{
				feeRecordService.save(feeRecord);
			}*/
			
			order.setIsPay(true);
			orderService.update(order);
			
			if(order.getType() == 1){//洗车订单
				PushUtil.pushTechnician(order.getId());
			}
			//将商品已买数量+1
			if(order.getType() == 3 || order.getType() == 2){//保养 或者 纯商品服务
				Set<ServerOrderDetail> sets = order.getServerOrderDetail();
				for(ServerOrderDetail detail : sets){
					Product product = detail.getProduct();
					Integer count = product.getBuyProductCount();
					product.setBuyProductCount(count+1);
					productService.update(product);
				}
			}
			
			log.info("消费编号："+feeRecord.getTradeNo() + "保存成功");
			//删除payOrder缓存
		//	CachedManager.remove("payOrder", confimId);
			//删除order缓存
			//CachedManager.remove("order", confimId);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}
	
	/**
	 * 充值接口
	 * @param out_trade_no 订单编号
	 * @param trade_no 商家交易号
	 * @param total_fee 交易金额
	 * @param type 交易类型   消费方式 比如 支付宝 银联 1=会员卡支付；2=支付宝 ；3=银联； 4=微信 5 现金支付
	 * @return
	 */
	public String saveRecharge(String out_trade_no,String trade_no,String total_fee,Integer type){
		MemberService memberService = (MemberService) SpringUtil.getBean("memberService");
		FeeRecordService feeRecordService = (FeeRecordService) SpringUtil.getBean("feeRecordService");
		RechargeBenefitsService benefitsService = (RechargeBenefitsService) SpringUtil.getBean("rechargeBenefitsService");
		MemberCouponService couponService = (MemberCouponService) SpringUtil.getBean("memberCouponService");
		log.info("充值编号："+out_trade_no + "进入充值保存流程");
		try{
			
			FeeRecord feeRecord = feeRecordService.findByRechargeNum(out_trade_no);
			
			if(feeRecord == null){
				return "fail";
			}
			
			if(!CommonUtil.compareToFloat(total_fee, feeRecord.getFee()+"")){
				return "fail";
			}
			
			feeRecord.setType(1);
			feeRecord.setPayWay(type);
			feeRecord.setVisible(true);
			feeRecord.setTradeNo(trade_no);
			feeRecordService.update(feeRecord);
			
			Member member = feeRecord.getMember();
			
			Float fanxian = 0f;
			List<RechargeBenefits> benefits = benefitsService.getListByMoney(total_fee);
			if(benefits != null && benefits.size() > 0){
				RechargeBenefits benefits2 = benefits.get(0);
				if(benefits2.getType() == 1){//福利类型：1=充值返现；2=充值送优惠券
					fanxian = CommonUtil.mul(total_fee, benefits2.getScale()+"");
				}else if(benefits2.getType() == 2){
					Coupon coupon = benefits2.getCoupon();
					if(benefits2.getCouponNumber() != null){
						MemberCoupon memberCoupon = null;
						for(long i = 0l; i < benefits2.getCouponNumber(); i++){
							memberCoupon = new MemberCoupon();
							memberCoupon.setCoupon(coupon);
							memberCoupon.setMember(feeRecord.getMember());
							
							memberCoupon.setUseType(coupon.getUseType());
							memberCoupon.setName(coupon.getName());
							memberCoupon.setBeginDate(coupon.getBeginDate());
							memberCoupon.setEndDate(coupon.getEndDate());
							memberCoupon.setIntroduction(coupon.getIntroduction());
							memberCoupon.setGainWay(3);//优惠券获取方法：1=积分兑换；2=系统发放；3=充值赠送
							memberCoupon.setType(coupon.getType());
							memberCoupon.setDiscount(coupon.getDiscount());
							memberCoupon.setMinPrice(coupon.getMinPrice());
							memberCoupon.setRate(coupon.getRate());
							memberCoupon.setImgPath(coupon.getImgPath());
							memberCoupon.setReceiveDate(new Date());
							memberCoupon.setStatus(0);
							memberCoupon.setPoint(coupon.getPoint());
							
							couponService.save(memberCoupon);
						}
					}
				}
			}
			
			fanxian = CommonUtil.add(feeRecord.getFee()+"",fanxian+"");//计算返现金额以及充值福利
			
			Float sum = CommonUtil.add(member.getMoney()+"",fanxian+"");
			member.setMoney(sum);
			memberService.update(member);
			
			log.info("消费编号："+out_trade_no + "进入充值保存成功");
			return "success";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "fail";
	}
	
	/**
	 * 退款接口
	 * @param tradeNo 商家交易号
	 * @param price 推荐金额
	 * @param status 状态
	 * @param batch_no 商家退款交易号
	 */
	public String refundFeeRecord(String tradeNo,String price,String status, String batch_no){
		
		OrderService orderService = (OrderService) SpringUtil.getBean("orderService");
		FeeRecordService feeRecordService = (FeeRecordService) SpringUtil.getBean("feeRecordService");
		MemberCouponService couponService = (MemberCouponService) SpringUtil.getBean("memberCouponService");
		if("SUCCESS".equals(status)){
			FeeRecord feeRecord = feeRecordService.findByHql("select o from com.hydom.account.ebean.FeeRecord o where o.tradeNo = '"+tradeNo+"'");
			Order order = feeRecord.getOrder();
			order.setStatus(34);
			order.setModifyDate(new Date());
			orderService.update(order);
			
			if(order.getMemberCoupon() != null){//如果使用优惠卷 优惠卷返回
				MemberCoupon memberCoupon = order.getMemberCoupon();
				memberCoupon.setStatus(0);
				memberCoupon.setUseDate(null);
				couponService.update(memberCoupon);
			}
			feeRecord.setModifyDate(new Date());
			feeRecord.setIsRefund(1);
			feeRecord.setRefundNo(batch_no);
			feeRecordService.update(feeRecord);
			return "success";
		}
		return "fail";
	}
}
