package com.hydom.vt.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//学生与课程的关系
@Entity
@Table(name = "t_usercourse")
@Getter
@Setter
public class UserCourse extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7386136681232139535L;
	// 0关注的课程 1观看的历史记录 2学生当前所在的房间
	private Integer types = 0;
	// 学生
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	// 课程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	private Course course;

}
