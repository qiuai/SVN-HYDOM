package com.carinsurance.infos;

import java.io.Serializable;

import com.carinsurance.abcpinying.SortModel;

public class UpDingDan_baoyang implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private String uid;
	// private String token;
	// private String
	private String uid;// 用户ID
	private String token;// 令牌

	private String ucid;// 用户车辆id
	private String stime;// 预约服务时间// 格式：2015-08-09 08:30
	private String phone;// 联系电话
	private String contact;// 联系人
	private String palateNumber;// 车牌
	private String color;// 颜色

	private String lat;// 纬度
	private String lng;// 经度
	private String address;// 详细地址

	private String cpid;// 优惠卷id
	private String sway;// 服务方式// 1=货到付款；2=支付宝 ；3=银联
	private String payWay;// 支付方式
	// 支付方式
	// 1=货到付款；2=支付宝 ；
	// 3=银联；4=微信

	private String httpData;// 商品服务json数据，格式示例如下：
							// {"scid1":{"p1":1,"p2":2},"scid2":{"p3":2,"p4":3,"p5":3}}

	// private String scid;// 服务类型ID
	// private String cleanType;//支付方式1=内部清洗；2=内外清洗 ；

	private SortModel sortModel;
	// 1=货到付款；2=支付宝 ；
	// 3=银联；4=微信

	// private String cmid;// 车型ID
	//
	// private String sid;// 街道ID
	// private String stime;// 预约服务开始时间
	// // 格式：2015-08-09 08:30
	//
	// private String etime;//预约服务结束时间
	// // 格式：2015-08-09 09:30
	//
	// private String sway;//配送方式
	// // 1=货到付款；2=支付宝 ；3=银联
	//
	// private String pway;//
	// // 支付方式
	// // 1=上门服务； 2=到门店
	//
	// private String ssid;//门店ID(no)

	// 用户界面展示的
	private String csName;

	public String getCsName() {
		return csName;
	}

	public void setCsName(String csName) {
		this.csName = csName;
	}

	public String getUid() {
		return uid;
	}

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public String getPalateNumber() {
		return palateNumber;
	}

	public void setPalateNumber(String palateNumber) {
		this.palateNumber = palateNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}



	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	// public String getCmid() {
	// return cmid;
	// }
	//
	// public void setCmid(String cmid) {
	// this.cmid = cmid;
	// }


	// public String getSid() {
	// return sid;
	// }
	//
	// public void setSid(String sid) {
	// this.sid = sid;
	// }
	//
	// public String getStime() {
	// return stime;
	// }
	//
	// public void setStime(String stime) {
	// this.stime = stime;
	// }

	// public String getEtime() {
	// return etime;
	// }

	// public void setEtime(String etime) {
	// this.etime = etime;
	// }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "UpDingDan_baoyang [uid=" + uid + ", token=" + token + ", ucid=" + ucid + ", stime=" + stime + ", phone=" + phone + ", contact=" + contact + ", palateNumber=" + palateNumber + ", color=" + color + ", lat=" + lat + ", lng=" + lng + ", address=" + address + ", cpid=" + cpid + ", sway=" + sway + ", payWay=" + payWay + ", httpData=" + httpData + ", sortModel=" + sortModel + ", csName=" + csName + "]";
	}

	public SortModel getSortModel() {
		return sortModel;
	}

	public void setSortModel(SortModel sortModel) {
		this.sortModel = sortModel;
	}

	// public String getSway() {
	// return sway;
	// }
	//
	// public void setSway(String sway) {
	// this.sway = sway;
	// }
	//
	// public String getPway() {
	// return pway;
	// }
	//
	// public void setPway(String pway) {
	// this.pway = pway;
	// }

	// public String getSsid() {
	// return ssid;
	// }
	//
	// public void setSsid(String ssid) {
	// this.ssid = ssid;
	// }

}