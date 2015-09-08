package com.hydom.common.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.hydom.common.bean.Pager;
import com.hydom.common.dao.CourseDao;
import com.hydom.common.service.CommentService;
import com.hydom.common.service.CourseService;
import com.hydom.vt.entity.Comment;
import com.hydom.vt.entity.Course;
import com.hydom.vt.entity.CourseTime;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, String>
		implements CourseService {

	@Resource
	private CourseDao courseDao;

	@Resource
	public void setBaseDao(CourseDao courseDao) {
		super.setBaseDao(courseDao);
	}

	@Override
	public Float getCourseScore(String courseId) {
		return courseDao.getCourseScore(courseId);
	}

	@Override
	public Pager getStartCourse(Pager pager) {
		return courseDao.getStartCourse(pager);
	}

	@Override
	public Date getStartCourse(String courseId) {
	
		return courseDao.getStartCourse(courseId);
	}
}
