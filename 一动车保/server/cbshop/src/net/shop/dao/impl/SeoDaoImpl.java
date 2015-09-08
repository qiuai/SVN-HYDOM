/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import net.shop.dao.SeoDao;
import net.shop.entity.Seo;
import net.shop.entity.Seo.Type;

import org.springframework.stereotype.Repository;

/**
 * Dao - SEO设置
 * 
 * 
 * 
 */
@Repository("seoDaoImpl")
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	@Override
	public Seo find(Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setFlushMode(FlushModeType.COMMIT).setParameter("type", type).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}