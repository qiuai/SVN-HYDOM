package com.hydom.account.ebean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.hydom.util.WebUtil;

public class PermissionTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String privilegeValue;

	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		Account account = WebUtil.getlogonAccount((HttpServletRequest) pageContext.getRequest());
		if (account != null && "admin".equals(account.getUsername())) {
			return EVAL_BODY_INCLUDE;
		}
		SystemPrivilege privilege = new SystemPrivilege(this.privilegeValue);
		if (account != null && account.getGroups() != null) {
			for (PrivilegeGroup group : account.getGroups()) {
				if (group.getPrivileges().contains(privilege)) {
					result = true;
					break;
				}
			}
		}
		return result ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	public String getPrivilegeValue() {
		return privilegeValue;
	}

	public void setPrivilegeValue(String privilegeValue) {
		this.privilegeValue = privilegeValue;
	}

}
