/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.CarDao;
import net.shop.entity.Car;

import org.springframework.stereotype.Repository;

/**
 * Dao - 车型
 * 
 * 
 * 
 */
@Repository("carDaoImpl")
public class CarDaoImpl extends BaseDaoImpl<Car, Long> implements CarDao {

}