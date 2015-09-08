package com.hydom.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hydom.account.ebean.Account;
import com.hydom.account.ebean.Privilege;
import com.hydom.account.ebean.PrivilegeGroup;

public class PermissionTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String privilegeValue;

	@Override
	public int doStartTag() throws JspException {
		try {
			boolean result = false;
			Account account = WebUtil.getlogonAccount(
					(HttpServletRequest) pageContext.getRequest()).getMember();
			if (account != null && "admin".equals(account.getUsername())) {
				return EVAL_BODY_INCLUDE;
			}
			Privilege privilege = new Privilege(this.privilegeValue);
			if (account != null && account.getGroups() != null) {
				for (PrivilegeGroup group : account.getGroups()) {
					if (group.getPrivileges().contains(privilege)) {
						result = true;
						break;
					}
				}
			}
			return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
		} catch (Exception e) {
			return SKIP_BODY; //
		}
	}

	public String getPrivilegeValue() {
		return privilegeValue;
	}

	public void setPrivilegeValue(String privilegeValue) {
		this.privilegeValue = privilegeValue;
	}

}
