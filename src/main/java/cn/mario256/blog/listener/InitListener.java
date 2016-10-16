/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.listener;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import cn.mario256.blog.service.ConfigService;
import cn.mario256.blog.service.ConfigService;
import cn.mario256.blog.service.SearchService;
import cn.mario256.blog.service.StaticService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Listener - 初始化
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	/** 安装初始化配置文件 */
	private static final String INSTALL_INIT_CONFIG_FILE_PATH = "/install_init.conf";

	/** Logger */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());

	/** ServletContext */
	private ServletContext servletContext;

	@Value("${system.version}")
	private String systemVersion;
	@Resource(name = "configServiceImpl")
	private ConfigService configService;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "searchServiceImpl")
	private SearchService searchService;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 事件执行
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			String info = "I|n|i|t|i|a|l|i|z|i|n|g| |T|U|R|I|N|G| |" + systemVersion;
			LOGGER.info(info.replace("|", ""));
			configService.init();
			/*File installInitConfigFile = new File(servletContext.getRealPath(INSTALL_INIT_CONFIG_FILE_PATH));
			if (installInitConfigFile.exists()) {*/
				staticService.generateAll();
				searchService.purge();
				searchService.index();
//				installInitConfigFile.delete();
			/*} else {
				staticService.generateIndex();
				staticService.generateOther();
			}*/
		}
	}

}