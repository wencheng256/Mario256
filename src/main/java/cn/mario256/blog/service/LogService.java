/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service;

import cn.mario256.blog.entity.Log;
import cn.mario256.blog.entity.Log;

/**
 * Service - 日志
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}