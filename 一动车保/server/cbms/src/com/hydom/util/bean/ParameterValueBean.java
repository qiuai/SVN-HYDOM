package com.hydom.util.bean;

import com.hydom.account.ebean.Parameter;

public class ParameterValueBean {
	private Parameter parameter;
	private String parameterValue;

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
