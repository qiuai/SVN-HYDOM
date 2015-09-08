package com.hydom.core.server.service;

import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.Car;
import com.hydom.util.dao.DAO;

/**
 * @Description 车型业务层接口
 * @author WY
 * @date 2015年7月1日 下午5:45:10
 */

public interface CarService extends DAO<Car>{
	
	/**
	 * 根据车型名称查询品牌是否存在
	 * @param name 名称
	 * @return true存在 false不存在
	 */
	public boolean isExist(String name);
	
	/**
	 * 根据商品 获取选中的车辆
	 * @param product
	 * @return
	 */
	public String getChooseCar(Product product);
}
