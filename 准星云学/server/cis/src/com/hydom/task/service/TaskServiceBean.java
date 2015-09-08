package com.hydom.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.task.ebean.Task;
import com.hydom.task.ebean.TaskRecord;

@Service
public class TaskServiceBean extends DAOSupport<Task> implements TaskService {
	@Resource
	private TaskRecordService taskRecordService;
	Log log = LogFactory.getLog("quartzLog");

	@Override
	public void resetMatchTask() {
		log.info("开始重置分配数：");
		List<TaskRecord> taskRecords = taskRecordService.listOverTimeRecord();
		for (TaskRecord record : taskRecords) {
			record.setIdentState(0);
			Task task = record.getTask();
			task.setMatchedNum(task.getMatchedNum() - 1);
			task.setCanNum(task.getCanNum() + 1);
			taskRecordService.update(record);
			this.update(task);
			log.info("超时重置：TaskRecord Id=" + record.getId());
		}
		log.info("本次重置分配数完成，共计：" + taskRecords.size());
	}

	@Override
	public Task findByTaskId(String taskId) {
		try {
			return (Task) em.createQuery(
					"select o from Task o where o.visible=?1 and o.taskId=?2")
					.setParameter(1, true).setParameter(2, taskId).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> listByTaskId(String taskId) {
		return em.createQuery("select o from Task o where o.visible=?1 and o.taskId=?2")
				.setParameter(1, true).setParameter(2, taskId).getResultList();
	}
}
