/*
 * 
 * 
 * 
 */
package net.shop.dao;

import net.shop.entity.Seo;
import net.shop.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 * 
 * 
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Type type);

}