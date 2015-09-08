/*
 * 
 * 
 * 
 */
package net.shop.dao;

import net.shop.Page;
import net.shop.Pageable;
import net.shop.entity.Deposit;
import net.shop.entity.Member;

/**
 * Dao - 预存款
 * 
 * 
 * 
 */
public interface DepositDao extends BaseDao<Deposit, Long> {

	/**
	 * 查找预存款分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款分页
	 */
	Page<Deposit> findPage(Member member, Pageable pageable);

}