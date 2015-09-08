package com.hydom.core.server.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Product;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.util.dao.DAOSupport;

/**
 * @Description 车型业务层实现
 * @author WY
 * @date 2015年7月1日 下午5:46:26
 */

@Service
public class CarServiceBean extends DAOSupport<Car> implements CarService{

	@Override
	public boolean isExist(String name) {
		Car car = null;
		try {
			car = (Car) em.createQuery("select o from Car o where o.visible=1 and o.name=?1")
			.setParameter(1, name).getSingleResult();
		} catch (NoResultException e) {
		}
		if(car==null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public String getChooseCar(Product product) {
		Set<Car> carSet = product.getCarSet();
		JSONArray array = new JSONArray();
		for(Car cb : carSet){
			array.add(cb.getId());
		}
		return array.toString();
	}

}
