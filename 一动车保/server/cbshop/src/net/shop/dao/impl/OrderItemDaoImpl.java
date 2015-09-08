/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.OrderItemDao;
import net.shop.entity.OrderItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * 
 * 
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}