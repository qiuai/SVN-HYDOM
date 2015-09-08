package com.hydom.zxy.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.service.impl.BaseServiceImpl;
import com.hydom.zxy.dao.VersionDao;
import com.hydom.zxy.entity.Version;
import com.hydom.zxy.service.VersionService;

@Service
public class VersionServiceImpl extends BaseServiceImpl<Version , String> implements VersionService {
	@Resource
	private VersionDao VersionDao;

	@Resource
	public void setBaseDao(VersionDao VersionDao) {
		super.setBaseDao(VersionDao);
	}
	
}