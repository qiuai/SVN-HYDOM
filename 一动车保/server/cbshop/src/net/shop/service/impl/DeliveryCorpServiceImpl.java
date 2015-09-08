/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.DeliveryCorpDao;
import net.shop.entity.DeliveryCorp;
import net.shop.service.DeliveryCorpService;

import org.springframework.stereotype.Service;

/**
 * Service - 物流公司
 * 
 * 
 * 
 */
@Service("deliveryCorpServiceImpl")
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, Long> implements DeliveryCorpService {

	@Resource(name = "deliveryCorpDaoImpl")
	public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
		super.setBaseDao(deliveryCorpDao);
	}

}