package com.hydom.vt.api;

import java.io.File;
import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hydom.common.AbstractController;
import com.hydom.util.HydomUtil;
import com.hydom.vt.util.Constant;

/**
 * 上传附件.
 * 
 * @author Holen
 * @version 1.0.0 2014.12.30 新建
 */
@Controller("apiUploadController")
@RequestMapping(value = "/api")
@Scope("prototype")
public class UploadController extends AbstractController {

	private final static int SMALL_WIDTH = 120;
	private final static int SMALL_HEGIHT = 120;
	private final static int BIG_WIDTH = 360;
	private final static int BIG_HEGIHT = 360;
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8417318641887643017L;

	/** 验证是否是图片. */
	private final static String REG_IMAGE = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";

	@RequestMapping(value = "/upload.do")
	@ResponseBody
	public void upload(
			@RequestParam(value = "Filedata", required = false) MultipartFile file,
			@RequestParam(defaultValue = "") String httpdata)
			throws IOException {
		System.out.println("开始");
		// String dateFloder = HydomUtil.getDateFloder();

		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(httpdata)) {
			result = JSONObject.fromObject(httpdata);
		}
		// 保存
		try {
			// 文件名称
			String fileName = file.getOriginalFilename();
			// String tempName = fileName;
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");

			// 路径
			String path = "upload/" + HydomUtil.getDateFloder();

			// 生成唯一的文件名称
			String uuid = HydomUtil.getUUID();
			try {

				fileName = uuid + fileName.substring(fileName.lastIndexOf("."));
			} catch (Exception e) {
				fileName = uuid;
			}
			// 是否是图片
			boolean isImage = fileName.matches(REG_IMAGE);
			// 图片源文件路径
			if (isImage) {
				path += "/source";
			}

			File targetFile = new File(basePath + path + File.separator,
					fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// JSONObject data = new JSONObject();
			result.put("source", path + "/" + fileName);
			result.put("realName", file.getOriginalFilename());
			result.put("length", file.getBytes().length);

			file.transferTo(targetFile);
			// 图片转大小图
			/*
			 * if (isImage) {
			 * 
			 * String smallPath = path + "\\small\\" + fileName; String bigPath
			 * = path + "\\big\\" + fileName; BufferedImage srcBufferedImage =
			 * ImageIO.read(targetFile); File samllFile = new File(basePath +
			 * smallPath); if (!samllFile.getParentFile().exists()) {
			 * samllFile.getParentFile().mkdirs(); } samllFile.createNewFile();
			 * File bigFile = new File(basePath + bigPath); if
			 * (!bigFile.getParentFile().exists()) {
			 * bigFile.getParentFile().mkdirs(); }
			 * 
			 * bigFile.createNewFile(); ImageUtil.zoom(srcBufferedImage,
			 * samllFile, SMALL_HEGIHT, SMALL_WIDTH);
			 * ImageUtil.zoom(srcBufferedImage, bigFile, BIG_HEGIHT, BIG_WIDTH);
			 * 
			 * data.put("small", smallPath); data.put("big", bigPath); }
			 */
			// result.put(DATA, data);
			// result.put(STATUS, CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("type", Constant.TYPE_99);
			result.put("error", "网络异常");
		}
		System.out.println(result);
		ajaxJSONObject(result);
	}

	@RequestMapping(value = "/sendFile.do")
	@ResponseBody
	public void upload(String filePath) {
		// System.out.println("开始");
		// String dateFloder = HydomUtil.getDateFloder();
		JSONObject result = new JSONObject();
		try {
			File file = new File(filePath);

			byte[] b = HydomUtil.File2byte(filePath);

			// 文件名称
			String fileName = file.getName();
			// String tempName = fileName;
			String basePath = request.getSession().getServletContext()
					.getRealPath("/");

			// 路径
			String path = "upload" + HydomUtil.getDateFloder();

			// 生成唯一的文件名称
			fileName = HydomUtil.getUUID()
					+ fileName.substring(fileName.lastIndexOf("."));

			// 是否是图片
			boolean isImage = fileName.matches(REG_IMAGE);
			// 图片源文件路径
			if (isImage) {
				path += File.separator + "source";
			}
			String fileReal = basePath + path + File.separator;

			HydomUtil.byte2File(b, fileReal, fileName);

			JSONObject data = new JSONObject();
			data.put("source", path + "/" + fileName);
			data.put("realName", file.getName());
			data.put("length", file.length());

			result.put(DATA, data);
			result.put(STATUS, CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(STATUS, CODE_FAILD);
		}

		ajaxJSONObject(result);
	}

}
