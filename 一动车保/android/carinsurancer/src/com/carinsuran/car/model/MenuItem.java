package com.carinsuran.car.model;

import java.util.ArrayList;

/**
 * 菜单实体
 * @author LILIN
 * 下午5:39:14
 */
public class MenuItem {
    //是否有子菜单
	private boolean hasChild;
	//菜单名字
	private String name;
    //子菜单集合
	private ArrayList<MenuItem> childMenuItems;
	
	//提供两种构造函数
	public MenuItem() {
	}

	public MenuItem(boolean hasChild, String name, ArrayList<MenuItem> childMenuItems) {
		this.hasChild = hasChild;
		this.name = name;
		this.childMenuItems = childMenuItems;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public ArrayList<MenuItem> getChildMenuItems() {
		return childMenuItems;
	}

	public void setChildMenuItems(ArrayList<MenuItem> childMenuItems) {
		this.childMenuItems = childMenuItems;
	}

	@Override
	public String toString() {
		
		return name;
	}
	
	
}
