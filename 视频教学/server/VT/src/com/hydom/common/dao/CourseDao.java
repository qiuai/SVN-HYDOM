package com.hydom.common.dao;

import java.util.Date;

import com.hydom.common.bean.Pager;
import com.hydom.vt.entity.Course;

public interface CourseDao extends BaseDao<Course, String> {

	public Float getCourseScore(String courseId);
	
	public Pager getStartCourse(Pager pager);
	public Date getStartCourse(String courseId);
}
