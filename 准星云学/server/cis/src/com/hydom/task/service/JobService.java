package com.hydom.task.service;

import com.hydom.dao.DAO;
import com.hydom.task.ebean.Job;

public interface JobService extends DAO<Job> {
	/**
	 * 
	 * @param taskId
	 * @return
	 */
	public Job findByTaskId(String taskId);

	/**
	 * 提交工单给数据服务端
	 * 
	 * @param jobId
	 */
	public void postJob(long jobId);

	/**
	 * 定时提交失败的任务
	 */
	public void feedbackTimer();
}
