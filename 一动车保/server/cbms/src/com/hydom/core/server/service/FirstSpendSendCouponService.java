package com.hydom.core.server.service;

import com.hydom.account.ebean.Order;
import com.hydom.core.server.ebean.FirstSpendSendCoupon;
import com.hydom.util.dao.DAO;

/**
 * @Description 首次消费送优惠券业务层接口
 * @author WY
 * @date 2015年7月3日 下午2:39:33
 */

public interface FirstSpendSendCouponService extends DAO<FirstSpendSendCoupon> {

	/**
	 * 首次消费送优惠券，实现的主要功能如下<br>
	 * <1>判断用户是否是首次洗车(保养/购买商品)，如果不是则不进行任何操作<br>
	 * <2>如果是查出符合赠送条件(订单消费金额满足最低消费金额要求)的优惠券<br>
	 * <3>如果有符合条件有多个、则最低消费金额要求最少的赠送给用户
	 * 
	 */
	public void gainCoupon(Order order);

}
