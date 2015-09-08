/*
 * 
 * 
 * 
 */
package com.hydom.util.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.dom4j.DocumentException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.hydom.util.CommonAttributes;
import com.hydom.util.bean.DateMapBean;


/**
 * Listener - 初始化
 * 
 * 
 * 
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {
	
	private ServletContext servletContent;
	public void setServletContext(ServletContext servletContext) {
		this.servletContent = servletContext;
	}
	
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("初始化系统设置......");
		try {
			initCleanServerConfig();
			initServerDateTimeConfig();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 系统参数（系统参数ID 洗车服务ID）
	 * @throws DocumentException
	 */
	void initCleanServerConfig() throws DocumentException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommonAttributes.getInstance().setCleanCar(pro.getProperty("server.cleancar"));
		CommonAttributes.getInstance().setSystemId(pro.getProperty("server.systemId"));
		CommonAttributes.getInstance().setPayURL(pro.getProperty("system.network_address"));
		CommonAttributes.getInstance().setSystemParam();
		
	}
	
	/**
	 * 获取一天服务时间
	 */
	void initServerDateTimeConfig(){
		LinkedHashMap<String,DateMapBean> dateTimeMap = new LinkedHashMap<String,DateMapBean>();
		Integer serviceTime = 2;
		// 工作时间 从9点开始
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 9);// 当前0点
		cal.set(Calendar.MINUTE, 0);// 0分
		cal.set(Calendar.SECOND, 0);// 0秒
		// Date startDate = cal.getTime();
		int key = 0 ;
		while (cal.get(Calendar.HOUR_OF_DAY) < 19) {
			DateMapBean bean = new DateMapBean();
			bean.setStartDate(cal.getTime());
			
			cal.add(Calendar.HOUR_OF_DAY, serviceTime);
			bean.setEndDate(cal.getTime());
			dateTimeMap.put(""+key, bean);
			key++;
		}
		CommonAttributes.getInstance().setDateTimeMap(dateTimeMap);
	}
}