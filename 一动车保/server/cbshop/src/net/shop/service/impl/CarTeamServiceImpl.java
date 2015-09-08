/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.CarTeamDao;
import net.shop.entity.CarTeam;
import net.shop.service.CarTeamService;

import org.springframework.stereotype.Service;

/**
 * Service - 车队
 * 
 * 
 * 
 */
@Service("carTeamServiceImpl")
public class CarTeamServiceImpl extends BaseServiceImpl<CarTeam, Long> implements CarTeamService{
	
	@Resource(name="carTeamDaoImpl")
	private CarTeamDao CarTeamDao;
	
	public void setBaseDao(CarTeamDao carTeamDao){
		super.setBaseDao(carTeamDao);
	}
	
}