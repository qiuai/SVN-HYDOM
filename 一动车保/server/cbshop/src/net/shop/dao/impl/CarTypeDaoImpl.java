/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.CarTypeDao;
import net.shop.entity.CarType;

import org.springframework.stereotype.Repository;

/**
 * Dao - 车系
 * 
 * 
 * 
 */
@Repository("carTypeDaoImpl")
public class CarTypeDaoImpl extends BaseDaoImpl<CarType, Long> implements CarTypeDao {

}