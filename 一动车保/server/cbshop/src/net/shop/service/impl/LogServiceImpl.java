/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.LogDao;
import net.shop.entity.Log;
import net.shop.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service - 日志
 * 
 * 
 * 
 */
@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

	@Resource(name = "logDaoImpl")
	private LogDao logDao;

	@Resource(name = "logDaoImpl")
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

	@Override
	public void clear() {
		logDao.removeAll();
	}

}