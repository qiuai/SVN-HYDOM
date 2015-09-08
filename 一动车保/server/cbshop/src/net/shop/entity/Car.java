/*
 * 
 * 
 * 
 */
package net.shop.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.shop.Setting;
import net.shop.util.SettingUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.DateUtils;


/**
 * Entity - 车辆型号
 * 
 * 
 * 
 */
@Entity
@Table(name = "cb_car")
public class Car extends BaseEntity {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2641047975128077012L;
	
	private CarBrand brand; //品牌
	private CarType carType;//车系
	private String name;//车辆名称
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="brand")
	public CarBrand getBrand() {
		return brand;
	}
	public void setBrand(CarBrand brand) {
		this.brand = brand;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="car_type")
	public CarType getCarType() {
		return carType;
	}
	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	
	@Column(name="car_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}