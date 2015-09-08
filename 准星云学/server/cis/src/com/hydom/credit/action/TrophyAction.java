package com.hydom.credit.action;

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

import com.hydom.credit.ebean.Trophy;
import com.hydom.credit.service.TrophyService;
import com.hydom.credit.service.TrophyTypeService;
import com.hydom.dao.PageView;
import com.hydom.util.WebUtil;

@Controller
@Scope(value = "prototype")
public class TrophyAction {
	@Resource
	private TrophyService trophyService;
	@Resource
	private TrophyTypeService trophyTypeService;
	private HttpServletRequest request;

	private Trophy trophy;
	private int maxresult = 10;
	private int page = 1;
	private long typeid;// 奖品类别ID
	private long id;
	private int m = 3;// 识别选中导航菜单
	private InputStream inputStream;

	private File img;
	private String imgFileName;
	private String imgContentType;
	private Log log = LogFactory.getLog("manageOPLog");

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Trophy> pageView = new PageView<Trophy>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setQueryResult(trophyService.getScrollData(pageView.getFirstResult(), maxresult,
				jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String addUI() {
		request = ServletActionContext.getRequest();
		request.setAttribute("types", trophyTypeService.list());
		return "success";
	}

	public String add() {
		trophy.setImage(this.saveAccessory(img, imgFileName));
		trophy.setImageName(imgFileName);
		trophy.setTrophyType(trophyTypeService.find(typeid));
		trophy.setDetailText(WebUtil.HtmltoText(trophy.getDetail()));
		trophyService.save(trophy);
		return "success";
	}

	public String editUI() {
		request = ServletActionContext.getRequest();
		trophy = trophyService.find(id);
		request.setAttribute("types", trophyTypeService.list());
		return "success";
	}

	public String edit() {
		Trophy entity = trophyService.find(id);
		entity.setName(trophy.getName());
		entity.setState(trophy.getState());
		entity.setStock(trophy.getStock());
		entity.setScore(trophy.getScore());
		entity.setMoney(trophy.getMoney());
		entity.setDetail(trophy.getDetail());
		entity.setDetailText(WebUtil.HtmltoText(trophy.getDetail()));
		entity.setTrophyType(trophyTypeService.find(typeid));
		if (img != null && img.length() > 0) {// 修改图片
			// 删除原图
			String oripath = entity.getImage();
			File oriFile = new File(ServletActionContext.getServletContext().getRealPath(oripath));
			if (!oriFile.delete()) {
				log.info("删除图片失败，请手动删除，删除路径：" + oriFile.getAbsolutePath());
			}
			// 更新图片地址及图片名
			entity.setImage(this.saveAccessory(img, imgFileName));
			entity.setImageName(imgFileName);
		}
		trophyService.update(entity);
		return "success";
	}

	public String show() {
		trophy = trophyService.find(id);
		return "success";
	}

	public String delete() {
		Trophy entity = trophyService.find(id);
		entity.setVisible(false);
		trophyService.update(entity);
		try {
			inputStream = new ByteArrayInputStream("1".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "success";
	}

	private String saveAccessory(File accessory, String accessoryFileName) {
		String savePath = null;
		if (accessory != null && accessoryFileName != null && !"".equals(accessoryFileName)) {
			String saveDir = ServletActionContext.getServletContext().getRealPath(
					"/app/resource/system/tropy");
			String suffix = accessoryFileName.substring(accessoryFileName.lastIndexOf("."),
					accessoryFileName.length()).toLowerCase();
			String fileName = new Date().getTime() + suffix;
			savePath = "app/resource/system/tropy/" + fileName;
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

	public Trophy getTrophy() {
		return trophy;
	}

	public void setTrophy(Trophy trophy) {
		this.trophy = trophy;
	}

	public long getTypeid() {
		return typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return imgContentType;
	}

	public void setImgContentType(String imgContentType) {
		this.imgContentType = imgContentType;
	}

}
