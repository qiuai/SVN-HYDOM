package com.hydom.vt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 视频
 * 
 */
@Entity
@Table(name = "t_video")
@Getter
@Setter
public class Video extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5008317497572314687L;
	// 所属课程
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	private Course course;
	// 所属老师
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	private String url;
	private String name;
	//是否有效 0无效 1有效
	private Integer enable=1;
}
