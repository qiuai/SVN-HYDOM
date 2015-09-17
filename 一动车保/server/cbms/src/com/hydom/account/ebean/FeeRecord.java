package com.hydom.account.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.hydom.core.server.ebean.CouponPackage;
import com.hydom.core.server.ebean.CouponPackageRecord;
import com.hydom.util.dao.BaseEntity;

/**
 * 充值消费记录表
 * 
 */
@Entity
@Table(name = "t_feerecord")
public class FeeRecord extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1212550022262222504L;

	/** 1=充值、2=消费、3=购买会员卡(优惠券包) */
	@Column(nullable = false)
	private Integer type;

	/** 充值或消费金额 */
	@Column(nullable = false)
	private Float fee;

	/** 支付编号 用来退款等 */
	@Column(name = "trade_no")
	private String tradeNo;

	/** 充值编号 */
	private String rechargeNo;

	/**
	 * 消费订单
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	/**
	 * 优惠券包
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "feeRecord", cascade = CascadeType.PERSIST)
	private CouponPackageRecord couponPackageRecord;

	/**
	 * 消费方式 比如 支付宝 银联 1=会员卡支付；<br>
	 * 2=支付宝 ；3=银联； 4=微信 5 现金支付
	 **/
	private Integer payWay;

	/**
	 * 消费者电话号码
	 */
	private String phone;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private Boolean visible = true;

	/**
	 * 是否退费 1 已退费 0 未退费
	 */
	private Integer isRefund;

	/**
	 * 退费商家交易码
	 */
	private String refundNo;

	/**
	 * 微信 退款编号
	 */
	private String refundContent;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getRechargeNo() {
		return rechargeNo;
	}

	public void setRechargeNo(String rechargeNo) {
		this.rechargeNo = rechargeNo;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRefundContent() {
		return refundContent;
	}

	public void setRefundContent(String refundContent) {
		this.refundContent = refundContent;
	}

	public CouponPackageRecord getCouponPackageRecord() {
		return couponPackageRecord;
	}

	public void setCouponPackageRecord(CouponPackageRecord couponPackageRecord) {
		this.couponPackageRecord = couponPackageRecord;
	}

}
