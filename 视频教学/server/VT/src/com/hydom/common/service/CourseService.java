package com.hydom.common.service;

import java.util.Date;

import com.hydom.common.bean.Pager;
import com.hydom.vt.entity.Course;


public interface CourseService extends BaseService<Course, String> {

	//课程评分
	public Float getCourseScore(String courseId);
	//即将开课
	public Pager getStartCourse(Pager pager);
	
	//即将开课
	public Date getStartCourse(String courseId);
}
