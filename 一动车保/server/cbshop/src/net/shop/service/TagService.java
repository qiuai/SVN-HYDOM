/*
 * 
 * 
 * 
 */
package net.shop.service;

import java.util.List;

import net.shop.Filter;
import net.shop.Order;
import net.shop.entity.Tag;
import net.shop.entity.Tag.Type;

/**
 * Service - 标签
 * 
 * 
 * 
 */
public interface TagService extends BaseService<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Type type);

	/**
	 * 查找标签(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 标签(缓存)
	 */
	List<Tag> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

}