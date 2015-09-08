/*
 * 
 * 
 * 
 */
package com.hydom.account.ebean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hydom.core.order.ebean.TechnicianBindRecord;
import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarTeam;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 订单
 * 
 */
@Entity
@Table(name = "t_order")
public class Order extends BaseEntity {
	private static final long serialVersionUID = 69734876638441645L;

	/** 洗车订单 保养订单 公用字段 */

	/** 联系电话 */
	private String phone;

	/** 联系人 */
	private String contact;

	/** 详细地址 */
	private String address;

	/** 支付方式 1=会员卡支付；2=支付宝 ；3=银联 4=微信 5=现成支付 **/
	private Integer payWay;

	/** 配送方式 1=上门服务； 2=到门店 **/
	private Integer serverWay = 1;

	/** 优惠价：优惠了多少钱 **/
	@Column(columnDefinition = "decimal(20,2)")
	private Float amount_paid;

	/** 原价 **/
	@Column(columnDefinition = "decimal(20,2)")
	private Float amount_money;

	/** 实际价格 **/
	@Column(columnDefinition = "decimal(20,2)")
	private Float price;

	/** 订单编号 **/
	@Column(unique = true, nullable = false)
	private String num;

	/**
	 * 已完结 0 <br>
	 * 洗车服务订单状态 1派单中 2路途中 3服务中 1-9洗车状态 <br>
	 * 保养服务订单状态 11 预约成功 12 已分配车队 11-19 保养服务状态<br>
	 * 纯商品订单状态 21已下单 22配货中 23送货中 21-29 纯商品订单状态 <br>
	 * 取消订单状态 31待审核 32 审核中 33 退款中 34已退款 35未通过 31-39取消订单状态<br>
	 */
	private Integer status;

	/** 备注 */
	private String remark;

	/** 类型 1洗车订单 2保养订单 3纯商品订单 **/
	private Integer type;

	/**
	 * 消费记录
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
	private FeeRecord feeRecord;

	/** 用户预约开始时间 **/
	@Column(name = "start_date")
	private Date startDate;

	/** 用户预约结束时间 **/
	@Column(name = "end_date")
	private Date endDate;

	/** 车型 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_id")
	private Car car;

	/** 行驶里程 单位为km */
	private Double drange;

	/**
	 * 车牌
	 */
	private String carNum;

	/**
	 * 车辆颜色
	 */
	private String carColor;

	/**
	 * 经度
	 */
	private Double lng;

	/**
	 * 纬度
	 */
	private Double lat;

	/** 用户 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	/** 优惠卷 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_coupon_id")
	private MemberCoupon memberCoupon;

	/**
	 * 保养订单：带商品时，在serverOrder中的serverOrderDetail进行体现
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
	@OrderBy("serviceType asc")
	private Set<ServerOrder> serverOrder = new HashSet<ServerOrder>();

	/**
	 * 订单中的商品：针对纯商品
	 * 
	 * @return
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
	private Set<ServerOrderDetail> serverOrderDetail = new HashSet<ServerOrderDetail>();

	/**
	 * 公共字段结束 流程 订单保存 1 服务订单 2、保存各服务订单中的所有商品
	 */

	/** 洗车专用字段 */

	/**
	 * 技师
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id")
	private Technician techMember;

	/** 当前对应的绑定记录 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tech_bindrecord_id")
	private TechnicianBindRecord technicianBindRecord;

	/** 技师与用户之间的距离：在绑定技师时填充该数据 */
	@Column(columnDefinition = "decimal(20,2)")
	private Double distance;

	/**
	 * 清洗方式 1 内部清洗 2 内外清洗
	 */
	@Column(name = "cleantype")
	private Integer cleanType;

	/**
	 * 技师接单时间
	 */
	@Column(name = "orders_Date")
	private Date ordersDate;

	/**
	 * 保养服务专用
	 */

	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private Area area;
	/**
	 * 服务开始时间
	 */
	@Column(name = "mark_startdate")
	private Date makeStartDate;

	/**
	 * 服务结束时间
	 */
	@Column(name = "mark_enddate")
	private Date makeEndDate;

	/** 车队 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_team_id")
	private CarTeam carTeam;

	/** 逻辑删除标志 **/
	@Column(name = "visible")
	private Boolean visible = true;

	/**
	 * 是否支付 默认为未支付false true为支付
	 */
	@Column(name = "ispay", nullable = false)
	private Boolean isPay = false;

