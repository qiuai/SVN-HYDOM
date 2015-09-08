/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.DeliveryTemplateDao;
import net.shop.entity.DeliveryTemplate;
import net.shop.service.DeliveryTemplateService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 快递单模板
 * 
 * 
 * 
 */
@Service("deliveryTemplateServiceImpl")
public class DeliveryTemplateServiceImpl extends BaseServiceImpl<DeliveryTemplate, Long> implements DeliveryTemplateService {

	@Resource(name = "deliveryTemplateDaoImpl")
	private DeliveryTemplateDao deliveryTemplateDao;

	@Resource(name = "deliveryTemplateDaoImpl")
	public void setBaseDao(DeliveryTemplateDao deliveryTemplateDao) {
		super.setBaseDao(deliveryTemplateDao);
	}

	@Override
	@Transactional(readOnly = true)
	public DeliveryTemplate findDefault() {
		return deliveryTemplateDao.findDefault();
	}

}