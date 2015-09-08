package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.dao.CourseTimeDao;
import com.hydom.common.service.CourseTimeService;
import com.hydom.vt.entity.CourseTime;

@Service
public class CourseTimeServiceImpl extends BaseServiceImpl<CourseTime, String>
		implements CourseTimeService {

	@Resource
	private CourseTimeDao courseTimeDao;

	@Resource
	public void setBaseDao(CourseTimeDao courseTimeDao) {
		super.setBaseDao(courseTimeDao);
	}

	@Override
	public Boolean delByCourseId(String courseId) {
		
	return	courseTimeDao.delete("t_coursetime", "courseId", courseId);
	}

	

	
}
