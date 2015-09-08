package com.hydom.account.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ProductBrand;
import com.hydom.util.dao.DAOSupport;

@Service
public class ProductBrandServiceBean extends DAOSupport<ProductBrand> implements
		ProductBrandService {

	@Override
	public ProductBrand findOneRecommendBrand() {
		try {
			return (ProductBrand) em
					.createQuery(
							"select o from ProductBrand  o where o.visible=?1 and o.commandBrand=?2 order by o.modifyDate desc")
					.setParameter(1, true).setParameter(2, 1).setMaxResults(1)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductBrand findbyName(String name) {
		String sql = "select o from ProductBrand o where o.name=:name and o.visible = :visible";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		query.setParameter("visible", true);
		List<ProductBrand> list = query.getResultList();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBrand> getAllProductBrand() {
		String sql = "select o from ProductBrand o where o.visible = :visible";
		Query query = em.createQuery(sql);
		query.setParameter("visible", true);
		List<ProductBrand> list = query.getResultList();
		return list;
	}
}
