package com.hydom.server;

import java.util.List;
import java.util.Map;

/**
 * 用于json对象转换
 * 
 * @author www.hydom.cn [heou]
 * 
 */
public class TaskData {
	private String taskId;// 工单ID，对应于文档中的taskID
	private String recycleTime;// 超时时间:以ms为单位
	private String matchNum;// 分配上限，默认来源系统配置
	private String accuracy;// 正确率

	private List<Map<String, Object>> message;

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getRecycleTime() {
		return recycleTime;
	}

	public void setRecycleTime(String recycleTime) {
		this.recycleTime = recycleTime;
	}

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}

	public List<Map<String, Object>> getMessage() {
		return message;
	}

	public void setMessage(List<Map<String, Object>> message) {
		this.message = message;
	}

}
