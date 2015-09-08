package com.hydom.account.service;

import com.hydom.account.ebean.Member;
import com.hydom.util.dao.DAO;

public interface MemberService extends DAO<Member> {

	/**
	 * 通过E-mail和密码查找对应的帐户
	 */
	public Member findByUP(String email, String password);

	/**
	 * 通过手机号查找对应的用户
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	public Member findByMobile(String mobile);

}
