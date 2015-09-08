/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.AttributeDao;
import net.shop.entity.Attribute;
import net.shop.service.AttributeService;

import org.springframework.stereotype.Service;

/**
 * Service - 属性
 * 
 * 
 * 
 */
@Service("attributeServiceImpl")
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, Long> implements AttributeService {

	@Resource(name = "attributeDaoImpl")
	public void setBaseDao(AttributeDao attributeDao) {
		super.setBaseDao(attributeDao);
	}

}