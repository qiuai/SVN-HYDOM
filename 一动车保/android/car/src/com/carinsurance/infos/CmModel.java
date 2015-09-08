package com.carinsurance.infos;

import java.io.Serializable;

public class CmModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	private String cmname;//车辆完整名称

	public String getCmname() {
		return cmname;
	}

	public void setCmname(String cmname) {
		this.cmname = cmname;
	}

	

	
	
	
}