/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.controller.admin;

import javax.annotation.Resource;

import cn.mario256.blog.service.PluginService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller - 存储插件
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminStoragePluginController")
@RequestMapping("/admin/storage_plugin")
public class StoragePluginController extends BaseController {

	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("storagePlugins", pluginService.getStoragePlugins());
		return "/admin/storage_plugin/list";
	}

}