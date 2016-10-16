/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service;

import cn.mario256.blog.TemplateConfig;
import cn.mario256.blog.TemplateConfig;

/**
 * Service - 模板
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface TemplateService {

	/**
	 * 读取模板文件内容
	 * 
	 * @param templateConfigId
	 *            模板配置ID
	 * @return 模板文件内容
	 */
	String read(String templateConfigId);

	/**
	 * 读取模板文件内容
	 * 
	 * @param templateConfig
	 *            模板配置
	 * @return 模板文件内容
	 */
	String read(TemplateConfig templateConfig);

	/**
	 * 写入模板文件内容
	 * 
	 * @param templateConfigId
	 *            模板配置ID
	 * @param content
	 *            模板文件内容
	 */
	void write(String templateConfigId, String content);

	/**
	 * 写入模板文件内容
	 * 
	 * @param templateConfig
	 *            模板配置
	 * @param content
	 *            模板文件内容
	 */
	void write(TemplateConfig templateConfig, String content);

}