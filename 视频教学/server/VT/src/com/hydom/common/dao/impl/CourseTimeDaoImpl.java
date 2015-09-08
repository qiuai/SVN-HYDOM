package com.hydom.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hydom.common.dao.CourseTimeDao;
import com.hydom.vt.entity.CourseTime;

@Repository
public class CourseTimeDaoImpl extends BaseDaoImpl<CourseTime, String>
		implements CourseTimeDao {

}
