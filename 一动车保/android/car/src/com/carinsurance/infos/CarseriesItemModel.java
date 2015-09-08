package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class CarseriesItemModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	private String topname;
	private List<CarseriesItemitemModel> cslist;

	@Override
	public String toString() {
		return "CarseriesItemModel [id=" + id + ", topname=" + topname + ", cslist=" + cslist + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopname() {
		return topname;
	}

	public void setTopname(String topname) {
		this.topname = topname;
	}

	public List<CarseriesItemitemModel> getCslist() {
		return cslist;
	}

	public void setCslist(List<CarseriesItemitemModel> cslist) {
		this.cslist = cslist;
	}

}