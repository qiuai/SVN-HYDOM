package com.carinsurance.nodes;

import java.util.List;

public class HotCategoryNode {
	private String id;
	private String name;
	private List<HotCategoryNode> childrens;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HotCategoryNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<HotCategoryNode> childrens) {
		this.childrens = childrens;
	}

}
