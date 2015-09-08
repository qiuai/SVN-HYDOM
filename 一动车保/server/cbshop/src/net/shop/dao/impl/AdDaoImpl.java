/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.AdDao;
import net.shop.entity.Ad;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * 
 * 
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

}