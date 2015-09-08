package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	private String result;
	private String oid;//订单id
	
	
	//下面的是我的订单需要额
//	private String oid;//订单id
	private String onum;//订单编号
	private String otype;//订单类型：1=洗车订单、2=保养订单、3=纯商品订单
	private String ostatus;//订单状态
	private String oprice;//实际付款金额
	private String ocanop;//用户能进行的操作：0=不能进行任何操作、1=能取消订单、2=能确认收货

	private String ocontact;//联系人（纯商品为空）
	private String ophone;//联系电话（纯商品为空）
	
	private String ostar;//技师星级
	List<SeriverTypeitemModel> sclist;
	
	
	
	public String getOstar() {
		return ostar;
	}
	public void setOstar(String ostar) {
		this.ostar = ostar;
	}
	public List<SeriverTypeitemModel> getSclist() {
		return sclist;
	}
	public void setSclist(List<SeriverTypeitemModel> sclist) {
		this.sclist = sclist;
	}
	public String getOnum() {
		return onum;
	}
	public void setOnum(String onum) {
		this.onum = onum;
	}
	public String getOtype() {
		return otype;
	}
	public void setOtype(String otype) {
		this.otype = otype;
	}
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	public String getOprice() {
		return oprice;
	}
	public void setOprice(String oprice) {
		this.oprice = oprice;
	}
	public String getOcanop() {
		return ocanop;
	}
	public void setOcanop(String ocanop) {
		this.ocanop = ocanop;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
	
	
}