package com.hydom.extra.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.dao.PageView;
import com.hydom.extra.ebean.AppVersion;
import com.hydom.extra.service.AppVersionService;

@Controller
@Scope(value = "prototype")
public class AppVersionAction {
	@Resource
	private AppVersionService appVersionService;

	private AppVersion appVersion;
	private int maxresult = 10;
	private int page = 1;
	private int m = 4;// 识别选中导航菜单
	private InputStream inputStream;

	private File app;
	private String appFileName;
	private String appContentType;
	private Log log = LogFactory.getLog("manageOPLog");
	private HttpServletRequest request;
	private long appid;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<AppVersion> pageView = new PageView<AppVersion>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setQueryResult(appVersionService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(), params.toArray(),
				orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String add() {
		try {
			appVersion.setFileName(appFileName);
			appVersion.setFilePath(saveAccessory(app, appFileName));
			appVersionService.save(appVersion);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return "success";
	}

	public String editUI() {
		appVersion = appVersionService.find(appid);
		return "success";
	}

	public String edit() {
		try{
		AppVersion entity = appVersionService.find(appid);
		entity.setVersion(appVersion.getVersion());
		entity.setState(appVersion.getState());
		if (app != null && app.length() > 0) {// 修改上传的文件
			// 删除原文件
			String oripath = entity.getFilePath();
			if (oripath != null && "".equals(oripath)) {
				File oriFile = new File(ServletActionContext.getServletContext()
						.getRealPath(oripath));
				if (!oriFile.delete()) {
					log.info("删除文件失败，请手动删除，删除路径：" + oriFile.getAbsolutePath());
				}
			}
			// 更新文件地址及文件名
			entity.setFilePath(this.saveAccessory(app, appFileName));
			entity.setFileName(appFileName);
		}
			appVersionService.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "success";
	}

	public String delete() {
		AppVersion entity = appVersionService.find(appid);
		entity.setVisible(false);
		appVersionService.update(entity);
		try {
			inputStream = new ByteArrayInputStream("1".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}

	private String saveAccessory(File accessory, String accessoryFileName) {
		String savePath = null;
		if (accessory != null && accessoryFileName != null
				&& !"".equals(accessoryFileName)) {
			String saveDir = ServletActionContext.getServletContext().getRealPath(
					"/app/resource/system/soft");
			String suffix = accessoryFileName.substring(
					accessoryFileName.lastIndexOf("."), accessoryFileName.length())
					.toLowerCase();
			String fileName = new Date().getTime() + suffix;
			savePath = "app/resource/system/soft/" + fileName;
			File file = new File(saveDir);
			if (file.exists()) {
				file.mkdirs();
			}
			try {
				FileUtils.copyFile(accessory, new File(file, fileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return savePath;
	}

	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public File getApp() {
		return app;
	}

	public void setApp(File app) {
		this.app = app;
	}

	public String getAppFileName() {
		return appFileName;
	}

	public void setAppFileName(String appFileName) {
		this.appFileName = appFileName;
	}

	public String getAppContentType() {
		return appContentType;
	}

	public void setAppContentType(String appContentType) {
		this.appContentType = appContentType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public String getImgContentType() {
		return appContentType;
	}

	public void setImgContentType(String imgContentType) {
		this.appContentType = imgContentType;
	}

	public long getAppid() {
		return appid;
	}

	public void setAppid(long appid) {
		this.appid = appid;
	}

}
