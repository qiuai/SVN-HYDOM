package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.ServiceType;
import com.hydom.util.dao.DAO;

public interface ServiceTypeService extends DAO< ServiceType> {
	
	/**
	 * 根据类型获取服务类型 服务类型
	 * @param i
	 * @return
	 */
	
	List<ServiceType> getServiceType(Integer i);
	
	/**
	 * 根据服务名称获取服务
	 * @param name
	 * @return
	 */
	ServiceType getEntitybyName(String name);

	
}
