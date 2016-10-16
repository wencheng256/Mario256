/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import cn.mario256.blog.plugin.LoginPlugin;
import cn.mario256.blog.plugin.StoragePlugin;
import cn.mario256.blog.service.PluginService;
import cn.mario256.blog.plugin.LoginPlugin;
import cn.mario256.blog.plugin.StoragePlugin;
import cn.mario256.blog.service.PluginService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Service - 插件
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("pluginServiceImpl")
public class PluginServiceImpl implements PluginService {

	@Resource
	private List<StoragePlugin> storagePlugins = new ArrayList<StoragePlugin>();
	@Resource
	private List<LoginPlugin> loginPlugins = new ArrayList<LoginPlugin>();
	@Resource
	private Map<String, StoragePlugin> storagePluginMap = new HashMap<String, StoragePlugin>();
	@Resource
	private Map<String, LoginPlugin> loginPluginMap = new HashMap<String, LoginPlugin>();



	public List<StoragePlugin> getStoragePlugins() {
		Collections.sort(storagePlugins);
		return storagePlugins;
	}

	public List<LoginPlugin> getLoginPlugins() {
		Collections.sort(loginPlugins);
		return loginPlugins;
	}

	public List<StoragePlugin> getStoragePlugins(final boolean isEnabled) {
		List<StoragePlugin> result = new ArrayList<StoragePlugin>();
		CollectionUtils.select(storagePlugins, new Predicate() {
			public boolean evaluate(Object object) {
				StoragePlugin storagePlugin = (StoragePlugin) object;
				return storagePlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	public List<LoginPlugin> getLoginPlugins(final boolean isEnabled) {
		List<LoginPlugin> result = new ArrayList<LoginPlugin>();
		CollectionUtils.select(loginPlugins, new Predicate() {
			public boolean evaluate(Object object) {
				LoginPlugin loginPlugin = (LoginPlugin) object;
				return loginPlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	public StoragePlugin getStoragePlugin(String id) {
		return storagePluginMap.get(id);
	}

	public LoginPlugin getLoginPlugin(String id) {
		return loginPluginMap.get(id);
	}

}