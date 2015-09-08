/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.ServiceStoreDao;
import net.shop.entity.ServiceStore;

import org.springframework.stereotype.Repository;

/**
 * Dao - 服务门店
 * 
 * 
 * 
 */
@Repository("serviceStoreDaoImpl")
public class ServiceStoreDaoImpl extends BaseDaoImpl<ServiceStore, Long> implements ServiceStoreDao {

}