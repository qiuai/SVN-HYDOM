package com.hydom.credit.action;

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

import com.hydom.credit.ebean.TrophyType;
import com.hydom.credit.service.TrophyTypeService;
import com.hydom.dao.PageView;

@Controller
@Scope(value = "prototype")
public class TrophyTypeAction {
	@Resource
	private TrophyTypeService trophyTypeService;

	private HttpServletRequest request;

	private TrophyType type;
	private int maxresult = 8;
	private int page = 1;
	private long id;
	private int m = 3;// 识别选中导航菜单
	private InputStream inputStream;
	private String name;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<TrophyType> pageView = new PageView<TrophyType>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("od", "desc");
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);

		pageView.setQueryResult(trophyTypeService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(), params.toArray(),
				orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String addUI() {
		return "success";
	}

	public String add() {
		trophyTypeService.save(type);
		return "success";
	}

	public String editUI() {
		type = trophyTypeService.find(id);
		return "success";
	}

	public String edit() {
		TrophyType entity = trophyTypeService.find(id);
		entity.setName(name);
		trophyTypeService.update(entity);
		try {
			inputStream = new ByteArrayInputStream("1".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String delete() {
		type = trophyTypeService.find(id);
		type.setVisible(false);
		trophyTypeService.update(type);
		try {
			inputStream = new ByteArrayInputStream("1".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public TrophyType getType() {
		return type;
	}

	public void setType(TrophyType type) {
		this.type = type;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
