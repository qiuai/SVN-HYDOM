package com.hydom.api.service;

import org.springframework.stereotype.Service;

import com.hydom.api.ebean.Token;
import com.hydom.util.dao.DAOSupport;

@Service
public class TokenServiceBean extends DAOSupport<Token> implements TokenService {

	@Override
	public Token findToken(String uid, String authId) {
		try {
			return (Token) em
					.createQuery(
							"select o from Token o where o.uid=?1 and o.authid=?2")
					.setParameter(1, uid).setParameter(2, authId)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deletAllTokenByUID(String uid) {
		return em.createQuery("delete from Token o where o.uid=?1")
				.setParameter(1, uid).executeUpdate();
	}

}
