package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class ProductDefaultItemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	
	private String scid;//服务类型ID
	private String pid;//商品ID
	private String pname;//商品名称
	private String pimage;//商品展示图地址
	private String pbuynum;//商品购买数
	private String price;//商品价格
	private String pcomts;//商品评论数
	
	private String ptotalnum;//商品总的数量
	private String poriprice;//商品原价
	private String pdisprice;//商品折后价
	private String pdiscount ;//商品折扣值

	private String purl;//商品图文详情url地址
	
	private String cmsup;//对车型的支持情况：cmsup=0 表示支持所有车型 cmsup=1 表示支持车型参cmlist
    
	List<ImgModel> imglist;//图片地址
	
	
	List<SupModel> suplist;//支持的服务名称：如“全国联保”
	
	List<ParamModel> paramlist;//参数名称 和值
	List<CmModel> cmlist;//支持的车型列表
	
	
	private String pimg;//
	public String getPoriprice() {
		return poriprice;
	}

	public void setPoriprice(String poriprice) {
		this.poriprice = poriprice;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getCmsup() {
		return cmsup;
	}

	public void setCmsup(String cmsup) {
		this.cmsup = cmsup;
	}

	public String getPdisprice() {
		return pdisprice;
	}

	public void setPdisprice(String pdisprice) {
		this.pdisprice = pdisprice;
	}

	public String getPdiscount() {
		return pdiscount;
	}

	public void setPdiscount(String pdiscount) {
		this.pdiscount = pdiscount;
	}

	public String getPtotalnum() {
		return ptotalnum;
	}

	public void setPtotalnum(String ptotalnum) {
		this.ptotalnum = ptotalnum;
	}
	private String number;//自己加的，商品数量
	
	
	@Override
	public String toString() {
		return "ProductDefaultItemModel [id=" + id + ", scid=" + scid + ", pid=" + pid + ", pname=" + pname + ", pimage=" + pimage + ", pbuynum=" + pbuynum + ", price=" + price + ", pcomts=" + pcomts + ", number=" + number + "]";
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScid() {
		return scid;
	}
	public void setScid(String scid) {
		this.scid = scid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public String getPbuynum() {
		return pbuynum;
	}
	public void setPbuynum(String pbuynum) {
		this.pbuynum = pbuynum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPcomts() {
		return pcomts;
	}
	public void setPcomts(String pcomts) {
		this.pcomts = pcomts;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<ImgModel> getImglist() {
		return imglist;
	}

	public void setImglist(List<ImgModel> imglist) {
		this.imglist = imglist;
	}

	public List<SupModel> getSuplist() {
		return suplist;
	}

	public void setSuplist(List<SupModel> suplist) {
		this.suplist = suplist;
	}

	public List<ParamModel> getParamlist() {
		return paramlist;
	}

	public void setParamlist(List<ParamModel> paramlist) {
		this.paramlist = paramlist;
	}

	public List<CmModel> getCmlist() {
		return cmlist;
	}

	public void setCmlist(List<CmModel> cmlist) {
		this.cmlist = cmlist;
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}
	
	
	
	
}