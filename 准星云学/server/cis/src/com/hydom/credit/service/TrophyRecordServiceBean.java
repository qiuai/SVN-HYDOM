package com.hydom.credit.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.credit.ebean.TrophyRecord;
import com.hydom.dao.DAOSupport;
import com.hydom.util.HelperUtil;

@Service
public class TrophyRecordServiceBean extends DAOSupport<TrophyRecord> implements
		TrophyRecordService {

	@SuppressWarnings("unchecked")
	@Override
	public List<TrophyRecord> list(long uid) {
		return em.createQuery(
				"select * from TrophyRecord t where t.visible=?1 and t.account.id=?2")
				.setParameter(1, true).setParameter(2, uid).getResultList();

	}

	public int clear(long uid) {
		return em.createQuery(
				"update TrophyRecord t set  t.visible=?1 where t.account.id=?2")
				.setParameter(1, false).setParameter(2, uid).executeUpdate();
	}

	@Override
	public long countAll(long uid) {
		return (Long) em.createQuery(
				"select count(t.id) from TrophyRecord t where t.account.id=?1")
				.setParameter(1, uid).getSingleResult();
	}

	@Override
	public long countMonth(long uid) {
		Date firtDayThisMonth = HelperUtil.firstDayThisMonth();
		Date lastDayThisMonth = HelperUtil.lastDayThisMonth();
		return (Long) em
				.createQuery(
						"select count(t.id) from TrophyRecord t where t.account.id=?1 and t.postTime>=?2 and t.postTime<?3")
				.setParameter(1, uid).setParameter(2, firtDayThisMonth).setParameter(3,
						lastDayThisMonth).getSingleResult();
	}
}
