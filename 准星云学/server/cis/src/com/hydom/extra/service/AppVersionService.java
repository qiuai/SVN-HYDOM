package com.hydom.extra.service;

import com.hydom.dao.DAO;
import com.hydom.extra.ebean.AppVersion;

public interface AppVersionService extends DAO<AppVersion> {

	public AppVersion isUpdate(double version);
}
