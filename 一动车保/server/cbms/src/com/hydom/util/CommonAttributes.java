/*
 * 
 * 
 * 
 */
package com.hydom.util;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.hydom.account.ebean.SystemParam;
import com.hydom.account.service.SystemParamService;
import com.hydom.util.bean.DateMapBean;
import com.hydom.util.bean.SystemBean;

/**
 * 公共参数
 * 
 * 
 * 
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** system.xml文件路径 */
	public static final String SYSTEM_XML_PATH = "/system.xml";

	/** config.properties文件路径 */
	public static final String CONFIG_PROPERTIES_PATH = "/config.properties";
	
	private static CommonAttributes instance;
	/**
	 * 洗车服务ID
	 */
	private String cleanCar;
	
	private String systemId;
	
	private LinkedHashMap<String,DateMapBean> dateTimeMap = new LinkedHashMap<String,DateMapBean>();
	
	private static SystemBean systemBean;
	
	private String payURL;
	
	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}
	
	public static CommonAttributes getInstance(){
		if(instance == null){
			instance = new CommonAttributes();
		}
		return instance;
	}
	
	
	public void setSystemParam() {
		// TODO Auto-generated method stub
		SystemParamService systemParamService = (SystemParamService) SpringUtil.getBean("SystemParamService");
		SystemParam param = systemParamService.find(this.getSystemId());
		this.setSystemBean(conver2SystemParam(param));
	}
	
	public SystemBean conver2SystemParam(SystemParam param){
		SystemBean systemBean = new SystemBean();
		String contentObj = param.getContent();
		try{
			JSONObject obj = JSONObject.fromObject(contentObj);
			if(obj.containsKey("startDate")){
				systemBean.setStartDate(obj.getString("startDate"));
			}else{
				systemBean.setStartDate("9:00");
			}

			if(obj.containsKey("endDate")){
				systemBean.setEndDate(obj.getString("endDate"));
			}else{
				systemBean.setEndDate("18:00");
			}

			if(obj.containsKey("content")){
				systemBean.setContent(obj.getString("content"));
			}else{
				systemBean.setContent("只支持贵阳地区");
			}
			
		}catch(Exception e){
			systemBean.setStartDate("9:00");
			systemBean.setEndDate("18:00");
			systemBean.setContent("只支持贵阳地区");
		}
		
		if(StringUtils.isEmpty(param.getVersion())){
			systemBean.setVersion("0.66");
		}else{
			systemBean.setVersion(param.getVersion());
		}
		return systemBean;
	}
	
	
	
	public String getCleanCar() {
		return cleanCar;
	}

	public void setCleanCar(String cleanCar) {
		this.cleanCar = cleanCar;
	}

	public LinkedHashMap<String, DateMapBean> getDateTimeMap() {
		return dateTimeMap;
	}

	public void setDateTimeMap(LinkedHashMap<String, DateMapBean> dateTimeMap) {
		this.dateTimeMap = dateTimeMap;
	}

	public SystemBean getSystemBean() {
		return systemBean;
	}

	public void setSystemBean(SystemBean systemBean) {
		this.systemBean = systemBean;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getPayURL() {
		return payURL;
	}

	public void setPayURL(String payURL) {
		this.payURL = payURL;
	}
	
}