package com.hydom.core.server.service;

import com.hydom.account.ebean.FeeRecord;
import com.hydom.account.ebean.Member;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.util.dao.DAO;

public interface CouponPackageRecordService extends DAO<CouponPackageRecord> {

	/***
	 * 会员卡的方式购买优惠券<br>
	 * 此方法只是将最终的数据持久化放到同一事务中，并将购买的会员卡返回优惠券给用户 ，因此在调用前应作以下数据准备：<br>
	 * <1>消费记录数据的初始化，包括购买时与会员卡记录表(CouponPackageRecord)的绑定<br>
	 * <2>对用户进行扣费(包括逻辑上判用户的余额是否足于支付)<br>
	 * 
	 * @param feeRecord
	 * @param member
	 */
	public void memberCarBuy(FeeRecord feeRecord, Member member);

	/**
	 * 购买成功后返优惠券给用户
	 */
	public void returnCoupon(FeeRecord feeRecord);
}
