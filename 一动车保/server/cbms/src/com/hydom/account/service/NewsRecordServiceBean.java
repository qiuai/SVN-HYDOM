package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.NewsRecord;
import com.hydom.util.dao.DAOSupport;

@Service
public class NewsRecordServiceBean extends DAOSupport<NewsRecord> implements
		NewsRecordService {
	@Override
	public NewsRecord starRecord(String uid, String nwid) {
		try {
			return (NewsRecord) em
					.createQuery(
							"select o from NewsRecord o where o.member.id=?1 and o.news.id=?2")
					.setParameter(1, uid).setParameter(2, nwid)
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
