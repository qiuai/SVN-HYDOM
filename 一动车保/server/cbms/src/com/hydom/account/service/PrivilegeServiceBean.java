package com.hydom.account.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Privilege;
import com.hydom.account.ebean.PrivilegeCategory;
import com.hydom.util.dao.DAOSupport;

@Service
public class PrivilegeServiceBean extends DAOSupport<Privilege> implements
		PrivilegeService {

	@SuppressWarnings("unchecked")
	public Map<String, List<Privilege>> listCategory() {
		List<PrivilegeCategory> categoryList = em.createQuery(
				"select o from PrivilegeCategory o order by o.lv asc")
				.getResultList();
		Map<String, List<Privilege>> map = new LinkedHashMap<String, List<Privilege>>();
		for (PrivilegeCategory pc : categoryList) {
			List<Privilege> privilegeList = em
					.createQuery(
							"select o from Privilege o where o.privilegeCategory.id=?1 order by o.lv asc")
					.setParameter(1, pc.getId()).getResultList();
			map.put(pc.getName(), privilegeList);
		}
		return map;
	}

	@Override
	public void saves(List<Privilege> sps) {
		for (Privilege sp : sps) {
			super.save(sp);
		}
	}

	@Override
	public Privilege findByURL(String url) {
		Query query = em.createQuery(
				"select o from Privilege o where o.url=?1").setParameter(
				1, url);
		try {
			Privilege sp = (Privilege) query.getSingleResult();
			return sp;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Privilege> listBylevel(int level) {
		return em.createQuery("select o from Privilege o where o.lv=?1")
				.setParameter(1, level).getResultList();
	}
}
