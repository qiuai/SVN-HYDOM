package com.hydom.account.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.service.CouponService;
import com.hydom.util.bean.MemberBean;
import com.hydom.util.dao.DAOSupport;

@Service("memberCouponService")
public class MemberCouponServiceBean extends DAOSupport<MemberCoupon> implements
		MemberCouponService {
	@Resource
	private CouponService couponService;
	@Resource
	private MemberService memberService;
	@Resource
	private ProductService productService;

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberCoupon> canUseList(double pay, String memberId,
			Object[] pids, int useType, int callOri) {
		try {
			if (pids != null && pids.length > 0) { // 检查产品是否支持使用优惠券
				for (Object pid : pids) {
					Product product = productService.find(pid.toString());
					if (product.getUseCoupon() == null
							|| product.getUseCoupon() != 0) { // 0可以使用
						return null;
					}
					if (product.getProuductUniqueType() != null
							&& product.getProuductUniqueType() == 3
							&& callOri == 1) {// 特价商品且来源app则不能使用优惠券
						return null;
					}
				}
			}
			Date now = new Date();
			// 起始日期<=当前日期；结束日期>=当前日期（或为null值的无限期）；未使用；
			Query query = em
					.createQuery(
							"select o from  MemberCoupon o where (o.beginDate<=?1 or o.beginDate is null) and (o.endDate is null or o.endDate>=?2) and o.status=?3 and o.member.id=?4 and ((o.type=1 and o.minPrice<=?5) or (o.type=2 and o.minPrice <=?6) or o.type=3) and o.useType=?7")
					.setParameter(1, now).setParameter(2, now)
					.setParameter(3, 0).setParameter(4, memberId)
					.setParameter(5, pay).setParameter(6, pay)
					.setParameter(7, useType);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean canUse(double pay, String memmberId, String[] pids,
			int useType, int callOri) {
		try {

			if (pids != null && pids.length > 0) { // 检查产品是否支持使用优惠券
				for (String pid : pids) {
					Product product = productService.find(pid);
					if (product.getUseCoupon() == null
							|| product.getUseCoupon() != 0) { // 0可以使用
						return false;
					}
					if (product.getProuductUniqueType() != null
							&& product.getProuductUniqueType() == 3
							&& callOri == 1) {// 特价商品且来源app则不能使用优惠券
						return false;
					}
				}
			}
			Date now = new Date();
			// 起始日期<=当前日期；结束日期>=当前日期（或为null值的无限期）；未使用；
			Long count = (Long) em
					.createQuery(
							"select count(o.id) from  MemberCoupon o where o.beginDate<=?1 and (o.endDate is null or o.endDate>=?2) and o.status=?3 and o.member.id=?4  and ((o.type=1 and o.minPrice<=?5) or (o.type=2 and o.minPrice<=?6) or o.type=3) and o.useType=?7")
					.setParameter(1, now).setParameter(2, now)
					.setParameter(3, 0).setParameter(4, memmberId)
					.setParameter(5, pay).setParameter(6, pay)
					.setParameter(7, useType).getSingleResult();
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MemberCoupon getCoupon(MemberBean bean, String couponId) {
		String jqpl = "form MemberCoupon o where 1=1 and o.coupon.id=:couponId and o.member.id=:memberId";
		Query query = em.createQuery(jqpl, MemberCoupon.class);
		query.setParameter("couponId", couponId);
		query.setParameter("memberId", bean.getMember().getId());
		return (MemberCoupon) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberCoupon> getCouponByMember(String id) {
		String hql = "select mc from MemberCoupon mc where mc.member.id = :memberId and (mc.coupon.endDate >= :nowDate or mc.coupon.endDate is null) and mc.status = 0 and mc.coupon.visible = :visible order by mc.createDate";
		Query query = em.createQuery(hql);
		query.setParameter("memberId", id);
		query.setParameter("nowDate", new Date());
		query.setParameter("visible", true);
		List<MemberCoupon> list = query.getResultList();
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int exchange(String uid, String cpid) {
		try {
			Member member = memberService.find(uid);
			Coupon coupon = couponService.find(cpid);
			int requireScore = (coupon.getPoint() == null ? 0 : coupon
					.getPoint());
			if (coupon.getIsExchange() && coupon.getIsEnabled()
					&& member.getAmount() >= requireScore) {// 可以兑换
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
				memberCoupon.setMember(member);
				memberCoupon.setCoupon(coupon);
				memberCoupon.setPoint(coupon.getPoint());
				memberCoupon.setGainWay(1); // 积分兑换
				memberCoupon.setReceiveDate(new Date());
				this.save(memberCoupon);
				member.setAmount(member.getAmount() - requireScore);
				memberService.update(member);
				return 1;
			} else {// 积分不足、或优惠券不支持积分兑换
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
