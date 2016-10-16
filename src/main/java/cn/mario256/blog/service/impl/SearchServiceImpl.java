/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import cn.mario256.blog.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Service - 搜索
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("searchServiceImpl")
@Transactional
public class SearchServiceImpl implements SearchService {

	/** 模糊查询最小相似度 */
	private static final float FUZZY_QUERY_MINIMUM_SIMILARITY = 0.5F;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void index() {

	}

	@Override
	public void index(Class<?> type) {

	}

	@Override
	public void purge() {

	}

	@Override
	public void purge(Class<?> type) {

	}
}