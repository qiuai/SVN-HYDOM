package com.hydom.common.dao.impl;

import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hydom.common.bean.Pager;
import com.hydom.common.dao.CourseDao;
import com.hydom.vt.entity.Course;

@Repository
public class CourseDaoImpl extends BaseDaoImpl<Course, String> implements
		CourseDao {

	@Override
	public Float getCourseScore(String courseId) {
		String sql = "select avg(score) from t_comment where 1=1 and courseId='"
				+ courseId + "'";

		SQLQuery query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();
		if (obj == null) {
			return 0f;
		}
		Float score = Float.valueOf(obj.toString());
		return (float) (Math.round(score * 10)) / 10;
	}

	@Override
	public Pager getStartCourse(Pager pager) {
		String countSql = "select count(*) from ( ";
		String sql = "select c.*, min(ct.startDate) nextStart from t_course c, t_coursetime ct where 1=1 and c.id=ct.courseId  and  (ct.startDate >=NOW() or c.`status`=1) GROUP BY ct.courseId ORDER BY ct.startDate ";
		SQLQuery query = getSession().createSQLQuery(countSql + sql + " ) t");
		Integer count = Integer.parseInt(query.uniqueResult().toString());
		pager.setTotalCount(count);
		int start = (pager.getPageNumber() - 1) * pager.getPageSize();

		SQLQuery query2 = getSession().createSQLQuery(
				"select * from (" + sql + " ) t LIMIT " + start + ","
						+ pager.getPageSize());
		query2.setResultTransformer(Transformers.aliasToBean(Course.class));
		pager.setList(query2.list());

		return pager;
	}

	@Override
	public Date getStartCourse(String courseId) {
		String sql = "select min(ct.startDate) nextStart from t_course c, t_coursetime ct where 1=1 and c.id=ct.courseId  and (ct.startDate >=NOW() or c.`status`=1)  and ct.courseId='"
				+ courseId + "'  ORDER BY ct.startDate asc";
		SQLQuery query = getSession().createSQLQuery(sql);
		Object obj = query.uniqueResult();

		return (Date) obj;
	}

}
