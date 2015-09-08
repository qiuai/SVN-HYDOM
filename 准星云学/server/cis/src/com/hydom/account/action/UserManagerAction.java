package com.hydom.account.action;

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

import com.hydom.account.ebean.Account;
import com.hydom.account.service.AccountService;
import com.hydom.dao.PageView;
import com.hydom.task.service.TaskRecordService;
import com.hydom.util.HelperUtil;

@Controller
@Scope(value = "prototype")
public class UserManagerAction {
	@Resource
	private AccountService accountService;
	@Resource
	private TaskRecordService taskRecordService;
	private HttpServletRequest request;

	private int maxresult = 10;
	private int page = 1;
	private int m = 2;// 识别选中导航菜单
	private String query_phone; // 手机号
	private String query_createTime;// 创建时间
	private String query_lastTime;// 最近登录时间

	private long accid;

	public String userList() {
		request = ServletActionContext.getRequest();
		PageView<Account> pageView = new PageView<Account>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.type=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(1);
		if (query_phone != null && !"".equals(query_phone)) {
			jpql.append(" and o.phone like ?" + (params.size() + 1));
			params.add("%" + query_phone + "%");
		}
		if (query_createTime != null && !"".equals(query_createTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(query_createTime);
				endDate = HelperUtil.addDays(startDate, 1);
			} catch (ParseException e) {
				startDate = HelperUtil.firstDayThisMonth();
				endDate = HelperUtil.lastDayThisMonth();
			}
			jpql.append(" and o.createTime>=?" + (params.size() + 1));
			params.add(startDate);
			jpql.append(" and o.createTime<?" + (params.size() + 1));
			params.add(endDate);
		}
		if (query_lastTime != null && !"".equals(query_lastTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(query_lastTime);
				endDate = HelperUtil.addDays(startDate, 1);
			} catch (ParseException e) {
				startDate = HelperUtil.firstDayThisMonth();
				endDate = HelperUtil.lastDayThisMonth();
			}
			jpql.append(" and o.lastSigninTime>=?" + (params.size() + 1));
			params.add(startDate);
			jpql.append(" and o.lastSigninTime<?" + (params.size() + 1));
			params.add(endDate);
		}

		pageView.setQueryResult(accountService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		for (Account account : pageView.getRecords()) {
			account.setCount_all(taskRecordService.count(account.getId()));
			account.setCount_month(taskRecordService.countThisMonth(account.getId()));
			account.setCount_rightPercent(taskRecordService.countRightPercent(account
					.getId()));
			account.setCount_processTime(taskRecordService.countProcessTime(account
					.getId()));
		}
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

	public String getQuery_lastTime() {
		return query_lastTime;
	}

	public void setQuery_lastTime(String queryLastTime) {
		query_lastTime = queryLastTime;
	}

	public String getQuery_phone() {
		return query_phone;
	}

	public void setQuery_phone(String queryPhone) {
		query_phone = queryPhone;
	}

	public String getQuery_createTime() {
		return query_createTime;
	}

	public void setQuery_createTime(String queryCreateTime) {
		query_createTime = queryCreateTime;
	}

	public long getAccid() {
		return accid;
	}

	public void setAccid(long accid) {
		this.accid = accid;
	}

}
