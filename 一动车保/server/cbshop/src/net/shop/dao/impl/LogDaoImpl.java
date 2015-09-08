/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import javax.persistence.FlushModeType;

import net.shop.dao.LogDao;
import net.shop.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao - 日志
 * 
 * 
 * 
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}