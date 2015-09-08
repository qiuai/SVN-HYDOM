/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.CartItemDao;
import net.shop.entity.CartItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 购物车项
 * 
 * 
 * 
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}