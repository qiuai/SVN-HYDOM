package com.hydom.extra.service;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.MessageDeleteRecord;

@Service
public class MessageDeleteRecordServiceBean extends DAOSupport<MessageDeleteRecord>
		implements MessageDeleteRecordService {

	@SuppressWarnings("unchecked")
	public List<Long> listMidsByAccid(long accid) {
		return em.createQuery("select o.msgid from MessageDeleteRecord o where o.accid=?1")
				.setParameter(1, accid).getResultList();
	}

	@Override
	public MessageDeleteRecord find(long accid, long msgid) {
		try {
			return (MessageDeleteRecord) em
					.createQuery(
							"select o from MessageDeleteRecord o where o.accid=?1 and o.msgid=?2")
					.setParameter(1, accid).setParameter(2, msgid).getSingleResult();
		} catch (NonUniqueResultException nur) {
			return new MessageDeleteRecord();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

}
