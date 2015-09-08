package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class StoreChildModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String soid;//门店ID
	private String soname;//门店名称
	private String soimage;   //门店图片
	private String sodistance; //门店到当前位置的距离
	private String sostar; // 门店星级
	private String socomts;  //门店评价数
	
	private String soaddress; //门店地址
	private List<StoreChilditemModel> sclist;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSoid() {
		return soid;
	}
	public void setSoid(String soid) {
		this.soid = soid;
	}
	public String getSoname() {
		return soname;
	}
	public void setSoname(String soname) {
		this.soname = soname;
	}
	public String getSoimage() {
		return soimage;
	}
	public void setSoimage(String soimage) {
		this.soimage = soimage;
	}
	public String getSodistance() {
		return sodistance;
	}
	public void setSodistance(String sodistance) {
		this.sodistance = sodistance;
	}
	public String getSostar() {
		return sostar;
	}
	public void setSostar(String sostar) {
		this.sostar = sostar;
	}
	public String getSocomts() {
		return socomts;
	}
	public void setSocomts(String socomts) {
		this.socomts = socomts;
	}
	public String getSoaddress() {
		return soaddress;
	}
	public void setSoaddress(String soaddress) {
		this.soaddress = soaddress;
	}
	public List<StoreChilditemModel> getSclist() {
		return sclist;
	}
	public void setSclist(List<StoreChilditemModel> sclist) {
		this.sclist = sclist;
	}
	
	
	
    
}