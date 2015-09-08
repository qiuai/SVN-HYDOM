package com.hydom.account.service;

import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;
import com.hydom.util.dao.DAO;

public interface SpecificationValueService extends DAO<SpecificationValue> {
	
	/**
	 * 
	 * @param name
	 * @param productCategory
	 * @return
	 */
	Specification findByCategoryAndName(String name, String productCategory);
	
}
