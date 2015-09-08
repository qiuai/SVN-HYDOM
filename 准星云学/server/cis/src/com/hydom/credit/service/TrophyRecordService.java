package com.hydom.credit.service;

import java.util.List;

import com.hydom.credit.ebean.TrophyRecord;
import com.hydom.dao.DAO;

public interface TrophyRecordService extends DAO<TrophyRecord> {

	/**
	 * 获取指帐户的所有兑换记录
	 * 
	 * @param uid
	 *            :用户ID【Account表中的ID】
	 * @return
	 */
	public List<TrophyRecord> list(long uid);

	/**
	 * 清空指定用户的兑换记录：进行逻辑删除
	 * 
	 * @param uid
	 * @return
	 */
	public int clear(long uid);

	/**
	 * 统计指定用户本月兑换次数
	 * 
	 * @param uid
	 * @return
	 */
	public long countMonth(long uid);

	/**
	 * 统计指定用户兑换总次数
	 * 
	 * @param uid
	 * @return
	 */
	public long countAll(long uid);

}
