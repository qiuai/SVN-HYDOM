package com.carinsurance.infos;

import java.io.Serializable;

public class PcModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pcid;
	private String pcname;
	private String pcimage;
	public String getPcid() {
		return pcid;
	}
	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	public String getPcname() {
		return pcname;
	}
	public void setPcname(String pcname) {
		this.pcname = pcname;
	}
	public String getPcimage() {
		return pcimage;
	}
	public void setPcimage(String pcimage) {
		this.pcimage = pcimage;
	}
	
}