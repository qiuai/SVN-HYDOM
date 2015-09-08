//package com.hydom.zxy.controller;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.annotation.Resource;
//import javax.imageio.ImageIO;
//
//import net.sf.json.JSONObject;
//import net.sf.json.JsonConfig;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.hydom.common.AbstractController;
//import com.hydom.util.HydomUtil;
//import com.hydom.util.ImageUtil;
//import com.hydom.zxy.entity.Attachment;
//import com.hydom.zxy.service.AttachmentService;
//
///**
// * 上传附件.
// * @author Holen
// * @version 1.0.0 2014.12.30 新建
// */
//@Controller
//public class UploadController extends AbstractController {
//
//	private final static int SMALL_WIDTH = 120;
//	private final static int SMALL_HEGIHT = 120;
//	private final static int BIG_WIDTH = 360;
//	private final static int BIG_HEGIHT = 360;
//	/**
//	 * serialVersionUID.
//	 */
//	private static final long serialVersionUID = 8417318641887643017L;
//	
//	/** 验证是否是图片. */
//	private final static String REG_IMAGE = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
//	
//	@Resource
//	private AttachmentService attachmentService;
//
//    @RequestMapping(value = "/upload.do")
//    @ResponseBody
//    public void upload(@RequestParam(value = "file", required = false) MultipartFile file) {  
////        System.out.println("开始");
////        String dateFloder = HydomUtil.getDateFloder();
//        
//    	JSONObject result = new JSONObject();
//        // 文件名称
//        String fileName = file.getOriginalFilename();
//        String tempName = fileName;
//        String basePath = request.getSession().getServletContext().getRealPath("/");
//        
//        // 路径
//        String path = "upload" + HydomUtil.getDateFloder();  
//
//        // 生成唯一的文件名称
//        fileName = HydomUtil.getUUID() + fileName.substring(fileName.lastIndexOf("."));
//
//        // 是否是图片
//        boolean isImage = fileName.matches(REG_IMAGE);
//        // 图片源文件路径
//        if (isImage) {
//        	path += File.separator + "source";
//        }
//        
//        File targetFile = new File(basePath + path + File.separator, fileName);  
//        if (!targetFile.exists()) {
//            targetFile.mkdirs();  
//        }
//  
//        // 附件实体
//        Attachment attachment = new Attachment();
//        attachment.setAttname(tempName);
//        attachment.setSourcepath(path + File.separator + fileName);
//        attachment.setIsuse(false);
//        
//        //保存  
//        try {
//            file.transferTo(targetFile);
//            
//            // 图片转大小图
//            if (isImage) {
//            	String smallPath = path + "\\small\\" + fileName;
//            	String bigPath =  path + "\\big\\" + fileName;
//            	BufferedImage srcBufferedImage = ImageIO.read(targetFile);
//            	File samllFile = new File(basePath + smallPath);
//            	if (!samllFile.getParentFile().exists()) {
//            		samllFile.getParentFile().mkdirs();
//            	}
//            	samllFile.createNewFile();
//            	File bigFile = new File(basePath + bigPath);
//            	if (!bigFile.getParentFile().exists()) {
//            		bigFile.getParentFile().mkdirs();
//            	}
//
//            	bigFile.createNewFile();
//            	ImageUtil.zoom(srcBufferedImage, samllFile, SMALL_HEGIHT, SMALL_WIDTH);
//            	ImageUtil.zoom(srcBufferedImage, bigFile, BIG_HEGIHT, BIG_WIDTH);
//            	
//            	attachment.setFiletype(1);
//            	attachment.setSmaillpath(smallPath);
//            	attachment.setBigpath(bigPath);
//            	
//            } else {
//            	attachment.setFiletype(0);
//            }
//            
//            // 保存附件，取得ID
//            attachmentService.save(attachment);
//            
//            JsonConfig config = new JsonConfig();
//            config.setExcludes(new String[] {"refid", "atttype", "isuse", "lastupdate", "cacheKey"});
//            
//            result.put("data", JSONObject.fromObject(attachment, config));
//        } catch (Exception e) {  
//            e.printStackTrace();
//            result.put(STATUS, -1);
//        }  
//  
//        ajaxJSONObject(result);
//    }  
//}
