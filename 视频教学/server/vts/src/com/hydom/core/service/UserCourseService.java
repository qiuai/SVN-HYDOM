package com.hydom.core.service;

public interface UserCourseService {
	/**
	 * 更新用户观看历史记录：并更新用户对应的当前课程状态
	 * 
	 */
	void saveOrUpdate(String roomId, String userId);

	/**
	 * 删除用户当前课程状态：通常发生在用户离开房间后
	 * 
	 * @param userId
	 * @return
	 */
	int deleteCurrentRoomState(String userId);
}
