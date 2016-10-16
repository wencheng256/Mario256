/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.controller.admin;

import cn.mario256.blog.service.CaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Controller - 共用
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController implements ServletContextAware {

	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	@Value("${system.description}")
	private String systemDescription;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;

	/** ServletContext */
	private ServletContext servletContext;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "/admin/common/main";
	}

	/**
	 * 首页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("systemName", systemName);
		model.addAttribute("systemVersion", systemVersion);
		model.addAttribute("systemDescription", systemDescription);
		model.addAttribute("javaVersion", System.getProperty("java.version"));
		model.addAttribute("javaHome", System.getProperty("java.home"));
		model.addAttribute("osName", System.getProperty("os.name"));
		model.addAttribute("osArch", System.getProperty("os.arch"));
		model.addAttribute("serverInfo", servletContext.getServerInfo());
		model.addAttribute("servletVersion", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());
		return "/admin/common/index";
	}

	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void captcha(String captchaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuilder().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuilder().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		OutputStream outputStream = response.getOutputStream();
		BufferedImage bufferedImage = captchaService.buildImage(captchaId);
		ImageIO.write(bufferedImage, "jpg", outputStream);
		outputStream.flush();
	}

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return "/admin/common/error";
	}

	/**
	 * 权限错误
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		return "/admin/common/unauthorized";
	}

}