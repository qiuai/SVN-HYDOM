/*
 * 
 * 
 * 
 */
package net.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Entity - 车系
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_car_type")
public class CarType extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2878243627467690394L;
	
	private String name; //类型名称
	private CarType parent; //父类
	private Integer level;//级别 最多两级  1 2
	private CarBrand carBrand;//所属品牌
	
	@Column(name="type_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	public CarType getParent() {
		return parent;
	}
	public void setParent(CarType parent) {
		this.parent = parent;
	}
	
	@Column(name="level")
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="car_brand_id")
	public CarBrand getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(CarBrand carBrand) {
		this.carBrand = carBrand;
	}
}