package com.hydom.user.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.account.ebean.Member;
import com.hydom.core.server.ebean.Car;
import com.hydom.util.dao.BaseEntity;

/***
 * 用户车辆信息记录
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_usercar")
public class UserCar extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 油耗 单位为L */
	private Double fuel;

	/** 行驶里程 单位为km */
	private Double drange;

	/** 排量 */
	private String engines;

	/** 上路日期 */
	private Date roadDate;

	/**
	 * 车牌号
	 */
	private String carNum;

	/** 车辆颜色 */
	private String carColor;

	/** 是否是用户的默认车：只能有一个默认车 */
	private Boolean defaultCar = false;

	/** 逻辑删除标志 */
	private Boolean visible = true;

	@ManyToOne(cascade = { CascadeType.REFRESH, }, optional = false)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(cascade = { CascadeType.REFRESH, }, optional = false)
	@JoinColumn(name = "car_id")
	private Car car;

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

	public Double getFuel() {
		return fuel;
	}

	public void setFuel(Double fuel) {
		this.fuel = fuel;
	}

	public Double getDrange() {
		return drange;
	}

	public void setDrange(Double drange) {
		this.drange = drange;
	}

	public String getEngines() {
		return engines;
	}

	public void setEngines(String engines) {
		this.engines = engines;
	}

	public Date getRoadDate() {
		return roadDate;
	}

	public void setRoadDate(Date roadDate) {
		this.roadDate = roadDate;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Boolean getDefaultCar() {
		return defaultCar;
	}

	public void setDefaultCar(Boolean defaultCar) {
		this.defaultCar = defaultCar;
	}

}
