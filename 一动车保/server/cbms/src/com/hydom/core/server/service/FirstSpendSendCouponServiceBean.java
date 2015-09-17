package com.hydom.core.server.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Order;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.OrderService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.util.dao.DAOSupport;

/**
 * @Description 首次消费送优惠券业务层实现
 * @author WY
 * @date 2015年7月3日 下午2:41:31
 */

@Service
public class FirstSpendSendCouponServiceBean extends
		DAOSupport<FirstSpendSendCoupon> implements FirstSpendSendCouponService {
	@Resource
	private MemberCouponService memberCouponService;
	@Resource
	private OrderService orderService;
	private Log log = LogFactory.getLog("coreDataLog");

	@Override
	public void gainCoupon(Order order) {
		try {
			if (order.getFirstSendCoupon() != null
					&& order.getFirstSendCoupon()) {// 此订单已进行过首次消费送优惠券
				log.info("该订单已参与过首次消费赠送优惠券活动，订单ID=" + order.getId());
				return;
			}
			// 判断用户是否是首次洗车(保养或购买产品)
			long count = (Long) em
					.createQuery(
							"select count(o.id) from Order o where o.type=:type and o.member.id=:memberid and o.isPay=:ispay and o.id!='"
									+ order.getId() + "'")
					.setParameter("type", order.getType())
					.setParameter("memberid", order.getMember().getId())
					.setParameter("ispay", true).getSingleResult();
			/** 类型 1洗车订单 2保养订单 3纯商品订单 **/
			String otypeText = order.getType() == 1 ? "洗车"
					: order.getType() == 2 ? "保养" : "购物";
			if (count <= 0) {// 是首次洗车(保养或购买产品)
				// 获取能赠送的优惠券
				FirstSpendSendCoupon sendCoupon = (FirstSpendSendCoupon) em
						.createQuery(
								"select o from FirstSpendSendCoupon o where o.range=:type and o.minPrice<=:minPrice order by o.minPrice asc")
						.setParameter("type", order.getType())
						.setParameter("minPrice", order.getPrice())
						.setMaxResults(1).getSingleResult();
				int num = sendCoupon.getCouponCount();
				Coupon coupon = sendCoupon.getCoupon();
				log.info("用户(" + order.getMember().getMobile() + ")首次"
						+ otypeText + " 本次获赠优惠券ID：" + coupon.getId() + " 名称："
						+ coupon.getName() + " 数量：" + num);
				for (int i = 0; i < num; i++) {
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
					memberCoupon.setMember(order.getMember());
					memberCoupon.setCoupon(coupon);
					memberCoupon.setPoint(coupon.getPoint());
					memberCoupon.setGainWay(5); // 首次消费赠送
					memberCoupon.setReceiveDate(new Date());
					memberCouponService.save(memberCoupon);
				}
				// 更新订单状态为已首次赠送优惠券
				Order dbOrder = orderService.find(order.getId());
				dbOrder.setFirstSendCoupon(true);
				orderService.update(dbOrder);
				log.info("用户(" + order.getMember().getMobile() + ")共计获取优惠券数："
						+ num);
			}
			log.info("用户(" + order.getMember().getMobile() + ")非首次" + otypeText
					+ " 订单ID" + order.getId());
		} catch (Exception e) {
			log.info("首次赠送优惠券异常：" + e.toString());
		}

	}
}
