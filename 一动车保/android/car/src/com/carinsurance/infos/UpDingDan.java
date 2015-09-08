package com.carinsurance.infos;

import java.io.Serializable;
import com.carinsurance.abcpinying.SortModel;

public class UpDingDan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private String uid;
	// private String token;
	// private String
	private String uid;// 用户ID
	private String token;// 令牌
	
	private String ucid;//用户车辆id 
	private String scid;// 服务类型ID
	private String contact;//联系人
	private String phone;//联系电话
	private String palateNumber;//车牌
	private String color;//颜色

	private String lat;//纬度
	private String lng;//经度
	private String address;//详细地址
	private String cpid;//优惠卷id
	
	
	private String cleanType;//支付方式1=内部清洗；2=内外清洗 ；
	private String payWay;//支付方式
	
	
	private SortModel sortModel;
//    1=货到付款；2=支付宝 ；
//    3=银联；4=微信 


	
	//下面的是保养的
//	private String uid;// 用户ID
//	private String token;// 令牌
//	private String ucid;// 用户车辆id
	private String stime;// 预约服务开始时间// 格式：2015-08-09 08:30
	private String etime;//预约服务结束时间// 格式：2015-08-09 08:30
//	private String phone;// 联系电话
//	private String contact;// 联系人
//	private String palateNumber;// 车牌
//	private String color;// 颜色

//	private String lat;// 纬度
//	private String lng;// 经度
//	private String address;// 详细地址

//	private String cpid;// 优惠卷id
	private String remark;//备注
	private String sway;// 服务方式// 1=货到付款；2=支付宝 ；3=银联
//	private String payWay;// 支付方式
	// 支付方式
	// 1=货到付款；2=支付宝 ；
	// 3=银联；4=微信

	private String httpData;// 商品服务json数据，格式示例如下：
//下面四个只是提交订单的时候的显示，然并卵
	private String tv_shangpingjiage;//商品价格
	private String tv_fuwufeiyong;//服务费用
	private String tv_youhuijuan;//优惠卷
	private String tv_shifukuan;//实付款
	
	
	//注意，下面的是特色商城 /order/product/save需要的
	private String pid;//商品ID
	private String pnumber;
//	private String phone;
//	private String contact;
	private String aid;
//	private String address;
//	private String cpid;;
//	private String payWay;
	
	
	private CouponItemModel couponItemModel;
	
	public CouponItemModel getCouponItemModel() {
		return couponItemModel;
	}

	public void setCouponItemModel(CouponItemModel couponItemModel) {
		this.couponItemModel = couponItemModel;
	}

	public String getRemark() {
		return remark;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPnumber() {
		return pnumber;
	}

	public void setPnumber(String pnumber) {
		this.pnumber = pnumber;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	//用户界面展示的
	private String csName;
	
	public String getTv_shangpingjiage() {
		return tv_shangpingjiage;
	}

	public void setTv_shangpingjiage(String tv_shangpingjiage) {
		this.tv_shangpingjiage = tv_shangpingjiage;
	}

	public String getTv_fuwufeiyong() {
		return tv_fuwufeiyong;
	}

	public void setTv_fuwufeiyong(String tv_fuwufeiyong) {
		this.tv_fuwufeiyong = tv_fuwufeiyong;
	}

	public String getTv_youhuijuan() {
		return tv_youhuijuan;
	}

	public void setTv_youhuijuan(String tv_youhuijuan) {
		this.tv_youhuijuan = tv_youhuijuan;
	}

	public String getTv_shifukuan() {
		return tv_shifukuan;
	}

	public void setTv_shifukuan(String tv_shifukuan) {
		this.tv_shifukuan = tv_shifukuan;
	}

	public String getCsName() {
		return csName;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
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

	public String getCleanType() {
		return cleanType;
	}

	public void setCleanType(String cleanType) {
		this.cleanType = cleanType;
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

//	public String getCmid() {
//		return cmid;
//	}
//
//	public void setCmid(String cmid) {
//		this.cmid = cmid;
//	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

//	public String getSid() {
//		return sid;
//	}
//
//	public void setSid(String sid) {
//		this.sid = sid;
//	}
//
//	public String getStime() {
//		return stime;
//	}
//
//	public void setStime(String stime) {
//		this.stime = stime;
//	}

//	public String getEtime() {
//		return etime;
//	}

//	public void setEtime(String etime) {
//		this.etime = etime;
//	}

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
		return "UpDingDan [uid=" + uid + ", token=" + token + ", ucid=" + ucid + ", scid=" + scid + ", contact=" + contact + ", phone=" + phone + ", palateNumber=" + palateNumber + ", color=" + color + ", lat=" + lat + ", lng=" + lng + ", address=" + address + ", cpid=" + cpid + ", cleanType=" + cleanType + ", payWay=" + payWay + ", sortModel=" + sortModel + ", csName=" + csName + "]";
	}

	public SortModel getSortModel() {
		return sortModel;
	}

	public void setSortModel(SortModel sortModel) {
		this.sortModel = sortModel;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getSway() {
		return sway;
	}

	public void setSway(String sway) {
		this.sway = sway;
	}

	public String getHttpData() {
		return httpData;
	}

	public void setHttpData(String httpData) {
		this.httpData = httpData;
	}

//	public String getSway() {
//		return sway;
//	}
//
//	public void setSway(String sway) {
//		this.sway = sway;
//	}
//
//	public String getPway() {
//		return pway;
//	}
//
//	public void setPway(String pway) {
//		this.pway = pway;
//	}

//	public String getSsid() {
//		return ssid;
//	}
//
//	public void setSsid(String ssid) {
//		this.ssid = ssid;
//	}


}
