package com.hydom.credit.service;

import java.util.List;

import com.hydom.credit.ebean.Trophy;
import com.hydom.dao.DAO;

public interface TrophyService extends DAO<Trophy> {

	/**
	 * 获取所有奖品列表
	 * 
	 * @return
	 */
	public List<Trophy> list();

	/**
	 * 获取最新添加的奖品记录
	 * 
	 * @return
	 */
	public Trophy newest();

}
