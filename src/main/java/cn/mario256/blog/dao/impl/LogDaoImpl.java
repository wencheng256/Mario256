/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.dao.impl;

import cn.mario256.blog.entity.Log;
import cn.mario256.blog.dao.LogDao;
import cn.mario256.blog.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao - 日志
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).executeUpdate();
	}

}