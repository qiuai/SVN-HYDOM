package com.hydom.core.server.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Member;
import com.hydom.account.ebean.MemberCoupon;
import com.hydom.account.service.FeeRecordService;
import com.hydom.account.service.MemberCouponService;
import com.hydom.account.service.MemberService;
import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.util.dao.DAOSupport;

@Service
public class CouponPackageRecordServiceBean extends
		DAOSupport<CouponPackageRecord> implements CouponPackageRecordService {
	@Resource
	private FeeRecordService feeRecordService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberCouponService memberCouponService;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void memberCarBuy(FeeRecord feeRecord, Member member) {
		memberService.update(member);
		feeRecord.setVisible(true);
		feeRecordService.save(feeRecord);
		returnCoupon(feeRecord);
	}

	@Override
	public void returnCoupon(FeeRecord feeRecord) {
		CouponPackageRecord couponPackageRecord = feeRecord
				.getCouponPackageRecord();
		Coupon coupon = couponPackageRecord.getCoupon();
		Member member = feeRecord.getMember();
		int count = couponPackageRecord.getCouponCount();
		for (int i = 0; i < count; i++) {
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
			memberCoupon.setGainWay(4); // 购买券包
			memberCoupon.setReceiveDate(new Date());
			memberCoupon.setCouponPackageRecord(couponPackageRecord);
			memberCouponService.save(memberCoupon);
		}
	}
}
