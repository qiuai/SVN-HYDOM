/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.OrderLogDao;
import net.shop.entity.OrderLog;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单日志
 * 
 * 
 * 
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

}