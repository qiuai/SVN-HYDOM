package com.hydom.core.server.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * @Description 首次消费送优惠券
 * @author WY
 * @date 2015年9月10日 上午9:52:44
 */

@Entity
@Table(name = "t_firstSpendSendCoupon")
public class FirstSpendSendCoupon extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 379493951909574372L;

	/** 活动名称 */
	@Column(name = "name")
	private String name;
	
	/** 活动范围（参与活动的服务类型）1 洗车服务  2 保养服务 3 商品 */
	@Column(name = "range")
	private Integer range;
	
	/** 最低消费额 */
	@Column(name = "min_price")
	private Float minPrice;
	
	/** 优惠券 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;
	
	/** 优惠券数量 */
	@Column(name = "coupon_count")
	private Integer couponCount;
	
	/** 介绍 */
	@Column(name = "introduction")
	private String introduction;
	
	/** 是否启用 */
	@Column(name = "is_enabled", nullable = false)
	private Boolean isEnabled = true;
	
	/** 逻辑删除标志 */
	@Column(name = "visible", nullable = false)
	private Boolean visible = true;

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Float minPrice) {
		this.minPrice = minPrice;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
