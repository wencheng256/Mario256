/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.controller.admin;

import javax.annotation.Resource;

import cn.mario256.blog.Message;
import cn.mario256.blog.Message;
import cn.mario256.blog.Pageable;
import cn.mario256.blog.service.LogService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller - 日志
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminLogController")
@RequestMapping("/admin/log")
public class LogController extends BaseController {

	@Resource(name = "logServiceImpl")
	private LogService logService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", logService.findPage(pageable));
		return "/admin/log/list";
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("log", logService.find(id));
		return "/admin/log/view";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long[] ids) {
		logService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 清空
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public @ResponseBody
	Message clear() {
		logService.clear();
		return SUCCESS_MESSAGE;
	}

}