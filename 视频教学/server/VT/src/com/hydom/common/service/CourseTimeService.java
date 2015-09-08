package com.hydom.common.service;

import com.hydom.vt.entity.CourseTime;


public interface CourseTimeService extends BaseService<CourseTime, String> {

	public Boolean delByCourseId(String courseId);
}
