package com.carinsurance.infos;

import java.io.Serializable;

public class SpModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pid;//产品或洗车服务ID
	private String pimg;//产品或具体服务图标
	private String pname;//产品或服务名称
	private String premark;//洗车服务说明，其他情况留空
	private String pprice;//产品价格，为服务时留空
	private String pnum;//产品数量，为服务时留空
	private String pcomt;
	public String getPid() {
		return pid;
	}
	
	public String getPcomt() {
		return pcomt;
	}

	public void setPcomt(String pcomt) {
		this.pcomt = pcomt;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPimg() {
		return pimg;
	}
	public void setPimg(String pimg) {
		this.pimg = pimg;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPremark() {
		return premark;
	}
	public void setPremark(String premark) {
		this.premark = premark;
	}
	public String getPprice() {
		return pprice;
	}
	public void setPprice(String pprice) {
		this.pprice = pprice;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	

}
