package com.hydom.account.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hydom.account.ebean.Account;
import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.service.AccountService;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.util.BaseAction;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.IDGenerator;
import com.hydom.util.bean.AdminBean;
import com.hydom.util.dao.PageView;

@RequestMapping("/manage/account")
@Controller
public class AccountAction extends BaseAction {
	@Resource
	private AccountService accountService;
	@Resource
	private PrivilegeGroupService groupService;
	@Autowired
	private HttpServletRequest request;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		PageView<Account> pageView = new PageView<Account>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if (queryContent != null && !"".equals(queryContent)) {
			jpql.append(" and (o.username like?" + (params.size() + 1)
					+ " or o.nickname=?" + (params.size() + 2) + ")");
			params.add("%" + queryContent + "%");
			params.add("%" + queryContent + "%");
		}
		pageView.setQueryResult(accountService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("/account/privilege/account_list");
		mav.addObject("pageView", pageView);
		mav.addObject("m", 3);
		return mav;
	}

	@RequestMapping("/add")
	public ModelAndView addUI() {
		List<PrivilegeGroup> groups = groupService.getScrollData()
				.getResultList();
		ModelAndView mav = new ModelAndView("/account/privilege/account_add");
		mav.addObject("groups", groups);
		mav.addObject("m", 3);
		return mav;
	}

	@RequestMapping("/save")
	public ModelAndView add(@ModelAttribute Account account,
			@RequestParam(required = false) String[] gids) {
		if (gids != null && gids.length > 0) {
			for (String gid : gids) {
				PrivilegeGroup group = groupService.find(gid);
				account.getGroups().add(group);
			}
		}
		accountService.save(account);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	@RequestMapping("/edit")
	public ModelAndView editUI(@RequestParam String acid) {
		Account account = accountService.find(acid);
		List<PrivilegeGroup> groups = groupService.getScrollData()
				.getResultList();
		request.setAttribute("groups", groups);
		StringBuffer ugs = new StringBuffer();
		for (PrivilegeGroup group : account.getGroups()) {
			ugs.append("#" + group.getId());
		}
		ModelAndView mav = new ModelAndView("/account/privilege/account_edit");
		mav.addObject("account", account);
		mav.addObject("ugs", ugs.toString());
		mav.addObject("m", 3);
		return mav;
	}

	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute Account account,
			@RequestParam(required = false) String[] gids) {
		Account entity = accountService.find(account.getId());
		entity.setPassword(account.getPassword());
		entity.setNickname(account.getNickname());
		entity.getGroups().clear();
		if (gids != null && gids.length > 0) {
			for (String gid : gids) {
				PrivilegeGroup group = groupService.find(gid);
				entity.getGroups().add(group);
			}
		}
		accountService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	@RequestMapping("/delete")
	public @ResponseBody
	String delete(@RequestParam String[] ids) {
		for (String id : ids) {
			Account entity = accountService.find(id);
			String deleteExtraName = DateTimeHelper.formatDateTimetoString(
					new Date(), "yyyyMMddHHmmss-");
			deleteExtraName += IDGenerator.getRandomString(6, 1);
			entity.setUsername(entity.getUsername() + "-DEL-" + deleteExtraName);
			entity.setVisible(false);
			accountService.update(entity);
		}
		return "{\"message\":\"删除成功\",\"status\":\"success\"}";
	}

	@RequestMapping("/checkUsername")
	public @ResponseBody
	String checkUsername(@RequestParam String username) {
		Account entity = accountService.findByUsername(username);
		if (entity != null) {
			return "0";
		} else {
			return "1";
		}
	}

	@RequestMapping("/resetPwd")
	public @ResponseBody
	String resetPwd(@RequestParam String accid) {
		try {
			Account entity = accountService.find(accid);
			entity.setPassword("123456");
			accountService.update(entity);
			return "1";
		} catch (Exception e) {
			return "0";
		}

	}

	@RequestMapping("/changePasswordView")
	public String changePasswordView(HttpServletResponse response,
			ModelMap model) {
		model.addAttribute("memberBean", getAdminBean(request));
		return "/common/changePsd";
	}

	@RequestMapping("/changePassword")
	public String changePassword(String newPass, String oldPass,
			HttpServletResponse response) {
		AdminBean bean = getAdminBean(request);
		if (bean == null) {
			return ajaxError("登录已失效,请重新登录", response);
		}
		Account account = accountService.find(bean.getMember().getId());
		if (!account.getPassword().equals(oldPass)) {
			return ajaxError("旧密码输入错误", response);
		}
		if (StringUtils.isEmpty(newPass)) {
			return ajaxError("请输入新密码", response);
		}
		account.setPassword(newPass);
		accountService.update(account);
		return ajaxSuccess("修改成功", response);
	}
}
