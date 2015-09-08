package com.hydom.account.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.ebean.SystemPrivilege;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.account.service.SystemPrivilegeService;
import com.hydom.dao.PageView;
import com.hydom.util.StringGenerator;

@Controller
@Scope(value = "prototype")
public class PrivilegeGroupAction {
	@Resource
	private PrivilegeGroupService groupService;
	@Resource
	private SystemPrivilegeService systemPrivilegeService;

	private PrivilegeGroup group;
	private String[] privilegeIds;
	private String gid;

	private HttpServletRequest request;
	private InputStream inputStream;
	private int m = 4;// 识别选中导航菜单
	private int page = 1;
	private int maxresult = 10;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<PrivilegeGroup> pageView = new PageView<PrivilegeGroup>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createTime", "desc");
		orderby.put("id", "desc");
		pageView.setQueryResult(groupService.getScrollData(pageView.getFirstResult(),
				maxresult, orderby));
		request.setAttribute("pageView", pageView);
		request.setAttribute("sps1", systemPrivilegeService.listBylevel(1));
		request.setAttribute("sps2", systemPrivilegeService.listBylevel(2));
		request.setAttribute("sps3", systemPrivilegeService.listBylevel(3));
		request.setAttribute("sps4", systemPrivilegeService.listBylevel(4));
		request.setAttribute("sps5", systemPrivilegeService.listBylevel(5));
		return "success";
	}

	public String add() {
		if (privilegeIds != null && privilegeIds.length > 0) {
			for (String id : privilegeIds) {
				group.getPrivileges().add(systemPrivilegeService.find(id));
			}
		}
		group.setId(StringGenerator.generatorID());
		groupService.save(group);
		return "success";
	}

	public String editUI() {
		group = groupService.find(gid);
		request = ServletActionContext.getRequest();
		StringBuffer gps = new StringBuffer();
		if (group.getPrivileges() != null) {
			for (SystemPrivilege p : group.getPrivileges()) {
				gps.append("#" + p.getId());
			}
		}
		request.setAttribute("gps", gps.toString());
		request.setAttribute("sps1", systemPrivilegeService.listBylevel(1));
		request.setAttribute("sps2", systemPrivilegeService.listBylevel(2));
		request.setAttribute("sps3", systemPrivilegeService.listBylevel(3));
		request.setAttribute("sps4", systemPrivilegeService.listBylevel(4));
		request.setAttribute("sps5", systemPrivilegeService.listBylevel(5));
		return "success";
	}

	public String edit() {
		PrivilegeGroup entity = groupService.find(gid);
		entity.setName(group.getName());
		entity.getPrivileges().clear();
		if (privilegeIds != null && privilegeIds.length > 0) {
			for (String id : privilegeIds) {
				entity.getPrivileges().add(systemPrivilegeService.find(id));
			}
		}
		try {
			groupService.update(entity);
		} catch (Exception e) {
			return "input";
		}
		return "success";
	}

	public String del() {
		try {
			PrivilegeGroup entity = groupService.find(gid);
			if (entity != null) {
				if (entity.getInitSign()) { // 表示为系统初始定义不能删除
					inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
				} else {
					groupService.delete(gid);
					inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public PrivilegeGroup getGroup() {
		return group;
	}

	public void setGroup(PrivilegeGroup group) {
		this.group = group;
	}

	public String[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
