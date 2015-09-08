package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hydom.common.dao.UserCourseDao;
import com.hydom.common.service.UserCourseService;
import com.hydom.vt.entity.UserCourse;

@Service
public class UserCourseServiceImpl extends BaseServiceImpl<UserCourse, String>
		implements UserCourseService {

	@Resource
	private UserCourseDao userCourseDao;

	@Resource
	public void setBaseDao(UserCourseDao userCourseDao) {
		super.setBaseDao(userCourseDao);
	}

	@Override
	public Integer getcount(String stuId, String courseId, Integer type) {
		String tem = "";
		if (StringUtils.isNotEmpty(stuId)) {
			tem = " userId='" + stuId + "' ";
		} else {
			tem = " courseId='" + courseId + "' ";
		}
		String sql = "select count(*) from t_usercourse where 1=1 and " + tem
				+ " and types='" + type + "'";
		Integer count = userCourseDao.countBySql(sql);
		return count;
	}
}
