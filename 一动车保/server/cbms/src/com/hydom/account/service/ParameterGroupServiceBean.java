package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ParameterGroup;
import com.hydom.util.dao.DAOSupport;

@Service
public class ParameterGroupServiceBean extends DAOSupport<ParameterGroup> implements  ParameterGroupService {

	@SuppressWarnings("unchecked")
	@Override
	public ParameterGroup findByNameAndCategory(String content,
			String productCategoryId) {
		String sql = "select o from ParameterGroup o where o.name = :name and o.productCategory.id = :productCategoryId";
		Query query = em.createQuery(sql);
		query.setParameter("name", content);
		query.setParameter("productCategoryId", productCategoryId);
		List<ParameterGroup> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
