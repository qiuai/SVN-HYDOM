/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.CarBrandDao;
import net.shop.entity.CarBrand;
import net.shop.service.CarBrandService;

import org.springframework.stereotype.Service;

/**
 * Service - 汽车品牌
 * 
 * 
 * 
 */
@Service("carBrandServiceImpl")
public class CarBrandServiceImpl extends BaseServiceImpl<CarBrand, Long> implements CarBrandService{
	
	@Resource(name="carBrandDaoImpl")
	private CarBrandDao carBrandDao;
	
	@Resource(name="carBrandDaoImpl")
	public void setBaseDao(CarBrandDao carBrandDao){
		super.setBaseDao(carBrandDao);
	}
	
}