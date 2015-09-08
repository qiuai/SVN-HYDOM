package com.hydom.core.server.ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 汽车品牌
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_car_brand")
public class CarBrand extends BaseEntity{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7341132282586384189L;
	
	/**品牌名称*/
	@Column(name="name",nullable=false)
	private String name;
	
	/**首字母*/
	@Column(name="initial",nullable=false)
	private String initial;
	
	/**简拼(拼音缩写)*/
	@Column(name="jp",nullable=false)
	private String jp;
	
	/**全拼*/
	@Column(name="qp",nullable=false)
	private String qp;
	
	/**图标*/
	@Column(name="ico")
	private String imgPath;
	
	/** 逻辑删除标志 */
	@Column(name="visible",nullable=false)
	private Boolean visible = true;
	
	/**
	 * 车系集合
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="carBrand")
	@Where(clause="visible = 1")
	@OrderBy("createDate desc")
	private List<CarType> carTypeList = new ArrayList<CarType>();
	
	/**
	 * 车辆集合
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="carBrand")
	@Where(clause="visible = 1")
	@OrderBy("createDate desc")
	private List<Car> carList = new ArrayList<Car>();
	
	public List<Car> getCarList() {
		return carList;
	}
	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}
	public List<CarType> getCarTypeList() {
		return carTypeList;
	}
	public void setCarTypeList(List<CarType> carTypeList) {
		this.carTypeList = carTypeList;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getQp() {
		return qp;
	}
	public void setQp(String qp) {
		this.qp = qp;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getJp() {
		return jp;
	}
	public void setJp(String jp) {
		this.jp = jp;
	}
	
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
