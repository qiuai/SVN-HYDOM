package com.hydom.util.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
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

import com.hydom.util.CommonUtil;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.IDGenerator;

/**
 * 图片上传
 * @author 刘顿
 */
public class UploadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 507539809955385240L;
	
	/** 学员头像存放路径 
	public static final String STUDENT_AVATAR_PATH = "upload/student";*/

	/** 员工头像存放路径 */
	public static final String IMG_PATH = "upload/img";
	public static final String FILE_PATH = "upload/file";
	
	public static final Integer IMG = 0;
	public static final Integer FILE = 1;
	
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=UTF-8");
			
			int type = Integer.parseInt(request.getParameter("type"));
			
			// 判断图片存放位置
			String root = "";
			if(type == IMG){
				root = IMG_PATH;
			}else if(type == FILE){
				root = FILE_PATH;
			}
			
			// 路径
			String path = root + CommonUtil.getDateFloder();
			
			// 获取请求内容中的图片
			String fileName = uploadImage(request, path);
			
			JSONObject json = new JSONObject();
			json.put(CODE, 0);
//			json.put("url", root.substring(1, root.length()) + fileName + ".jpg");
			json.put("url", path + "/" + fileName );
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
			FileItem uplFile = (FileItem) fields.get("file");
			String name = uplFile.getName();
			
			String oriExtName = name.substring(name.lastIndexOf("."));
			
			 // 获取本地文件路径
			String path = request.getServletContext().getRealPath(diskPath);
			String fileName = CommonUtil.getUploadImageName() + oriExtName;
	    	File file = new File(path, fileName);
	    	file.getParentFile().mkdirs();
	    	file.createNewFile();
			
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
			return fileName;
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	    return "";
	}
}
