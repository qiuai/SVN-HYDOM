package com.hydom.account.service;


import java.math.BigDecimal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.RechargeBenefits;
import com.hydom.util.dao.DAOSupport;

@Service("rechargeBenefitsService")
public class RechargeBenefitsServiceBean extends DAOSupport<RechargeBenefits>
		implements RechargeBenefitsService {
	@Override
	public boolean isExist(String money) {
		BigDecimal bd=new BigDecimal(money);
		System.out.println("充值金额："+bd);
		RechargeBenefits rechargeBenefits = null;
		try {
			rechargeBenefits = (RechargeBenefits) em.createQuery("select o from RechargeBenefits o where o.money=?1 and o.visible = 1")
					.setParameter(1, bd).getSingleResult();
		} catch (NoResultException e) {
		}
		if(rechargeBenefits==null){
			return false;
		}else{
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeBenefits> getListByMoney(String total_fee) {
		try{
			
			String sql = "select o from RechargeBenefits o where o.money <= :money and o.enable = :enable and o.visible = :visible order by o.money desc";
			Query query = em.createQuery(sql);
			BigDecimal b1 = new BigDecimal(total_fee);
			query.setParameter("money", b1);
			query.setParameter("enable", true);
			query.setParameter("visible", true);
			return query.getResultList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
