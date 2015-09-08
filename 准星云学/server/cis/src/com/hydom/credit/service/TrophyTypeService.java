package com.hydom.credit.service;

import java.util.List;

import com.hydom.credit.ebean.TrophyType;
import com.hydom.dao.DAO;

public interface TrophyTypeService extends DAO<TrophyType> {

	/**
	 * 列出所有奖品分类
	 * 
	 * @return
	 */
	public List<TrophyType> list();

}
