package com.hydom.api.service;

import com.hydom.api.ebean.Token;
import com.hydom.util.dao.DAO;

public interface TokenService extends DAO<Token> {

	/***
	 * 通过用户ID和令牌值获取Token实体
	 * 
	 * @param uid
	 * @param authId
	 * @return
	 */
	public Token findToken(String uid, String authId);

	/**
	 * 删除指定用户的所有令牌
	 * 
	 * @param uid
	 * @return 删除信息数目
	 */
	public int deletAllTokenByUID(String uid);

}
