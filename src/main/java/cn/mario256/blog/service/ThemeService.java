/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service;

import java.util.List;

import cn.mario256.blog.Theme;
import cn.mario256.blog.Theme;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 主题
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface ThemeService {

	/**
	 * 获取所有主题
	 * 
	 * @return 所有主题
	 */
	List<Theme> getAll();

	/**
	 * 获取主题
	 * 
	 * @param id
	 *            ID
	 * @return 主题
	 */
	Theme get(String id);

	/**
	 * 上传主题
	 * 
	 * @param multipartFile
	 *            上传文件
	 * @return 是否上传成功
	 */
	boolean upload(MultipartFile multipartFile);

}