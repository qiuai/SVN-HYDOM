package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.dao.UserDao;
import com.hydom.common.service.UserService;
import com.hydom.vt.entity.User;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Resource
	private UserDao memberDao;

	@Resource
	public void setBaseDao(UserDao memberDao) {
		super.setBaseDao(memberDao);
	}
}
