package com.hydom.account.action;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.account.ebean.SystemParam;
import com.hydom.account.service.SystemParamService;
import com.hydom.util.BaseAction;
import com.hydom.util.CommonAttributes;
import com.hydom.util.bean.SystemBean;

/**
 * 系统参数
 * 
 * @author Administrator
 * 
 */
@RequestMapping("/manage/system")
@Controller
public class SystemAction extends BaseAction {

	private static String base = "/account";
	private static String mark = "12";
	@Resource
	private SystemParamService systemParamService;

	@RequestMapping("/view")
	public String getSystemView(ModelMap model) {
		SystemBean bean = CommonAttributes.getInstance().getSystemBean();
		model.addAttribute("entity", bean);
		model.addAttribute("m", mark);
		return base + "/system_view";
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String getUpdate(@RequestParam String startDate,
			@RequestParam String endDate, String content, String price,
			String version, HttpServletResponse response) {
		try {
			JSONObject obj = new JSONObject();
			obj.put("startDate", startDate);
			obj.put("endDate", endDate);
			obj.put("content", content);
			obj.put("price", price);

			SystemParam systemParam = systemParamService.find(CommonAttributes
					.getInstance().getSystemId());
			systemParam.setContent(obj.toString());
			systemParam.setVersion(version);
			systemParamService.update(systemParam);

			CommonAttributes.getInstance().setSystemParam();

			return ajaxSuccess("", response);
		} catch (Exception e) {

		}
		return ajaxError("修改出错", response);
	}

	/**
	 * 删除技师图片
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteView")
	public String deleteView(ModelMap model) {
		model.addAttribute("m", mark);
		return base + "/system_delete";
	}

	/**
	 * 删除 upload/app下面所有的图片
	 * 
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @return
	 */
	@RequestMapping("/deleteFile")
	@ResponseBody
	public String deleteFile(@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			HttpServletRequest request, HttpServletResponse response) {
		String path = request.getSession().getServletContext()
				.getRealPath("upload/app");
		System.out.println(path);

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		endDate = cal.getTime();

		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		startDate = cal.getTime();

		while (startDate.before(endDate)) {
			String separator = File.separator + cal.get(Calendar.YEAR)
					+ File.separator + (cal.get(Calendar.MONTH) + 1)
					+ File.separator + cal.get(Calendar.DAY_OF_MONTH);
			String separator1 = File.separator + cal.get(Calendar.YEAR)
					+ File.separator + (cal.get(Calendar.MONTH) + 1);
			String separator2 = File.separator + cal.get(Calendar.YEAR);
			String filePath = path + separator;
			removeFile(filePath);
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return ajaxSuccess("成功", response);
	}

	public static void removeFile(String path) {
		// path =
		// "D:/apache-tomcat-7.0/apache-tomcat-7.0.61/webapps/cbms/upload/app/2015/8/15";
		File file = new File(path);
		if (file.exists()) {
			deleteFile(file);
			// System.out.println(file.delete());
		}
	}

	public static void main(String[] args) {
		removeFile("");
	}

	public static void deleteFile(File file) {// 删除
		File[] fs = file.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (!fs[i].isDirectory()) {
				fs[i].delete();
				System.out.println(fs[i].getAbsolutePath());
			} else {
				deleteFile(fs[i]);
				if (fs[i].listFiles().length <= 0) {
					fs[i].delete();
				}
			}
		}
	}
}
