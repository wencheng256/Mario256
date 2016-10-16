/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.plugin.qqLogin;

import cn.mario256.blog.Message;
import cn.mario256.blog.controller.admin.BaseController;
import cn.mario256.blog.Message;
import cn.mario256.blog.controller.admin.BaseController;
import cn.mario256.blog.entity.PluginConfig;
import cn.mario256.blog.service.PluginConfigService;
import cn.mario256.blog.plugin.LoginPlugin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - QQ登录
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminQqLoginController")
@RequestMapping("/admin/login_plugin/qq_login")
public class QqLoginController extends BaseController {

	@Resource(name = "qqLoginPlugin")
	private QqLoginPlugin qqLoginPlugin;
	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public @ResponseBody
    Message install() {
		if (!qqLoginPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(qqLoginPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 卸载
	 */
	@RequestMapping(value = "/uninstall", method = RequestMethod.POST)
	public @ResponseBody
	Message uninstall() {
		if (qqLoginPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(qqLoginPlugin.getId());
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = qqLoginPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/com/turing/xx/plugin/qqLogin/setting";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String loginMethodName, String oauthKey, String oauthSecret, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order, RedirectAttributes redirectAttributes) {
		PluginConfig pluginConfig = qqLoginPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(LoginPlugin.LOGIN_METHOD_NAME_ATTRIBUTE_NAME, loginMethodName);
		attributes.put("oauthKey", oauthKey);
		attributes.put("oauthSecret", oauthSecret);
//		attributes.put(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
//		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:/admin/login_plugin/list.jhtml";
	}

}