package com.carinsurance.abcpinying;

import java.io.Serializable;
import java.util.List;

import com.carinsurance.infos.SeriverTypeitemModel;

public class SortModel implements Serializable {

	/**
	 * "cbid": "973ce43c-dcc7-4a25-bc59-9fc486991506", "name": "大众", "fletter":
	 * "D", "cbimage": null },
	 */
	private static final long serialVersionUID = 3385583022185114227L;
	private String id;// id
	private String cbid;//车辆品牌id
	private String name;//车辆品牌名称
	private String fletter;//
	private String cbimage;//车的logo图片

//	private String fuwutypeid;//服务类型id
	
	
	private String type;// 为0时选车系，1时选车型

	private String csid;//车系id
	private String cs_name;//车系名称
	
	private String cmid;//车型id
	private String cm_name;//车型名 称 
	
	
	private String topName;//属于哪种类
	
	//后面加的
//	private String sctop;
	// 服务类型所属大分类：
	// sctop=1 纯服务类型；
	// sctop=2 带商品服务类型
//	private String scid;//服务类型id
//	private String scname;//这个种类的名称
//	private String scimage;//图片
//	private String scremark;//描述
	//首页server/category接口中的数据
	private SeriverTypeitemModel SeriverTypeitemModel0;
	private String ucid;//客户车辆id
	
	private String color;//车的颜色
	
	private String plateNumber;//车牌号
	
	
	private List<SeriverTypeitemModel> selectSeriverTypeitemList;//选中的服务类型
	
	private String is_zibeipeijian;//是否自备配件 0 不是         1自备配件(只收服务费)
	

	
	private String pci_id;
	private String pci_name;
	
	
	
	
	public String getPci_id() {
		return pci_id;
	}

	public void setPci_id(String pci_id) {
		this.pci_id = pci_id;
	}

	public String getPci_name() {
		return pci_name;
	}

	public void setPci_name(String pci_name) {
		this.pci_name = pci_name;
	}

	public String getIs_zibeipeijian() {
		return is_zibeipeijian;
	}

	public void setIs_zibeipeijian(String is_zibeipeijian) {
		this.is_zibeipeijian = is_zibeipeijian;
	}

	public List<SeriverTypeitemModel> getSelectSeriverTypeitemList() {
		return selectSeriverTypeitemList;
	}

	public void setSelectSeriverTypeitemList(List<SeriverTypeitemModel> selectSeriverTypeitemList) {
		this.selectSeriverTypeitemList = selectSeriverTypeitemList;
	}

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public SeriverTypeitemModel getSeriverTypeitemModel0() {
		return SeriverTypeitemModel0;
	}

	public void setSeriverTypeitemModel0(SeriverTypeitemModel seriverTypeitemModel0) {
		SeriverTypeitemModel0 = seriverTypeitemModel0;
	}

	public String getCsid() {
		return csid;
	}

	public void setCsid(String csid) {
		this.csid = csid;
	}

	public String getCs_name() {
		return cs_name;
	}

	public void setCs_name(String cs_name) {
		this.cs_name = cs_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCbid() {
		return cbid;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFletter() {
		return fletter;
	}

	public void setFletter(String fletter) {
		this.fletter = fletter;
	}

	public String getCbimage() {
		return cbimage;
	}

	public void setCbimage(String cbimage) {
		this.cbimage = cbimage;
	}

//	public String getFuwutypeid() {
//		return fuwutypeid;
//	}
//
//	public void setFuwutypeid(String fuwutypeid) {
//		this.fuwutypeid = fuwutypeid;
//	}

	// private String is_firend;// 是否是好友
	// private String motto;// 座右铭
	// private String head;// 头像
	// private String nickName;// 昵称
	// private String uid;// 朋友id
	// private String is_login;// 是否上线
	//
	// // private String name; //显示的数据
	private String sortLetters; // 显示数据拼音的首字母
	//
	// private boolean juddd;// 手动添加参数，判断是否被选中
	// private String email;// 邮箱
	// private String phone;// 手机号

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getTopName() {
		return topName;
	}

	public void setTopName(String topName) {
		this.topName = topName;
	}

	public String getCmid() {
		return cmid;
	}

	public void setCmid(String cmid) {
		this.cmid = cmid;
	}

	public String getCm_name() {
		return cm_name;
	}

	public void setCm_name(String cm_name) {
		this.cm_name = cm_name;
	}




	@Override
	public String toString() {
		return "SortModel [id=" + id + ", cbid=" + cbid + ", name=" + name + ", fletter=" + fletter + ", cbimage=" + cbimage + ", type=" + type + ", csid=" + csid + ", cs_name=" + cs_name + ", cmid=" + cmid + ", cm_name=" + cm_name + ", topName=" + topName + ", SeriverTypeitemModel0=" + SeriverTypeitemModel0 + ", ucid=" + ucid + ", color=" + color + ", plateNumber=" + plateNumber + ", selectSeriverTypeitemList=" + selectSeriverTypeitemList + ", sortLetters=" + sortLetters + "]";
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

//	public String getSctop() {
//		return sctop;
//	}
//
//	public void setSctop(String sctop) {
//		this.sctop = sctop;
//	}

//	public String getScid() {
//		return scid;
//	}
//
//	public void setScid(String scid) {
//		this.scid = scid;
//	}

//	public String getScname() {
//		return scname;
//	}
//
//	public void setScname(String scname) {
//		this.scname = scname;
//	}
//
//	public String getScimage() {
//		return scimage;
//	}
//
//	public void setScimage(String scimage) {
//		this.scimage = scimage;
//	}
//
//	public String getScremark() {
//		return scremark;
//	}
//
//	public void setScremark(String scremark) {
//		this.scremark = scremark;
//	}




	

	//
	// private String url; //头像的url
	// private int MutualFriendNumber;//共同好友数量
	// private int PhoneNumber;//电话号码
	// private String email;//电子邮箱
	// public String getName() {
	// return name;
	// }
	// public void setName(String name) {
	// this.name = name;
	// }

}
