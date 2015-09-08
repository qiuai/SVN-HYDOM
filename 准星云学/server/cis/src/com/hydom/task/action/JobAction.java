package com.hydom.task.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.dao.PageView;
import com.hydom.task.ebean.Job;
import com.hydom.task.service.JobService;
import com.hydom.util.HelperUtil;

@Controller
@Scope(value = "prototype")
public class JobAction {
	@Resource
	private JobService jobService;

	private HttpServletRequest request;

	private int maxresult = 10;
	private int page = 1;
	private int m = 1;// 识别选中导航菜单
	private String query_createTime;
	private String query_finishTime;
	private String query_taskId;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Job> pageView = new PageView<Job>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("feedback", "asc");
		orderby.put("id", "asc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if (query_taskId != null && !"".equals(query_taskId)) {
			jpql.append(" and o.taskId like ?" + (params.size() + 1));
			params.add("%" + query_taskId + "%");
		}
		if (query_createTime != null && !"".equals(query_createTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(query_createTime);
				endDate = HelperUtil.addDays(startDate, 1);
			} catch (ParseException e) {// 如果出现异常查询当月
				startDate = HelperUtil.firstDayThisMonth();
				endDate = HelperUtil.lastDayThisMonth();
			}
			jpql.append(" and o.createTime>=?" + (params.size() + 1));
			params.add(startDate);
			jpql.append(" and o.createTime<?" + (params.size() + 1));
			params.add(endDate);
		}
		if (query_finishTime != null && !"".equals(query_finishTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(query_finishTime);
				endDate = HelperUtil.addDays(startDate, 1);
			} catch (ParseException e) {// 如果出现异常查询当月
				startDate = HelperUtil.firstDayThisMonth();
				endDate = HelperUtil.lastDayThisMonth();
			}
			jpql.append(" and o.finishTime>=?" + (params.size() + 1));
			params.add(startDate);
			jpql.append(" and o.finishTime<?" + (params.size() + 1));
			params.add(endDate);
		}

		pageView.setQueryResult(jobService.getScrollData(pageView.getFirstResult(), maxresult, jpql
				.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);

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

	public String getQuery_createTime() {
		return query_createTime;
	}

	public void setQuery_createTime(String queryCreateTime) {
		query_createTime = queryCreateTime;
	}

	public String getQuery_finishTime() {
		return query_finishTime;
	}

	public void setQuery_finishTime(String queryFinishTime) {
		query_finishTime = queryFinishTime;
	}

	public String getQuery_taskId() {
		return query_taskId;
	}

	public void setQuery_taskId(String queryTaskId) {
		query_taskId = queryTaskId;
	}

}
