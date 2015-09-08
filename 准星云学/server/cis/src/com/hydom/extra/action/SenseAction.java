package com.hydom.extra.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.dao.PageView;
import com.hydom.extra.ebean.Sense;
import com.hydom.extra.service.SenseService;

@Controller
@Scope(value = "prototype")
public class SenseAction {
	@Resource
	private SenseService senseService;
	private Sense sense;
	private int page = 1;
	private int maxresult = 10;
	private int m = 4;// 识别选中导航菜单
	private long sid;
	private InputStream inputStream;

	private HttpServletRequest request;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Sense> pageView = new PageView<Sense>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setQueryResult(senseService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String delete() {
		try {
			Sense entitiy = senseService.find(sid);
			entitiy.setVisible(false);
			senseService.update(entitiy);
			inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
		return "success";
	}

	public Sense getSense() {
		return sense;
	}

	public void setSense(Sense sense) {
		this.sense = sense;
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

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
