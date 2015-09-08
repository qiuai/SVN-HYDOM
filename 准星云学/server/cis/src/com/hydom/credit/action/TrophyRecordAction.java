package com.hydom.credit.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.credit.ebean.Trophy;
import com.hydom.credit.ebean.TrophyRecord;
import com.hydom.credit.service.TrophyRecordService;
import com.hydom.dao.PageView;

@Controller
@Scope(value = "prototype")
public class TrophyRecordAction {
	@Resource
	private TrophyRecordService trophyRecordService;
	private HttpServletRequest request;

	private ScoreRecord scoreRecord;
	private int maxresult = 10;
	private int page = 1;
	private long trid;
	private int m = 2;// 识别选中导航菜单

	private InputStream inputStream;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<TrophyRecord> pageView = new PageView<TrophyRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("sign", "asc");
		orderby.put("id", "desc");
		pageView.setQueryResult(trophyRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String process() {
		try {
			TrophyRecord entity = trophyRecordService.find(trid);
			if (entity.getProcessTime() == null && !entity.getSign()) {
				entity.setSign(true);
				Date now = new Date();
				entity.setProcessTime(now);
				trophyRecordService.update(entity);
				Trophy trophy = entity.getTrophy();
				trophy.setExchangeNum(entity.getNumber());
				trophyRecordService.update(entity);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String result = sdf.format(now);
				inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
			} else {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public long getTrid() {
		return trid;
	}

	public void setTrid(long trid) {
		this.trid = trid;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
