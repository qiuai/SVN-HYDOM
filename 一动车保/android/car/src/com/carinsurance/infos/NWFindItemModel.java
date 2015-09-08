package com.carinsurance.infos;

import java.io.Serializable;

public class NWFindItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nwid;//ID
	private String nwtitle;//标题
	
	private String nwimage;//展示图
	private String nwdate;//发布日期 格式：2015-08-09
	private String nwstar;//赞数
	
	private String nwhtml;//html地址(作废)
	private String nwurl;//html地址
	
	
	
	public String getNwurl() {
		return nwurl;
	}

	public void setNwurl(String nwurl) {
		this.nwurl = nwurl;
	}

	public String getNwid() {
		return nwid;
	}

	public void setNwid(String nwid) {
		this.nwid = nwid;
	}

	public String getNwtitle() {
		return nwtitle;
	}

	public void setNwtitle(String nwtitle) {
		this.nwtitle = nwtitle;
	}

	public String getNwimage() {
		return nwimage;
	}

	public void setNwimage(String nwimage) {
		this.nwimage = nwimage;
	}

	public String getNwdate() {
		return nwdate;
	}

	public void setNwdate(String nwdate) {
		this.nwdate = nwdate;
	}

	public String getNwstar() {
		return nwstar;
	}

	public void setNwstar(String nwstar) {
		this.nwstar = nwstar;
	}

	public String getNwhtml() {
		return nwhtml;
	}

	public void setNwhtml(String nwhtml) {
		this.nwhtml = nwhtml;
	}

	
	
	
}