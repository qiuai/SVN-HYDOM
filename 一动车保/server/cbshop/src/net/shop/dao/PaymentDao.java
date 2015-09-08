/*
 * 
 * 
 * 
 */
package net.shop.dao;

import net.shop.entity.Payment;

/**
 * Dao - 收款单
 * 
 * 
 * 
 */
public interface PaymentDao extends BaseDao<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);

}