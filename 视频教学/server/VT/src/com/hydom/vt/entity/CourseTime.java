package com.hydom.vt.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.DateTimeHelper;

import lombok.Getter;
import lombok.Setter;

//课程的上课时间
@Entity
@Table(name = "t_coursetime")
@Getter
@Setter
public class CourseTime extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6286046719671083087L;
	// 所属老师
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "courseId")
	private Course course;
	
	private Date startDate;
	
	private Date endDate;

	public CourseTime() {

	}

	/*
	 * date 开始日期(年月日) time 时间（时分）
	 */
	public CourseTime(Date date,String startTime,String endTime,String courseId) throws Exception {
		this.course=new Course(courseId);
		String startDate=DateTimeHelper.formatDateTimetoString(date,"yyyy-MM-dd")+" "+startTime;;
		String endDate=DateTimeHelper.formatDateTimetoString(date,"yyyy-MM-dd")+" "+endTime;
		this.startDate=DateTimeHelper.parseToDate(startDate,"yyyy-MM-dd HH:mm");
		this.endDate=DateTimeHelper.parseToDate(endDate,"yyyy-MM-dd HH:mm");
	}
}
