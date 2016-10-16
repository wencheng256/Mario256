/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import cn.mario256.blog.util.FreeMarkerUtils;
import cn.mario256.blog.util.SystemUtils;
import cn.mario256.blog.TemplateConfig;
import cn.mario256.blog.service.TemplateService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

import freemarker.template.TemplateException;

/**
 * Service - 模板
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("templateServiceImpl")
public class TemplateServiceImpl implements TemplateService, ServletContextAware {

	/** ServletContext */
	private ServletContext servletContext;

	@Value("${template.loader_path}")
	private String templateLoaderPath;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String read(String templateConfigId) {
		Assert.hasText(templateConfigId);

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		return read(templateConfig);
	}

	public String read(TemplateConfig templateConfig) {
		Assert.notNull(templateConfig);

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + FreeMarkerUtils.process(templateConfig.getRealTemplatePath()));
			File templateFile = new File(templatePath);
			return FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void write(String templateConfigId, String content) {
		Assert.hasText(templateConfigId);

		TemplateConfig templateConfig = SystemUtils.getTemplateConfig(templateConfigId);
		write(templateConfig, content);
	}

	public void write(TemplateConfig templateConfig, String content) {
		Assert.notNull(templateConfig);

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + FreeMarkerUtils.process(templateConfig.getRealTemplatePath()));
			File templateFile = new File(templatePath);
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}