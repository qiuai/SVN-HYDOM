package com.hydom.extra.service;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.SystemConfig;

@Service
public class SystemConfigServiceBean extends DAOSupport<SystemConfig> implements
		SystemConfigService {

}
