package com.hydom.core.server.ebean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import com.hydom.account.ebean.Product;
import com.hydom.user.ebean.UserCar;
import com.hydom.util.dao.BaseEntity;

/**
 * Entity - 车辆型号
 * 
 * 
 * 
 */
@Entity
@Table(name = "t_car")
public class Car extends BaseEntity{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2059974918302791083L;

	/**车型名称*/
	@Column(name="car_name")
	private String name;
	
	/**所属车系*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="car_type")
	private CarType carType;
	
	/**所属品牌*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="brand")
	private CarBrand carBrand;
	
	/**图片*/
	@Column(name="imgPath")
	private String imgPath;
	
	/** 逻辑删除标志 **/
	@Column(name="visible")
	private Boolean visible = true;
	
	/**
	 * 商品
	 */
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="carSet")
	private Set<Product> productSet = new HashSet<Product>();
	
	/**
	 * 用户是适用集合
	 * @return
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="car")
	@Where(clause="visible = 1")
	private Set<UserCar> userCarSet = new HashSet<UserCar>();
	
	
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

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
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

	public Set<UserCar> getUserCarSet() {
		return userCarSet;
	}

	public void setUserCarSet(Set<UserCar> userCarSet) {
		this.userCarSet = userCarSet;
	}
}
