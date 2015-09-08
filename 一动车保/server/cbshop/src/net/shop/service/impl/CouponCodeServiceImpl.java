/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import net.shop.Page;
import net.shop.Pageable;
import net.shop.dao.CouponCodeDao;
import net.shop.dao.MemberDao;
import net.shop.entity.Coupon;
import net.shop.entity.CouponCode;
import net.shop.entity.Member;
import net.shop.service.CouponCodeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 优惠码
 * 
 * 
 * 
 */
@Service("couponCodeServiceImpl")
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;

	@Resource(name = "couponCodeDaoImpl")
	public void setBaseDao(CouponCodeDao couponCodeDao) {
		super.setBaseDao(couponCodeDao);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean codeExists(String code) {
		return couponCodeDao.codeExists(code);
	}

	@Override
	@Transactional(readOnly = true)
	public CouponCode findByCode(String code) {
		return couponCodeDao.findByCode(code);
	}

	@Override
	public CouponCode build(Coupon coupon, Member member) {
		return couponCodeDao.build(coupon, member);
	}

	@Override
	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		return couponCodeDao.build(coupon, member, count);
	}

	@Override
	public CouponCode exchange(Coupon coupon, Member member) {
		Assert.notNull(coupon);
		Assert.notNull(member);

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		member.setPoint(member.getPoint() - coupon.getPoint());
		memberDao.merge(member);

		return couponCodeDao.build(coupon, member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		return couponCodeDao.findPage(member, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		return couponCodeDao.count(coupon, member, hasBegun, hasExpired, isUsed);
	}

}