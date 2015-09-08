package com.hydom.credit.action;

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

import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.credit.ebean.TrophyRecord;
import com.hydom.credit.service.ScoreRecordService;
import com.hydom.dao.PageView;
import com.hydom.server.SvgImage;
import com.hydom.task.ebean.TaskRecord;
import com.hydom.util.HelperUtil;

@Controller
@Scope(value = "prototype")
public class ScoreRecordAction {
	@Resource
	private ScoreRecordService scoreRecordService;
	private HttpServletRequest request;

	private ScoreRecord scoreRecord;
	private int maxresult = 10;
	private int page = 1;
	private long srid;
	private long accid;
	private int m = 2;// 识别选中导航菜单
	private String query_uid;
	private String query_phone;
	private String query_createTime;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<ScoreRecord> pageView = new PageView<ScoreRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);

		if (query_uid != null && !"".equals(query_uid)) {
			try {
				long uid = Long.parseLong(query_uid);
				jpql.append(" and o.account.id =?" + (params.size() + 1));
				params.add(uid);
			} catch (Exception e) {
				jpql.append(" and o.account.id =?" + (params.size() + 1));
				params.add(null);
			}
		}
		if (query_phone != null && !"".equals(query_phone)) {
			jpql.append(" and o.account.phone like ?" + (params.size() + 1));
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
		pageView.setQueryResult(scoreRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	/**
	 * 积分中心：查看完成任务的详细
	 * 
	 * @return
	 */
	public String showTaskRecord() {
		ScoreRecord record = scoreRecordService.find(srid);
		if (record != null) {
			request = ServletActionContext.getRequest();
			TaskRecord taskRecord = record.getTaskRecord();
			request.setAttribute("taskRecord", taskRecord);
			SvgImage si = new SvgImage(taskRecord.getTask().getMetricPoint());
			request.setAttribute("si", si);
		}
		return "success";
	}

	/**
	 * 积分中心：查看兑换奖品的详细
	 * 
	 * @return
	 */
	public String showTrophyRecord() {
		ScoreRecord record = scoreRecordService.find(srid);
		if (record != null) {
			request = ServletActionContext.getRequest();
			TrophyRecord trophyRecord = record.getTrophyRecord();
			request.setAttribute("trophyRecord", trophyRecord);
		}
		return "success";
	}

	/**
	 * 个人积分 消费列表
	 * 
	 * @return
	 */
	public String listUse() {
		request = ServletActionContext.getRequest();
		PageView<ScoreRecord> pageView = new PageView<ScoreRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.account.id=?2 and o.sign=?3");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(accid);
		params.add(false);
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
		pageView.setQueryResult(scoreRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	/**
	 * 个人积分 积分列表
	 * 
	 * @return
	 */
	public String listHeap() {
		request = ServletActionContext.getRequest();
		PageView<ScoreRecord> pageView = new PageView<ScoreRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.account.id=?2 and o.sign=?3");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(accid);
		params.add(true);
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
		pageView.setQueryResult(scoreRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public ScoreRecord getScoreRecord() {
		return scoreRecord;
	}

	public void setScoreRecord(ScoreRecord scoreRecord) {
		this.scoreRecord = scoreRecord;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getSrid() {
		return srid;
	}

	public void setSrid(long srid) {
		this.srid = srid;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public long getAccid() {
		return accid;
	}

	public void setAccid(long accid) {
		this.accid = accid;
	}

	public String getQuery_uid() {
		return query_uid;
	}

	public void setQuery_uid(String queryUid) {
		query_uid = queryUid;
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

}
