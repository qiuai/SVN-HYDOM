package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.ProductLabel;
import com.hydom.util.dao.DAO;

public interface ProductLabelService extends DAO<ProductLabel>{
	/**
	 * 验证商品标签名称是否存在
	 * @param name
	 * @return
	 */
	public boolean isExist(String name);

	public List<ProductLabel> getProductLabelVisible(boolean b);
}
