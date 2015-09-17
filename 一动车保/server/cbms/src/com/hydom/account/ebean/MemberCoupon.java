/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.core.server.ebean.Coupon;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 会员领取优惠券记录
 */
@Entity
@Table(name = "t_member_coupon")
public class MemberCoupon extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3915996210726461009L;

	/** 优惠券使用类型：1=洗车优惠券、2=保养优惠券、3=商品优惠券 */
	@Column(nullable = false)
	private Integer useType;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 有效时间 起始时间 */
	@Column(name = "begin_date")
	private Date beginDate;

	/** 有效时间 结束时间 */
	@Column(name = "end_date")
	private Date endDate;

	/** 介绍 */
	@Column(name = "introduction")
	private String introduction;

	/** 优惠券获取方法：1=积分兑换；2=系统发放；3=充值赠送；4=购买券包；5=首次消费赠送 */
	private Integer gainWay;

	/** 优惠券类型 1满额打折 (minPrice,rate) 2满额优惠(minPrice,discount) 3减免(discount) */
	private Integer type = 1;

	/** 折扣额 */
	@Column(name = "discount", columnDefinition = "decimal(20,2)")
	private Double discount;

	/** 最小商品价格 */
	@Column(name = "min_price", columnDefinition = "decimal(20,2)")
	private Double minPrice;

	/** 折扣率 */
	@Column(name = "rate", columnDefinition = "decimal(20,2)")
	private Double rate;

	/** 图片 */
	@Column(name = "imgPath")
	private String imgPath;

	/**
	 * 使用时间
	 */
	@Column(name = "use_date")
	private Date useDate;

	/**
	 * 领取时间
	 */
	@Column(name = "receive_date")
	private Date receiveDate;

	/**
	 * 优惠券使用状态 0未使用 1已使用 2已过期
	 */
	@Column(name = "status")
	private Integer status = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	/** 积分兑换数 */
	@Column(name = "point")
	private Integer point = 0;

	/** 券包购买记录 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "couponPackage_id")
	private CouponPackageRecord couponPackageRecord;

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	private Boolean visible = true;

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getGainWay() {
		return gainWay;
	}

	public void setGainWay(Integer gainWay) {
		this.gainWay = gainWay;
	}

	public CouponPackageRecord getCouponPackageRecord() {
		return couponPackageRecord;
	}

	public void setCouponPackageRecord(CouponPackageRecord couponPackageRecord) {
		this.couponPackageRecord = couponPackageRecord;
	}

}