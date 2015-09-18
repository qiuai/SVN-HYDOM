package com.hydom.core.service;

import java.util.List;

import com.hydom.core.ebean.Room;
import com.hydom.core.ebean.Video;

public interface VideoService {

	/**
	 * 列出指定房间内的所有视频列表
	 * 
	 * @param roomId
	 * @return
	 */
	public List<Video> listByRoomId(String roomId);

	
	public Video findOne(String id);
}
