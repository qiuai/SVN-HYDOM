package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.ProductCategory;
import com.hydom.util.dao.DAO;

public interface ProductCategoryService extends DAO<ProductCategory> {

	List<ProductCategory> findProductCategory(ProductCategory productCategory);

	List<ProductCategory> findChildren(ProductCategory productCategory,
			Integer count);

	/**
	 * 根据父分类ID 得到所有子类ID
	 * 
	 * @param parentids
	 *            父类ID字串数组
	 * @return
	 */
	public List<String> getSubTypeid(String[] parentids);

	/**
	 * 根据父分类 得到所有直接子类列表
	 * 
	 * @param parentId
	 * @return
	 */
	List<ProductCategory> listChildCategory(String parentId);
	
	/**
	 * 模糊名称 查询所有类集合
	 * @param queryContent
	 * @return
	 */
	List<ProductCategory> findProductCategorybyName(String queryContent);
	
	List<ProductCategory> findProductCategoryByEntityIds(List<String> m);
	
	/**
	 * 根据名称  跟 分类上级ID 获取分类
	 * @param name
	 * @param parentId
	 * @return
	 */
	ProductCategory getEntityByNameAndParentId(String name, String parentId);
}
