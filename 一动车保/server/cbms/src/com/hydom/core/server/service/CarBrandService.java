package com.hydom.core.server.service;

import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.util.dao.DAO;

/**
 * @Description:汽车品牌业务层接口
 * @author WY
 * @date 2015年6月26日 下午5:27:26
 */

public interface CarBrandService extends DAO<CarBrand>{

	/**
	 * 根据品牌名称查询品牌是否存在
	 * @param name 品牌名称
	 * @return true存在 false不存在
	 */
	public boolean isExist(String name);
	
	/**
	 * 根据商品获取选择的品牌
	 * @param product
	 * @return
	 */
	public String getChooseBrand(Product product);
}
