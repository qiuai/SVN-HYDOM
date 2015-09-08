package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Specification;
import com.hydom.account.ebean.SpecificationValue;
import com.hydom.util.dao.DAOSupport;

@Service
public class SpecificationValueServiceBean extends DAOSupport<SpecificationValue> implements SpecificationValueService {

	@SuppressWarnings("unchecked")
	@Override
	public Specification findByCategoryAndName(String name, String productCategory) {
		String sql = "select o from Specification o where o.name = :name and o.productCategory.id = :productCategory and o.visible=true";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		query.setParameter("productCategory", productCategory);
		List<Specification> list = query.getResultList();
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
}
