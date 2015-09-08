package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.RechargeBenefits;
import com.hydom.util.dao.DAO;

public interface RechargeBenefitsService extends DAO<RechargeBenefits> {
	
	public boolean isExist(String money);
	/**
	 * 根据金额 获取充值福利列表
	 * @param total_fee
	 * @return
	 */
	List<RechargeBenefits> getListByMoney(String total_fee);

}
