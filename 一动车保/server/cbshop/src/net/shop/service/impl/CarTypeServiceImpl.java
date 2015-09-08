/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.CarTypeDao;
import net.shop.entity.CarType;
import net.shop.service.CarTypeService;

import org.springframework.stereotype.Service;

/**
 * Service - 车系
 * 
 * 
 * 
 */
@Service("carTypeServiceImpl")
public class CarTypeServiceImpl extends BaseServiceImpl<CarType, Long> implements CarTypeService{
	
	@Resource(name="carTypeDaoImpl")
	private CarTypeDao carTypeDao;

	@Resource(name="carTypeDaoImpl")
	public void setBaseDao(CarTypeDao carTypeDao){
		super.setBaseDao(carTypeDao);
	}
	
}