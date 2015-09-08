package com.hydom.vt.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * 课程 - 实体（一个房间即一个课程）
 * 
 */
@Entity
@Table(name = "t_course")
@Getter
@Setter
public class Course extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5227126791736659099L;

	// 课程名称
	private String name;
	// 开始时间
	private Date startDate;
	// 结束时间
	private Date endDate;
	// 时间段 10:30-11:30
	private String time;
	// 房间密码
	private String password;
	// 最多人数
	private Integer maxStuNum;
	// 收费
	private Float money;
	// 该房间总收入
	private Float countMoney;
	// 房间模式 1 直播 0 录播；
	private Integer types;
	// 备注
	private String remark;
	// 主要内容（知识点）
	private String mainContent;
	// 地址
	private String url;
	// 每周星期几上课（[1,3,5]）
	private String coursePeriodic;
	// 是否热门
	private Boolean isHot = false;

	// 课程背景图片
	private String image;
	// 观看次数
	private Integer number = 0;

	// 所属老师
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	// 状态 1正在上课 0未上课
	private Integer status = 0;
	// 是否可申请分享音频
	private Boolean microphoneenable = false;
	// 是否可申请分享视频
	private Boolean screenshareenable = false;
	// 当前视频流
	private String nowFlow;
	// 课时时间
	private String timeLength;
	// 评分
	@Transient
	private Float score;
	// 是否关注
	@Transient
	private Boolean isFocus;
	@Transient
	private Date nextStart;

	public void setUserId(String userId) {
		this.user = new User(userId);
	}

	/*
	 * //直播流名,房间模式为直播时有值 private String livestream; //录播流名,房间模式为录播时有值 private String recordstream; //麦克风流名 private String microphonestream;
	 */

	public Course() {

	}

	public Course(String id) {
		this.id = id;
	}
}
