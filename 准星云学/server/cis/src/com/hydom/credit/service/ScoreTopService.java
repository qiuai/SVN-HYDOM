package com.hydom.credit.service;

import java.util.Date;
import java.util.List;

import com.hydom.credit.ebean.ScoreTop;
import com.hydom.dao.DAO;

public interface ScoreTopService extends DAO<ScoreTop> {

	/**
	 * 获取指定日期榜单
	 * 
	 * @param date
	 * @return
	 */
	public List<ScoreTop> listTheDay(Date date);

	/**
	 * 系统每隔2小时定时刷新当天积分榜单
	 */
	public void generate();

	/**
	 * 每天晚上12点过后，更新昨日榜单
	 */
	public void updateYestoady();

}
