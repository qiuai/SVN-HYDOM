package com.carinsurance.infos;

import java.io.Serializable;

public class CarTypeitemModel implements Serializable {

	/**
	 * "cbid": "973ce43c-dcc7-4a25-bc59-9fc486991506", "name": "大众", "fletter":
	 * "D", "cbimage": null },
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String cbid;
	private String cbname;
	private String fletter;
	private String cbimage;

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

	
	public String getCbname() {
		return cbname;
	}

	public void setCbname(String cbname) {
		this.cbname = cbname;
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

}
