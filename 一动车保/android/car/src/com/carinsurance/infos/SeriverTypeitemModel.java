package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class SeriverTypeitemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String sctop;
	// 服务类型所属大分类：
	// sctop=1 纯服务类型；
	// sctop=2 带商品服务类型
	private String scid;// 服务类型id
	// private String scway;
	// 服务方式：
	// scway=1 上门服务 + 门店服务
	// scway=2 上门服务
	// scway=3 门店服务

	private String scname;// 这个种类的名称

	private String scprice;// 服务类型价格
	private String scimage;// 图片
	private String scremark;// 描述
	private String scremark1;
	private String scremark2;

	// 选中的商品
	List<ProductDefaultItemModel> productDefaultItemModel_list;

	// List<ProductDefaultItemModel> splist;
	// 产品或服务具体数据信息
	List<SpModel> plist;

	//下面三个是保养详情 的
	private String scimg;
//	private String scname;
//	private String scprice;
//	List<SpModel> plist;
	private String sccomt;// 能否评论：1=可以评论、0=不可评论


	public String getScimg() {
		return scimg;
	}

	
	public void setScimg(String scimg) {
		this.scimg = scimg;
	}

	public String getSccomt() {
		return sccomt;
	}

	public void setSccomt(String sccomt) {
		this.sccomt = sccomt;
	}

	public List<SpModel> getPlist() {
		return plist;
	}

	public void setPlist(List<SpModel> plist) {
		this.plist = plist;
	}

	public List<ProductDefaultItemModel> getProductDefaultItemModel_list() {
		return productDefaultItemModel_list;
	}

	public String getScprice() {
		return scprice;
	}

	public void setScprice(String scprice) {
		this.scprice = scprice;
	}

	public void setProductDefaultItemModel_list(List<ProductDefaultItemModel> productDefaultItemModel_list) {
		this.productDefaultItemModel_list = productDefaultItemModel_list;
	}

	@Override
	public String toString() {
		return "SeriverTypeitemModel [id=" + id + ", sctop=" + sctop + ", scid=" + scid + ", scname=" + scname + ", scimage=" + scimage + ", scremark=" + scremark + ", scremark1=" + scremark1 + ", scremark2=" + scremark2 + ", productDefaultItemModel_list=" + productDefaultItemModel_list + "]";
	}

	// "sctop": 1,
	// "scid": "a4c3ec1d-6a4d-4bdd-aa8a-8323ce862194",
	// "scname": "换轮胎",
	// "scimage": null
	public String getSctop() {
		return sctop;
	}

	public void setSctop(String sctop) {
		this.sctop = sctop;
	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getScname() {
		return scname;
	}

	public void setScname(String scname) {
		this.scname = scname;
	}

	public String getScimage() {
		return scimage;
	}

	public void setScimage(String scimage) {
		this.scimage = scimage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScremark() {
		return scremark;
	}

	public void setScremark(String scremark) {
		this.scremark = scremark;
	}

	public String getScremark1() {
		return scremark1;
	}

	public void setScremark1(String scremark1) {
		this.scremark1 = scremark1;
	}

	public String getScremark2() {
		return scremark2;
	}

	public void setScremark2(String scremark2) {
		this.scremark2 = scremark2;
	}

}