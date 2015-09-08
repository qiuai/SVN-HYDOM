package com.carinsurance.infos;

import java.io.Serializable;

public class CarseriesItemitemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String csid;
	private String csname;

	

	
	@Override
	public String toString() {
		return "CarseriesItemitemModel [csid=" + csid + ", csname=" + csname + "]";
	}

	// "csid": "30",
	// "name": "科迈罗Camaro"
	public String getCsid() {
		return csid;
	}

	public void setCsid(String csid) {
		this.csid = csid;
	}

	public String getCsname() {
		return csname;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}


	
}
