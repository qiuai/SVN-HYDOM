package com.hydom.account.service;

import java.util.List;
import java.util.Map;

import com.hydom.account.ebean.Privilege;
import com.hydom.util.dao.DAO;

public interface PrivilegeService extends DAO<Privilege> {

	public void saves(List<Privilege> sps);

	public Privilege findByURL(String url);

	public List<Privilege> listBylevel(int level);

	public Map<String, List<Privilege>> listCategory();

}
