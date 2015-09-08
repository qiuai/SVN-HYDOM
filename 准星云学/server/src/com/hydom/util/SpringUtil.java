package com.hydom.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * 工具类 - Spring
 */

@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) applicationContext.getBean(clazz.getSimpleName());
	}
	
	/**
	 * 获取资源.
	 * @param key - 资源的Key
	 * @return 资源
	 */
	public static String getText(String key) {
		DelegatingMessageSource resource = (DelegatingMessageSource) getBean("messageSource");
		return resource.getMessage(key, null, Locale.CHINA);
//		return applicationContext.getMessage(key, null, Locale.CHINA);
	}
}
