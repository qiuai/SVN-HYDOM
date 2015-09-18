package com.hydom.core.service;

import com.hydom.core.ebean.Room;

public interface RoomService {

	/**
	 * 通过课程(即房间)ID和用户ID查找唯一的课程(房间)记录
	 * 
	 * @param cid
	 *            课程ID
	 * @param uid
	 *            用户ID
	 * @return
	 */
	public Room findByUC(String cid, String uid);

	public Room findOne(String id);

	/**
	 * 设置指定房间的当前直播流名
	 * 
	 * @param roomId
	 *            房间ID
	 * @param streamName
	 *            当前直播流名
	 */
	public int setCurrentLiveStream(String roomId, String streamName);

	/**
	 * 设置指定房间的状态：
	 * 
	 * @param roomId
	 * @param status
	 *            1正在上课 ；0未上课
	 * @return
	 */
	public int setRoomStatus(String roomId, int status);

	/***
	 * 根据房间ID得到房间评分
	 * 
	 * @param roomId
	 * @return
	 */
	public double avgScore(String roomId);

}
