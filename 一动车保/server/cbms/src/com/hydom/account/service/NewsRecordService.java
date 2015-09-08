package com.hydom.account.service;

import com.hydom.account.ebean.NewsRecord;
import com.hydom.util.dao.DAO;

public interface NewsRecordService extends DAO<NewsRecord> {

	/***
	 * 通过用户ID和新闻ID得到具体的记录
	 * 
	 * @param uid
	 * @param nwid
	 * @return
	 */
	public NewsRecord starRecord(String uid, String nwid);
}
