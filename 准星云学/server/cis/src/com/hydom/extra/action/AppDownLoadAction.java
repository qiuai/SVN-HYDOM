package com.hydom.extra.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.extra.ebean.AppVersion;
import com.hydom.extra.service.AppVersionService;

@Controller
@Scope(value = "prototype")
public class AppDownLoadAction {
	@Resource
	private AppVersionService appVersionService;
	@SuppressWarnings("unused")
	private String fileName;
	private long appid;

	public InputStream getInputStream() {
		try {
			AppVersion apv = appVersionService.find(appid);
			return ServletActionContext.getServletContext().getResourceAsStream(apv.getFilePath());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("下载文件出现错误");
		}
	}

	public String getFileName() throws UnsupportedEncodingException {
		AppVersion apv = appVersionService.find(appid);
		String downloadName = new String(apv.getFileName().getBytes(), "iso8859-1");
		return downloadName;
	}

	public String execute() {
		return "success";
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getAppid() {
		return appid;
	}

	public void setAppid(long appid) {
		this.appid = appid;
	}

}
