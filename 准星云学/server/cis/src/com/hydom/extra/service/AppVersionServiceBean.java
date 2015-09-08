package com.hydom.extra.service;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.AppVersion;

@Service
public class AppVersionServiceBean extends DAOSupport<AppVersion> implements AppVersionService {

	@Override
	public AppVersion isUpdate(double version) {
		try {
			return (AppVersion) em
					.createQuery(
							"select o from AppVersion o where  o.visible=?1 and o.state=?2 and o.version>?3 order by o.uploadTime desc")
					.setParameter(1, true).setParameter(2, 1).setParameter(3, version)
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
