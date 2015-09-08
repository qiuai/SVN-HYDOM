package com.hydom.core.server.ebean;

import java.util.ArrayList;
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

import org.hibernate.annotations.Where;

import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 车系
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_car_type")
public class CarType extends BaseEntity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -313035025909028416L;
	
	/**品牌名称*/
	@Column(name="type_name")
	private String name;
	
	/**上级车系*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private CarType parent;
	
	/**分成等级*/
	@Column(name="level")
	private Integer level;
	
	/**所属品牌id*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="car_brand_id")
	private CarBrand carBrand;

	/**简拼(拼音缩写)*/
	@Column(name="jp")
	private String jp;
	
	/**全拼*/
	@Column(name="qp")
	private String qp;
	
	/** 逻辑删除标志 **/
	@Column(name="visible")
	private Boolean visible = true;
	
	/**
	 * 车刑集合
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="carType")
	@Where(clause="visible = 1")
	@OrderBy("createDate desc")
	private List<Car> carList = new ArrayList<Car>();
	
	/**
	 * 子分类集合
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	@Where(clause="visible = 1")
	@OrderBy("jp asc")
	private Set<CarType> carTypeSet = new HashSet<CarType>();
	
	
	public String getJp() {
		return jp;
	}

	public void setJp(String jp) {
		this.jp = jp;
	}
	
	public String getQp() {
		return qp;
	}

	public void setQp(String qp) {
		this.qp = qp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CarType getParent() {
		return parent;
	}

	public void setParent(CarType parent) {
		this.parent = parent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public CarBrand getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(CarBrand carBrand) {
		this.carBrand = carBrand;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<Car> getCarList() {
		return carList;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	public Set<CarType> getCarTypeSet() {
		return carTypeSet;
	}

	public void setCarTypeSet(Set<CarType> carTypeSet) {
		this.carTypeSet = carTypeSet;
	}
	
}
