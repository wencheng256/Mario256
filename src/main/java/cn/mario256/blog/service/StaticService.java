/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service;

import java.util.Map;

/**
 * Service - 静态化
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface StaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int generate(String templatePath, String staticPath, Map<String, Object> model);


	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int generateIndex();

	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int generateSitemap();

	/**
	 * 生成其它静态
	 * 
	 * @return 生成数量
	 */
	int generateOther();

	/**
	 * 生成所有静态
	 * 
	 * @return 生成数量
	 */
	int generateAll();

	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);


	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

	/**
	 * 删除其它静态
	 * 
	 * @return 删除数量
	 */
	int deleteOther();

}