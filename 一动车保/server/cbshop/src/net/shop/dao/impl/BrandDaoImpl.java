/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.BrandDao;
import net.shop.entity.Brand;

import org.springframework.stereotype.Repository;

/**
 * Dao - 品牌
 * 
 * 
 * 
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

}