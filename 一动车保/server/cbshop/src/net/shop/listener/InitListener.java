/*
 * 
 * 
 * 
 */
package net.shop.listener;

import java.io.File;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import net.shop.service.CacheService;
import net.shop.service.SearchService;
import net.shop.service.StaticService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Listener - 初始化
 * 
 * 
 * 
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	/** 安装初始化配置文件 */
	private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	/** logger */
	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	/** servletContext */
	private ServletContext servletContext;

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			String info = "I|n|i|t|i|a|l|i|z|i|n|g|  "+systemName+" | "+ systemVersion;
			logger.info(info.replace("|", ""));
			File installInitConfigFile = new File(servletContext.getRealPath(INSTALL_INIT_CONFIG_FILE_PATH));
			if (installInitConfigFile.exists()) {
				cacheService.clear();
				staticService.buildAll();
				searchService.purge();
				searchService.index();
				installInitConfigFile.delete();
			} else {
				staticService.buildIndex();//创建静态首页
				staticService.buildOther();
			}
		}
	}

}