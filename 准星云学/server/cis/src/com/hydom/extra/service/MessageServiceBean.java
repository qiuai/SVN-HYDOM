package com.hydom.extra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.Message;

@Service
public class MessageServiceBean extends DAOSupport<Message> implements MessageService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> list() {
		return em.createQuery("select o from Message o where o.visible=?1").setParameter(
				1, true).getResultList();
	}
}
