package com.hydom.util.bean;

import java.io.Serializable;

import com.hydom.core.server.ebean.Car;
import com.hydom.core.server.ebean.CarBrand;
import com.hydom.core.server.ebean.CarType;
import com.hydom.user.ebean.UserCar;
import com.hydom.user.service.UserCarService;
import com.hydom.util.SpringUtil;

/**
 * 用户车辆
 * @author Administrator
 *
 */
public class UserCarBean implements Serializable{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6684420136385100356L;
	
	/**
	 * 车牌
	 */
	private String carNum;
	
	/**
	 * 车牌颜色
	 */
	private String carColor;
	
	private Car car;
	
	private CarType carType;
	private CarBrand carBrand;
	
	private UserCar userCar;
	
	public UserCarBean(){}
	
	public UserCarBean(String carNum,String carColor,Car car,UserCar userCar){
		this.carNum = carNum;
		this.carColor = carColor;
		this.car = car;
		this.userCar = userCar;
		this.carType = car.getCarType();
		this.carBrand = car.getCarBrand();
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

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public UserCar getUserCar() {
		return userCar;
	}

	public void setUserCar(UserCar userCar) {
		this.userCar = userCar;
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
	
}
