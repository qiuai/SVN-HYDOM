/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ReturnsDao;
import net.shop.entity.Returns;
import net.shop.service.ReturnsService;

import org.springframework.stereotype.Service;

/**
 * Service - 退货单
 * 
 * 
 * 
 */
@Service("returnsServiceImpl")
public class ReturnsServiceImpl extends BaseServiceImpl<Returns, Long> implements ReturnsService {

	@Resource(name = "returnsDaoImpl")
	public void setBaseDao(ReturnsDao returnsDao) {
		super.setBaseDao(returnsDao);
	}

}