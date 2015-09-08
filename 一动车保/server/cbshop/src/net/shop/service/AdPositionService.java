/*
 * 
 * 
 * 
 */
package net.shop.service;

import net.shop.entity.AdPosition;
/**
 * Service - 广告位
 * 
 * 
 * 
 */
public interface AdPositionService extends BaseService<AdPosition, Long> {

	/**
	 * 查找广告位(缓存)
	 * 
	 * @param id
	 *            ID
	 * @param cacheRegion
	 *            缓存区域
	 * @return 广告位(缓存)
	 */
	AdPosition find(Long id, String cacheRegion);

}