package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.ProductBrand;
import com.hydom.util.dao.DAO;

public interface ProductBrandService extends DAO<ProductBrand> {
	/**
	 * 以【修改时间】进行排序，获取最新修改的一个推荐品牌，
	 * 
	 * @return
	 */
	public ProductBrand findOneRecommendBrand();
	
	/**
	 * 判断是否重名
	 * @param name
	 * @return
	 */
	public ProductBrand findbyName(String name);
	
	/**
	 * 获取所有品牌
	 * @return
	 */
	public List<ProductBrand> getAllProductBrand();

}
