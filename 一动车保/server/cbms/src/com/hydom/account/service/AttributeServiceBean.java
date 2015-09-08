package com.hydom.account.service;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hydom.account.ebean.Attribute;
import com.hydom.account.ebean.Product;
import com.hydom.util.dao.DAOSupport;

@Service
public class AttributeServiceBean extends DAOSupport<Attribute> implements  AttributeService {

	@Override
	public void save(Attribute entity) {
		Assert.notNull(entity);
		String jpql = "select attribute.propertyIndex from Attribute attribute where attribute.productCategory = :productCategory";
		List<Integer> propertyIndexs = em.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("productCategory", entity.getProductCategory()).getResultList();
		for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			if (!propertyIndexs.contains(i)) {
				entity.setPropertyIndex(i);
				super.save(entity);
				break;
			}
		}
	}

	@Override
	public void remove(Attribute entity) {
		if (entity != null) {
			String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entity.getPropertyIndex();
			String jpql = "update Product product set product." + propertyName + " = null where product.productCategory = :productCategory";
			em.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("productCategory", entity.getProductCategory()).executeUpdate();
			super.remove(entity);
		}
	}
}
