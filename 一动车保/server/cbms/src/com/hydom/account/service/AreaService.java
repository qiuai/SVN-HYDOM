package com.hydom.account.service;

import java.util.List;

import com.hydom.account.ebean.Area;
import com.hydom.util.dao.DAO;

public interface AreaService extends DAO<Area> {

	List<Area> getRootArea();

	/**
	 * 根据上级ID 名称
	 * 
	 * @param prantId
	 * @param content
	 * @return
	 */
	Area getEntitybyNameAndPrantId(String parentId, String content);

	/**
	 * 根据父类地区获取全部子类区域
	 * 
	 * @param find
	 * @return
	 */
	List<Area> getAreaByParentId(Area find);

	/***
	 * 根据子地区获取完整的地区名称
	 * 
	 * @param aid
	 * @return
	 */
	public String fullAreaName(String aid);

}
