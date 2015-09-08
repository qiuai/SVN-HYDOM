/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ServiceStoreDao;
import net.shop.entity.ServiceStore;
import net.shop.service.ServiceStoreService;

import org.springframework.stereotype.Service;

/**
 * Service - 服务门店
 * 
 * 
 * 
 */
@Service("serviceStoreServiceImpl")
public class ServiceStoreServiceImpl extends BaseServiceImpl<ServiceStore, Long> implements ServiceStoreService{
	
	@Resource(name="serviceStoreDaoImpl")
	private ServiceStoreDao serviceStoreDao;
	
	public void setBaseDao(ServiceStoreDao serviceStoreDao){
		super.setBaseDao(serviceStoreDao);
	}
	
}