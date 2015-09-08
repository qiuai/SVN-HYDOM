package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.SystemPrivilege;
import com.hydom.dao.DAO;

public interface SystemPrivilegeService extends DAO<SystemPrivilege> {

	/**
	 * 批量保存权限
	 */
	public void saves(List<SystemPrivilege> sps);

	/**
	 * 通过url地址找到权限对象
	 * 
	 * @param url
	 * @return
	 */
	public SystemPrivilege findByURL(String url);

	/**
	 * 列出权限
	 * 
	 * @param level
	 * @return
	 */
	public List<SystemPrivilege> listBylevel(int level);

}
