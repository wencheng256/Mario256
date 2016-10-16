/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.plugin.localStorage;

import javax.annotation.Resource;

import cn.mario256.blog.controller.admin.BaseController;
import cn.mario256.blog.entity.PluginConfig;
import cn.mario256.blog.controller.admin.BaseController;
import cn.mario256.blog.entity.PluginConfig;
import cn.mario256.blog.service.PluginConfigService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 本地文件存储
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminLocalStorageController")
@RequestMapping("/admin/storage_plugin/local_storage")
public class LocalStorageController extends BaseController {

	@Resource(name = "localStoragePlugin")
	private LocalStoragePlugin localStoragePlugin;
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/com/turing/xx/plugin/localStorage/setting";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Integer order, RedirectAttributes redirectAttributes) {
		PluginConfig pluginConfig = localStoragePlugin.getPluginConfig();
		pluginConfig.setIsEnabled(true);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/storage_plugin/list.jhtml";
	}

}