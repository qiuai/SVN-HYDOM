/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.Page;
import net.shop.Pageable;
import net.shop.dao.DepositDao;
import net.shop.entity.Deposit;
import net.shop.entity.Member;
import net.shop.service.DepositService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 预存款
 * 
 * 
 * 
 */
@Service("depositServiceImpl")
public class DepositServiceImpl extends BaseServiceImpl<Deposit, Long> implements DepositService {

	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;

	@Resource(name = "depositDaoImpl")
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Deposit> findPage(Member member, Pageable pageable) {
		return depositDao.findPage(member, pageable);
	}

}