package com.hydom.vt.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_comment")
@Getter
@Setter
public class Comment extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4413605459206275997L;
	// 学生
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	// 课程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	private Course course;
	// 评分
	private Integer score = 0;
	// 评论内容
	private String content;

}
