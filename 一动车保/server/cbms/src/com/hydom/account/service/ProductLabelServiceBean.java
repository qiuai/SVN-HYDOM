package com.hydom.account.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ProductLabel;
import com.hydom.util.dao.DAOSupport;
/**
 * @Description:商品标签业务层实现
 * @author fxw
 *
 */
@Service
public class ProductLabelServiceBean extends DAOSupport<ProductLabel> implements ProductLabelService {

	@Override
	public boolean isExist(String name) {
		ProductLabel productLable = null;
		try {
			productLable = (ProductLabel) em.createQuery("select o from ProductLabel o where o.labelName=?1")
					.setParameter(1, name).getSingleResult();
			/*productLable = (productLable) em.createQuery("select o from ProductLabel o where o.labelName=?1")
			.setParameter(1, name).getSingleResult();*/
		} catch (NoResultException e) {
		}
		if(productLable==null){
			return false;
		}else{
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductLabel> getProductLabelVisible(boolean b) {
		String jpql = "select o from com.hydom.account.ebean.ProductLabel o where 1=1 and o.labelStats = ?1";
		return em.createQuery(jpql).setParameter(1, b).getResultList();
	}
}
