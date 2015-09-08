package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Member;
import com.hydom.util.dao.DAOSupport;

@Service("memberService")
public class MemberServiceBean extends DAOSupport<Member> implements
		MemberService {

	@Override
	public Member findByUP(String email, String password) {
		try {
			return (Member) em
					.createQuery(
							"select o from Member o where o.visible=?1 and o.email=?2 and o.password=?3")
					.setParameter(1, true).setParameter(2, email)
					.setParameter(3, password).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Member findByMobile(String mobile) {
		try {
			return (Member) em
					.createQuery("select o from Member o where o.mobile=?1 and o.visible=true")
					.setParameter(1, mobile).getSingleResult();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
}
