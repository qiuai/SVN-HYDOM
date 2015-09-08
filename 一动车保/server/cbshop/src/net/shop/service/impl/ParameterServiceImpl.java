/*
 * 
 * 
 * 
 */
package net.shop.service.impl;

import javax.annotation.Resource;

import net.shop.dao.ParameterDao;
import net.shop.entity.Parameter;
import net.shop.service.ParameterService;

import org.springframework.stereotype.Service;

/**
 * Service - 参数
 * 
 * 
 * 
 */
@Service("parameterServiceImpl")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long> implements ParameterService {

	@Resource(name = "parameterDaoImpl")
	public void setBaseDao(ParameterDao parameterDao) {
		super.setBaseDao(parameterDao);
	}

}