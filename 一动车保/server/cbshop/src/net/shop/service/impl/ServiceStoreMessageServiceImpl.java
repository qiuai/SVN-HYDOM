/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ServiceStoreMessageDao;
import net.shop.entity.ServiceStoreMessage;
import net.shop.service.ServiceStoreMessageService;

import org.springframework.stereotype.Service;

/**
 * Service - 服务门店
 * 
 * 
 * 
 */
@Service("serviceStoreMessageServiceImpl")
public class ServiceStoreMessageServiceImpl extends BaseServiceImpl<ServiceStoreMessage, Long> implements ServiceStoreMessageService{
	
	@Resource(name="serviceStoreMessageDaoImpl")
	private ServiceStoreMessageDao serviceStoreMessageDao;
	
	public void setBaseDao(ServiceStoreMessageDao serviceStoreMessageDao){
		super.setBaseDao(serviceStoreMessageDao);
	}
	
}