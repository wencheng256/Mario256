/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import javax.annotation.Resource;

import cn.mario256.blog.entity.Log;
import cn.mario256.blog.dao.LogDao;
import cn.mario256.blog.entity.Log;
import cn.mario256.blog.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service - 日志
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

	@Resource(name = "logDaoImpl")
	private LogDao logDao;

	public void clear() {
		logDao.removeAll();
	}

}