/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import cn.mario256.blog.util.WebUtils;
import cn.mario256.blog.Setting;
import cn.mario256.blog.util.SystemUtils;
import cn.mario256.blog.util.WebUtils;
import cn.mario256.blog.service.CacheService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 统计
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("statisticsController")
@RequestMapping("/admin/statistics")
public class StatisticsController extends BaseController {

	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		model.addAttribute("cnzzSiteId", setting.getCnzzSiteId());
		model.addAttribute("cnzzPassword", setting.getCnzzPassword());
		return "/admin/statistics/view";
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String setting(ModelMap model) {
		Setting setting = SystemUtils.getSetting();
		model.addAttribute("isCnzzEnabled", setting.getIsCnzzEnabled());
		return "/admin/statistics/setting";
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.POST)
	public String setting(@RequestParam(defaultValue = "false") Boolean isEnabled, RedirectAttributes redirectAttributes) {
		Setting setting = SystemUtils.getSetting();
		if (isEnabled) {
			if (StringUtils.isEmpty(setting.getCnzzSiteId()) || StringUtils.isEmpty(setting.getCnzzPassword())) {
				String domain = setting.getSiteUrl().replaceAll("(^[\\s\\S]*?[^a-zA-Z0-9-.]+)|([^a-zA-Z0-9-.][\\s\\S]*$)", "");
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("domain", domain);
				parameterMap.put("key", DigestUtils.md5Hex(domain + "Lfg4uP0H"));
				String content = WebUtils.get("http://intf.cnzz.com/user/companion/tuling123.php", parameterMap);
				if (StringUtils.contains(content, "@")) {
					setting.setCnzzSiteId(StringUtils.substringBefore(content, "@"));
					setting.setCnzzPassword(StringUtils.substringAfter(content, "@"));
				}
			}
		}
		setting.setIsCnzzEnabled(isEnabled);
		SystemUtils.setSetting(setting);
		cacheService.clear();
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:setting.jhtml";
	}

}