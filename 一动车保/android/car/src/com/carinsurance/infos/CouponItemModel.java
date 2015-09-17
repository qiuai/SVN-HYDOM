package com.carinsurance.infos;

import java.io.Serializable;

public class CouponItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String cpid;// 优惠券ID
	private String cptype;// 优惠券类型
	// 1=满额打折、 2=满额减免、3=免额多少

	private String cpname;// 优惠券名称
	private String cpintroduction;// 优惠券介绍
	private String cpimg;// 优惠券展示图
	private String cppid;// 优惠券包ID
	private String cppimg;// 优惠券包的展示图
	private String cppprice;//优惠券包价格
	
	public String getCppimg() {
		return cppimg;
	}

	public void setCppimg(String cppimg) {
		this.cppimg = cppimg;
	}

	// private String cpid;
	// private String cptype;
	// private String cpname;
	// private String cpintroduction;
	// private String cpimg;
	private String cpexscore;// 优惠券兑换需要的积分

	// 以下是选择优惠卷的
//	private String cpid;// string 优惠券ID
//	private String cptype;// uint 优惠券类型
//	// 1=满额打折、 2=满额减免、3=免额多少
//	private String cpname;// string 优惠券名称
//	private String cpintroduction;// string 优惠券介绍
//	private String cpimg; // string 优惠券展示图
	private String cpminprice; // string 详见下计算方法
	private String cprate;// string 详见下计算方法
	private String cpmoney;// string 详见下计算方法
//	1.满额打折：满多少钱(cpminprice)打几(cprate)折
//	2.满额减免：满多少钱(cpminprice)优惠(cpmoney)多少钱
//	3.免额多少：直接免多少钱(cpmoney)

	public String getCpminprice() {
		return cpminprice;
	}

	public String getCppid() {
		return cppid;
	}

	public void setCppid(String cppid) {
		this.cppid = cppid;
	}

	public String getCppprice() {
		return cppprice;
	}

	public void setCppprice(String cppprice) {
		this.cppprice = cppprice;
	}

	public void setCpminprice(String cpminprice) {
		this.cpminprice = cpminprice;
	}

	public String getCprate() {
		return cprate;
	}

	public void setCprate(String cprate) {
		this.cprate = cprate;
	}

	public String getCpmoney() {
		return cpmoney;
	}

	public void setCpmoney(String cpmoney) {
		this.cpmoney = cpmoney;
	}

	public String getCpexscore() {
		return cpexscore;
	}

	public void setCpexscore(String cpexscore) {
		this.cpexscore = cpexscore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getCptype() {
		return cptype;
	}

	public void setCptype(String cptype) {
		this.cptype = cptype;
	}

	public String getCpname() {
		return cpname;
	}

	public void setCpname(String cpname) {
		this.cpname = cpname;
	}

	public String getCpintroduction() {
		return cpintroduction;
	}

	public void setCpintroduction(String cpintroduction) {
		this.cpintroduction = cpintroduction;
	}

	public String getCpimg() {
		return cpimg;
	}

	public void setCpimg(String cpimg) {
		this.cpimg = cpimg;
	}

}
