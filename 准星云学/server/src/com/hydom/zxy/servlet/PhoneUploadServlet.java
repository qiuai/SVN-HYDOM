package com.hydom.zxy.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 手机上传图片
 * @author 肖兆升
 * @version 1.0.0 2014年12月10日
 */
public class PhoneUploadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 507539809955385240L;
	
	/** 图片存放路径 */
	public static final String IMAGES_PATH = "/upload/images/";

	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8");
			
			// 获取请求内容中的图片
			String fileName = uploadImage(request, IMAGES_PATH);
			
			JSONObject json = new JSONObject();
			json.put(CODE, 0);
			json.put("url", IMAGES_PATH.substring(1, IMAGES_PATH.length()) + fileName + ".jpg");
			System.out.println(json.toString());
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片上传
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String uploadImage(HttpServletRequest request, String diskPath) throws IOException {
	    // 获取本地文件路径
		String path = request.getServletContext().getRealPath(diskPath);
		String fileName = UUID.randomUUID().toString() ;
    	File file = new File(path, fileName + ".jpg");
    	file.getParentFile().mkdirs();
    	file.createNewFile();
    	
    	FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 中文乱码
		upload.setHeaderEncoding("utf-8");
		try {
			List items = upload.parseRequest(request);
			Map fields = new HashMap();
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField())
					fields.put(item.getFieldName(), item.getString());
				else
					fields.put(item.getFieldName(), item);
			}
			
			// 获取上传表单中的file
			FileItem uplFile = (FileItem) fields.get("upload");
			InputStream ins = uplFile.getInputStream();
			OutputStream ous = new FileOutputStream(file);
			try {
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = ins.read(buffer)) > -1) {
					ous.write(buffer, 0, len);
				}
			} finally {
				ous.close();
				ins.close();
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	    return fileName;
	}
}
