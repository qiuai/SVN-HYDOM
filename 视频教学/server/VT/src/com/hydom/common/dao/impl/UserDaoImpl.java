package com.hydom.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hydom.common.dao.UserDao;
import com.hydom.vt.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

}
