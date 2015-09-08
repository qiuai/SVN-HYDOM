/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.CarDao;
import net.shop.entity.Car;
import net.shop.service.CarService;

import org.springframework.stereotype.Service;

/**
 * Service - 车型
 * 
 * 
 * 
 */
@Service("carServiceImpl")
public class CarServiceImpl extends BaseServiceImpl<Car, Long> implements CarService{
	
	@Resource(name="carDaoImpl")
	private CarDao carDao;

	@Resource(name="carDaoImpl")
	public void setBaseDao(CarDao carDao){
		super.setBaseDao(carDao);
	}
	
}