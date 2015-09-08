package com.carinsurance.xmlparse;

import java.io.Serializable;

public class Weather implements Serializable{

	private int id;
	private  String name;
	
//	public List<String> list;
//
//	public List<String> getList() {
//		return list;
//	}
//
//	public void setList(List<String> list) {
//		this.list = list;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Weather [id=" + id + ", name=" + name + "]";
	}
	
	
}
