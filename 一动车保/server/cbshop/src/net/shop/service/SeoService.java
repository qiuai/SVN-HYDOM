/*
 * 
 * 
 * 
 */
package net.shop.service;

import net.shop.entity.Seo;
import net.shop.entity.Seo.Type;

/**
 * Service - SEO设置
 * 
 * 
 * 
 */
public interface SeoService extends BaseService<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Type type);

	/**
	 * 查找SEO设置(缓存)
	 * 
	 * @param type
	 *            类型
	 * @param cacheRegion
	 *            缓存区域
	 * @return SEO设置(缓存)
	 */
	Seo find(Type type, String cacheRegion);

}