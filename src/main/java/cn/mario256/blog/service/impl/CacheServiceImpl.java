/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import javax.annotation.Resource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import cn.mario256.blog.service.CacheService;
import cn.mario256.blog.service.ConfigService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Service - 缓存
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "messageSource")
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
	@Resource(name = "configServiceImpl")
	private ConfigService configService;

	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}

	@CacheEvict(value = { "setting", "logConfig", "templateConfig", "pluginConfig", "messageConfig", "area", "seo", "adPosition", "memberAttribute", "navigation", "tag", "friendLink", "brand", "attribute", "article", "articleCategory", "goods", "productCategory", "review", "consultation",
			"promotion", "shipping", "authorization" }, allEntries = true)
	public void clear() {
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
		reloadableResourceBundleMessageSource.clearCache();
		configService.init();
	}

}