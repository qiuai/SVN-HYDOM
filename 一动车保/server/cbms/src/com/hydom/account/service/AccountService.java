package com.hydom.account.service;

import com.hydom.account.ebean.Account;
import com.hydom.util.dao.DAO;

public interface AccountService extends DAO<Account> {

	/**
	 * 通过用户名和密码查找对应的帐户
	 * 
	 * @param username
	 * @return
	 */
	public Account findByUP(String username, String password);

	/**
	 * 通过用户查找对应的帐户
	 * 
	 * @param username
	 * @return
	 */
	public Account findByUsername(String username);

}
