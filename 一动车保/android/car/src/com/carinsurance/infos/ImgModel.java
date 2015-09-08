package com.carinsurance.infos;

import java.io.Serializable;

public class ImgModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
   
	private String pimg;//图片地址()
    
	
	//注意，下面的是首页的
	private String adid;
	private String adimg;
	private String adurl;
	
	
	@Override
	public String toString() {
		return "ImgModel [id=" + id + ", pimg=" + pimg + ", adid=" + adid + ", adimg=" + adimg + ", adurl=" + adurl + "]";
	}

	public String getPimg() {
		return pimg;
	}

	public void setPimg(String pimg) {
		this.pimg = pimg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}

	public String getAdimg() {
		return adimg;
	}

	public void setAdimg(String adimg) {
		this.adimg = adimg;
	}

	public String getAdurl() {
		return adurl;
	}

	public void setAdurl(String adurl) {
		this.adurl = adurl;
	}

	
	
	
}