	public Double getDrange() {
		return drange;
	}

	public void setDrange(Double drange) {
		this.drange = drange;
	}

	public FeeRecord getFeeRecord() {
		return feeRecord;
	}

	public void setFeeRecord(FeeRecord feeRecord) {
		this.feeRecord = feeRecord;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getServerWay() {
		return serverWay;
	}

	public void setServerWay(Integer serverWay) {
		this.serverWay = serverWay;
	}

	public Float getAmount_paid() {
		return amount_paid;
	}

	public void setAmount_paid(Float amount_paid) {
		this.amount_paid = amount_paid;
	}

	public Float getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(Float amount_money) {
		this.amount_money = amount_money;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberCoupon getMemberCoupon() {
		return memberCoupon;
	}

	public void setMemberCoupon(MemberCoupon memberCoupon) {
		this.memberCoupon = memberCoupon;
	}

	public Integer getCleanType() {
		return cleanType;
	}

	public void setCleanType(Integer cleanType) {
		this.cleanType = cleanType;
	}

	public Technician getTechMember() {
		return techMember;
	}

	public void setTechMember(Technician techMember) {
		this.techMember = techMember;
	}

	public Date getMakeStartDate() {
		return makeStartDate;
	}

	public void setMakeStartDate(Date makeStartDate) {
		this.makeStartDate = makeStartDate;
	}

	public CarTeam getCarTeam() {
		return carTeam;
	}

	public void setCarTeam(CarTeam carTeam) {
		this.carTeam = carTeam;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Set<ServerOrder> getServerOrder() {
		return serverOrder;
	}

	public void setServerOrder(Set<ServerOrder> serverOrder) {
		this.serverOrder = serverOrder;
	}

	public Set<ServerOrderDetail> getServerOrderDetail() {
		return serverOrderDetail;
	}

	public void setServerOrderDetail(Set<ServerOrderDetail> serverOrderDetail) {
		this.serverOrderDetail = serverOrderDetail;
	}

	public TechnicianBindRecord getTechnicianBindRecord() {
		return technicianBindRecord;
	}

	public void setTechnicianBindRecord(
			TechnicianBindRecord technicianBindRecord) {
		this.technicianBindRecord = technicianBindRecord;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}

	public Date getMakeEndDate() {
		return makeEndDate;
	}

	public void setMakeEndDate(Date makeEndDate) {
		this.makeEndDate = makeEndDate;
	}

	public Date getOrdersDate() {
		return ordersDate;
	}

	public void setOrdersDate(Date ordersDate) {
		this.ordersDate = ordersDate;
	}

	@Transient
	public String getDateTimeMap() {

		if (this.type == null || this.type == 1) {
			return "";
		}

		Date startDate = this.startDate;
		Date endDate = this.endDate;

		String time = DateTimeHelper.formatDateTimetoString(startDate,
				"yyyy-MM-dd");

		String start = DateTimeHelper
				.formatDateTimetoString(startDate, "HH:mm");
		String end = DateTimeHelper.formatDateTimetoString(endDate, "HH:mm");

		return time + "(" + start + " - " + end + ")";
	}

	@Transient
	public String getStatusString() {

		/**
		 * 已完结 0 <br>
		 * 洗车服务订单状态 1派单中 2路途中 3服务中 1-9洗车状态 <br>
		 * 保养服务订单状态 11 预约成功 12 已分配车队 11-19 保养服务状态<br>
		 * 纯商品订单状态 21已下单 22配货中 23送货中 21-29 纯商品订单状态 <br>
		 * 取消订单状态 31待审核 32 审核中 33 退款中 34已退款 35未通过 36已完结【待定】 31-39取消订单状态<br>
		 */
		switch (status) {
		case 0:
			return "已完结";
		case 1:
			return "派单中";
		case 2:
			return "路途中";
		case 3:
			return "服务中";

		case 11:
			return "预约成功";
		case 12:
			return "已分配车队";

		case 21:
			return "已下单";
		case 22:
			return "配货中";
		case 23:
			return "送货中";

		case 31:
			return "待审核";
		case 32:
			return "审核中";
		case 33:
			return "退款中";
		case 34:
			return "已退款";
		case 35:
			return "退款未通过";
		case 36:
			return "已完结";
		default:
			return "未知状态";
		}

	}
}