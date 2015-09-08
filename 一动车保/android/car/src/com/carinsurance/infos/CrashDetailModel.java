package com.carinsurance.infos;

import java.io.Serializable;

public class CrashDetailModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result;
	private String oid;
	private String onum;
	private String ostatus;
	private String ocontact;
	private String ophone;
	private String ocmname;
	private String ocleanType;
	private String oplateNum;
	private String ocarcolor;
	private String osimgpath;
	private String osname;
	private String osremark;
	private String osprice;
	private String address;
	private String lat;
	private String lng;
	private String payway;
	private String usecoup;//是否使用优惠券
	private String opmoney;
	private String ocmoney;
	private String cpmoney;//优惠金额
	private String paymoney;//实际支付
	
//	"result":"001","oid":"67c49b68-f7df-4b0f-9d21-36409b9ff7e0","onum":"20150811172542243455","ostatus":"待审核","ocontact":"123","ophone":"123","ocmname":"大众车型13","ocleanType":"1","oplateNum":"","ocarcolor":"","osimgpath":"upload/img/2015/6/16/0b4f207e-8b43-4ada-ad13-c8ea8798d87f.jpg","osname":"上门洗车","osremark":"支持各种车型","osprice":"30.0","address":"四川省成都市武侯区望江路街道临江东路36","lat":30.643554,"lng":104.07962,"payway":"1","usecoup":"未使用","orimoney":"null","cpmoney":"null","paymoney":"null"}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOnum() {
		return onum;
	}
	public void setOnum(String onum) {
		this.onum = onum;
	}
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	public String getOcontact() {
		return ocontact;
	}
	public void setOcontact(String ocontact) {
		this.ocontact = ocontact;
	}
	public String getOphone() {
		return ophone;
	}
	public void setOphone(String ophone) {
		this.ophone = ophone;
	}
	public String getOcmname() {
		return ocmname;
	}
	public void setOcmname(String ocmname) {
		this.ocmname = ocmname;
	}
	public String getOcleanType() {
		return ocleanType;
	}
	public void setOcleanType(String ocleanType) {
		this.ocleanType = ocleanType;
	}
	public String getOplateNum() {
		return oplateNum;
	}
	public void setOplateNum(String oplateNum) {
		this.oplateNum = oplateNum;
	}
	public String getOcarcolor() {
		return ocarcolor;
	}
	public void setOcarcolor(String ocarcolor) {
		this.ocarcolor = ocarcolor;
	}
	public String getOsimgpath() {
		return osimgpath;
	}
	public void setOsimgpath(String osimgpath) {
		this.osimgpath = osimgpath;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname(String osname) {
		this.osname = osname;
	}
	public String getOsremark() {
		return osremark;
	}
	public void setOsremark(String osremark) {
		this.osremark = osremark;
	}
	public String getOsprice() {
		return osprice;
	}
	public void setOsprice(String osprice) {
		this.osprice = osprice;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getUsecoup() {
		return usecoup;
	}
	public void setUsecoup(String usecoup) {
		this.usecoup = usecoup;
	}
	public String getOpmoney() {
		return opmoney;
	}
	public void setOpmoney(String opmoney) {
		this.opmoney = opmoney;
	}
	public String getOcmoney() {
		return ocmoney;
	}
	public void setOcmoney(String ocmoney) {
		this.ocmoney = ocmoney;
	}
	public String getCpmoney() {
		return cpmoney;
	}
	public void setCpmoney(String cpmoney) {
		this.cpmoney = cpmoney;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	
	

}
