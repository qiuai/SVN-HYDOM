package com.hydom.credit.service;

import java.util.Date;
import java.util.List;

import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.dao.DAO;

public interface ScoreRecordService extends DAO<ScoreRecord> {
	/**
	 * 计算指定日期内，积分最多的maxresult位用户
	 * 
	 * @param startDate
	 * @param endDate
	 * @param maxresult
	 * @return：List<数组｛Account,积分｝> list按积分降序
	 */
	public List<Object[]> top(Date startDate, Date endDate, int maxresult);
}
