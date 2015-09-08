package com.hydom.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class UploadImageUtil {
	
	/** 验证是否是图片. */
	private final static String REG_IMAGE = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
	private final static int SMALL_WIDTH = 120;
	private final static int SMALL_HEGIHT = 120;
	private final static int BIG_WIDTH = 360;
	private final static int BIG_HEGIHT = 360;
	private final static int BANNER_BIG_WIDTH = 1200;
	private final static int BANNER_BIG_HEGIHT = 600;
	private final static int BANNER_SMALL_WIDTH = 640;
	private final static int BANNER_SMALL_HEGIHT = 240;

	
	/**
	 * 上传文件处理
	 * 
	 * @param file
	 * @return
	 */
	public static Map<String, String> uploadFile(MultipartFile file,
			HttpServletRequest request) {

		Map<String, String> data = new HashMap<String, String>();

		String root = "upload/img";
		// MultipartFile file = bean.getFile();
		String realName = file.getOriginalFilename();
		// String tempName = fileName;
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");

		// 路径
		String path = root + CommonUtil.getDateFloder();

		String oriFileName = file.getOriginalFilename();
		String oriExtName = oriFileName.substring(oriFileName.lastIndexOf("."));
		
		// 生成唯一的文件名称
		String fileName = CommonUtil.getUploadImageName()+oriExtName;

		// 是否是图片
		boolean isImage = fileName.matches(REG_IMAGE);
		// 图片源文件路径
		if (isImage) {
			path += "/source";
		}
		String filePath = path + "/" + fileName;
		File targetFile = new File(basePath + path + "/", fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);

			// 图片转大小图
			if (isImage) {

				String smallPath = path + "/small/" + fileName;
				String bigPath = path + "/big/" + fileName;
				BufferedImage srcBufferedImage = ImageIO.read(targetFile);
				File samllFile = new File(basePath + smallPath);
				if (!samllFile.getParentFile().exists()) {
					samllFile.getParentFile().mkdirs();
				}
				samllFile.createNewFile();
				File bigFile = new File(basePath + bigPath);
				if (!bigFile.getParentFile().exists()) {
					bigFile.getParentFile().mkdirs();
				}

				bigFile.createNewFile();

				ImageUtils.zoom(srcBufferedImage, samllFile,
						BANNER_SMALL_HEGIHT, BANNER_SMALL_WIDTH);
				ImageUtils.zoom(srcBufferedImage, bigFile, BANNER_BIG_HEGIHT,
						BANNER_BIG_WIDTH);

				data.put("small", smallPath);
				data.put("big", bigPath);
				data.put("source", filePath);
			} else {
				data.put("small", "");
				data.put("big", "");
				data.put("source", filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 上传文件处理
	 * 
	 * @param file
	 * @return
	 */
	public static Map<String, String> uploadFileApp(MultipartFile file,
			HttpServletRequest request) {

		Map<String, String> data = new HashMap<String, String>();

		String root = "upload/app";
		// MultipartFile file = bean.getFile();
		String realName = file.getOriginalFilename();
		// String tempName = fileName;
		String basePath = request.getSession().getServletContext()
				.getRealPath("/");

		// 路径
		String path = root + CommonUtil.getDateFloder();

		String oriFileName = file.getOriginalFilename();
		String oriExtName = oriFileName.substring(oriFileName.lastIndexOf("."));
		
		// 生成唯一的文件名称
		String fileName = CommonUtil.getUploadImageName()+oriExtName;

		// 是否是图片
		boolean isImage = fileName.matches(REG_IMAGE);
		// 图片源文件路径
		if (isImage) {
			path += "/source";
		}
		String filePath = path + "/" + fileName;
		File targetFile = new File(basePath + path + "/", fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);

			// 图片转大小图
			if (isImage) {

				String smallPath = path + "/small/" + fileName;
				String bigPath = path + "/big/" + fileName;
				BufferedImage srcBufferedImage = ImageIO.read(targetFile);
				File samllFile = new File(basePath + smallPath);
				if (!samllFile.getParentFile().exists()) {
					samllFile.getParentFile().mkdirs();
				}
				samllFile.createNewFile();
				File bigFile = new File(basePath + bigPath);
				if (!bigFile.getParentFile().exists()) {
					bigFile.getParentFile().mkdirs();
				}

				bigFile.createNewFile();

				ImageUtils.zoom(srcBufferedImage, samllFile,
						BANNER_SMALL_HEGIHT, BANNER_SMALL_WIDTH);
				ImageUtils.zoom(srcBufferedImage, bigFile, BANNER_BIG_HEGIHT,
						BANNER_BIG_WIDTH);

				data.put("small", smallPath);
				data.put("big", bigPath);
				data.put("source", filePath);
			} else {
				data.put("small", "");
				data.put("big", "");
				data.put("source", filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
}
