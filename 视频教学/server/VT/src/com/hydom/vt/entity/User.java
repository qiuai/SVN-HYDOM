package com.hydom.vt.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

//用户
@Entity
@Table(name = "t_user")
@Getter
@Setter
public class User extends BaseEntity {

	/**
	 * 8319029417827831608L
	 */
	private static final long serialVersionUID = 8319029417827831608L;
	// 用户名
	private String username;
	private String password;
	// 昵称
	private String nickname;
	// 性别 1为男性，0为女性，2 未知；
	private Integer sex;
	// 城市
	private String city;
	// 学校
	private String school;
	// 等级（通过收入计算）
	private Integer level = 1;
	// 收入
	private Float income;
	// 邮箱
	private String email;
	// 电话
	private String tel;
	// 头像
	private String avatar;
	// 余额
	private Float money = 0f;
	// 是否老师
	private Boolean isteacher = false;
	// 地址
	private String address;

	// 备注
	private String remark;

	// 教师课程总观看量
	@Transient
	private Integer count;
    //用户状态 0可用 1不可用
	private Integer status=0;
	//管理员 0普通用户 1管理员
	private Integer adminType=0;
	/* 关注老师的学生 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "teacherSet", fetch = FetchType.LAZY)
	public Set<User> studentSet;

	/* 学生关注的老师 */
	@ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
	@JoinTable(name = "t_stu_teacher", joinColumns = { @JoinColumn(name = "studentId") }, inverseJoinColumns = { @JoinColumn(name = "teacherId") })
	public Set<User> teacherSet;

	public User() {

	}

	public User(String id) {
		this.id = id;
	}
}
