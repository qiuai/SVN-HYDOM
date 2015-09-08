package com.hydom.account.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.account.ebean.Account;
import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.service.AccountService;
import com.hydom.account.service.PrivilegeGroupService;
import com.hydom.dao.PageView;
import com.hydom.util.HelperUtil;
import com.hydom.util.WebUtil;

@Controller
@Scope(value = "prototype")
public class AccountAction {
	@Resource
	private AccountService accountService;
	@Resource
	private PrivilegeGroupService groupService;
	private InputStream inputStream;

	private HttpServletRequest request;

	private int maxresult = 10;
	private int page = 1;
	private int m = 4;// 识别选中导航菜单
	private Account account;
	private String query_username;
	private String query_phone;
	private String query_createTime;

	private long accid;
	private String[] gids;

	private String username;
	private String oripwd;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Account> pageView = new PageView<Account>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.type=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(2);
		if (query_username != null && !"".equals(query_username)) {
			jpql.append(" and o.username like ?" + (params.size() + 1));
			params.add("%" + query_username + "%");
		}
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
		pageView.setQueryResult(accountService.getScrollData(pageView.getFirstResult(), maxresult,
				jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String checkUsername() {
		Account account = accountService.findByUsername(username);
		try {
			if (account != null) {
				inputStream = new ByteArrayInputStream("0".getBytes("UTF-8"));
			} else {
				inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return "success";
	}

	public String addUI() {
		request = ServletActionContext.getRequest();
		List<PrivilegeGroup> groups = groupService.getScrollData().getResultList();
		request.setAttribute("groups", groups);
		return "success";
	}

	public String add() {
		account.setCreateTime(new Date());
		account.setType(2);
		/** 权限组操作 **/
		if (gids != null && gids.length > 0) {
			for (String gid : gids) {
				account.getGroups().add(groupService.find(gid));
			}
		}
		accountService.save(account);
		return "success";
	}

	public String editUI() {
		account = accountService.find(accid);
		request = ServletActionContext.getRequest();
		List<PrivilegeGroup> groups = groupService.getScrollData().getResultList();
		request.setAttribute("groups", groups);
		StringBuffer ugs = new StringBuffer();
		for (PrivilegeGroup group : account.getGroups()) {
			ugs.append("#" + group.getId());
		}
		request.setAttribute("ugs", ugs.toString());
		return "success";
	}

	public String edit() {
		Account entity = accountService.find(accid);
		entity.setPassword(account.getPassword());
		entity.setNickname(account.getNickname());
		entity.setPhone(account.getPhone());
		/** 权限组操作 **/
		entity.getGroups().clear();
		if (gids != null && gids.length > 0) {
			for (String gid : gids) {
				entity.getGroups().add(groupService.find(gid));
			}
		}
		accountService.update(entity);
		return "success";
	}

	public String delete() {
		try {
			Account entity = accountService.find(accid);
			entity.setVisible(false);
			accountService.update(entity);
			inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/** myProfile相关 */
	public String myProfile() {
		return "success";
	}

	public String myProfileEditUI() {
		return "success";
	}

	public String myProfileEdit() {
		request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Account loginAccount = (Account) session.getAttribute("loginAccount");
		if (loginAccount == null) {
			return "fail";
		}
		Account entity = accountService.find(loginAccount.getId());
		if (oripwd != null && oripwd.equals(entity.getPassword())) {
			entity.setPassword(account.getPassword());
			entity.setPhone(account.getPhone());
			entity.setNickname(account.getNickname());
			accountService.update(entity);
			session.setAttribute("loginAccount", entity);
			HttpServletResponse response = ServletActionContext.getResponse();
			WebUtil.delCookie(request, response, "username");
			WebUtil.delCookie(request, response, "password");
			WebUtil.delCookie(request, response, "rememberMe");
		} else {
			request.setAttribute("error", "原密码输入错误");
			return "oripwdError";
		}
		return "success";
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
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

	public long getAccid() {
		return accid;
	}

	public void setAccid(long accid) {
		this.accid = accid;
	}

	public Account getAccount() {
		return account;
	}

	public String getQuery_username() {
		return query_username;
	}

	public void setQuery_username(String queryUsername) {
		query_username = queryUsername;
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

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getGids() {
		return gids;
	}

	public void setGids(String[] gids) {
		this.gids = gids;
	}

	public String getOripwd() {
		return oripwd;
	}

	public void setOripwd(String oripwd) {
		this.oripwd = oripwd;
	}

}
