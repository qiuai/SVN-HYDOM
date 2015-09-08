package com.carinsurance.infos;

import java.io.Serializable;

public class CarXitemModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cmid;
	private String cmname;
	public String getCmid() {
		return cmid;
	}
	public void setCmid(String cmid) {
		this.cmid = cmid;
	}
	public String getCmname() {
		return cmname;
	}
	public void setCmname(String cmname) {
		this.cmname = cmname;
	}


	
	
}
