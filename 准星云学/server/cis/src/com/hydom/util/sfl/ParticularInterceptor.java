package com.hydom.util.sfl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Account;
import com.hydom.account.ebean.PrivilegeGroup;
import com.hydom.account.ebean.SystemPrivilege;
import com.hydom.account.service.SystemPrivilegeService;
import com.hydom.util.WebUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Ï¸Á£¶ÈÀ¹½ØÆ÷
 */
@Service
public class ParticularInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@Resource
	private SystemPrivilegeService systemPrivilegeService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Account account = WebUtil.getlogonAccount(ServletActionContext.getRequest());
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer requestUrl = request.getRequestURL();
		if (request.getQueryString() != null && request.getQueryString().length() > 0) {
			requestUrl.append("#" + request.getQueryString());
		}
		String url = requestUrl.substring(requestUrl.indexOf("manage"));
		SystemPrivilege sp = systemPrivilegeService.findByURL(url);
		if (sp != null) { // request url is required
			if (account != null && account.getGroups() != null) {
				for (PrivilegeGroup group : account.getGroups()) {
					if (group.getPrivileges().contains(sp)) {
						return invocation.invoke();
					}
				}
				return "unauth";
			} else {
				return "unauth";
			}
		} else {
			return invocation.invoke();
		}
	}

}
