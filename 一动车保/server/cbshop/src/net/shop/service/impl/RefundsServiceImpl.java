/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.RefundsDao;
import net.shop.entity.Refunds;
import net.shop.service.RefundsService;

import org.springframework.stereotype.Service;

/**
 * Service - 退款单
 * 
 * 
 * 
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

}