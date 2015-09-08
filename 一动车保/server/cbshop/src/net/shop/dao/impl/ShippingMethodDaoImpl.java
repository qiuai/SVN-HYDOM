/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.ShippingMethodDao;
import net.shop.entity.ShippingMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 配送方式
 * 
 * 
 * 
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

}