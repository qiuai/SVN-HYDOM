/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ShippingMethodDao;
import net.shop.entity.ShippingMethod;
import net.shop.service.ShippingMethodService;

import org.springframework.stereotype.Service;

/**
 * Service - 配送方式
 * 
 * 
 * 
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}

}