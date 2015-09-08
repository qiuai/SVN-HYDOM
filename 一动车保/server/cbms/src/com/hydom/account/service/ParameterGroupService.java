package com.hydom.account.service;

import com.hydom.account.ebean.ParameterGroup;
import com.hydom.util.dao.DAO;

public interface ParameterGroupService extends DAO<ParameterGroup> {
	
	/**
	 * 根据名称获取
	 * @param content
	 * @param productCategoryId
	 * @return
	 */
	ParameterGroup findByNameAndCategory(String content,
			String productCategoryId);
	
}
