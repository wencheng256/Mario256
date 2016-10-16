/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.dao;

import cn.mario256.blog.entity.Log;
import cn.mario256.blog.entity.Log;

/**
 * Dao - 日志
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface LogDao extends BaseDao<Log, Long> {

	/**
	 * 删除所有日志
	 */
	void removeAll();

}