package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.dao.TestDao;
import com.hydom.common.service.TestService;
import com.hydom.vt.entity.Test;

/**
 * Service实现类 -测试Service
 * 
 * @author Holen
 * @version 1.0.0 2014.6.8 新建
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<Test, String> implements TestService {

	@Resource
	private TestDao testDao;

	@Resource
	public void setBaseDao(TestDao testDao) {
		super.setBaseDao(testDao);
	}
}
