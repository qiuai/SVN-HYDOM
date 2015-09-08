package com.hydom.account.action;

import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.ebean.Privilege;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.account.service.PrivilegeService;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/account/group")
@Controller
public class PrivilegeGroupAction {
	@Resource
	private PrivilegeGroupService groupService;
	@Resource
	private PrivilegeService privilegeService;

	private int maxresult = 10;
	private int m = 3;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page) {
		privilegeService.listCategory();
		PageView<PrivilegeGroup> pageView = new PageView<PrivilegeGroup>(
				maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("createTime", "desc");
		orderby.put("id", "desc");
		pageView.setQueryResult(groupService.getScrollData(
				pageView.getFirstResult(), maxresult, orderby));
		ModelAndView mav = new ModelAndView(
				"/account/privilege/privilegeGroup_list");
		mav.addObject("pageView", pageView);
		mav.addObject("m", m);
		mav.addObject("pmap", privilegeService.listCategory());
		return mav;
	}

	@RequestMapping("/save")
	public ModelAndView add(String name, String[] privilegeIds) {
		PrivilegeGroup group = new PrivilegeGroup();
		group.setName(name);
		if (privilegeIds != null && privilegeIds.length > 0) {
			for (String id : privilegeIds) {
				group.getPrivileges().add(privilegeService.find(id));
			}
		}
		groupService.save(group);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	@RequestMapping("/edit")
	public ModelAndView editUI(String gid) {
		PrivilegeGroup group = groupService.find(gid);
		StringBuffer gps = new StringBuffer();
		if (group.getPrivileges() != null) {
			for (Privilege p : group.getPrivileges()) {
				gps.append("#" + p.getId());
			}
		}
		ModelAndView mav = new ModelAndView(
				"/account/privilege/privilegeGroup_edit");
		mav.addObject("m", m);
		mav.addObject("gps", gps);
		mav.addObject("group", group);
		mav.addObject("pmap", privilegeService.listCategory());
		return mav;
	}

	@RequestMapping("/update")
	public ModelAndView edit(String gid, String[] privilegeIds, String name) {
		PrivilegeGroup entity = groupService.find(gid);
		entity.setName(name);
		entity.getPrivileges().clear();
		if (privilegeIds != null && privilegeIds.length > 0) {
			for (String pid : privilegeIds) {
				entity.getPrivileges().add(privilegeService.find(pid));
			}
		}
		groupService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	@RequestMapping(value = "/delete", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String delete(String gid) {
		PrivilegeGroup entity = groupService.find(gid);
		if (entity != null) {
			if (entity.getInitSign()) { // 系统初始权限不能删除
				return "0";
			} else {
				groupService.delete(gid);
				return "1";
			}
		} else {
			return "-1";// GID错误
		}
	}

}
