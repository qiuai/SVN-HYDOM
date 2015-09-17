package com.hydom.core.server.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * @Description 优惠券包
 * @author WY
 * @date 2015年9月7日 上午9:52:44
 */

@Entity
@Table(name = "t_couponPackage")
public class CouponPackage extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6962647472920783593L;

	/** 券包名称 */
	@Column(name = "name")
	private String name;
	
	/** 券包价格 */
	@Column(name = "price")
	private Float price;

	/** 券包图片 */
	@Column(name = "imgPath")
	private String imgPath;
	
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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
