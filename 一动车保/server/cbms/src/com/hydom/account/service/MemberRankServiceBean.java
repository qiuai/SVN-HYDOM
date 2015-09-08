package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.MemberRank;
import com.hydom.util.dao.DAOSupport;

@Service
public class MemberRankServiceBean extends DAOSupport<MemberRank> implements MemberRankService {
	
	@SuppressWarnings("unchecked")
	@Override
	public MemberRank getEntityByName(String name) {
		String sql = "select o from MemberRank o where o.visible = :visible and o.name = :name";
		Query query = em.createQuery(sql);
		query.setParameter("visible", true);
		query.setParameter("name", name);
	
		List<MemberRank> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
