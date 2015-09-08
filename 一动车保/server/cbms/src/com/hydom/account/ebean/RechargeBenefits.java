package com.hydom.account.ebean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.core.server.ebean.Coupon;
import com.hydom.util.dao.BaseEntity;

/**
 * 充值福利定义
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_recharge_benefits")
public class RechargeBenefits extends BaseEntity {

	private static final long serialVersionUID = 6100851353056203127L;

	/** 福利类型：1=充值返现；2=充值送优惠券 */
	private Integer type = 0;

	/**金额 */
	@Column(columnDefinition = "decimal(20,2)")
	private BigDecimal money;

	/** 返现比例：小于1 */
	@Column(columnDefinition = "decimal(20,2)")
	private Float scale;

	/** 是否启用 */
	//默认禁用
	private Boolean enable = false;

	/** 赠送的优惠券 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	/** 赠送的优惠券数量 */
	private Long couponNumber;

	private Boolean visible = true;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Long couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
