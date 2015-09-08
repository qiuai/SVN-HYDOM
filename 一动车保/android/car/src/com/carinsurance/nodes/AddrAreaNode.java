package com.carinsurance.nodes;

import java.util.ArrayList;
import java.util.List;

public class AddrAreaNode{
	private String parentId = null;
	private String aid;
	private String name;
	private List<AddrAreaNode> childrens = null; // 缓存数据

	public AddrAreaNode() {
	}
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<AddrAreaNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AddrAreaNode> childrens) {
		this.childrens = childrens;
	}

	public void noneChildren() {
		this.childrens = new ArrayList<>();
	}

	public boolean hasChildren() {
		if (this.childrens == null) {
			return false;
		} else if (this.childrens.size() == 0) {
			return false;
		}

		return true;
	}

	public boolean isIinited() {
		if (this.childrens == null) {
			return false;
		}
		return true;
	}
	
	public boolean isFirstLevel(){
		if (this.parentId == null) {
			return true;
		}
		return false;
	}
}
