/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ServicesTypeDao;
import net.shop.entity.ServicesType;
import net.shop.service.ServicesTypeService;

import org.springframework.stereotype.Service;

/**
 * Service - 服务
 * 
 * 
 * 
 */
@Service("servicesTypeServiceImpl")
public class ServicesTypeServiceImpl extends BaseServiceImpl<ServicesType, Long> implements ServicesTypeService{
	
	@Resource(name="servicesTypeDaoImpl")
	private ServicesTypeDao servicesTypeDao;
	
	@Resource(name="servicesTypeDaoImpl")
	public void setBaseDao(ServicesTypeDao servicesTypeDao){
		super.setBaseDao(servicesTypeDao);
	}
	
}