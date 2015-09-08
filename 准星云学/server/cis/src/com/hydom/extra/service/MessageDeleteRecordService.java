package com.hydom.extra.service;

import java.util.List;

import com.hydom.dao.DAO;
import com.hydom.extra.ebean.MessageDeleteRecord;

public interface MessageDeleteRecordService extends DAO<MessageDeleteRecord> {

	/**
	 * 列出指定帐户的删除的所有消息ID集合
	 * 
	 * @param accid
	 *            ：帐户ID
	 * @return
	 */
	public List<Long> listMidsByAccid(long accid);

	public MessageDeleteRecord find(long accid, long msgid);
}
