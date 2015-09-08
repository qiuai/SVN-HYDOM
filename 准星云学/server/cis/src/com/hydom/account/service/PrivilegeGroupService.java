package com.hydom.account.service;

import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.dao.DAO;

public interface PrivilegeGroupService extends DAO<PrivilegeGroup> {

	/**
	 * 根据权限组名称查找对应的权限组记录
	 * 
	 * @param groupName
	 * @return
	 */
	public PrivilegeGroup findByName(String groupName);

}
