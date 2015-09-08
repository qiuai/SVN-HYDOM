package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;
/**
 * 门店列表model
 * @author Administrator
 *
 */
public class MendianListModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String pageNumber;
	private List<MendianModel> data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<MendianModel> getData() {
		return data;
	}
	public void setData(List<MendianModel> data) {
		this.data = data;
	}
	

	
	
}
