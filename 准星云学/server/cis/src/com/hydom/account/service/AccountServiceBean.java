package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Account;
import com.hydom.dao.DAOSupport;

@Service
public class AccountServiceBean extends DAOSupport<Account> implements AccountService {

	@Override
	public Account findByUP(String username, String password) {
		try {
			return (Account) em
					.createQuery(
							"select o from Account o where o.visible=?1 and o.username=?2 and o.password=?3")
					.setParameter(1, true).setParameter(2, username).setParameter(3,
							password).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Account findByUsername(String username) {
		try {
			return (Account) em
					.createQuery("select o from Account o where o.username=?1")
					.setParameter(1, username).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
