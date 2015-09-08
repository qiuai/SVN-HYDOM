/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import net.shop.dao.PaymentDao;
import net.shop.entity.Payment;

import org.springframework.stereotype.Repository;

/**
 * Dao - 收款单
 * 
 * 
 * 
 */
@Repository("paymentDaoImpl")
public class PaymentDaoImpl extends BaseDaoImpl<Payment, Long> implements PaymentDao {

	@Override
	public Payment findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select payment from Payment payment where lower(payment.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Payment.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}