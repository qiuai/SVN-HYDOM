/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.PaymentMethodDao;
import net.shop.entity.PaymentMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 支付方式
 * 
 * 
 * 
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}