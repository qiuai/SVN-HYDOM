/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import net.shop.dao.PluginConfigDao;
import net.shop.entity.PluginConfig;

import org.springframework.stereotype.Repository;

/**
 * Dao - 插件配置
 * 
 * 
 * 
 */
@Repository("pluginConfigDaoImpl")
public class PluginConfigDaoImpl extends BaseDaoImpl<PluginConfig, Long> implements PluginConfigDao {

	@Override
	public boolean pluginIdExists(String pluginId) {
		if (pluginId == null) {
			return false;
		}
		String jpql = "select count(*) from PluginConfig pluginConfig where pluginConfig.pluginId = :pluginId";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("pluginId", pluginId).getSingleResult();
		return count > 0;
	}

	@Override
	public PluginConfig findByPluginId(String pluginId) {
		if (pluginId == null) {
			return null;
		}
		try {
			String jpql = "select pluginConfig from PluginConfig pluginConfig where pluginConfig.pluginId = :pluginId";
			return entityManager.createQuery(jpql, PluginConfig.class).setFlushMode(FlushModeType.COMMIT).setParameter("pluginId", pluginId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}