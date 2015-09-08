package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.SystemPrivilege;
import com.hydom.dao.DAOSupport;

@Service
public class SystemPrivilegeServiceBean extends DAOSupport<SystemPrivilege> implements
		SystemPrivilegeService {

	@Override
	public void saves(List<SystemPrivilege> sps) {
		for (SystemPrivilege sp : sps) {
			super.save(sp);
		}
	}

	@Override
	public SystemPrivilege findByURL(String url) {
		Query query = em.createQuery("select o from SystemPrivilege o where o.url=?1")
				.setParameter(1, url);
		try {
			SystemPrivilege sp = (SystemPrivilege) query.getSingleResult();
			return sp;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemPrivilege> listBylevel(int level) {
		return em.createQuery("select o from SystemPrivilege o where o.level=?1")
				.setParameter(1, level).getResultList();

	}
}
