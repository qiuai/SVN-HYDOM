package com.hydom.task.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.dao.PageView;
import com.hydom.server.SvgImage;
import com.hydom.task.ebean.TaskRecord;
import com.hydom.task.service.TaskRecordService;

@Controller
@Scope(value = "prototype")
public class TaskRecordAction {
	@Resource
	private TaskRecordService taskRecordService;
	private HttpServletRequest request;

	private int maxresult = 10;
	private int page = 1;
	private int m = 1;// 识别选中导航菜单
	private long taskId;
	private long jobid;
	private long taskRecordId;
	private TaskRecord taskRecord;
	private SvgImage si;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<TaskRecord> pageView = new PageView<TaskRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("sign", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.task.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(taskId);
		pageView.setQueryResult(taskRecordService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(), params.toArray(),
				orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String show() {
		taskRecord = taskRecordService.find(taskRecordId);
		String metricPoint = taskRecord.getTask().getMetricPoint();
		si = new SvgImage(metricPoint);
		return "success";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getTaskRecordId() {
		return taskRecordId;
	}

	public void setTaskRecordId(long taskRecordId) {
		this.taskRecordId = taskRecordId;
	}

	public TaskRecord getTaskRecord() {
		return taskRecord;
	}

	public void setTaskRecord(TaskRecord taskRecord) {
		this.taskRecord = taskRecord;
	}

	public SvgImage getSi() {
		return si;
	}

	public void setSi(SvgImage si) {
		this.si = si;
	}

	public long getJobid() {
		return jobid;
	}

	public void setJobid(long jobid) {
		this.jobid = jobid;
	}

}
