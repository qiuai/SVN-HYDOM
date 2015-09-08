package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class BaoyangOrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String oid;
	private String onum;
	private String ostatus;
	private String ocontact;
	private String ophone;
	private String ocmname;
	private String odate;
	private String oplateNum;
	private String ocarcolor;
	private String address;
	private String lat;
	private String lng;
	private String payway;
	private String usecoup;
	private String stime;
	private String orimoney;
	private String cpmoney;
	private String paymoney;
	
	private String opmoney;
	private String ocmoney;
//	opmoney
//	ocmoney
//	cpmoney
//	paymoney

	
	private List<SeriverTypeitemModel> sclist;

	@Override
	public String toString() {
		return "BaoyangOrderDetail [oid=" + oid + ", onum=" + onum + ", ostatus=" + ostatus + ", ocontact=" + ocontact + ", ophone=" + ophone + ", ocmname=" + ocmname + ", odate=" + odate + ", oplateNum=" + oplateNum + ", ocarcolor=" + ocarcolor + ", address=" + address + ", lat=" + lat + ", lng=" + lng + ", payway=" + payway + ", usecoup=" + usecoup + ", stime=" + stime + ", orimoney=" + orimoney + ", cpmoney=" + cpmoney + ", paymoney=" + paymoney + ", sclist=" + sclist + "]";
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

	public List<SeriverTypeitemModel> getSclist() {
		return sclist;
	}

	public void setSclist(List<SeriverTypeitemModel> sclist) {
		this.sclist = sclist;
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

	public String getOdate() {
		return odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
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

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getOrimoney() {
		return orimoney;
	}

	public void setOrimoney(String orimoney) {
		this.orimoney = orimoney;
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