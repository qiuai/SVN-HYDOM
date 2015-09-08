package com.hydom.extra.action;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.extra.ebean.SystemConfig;
import com.hydom.extra.service.SystemConfigService;
import com.hydom.util.WebUtil;

@Controller
@Scope(value = "prototype")
public class SystemConfigAction {
	@Resource
	private SystemConfigService systemConfigService;

	private String scid;
	private int m = 5;// 识别选中导航菜单
	private SystemConfig config;

	public String show() {
		config = systemConfigService.find(scid);
		if ("match".equals(scid)) {// 计算精准的百分比
			HttpServletRequest request = ServletActionContext.getRequest();
			BigDecimal b1 = new BigDecimal(Double.toString(config.getValueDouble()));
			BigDecimal b2 = new BigDecimal("100");
			request.setAttribute("percent", b1.multiply(b2).doubleValue());
		}
		return "success";
	}

	public String editUI() {
		config = systemConfigService.find(scid);
		if ("match".equals(scid)) {// 计算精准的百分比
			HttpServletRequest request = ServletActionContext.getRequest();
			BigDecimal b1 = new BigDecimal(Double.toString(config.getValueDouble()));
			BigDecimal b2 = new BigDecimal("100");
			request.setAttribute("percent", b1.multiply(b2).doubleValue());
		}
		return "success";
	}

	public String edit() {
		SystemConfig entity = systemConfigService.find(scid);
		entity.setValueContent(config.getValueContent());
		entity.setValueInt(config.getValueInt());
		entity.setValueLong(config.getValueLong());
		entity.setValueShort(config.getValueShort());
		entity.setValueString(config.getValueString());
		entity.setValueText(WebUtil.HtmltoText(config.getValueContent()));
		entity.setValueDouble(config.getValueDouble());
		if ("match".equals(scid)) {// 
			if (config.getValueDouble() >= 1) {// 如果大于1除100：计算精准百分比
				BigDecimal b1 = new BigDecimal(Double.toString(config.getValueDouble()));
				BigDecimal b2 = new BigDecimal("100");
				entity.setValueDouble(b1.divide(b2).doubleValue());
			} else {
				entity.setValueDouble(config.getValueDouble());
			}
		}
		systemConfigService.update(entity);
		return "success";
	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public SystemConfig getConfig() {
		return config;
	}

	public void setConfig(SystemConfig config) {
		this.config = config;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

}
