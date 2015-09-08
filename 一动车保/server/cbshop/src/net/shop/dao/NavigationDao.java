/*
 * 
 * 
 * 
 */
package net.shop.dao;

import java.util.List;

import net.shop.entity.Navigation;
import net.shop.entity.Navigation.Position;

/**
 * Dao - 导航
 * 
 * 
 * 
 */
public interface NavigationDao extends BaseDao<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

}