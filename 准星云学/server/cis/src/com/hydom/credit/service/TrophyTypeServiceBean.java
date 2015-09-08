package com.hydom.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.credit.ebean.TrophyType;
import com.hydom.dao.DAOSupport;

@Service
public class TrophyTypeServiceBean extends DAOSupport<TrophyType> implements
		TrophyTypeService {

	@SuppressWarnings("unchecked")
	@Override
	public List<TrophyType> list() {
		return em
				.createQuery(
						"select o from TrophyType o where o.visible=?1 order by o.od desc o.id desc")
				.setParameter(1, true).getResultList();
	}
}
