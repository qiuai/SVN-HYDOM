package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.Navigation;
import com.hydom.util.bean.NavigationBean;
import com.hydom.util.dao.DAO;

public interface NavigationService extends DAO<Navigation> {

	public List<NavigationBean> getNavigationList();
	
}
