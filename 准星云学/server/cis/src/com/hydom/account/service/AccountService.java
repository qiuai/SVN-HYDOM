package com.hydom.account.service;

import com.hydom.account.ebean.Account;
import com.hydom.dao.DAO;

public interface AccountService extends DAO<Account> {

	/**
	 * 通过用户名和密码查找Account
	 * 
	 * @param username
	 *            :用户名【手机号】
	 * @param password
	 *            ：密码
	 * @return
	 */
	public Account findByUP(String username, String password);

	/**
	 * 通过用户名查找Account
	 * 
	 * @param username
	 *            ：用户名【手机号】
	 * @return
	 */
	public Account findByUsername(String username);

}
