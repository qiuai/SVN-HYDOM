package com.hydom.task.service;

import java.util.List;

import com.hydom.dao.DAO;
import com.hydom.task.ebean.Task;

public interface TaskService extends DAO<Task> {
	/**
	 * 查找工单
	 * 
	 * @param taskId
	 *            ：工单ID
	 * @return
	 */
	public Task findByTaskId(String taskId);

	/**
	 * 通过taskId找出所有区块任务
	 * 
	 * @param taskId
	 * @return
	 */
	public List<Task> listByTaskId(String taskId);

	/**
	 * 定时检查超时工单 <br>
	 * 1.对超时任务(TaskRecord)作如下设置： identState=0表示识别超时 <br>
	 * 2.重置Task分配信息：matchedNum-1，canNum+1
	 */
	public void resetMatchTask();
}
