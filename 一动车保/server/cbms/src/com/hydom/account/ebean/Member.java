/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hydom.user.ebean.UserCar;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 会员
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_member")
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;

	/**
	 * 性别
	 */
	public class Gender {

		public final static int male = 0;
		public final static int female = 1;
	}

	/**
	 * 用户名
	 * 
	 * @Column(nullable=false,unique=true) private String username;
	 */

	/** 密码 */
	private String password;

	/** E-mail */
	private String email;

	/** 姓名 */
	private String name;

	/** 性别 0 男 1女 */
	private Integer gender;

	/** 出生日期 */
	private Date birth;

	/** 地址 */
	private String address;

	/** 电话 */
	private String phone;

	/** 头像路径 */
	private String photo;

	/** 昵称 */
	private String nickname;

	/** 手机 */
	@Column(nullable = false, unique = true)
	private String mobile;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "area_id", updatable = false)
	private Area area;

	/**
	 * 积分
	 */
	@Column(columnDefinition = "decimal(20,2)")
	private Float amount = 0f;

	/**
	 * 余额
	 */
	@Column(columnDefinition = "decimal(20,2)")
	private Float money = 0f;

	/**
	 * 用户状态 1 正常 0 不正常
	 */
	private Integer status = 1;

	/**
	 * 是否删除
	 */
	private Boolean visible = true;

	/**
	 * 会员等级
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_rank_id")
	private MemberRank memberRank;

	/**
	 * 用户车辆
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@OrderBy("createDate desc")
	private List<UserCar> userCarSet = new ArrayList<UserCar>();

	/**
	 * 用户领取的优惠卷
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private Set<MemberCoupon> couponSet = new HashSet<MemberCoupon>();

	@Transient
	public String getMemberNamePhone() {
		String phone = this.mobile;
		String codePhone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})",
				"$1****$2");
		return codePhone;
	}

	public Set<MemberCoupon> getCouponSet() {
		return couponSet;
	}

	public void setCouponSet(Set<MemberCoupon> couponSet) {
		this.couponSet = couponSet;
	}

	public List<UserCar> getUserCarSet() {
		return userCarSet;
	}

	public void setUserCarSet(List<UserCar> userCarSet) {
		this.userCarSet = userCarSet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}