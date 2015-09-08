package com.hydom.account.ebean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 帐户表：用户、管理员共用此表，通过type区别
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Entity
@Table(name = "t_account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String username;// 用户名（前期手机号即用户名）
	@Column
	private String phone;// 手机（利于后期扩展）
	@Column
	private String password;// 密码
	@Column
	private String nickname;// /用户昵称
	@Column
	private String backname;// /银行名称
	@Column
	private String backaccount;// /银行帐号
	@Column
	private String pay;// 支付宝帐号
	@Column
	private int type = 1;// 帐户类型：1=普通用户、2=后台管理员
	@Column
	private double score = 0;// 用户的总积分
	@Column
	private Integer state;// 0=休息一下，1=识别中，2=注销
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSigninTime;// 最后登录时间
	@Column
	private String lastSignIp;// 最后登录IP
	@Column
	private String lastSignPosition;// 最后登录地点
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastSignoutTime;// 最后注销时间
	@Column
	private Boolean visible = true;
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "t_account_group", joinColumns = @JoinColumn(name = "acc_id"), inverseJoinColumns = @JoinColumn(name = "g_id"))
	private Set<PrivilegeGroup> groups = new HashSet<PrivilegeGroup>();// 权限组

	@Transient
	private long count_all; // 识别总数
	@Transient
	private long count_month; // 当月识别总数
	@Transient
	private double count_rightPercent; // 正确率
	@Transient
	private double count_processTime;// 平均处理速度

	public Account() {

	}

	public Account(String username, String password, String phone) {
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.createTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastSigninTime() {
		return lastSigninTime;
	}

	public void setLastSigninTime(Date lastSigninTime) {
		this.lastSigninTime = lastSigninTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Set<PrivilegeGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<PrivilegeGroup> groups) {
		this.groups = groups;
	}

	public Date getLastSignoutTime() {
		return lastSignoutTime;
	}

	public void setLastSignoutTime(Date lastSignoutTime) {
		this.lastSignoutTime = lastSignoutTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getCount_all() {
		return count_all;
	}

	public void setCount_all(long countAll) {
		count_all = countAll;
	}

	public long getCount_month() {
		return count_month;
	}

	public void setCount_month(long countMonth) {
		count_month = countMonth;
	}

	public double getCount_rightPercent() {
		return count_rightPercent;
	}

	public void setCount_rightPercent(double countRightPercent) {
		count_rightPercent = countRightPercent;
	}

	public double getCount_processTime() {
		return count_processTime;
	}

	public void setCount_processTime(double countProcessTime) {
		count_processTime = countProcessTime;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBackname() {
		return backname;
	}

	public void setBackname(String backname) {
		this.backname = backname;
	}

	public String getBackaccount() {
		return backaccount;
	}

	public void setBackaccount(String backaccount) {
		this.backaccount = backaccount;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getLastSignIp() {
		return lastSignIp;
	}

	public void setLastSignIp(String lastSignIp) {
		this.lastSignIp = lastSignIp;
	}

	public String getLastSignPosition() {
		return lastSignPosition;
	}

	public void setLastSignPosition(String lastSignPosition) {
		this.lastSignPosition = lastSignPosition;
	}

}
