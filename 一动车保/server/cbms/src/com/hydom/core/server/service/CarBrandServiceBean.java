package com.hydom.core.server.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.util.dao.DAOSupport;

/**
 * @Description:汽车品牌业务层实现
 * @author WY
 * @date 2015年6月26日 下午5:33:32
 */

@Service
public class CarBrandServiceBean extends DAOSupport<CarBrand> implements CarBrandService{

	@Override
	public boolean isExist(String name) {
		CarBrand carBrand = null;
		try {
			carBrand = (CarBrand) em.createQuery("select o from CarBrand o where o.visible=1 and o.name=?1")
			.setParameter(1, name).getSingleResult();
		} catch (NoResultException e) {
		}
		if(carBrand==null){
			return false;
		}else{
			return true;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getChooseBrand(Product product) {
		String hql = "select cb from CarBrand cb join cb.carList c where c in(:carSet)";
		
	//	Query query = em.createQuery(hql);
//		query.setParameter("carSet", product.getCarSet());
		Set<Car> carSet = product.getCarSet();
		
		Set<CarBrand> carBrands = new HashSet<CarBrand>();
		for(Car car : carSet){
			carBrands.add(car.getCarBrand());
		}
		
		JSONArray array = new JSONArray();
		String brandIds = "";
		for(CarBrand cb : carBrands){
			array.add(cb.getId());
		}
		return array.toString();
	}

}
