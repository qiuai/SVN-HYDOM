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
import com.hydom.task.ebean.Task;
import com.hydom.task.service.TaskService;

@Controller
@Scope(value = "prototype")
public class TaskAction {
	@Resource
	private TaskService taskService;
	private HttpServletRequest request;

	private int maxresult = 10;
	private int page = 1;
	private int m = 1;// 识别选中导航菜单
	private long jobid; //
	private long taskId;
	private Task task;
	private SvgImage si;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Task> pageView = new PageView<Task>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("finishTime", "asc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.job.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(jobid);
		pageView.setQueryResult(taskService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String show() {
		task = taskService.find(taskId);
		String metricPoint = task.getMetricPoint();
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

	public long getJobid() {
		return jobid;
	}

	public void setJobid(long jobid) {
		this.jobid = jobid;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public SvgImage getSi() {
		return si;
	}

	public void setSi(SvgImage si) {
		this.si = si;
	}

}
