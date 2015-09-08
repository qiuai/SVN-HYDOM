package com.hydom.common.service;

import com.hydom.vt.entity.UserCourse;


public interface UserCourseService extends BaseService<UserCourse, String> {
//学生与课程关系总人数 
	public Integer getcount(String stuId,String courseId,Integer type);
}
