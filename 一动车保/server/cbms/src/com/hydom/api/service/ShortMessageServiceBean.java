package com.hydom.api.service;

import org.springframework.stereotype.Service;

import com.hydom.api.ebean.ShortMessage;
import com.hydom.util.dao.DAOSupport;

@Service
public class ShortMessageServiceBean extends DAOSupport<ShortMessage> implements
		ShortMessageService {

	@Override
	public ShortMessage findByPhoneAndCode(String phone, String code, int type) {
		try {
			return (ShortMessage) em
					.createQuery(
							"select o from ShortMessage o where o.visible=?1 and o.phone=?2 and o.code=?3 and o.type=?4 order by o.createDate desc")
					.setParameter(1, true).setParameter(2, phone)
					.setParameter(3, code).setParameter(4, type)
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ShortMessage lastSMS(String phone, int type) {
		try {
			return (ShortMessage) em
					.createQuery(
							"select o from ShortMessage o where o.visible=?1 and o.phone=?2 and o.type=?4 order by o.createDate desc")
					.setParameter(1, true).setParameter(2, phone)
					.setParameter(4, type).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
