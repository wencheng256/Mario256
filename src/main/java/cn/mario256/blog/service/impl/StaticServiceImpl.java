/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import cn.mario256.blog.TemplateConfig;
import cn.mario256.blog.TemplateConfig;
import cn.mario256.blog.service.StaticService;
import cn.mario256.blog.util.SystemUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Service - 静态化
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("staticServiceImpl")
public class StaticServiceImpl implements StaticService, ServletContextAware {

	/** Sitemap最大地址数 */
	private static final Integer SITEMAP_MAX_SIZE = 10000;

	/** ServletContext */
	private ServletContext servletContext;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 设置ServletContext
	 * 
	 * @param servletContext
	 *            ServletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional(readOnly = true)
	public int generate(String templatePath, String staticPath, Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		Writer writer = null;
		try {
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDir = staticFile.getParentFile();
			if (staticDir != null) {
				staticDir.mkdirs();
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staticFile), "UTF-8"));
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}



	@Transactional(readOnly = true)
	public int generateIndex() {
		/*TemplateConfig templateConfig = SystemUtils.getTemplateConfig("index");
		return generate(templateConfig.getRealTemplatePath(), templateConfig.getRealStaticPath(), null);*/
		return 0;
	}

	@Override
	public int generateSitemap() {
		return 0;
	}


	@Transactional(readOnly = true)
	public int generateOther() {
		int generateCount = 0;
		TemplateConfig shopCommonJsTemplateConfig = SystemUtils.getTemplateConfig("shopCommonJs");
		TemplateConfig adminCommonJsTemplateConfig = SystemUtils.getTemplateConfig("adminCommonJs");
//		generateCount += generate(shopCommonJsTemplateConfig.getRealTemplatePath(), shopCommonJsTemplateConfig.getRealStaticPath(), null);
		generateCount += generate(adminCommonJsTemplateConfig.getRealTemplatePath(), adminCommonJsTemplateConfig.getRealStaticPath(), null);
		return generateCount;
	}

	@Transactional
	public int generateAll() {
		int generateCount = 0;
		generateCount += generateIndex();
		generateCount += generateSitemap();
		generateCount += generateOther();
		return generateCount;
	}

	@Transactional(readOnly = true)
	public int delete(String staticPath) {
		if (StringUtils.isEmpty(staticPath)) {
			return 0;
		}
		File staticFile = new File(servletContext.getRealPath(staticPath));
		return FileUtils.deleteQuietly(staticFile) ? 1 : 0;
	}





	@Transactional(readOnly = true)
	public int deleteIndex() {
		TemplateConfig templateConfig = SystemUtils.getTemplateConfig("index");
		return delete(templateConfig.getRealStaticPath());
	}

	@Transactional(readOnly = true)
	public int deleteOther() {
		int deleteCount = 0;
		TemplateConfig shopCommonJsTemplateConfig = SystemUtils.getTemplateConfig("shopCommonJs");
		TemplateConfig adminCommonJsTemplateConfig = SystemUtils.getTemplateConfig("adminCommonJs");
		deleteCount += delete(shopCommonJsTemplateConfig.getRealStaticPath());
		deleteCount += delete(adminCommonJsTemplateConfig.getRealStaticPath());
		return deleteCount;
	}

	/**
	 * SitemapUrl
	 * 
	 * @author TURINGROBOT Team
	 * @version 4.0
	 */
	public static class SitemapUrl {

		/**
		 * 更新频率
		 */
		public enum Changefreq {

			/** 经常 */
			always,

			/** 每小时 */
			hourly,

			/** 每天 */
			daily,

			/** 每周 */
			weekly,

			/** 每月 */
			monthly,

			/** 每年 */
			yearly,

			/** 从不 */
			never
		}

		/** 链接地址 */
		private String loc;

		/** 最后修改日期 */
		private Date lastmod;

		/** 更新频率 */
		private Changefreq changefreq;

		/** 权重 */
		private float priority;

		/**
		 * 获取链接地址
		 * 
		 * @return 链接地址
		 */
		public String getLoc() {
			return loc;
		}

		/**
		 * 设置链接地址
		 * 
		 * @param loc
		 *            链接地址
		 */
		public void setLoc(String loc) {
			this.loc = loc;
		}

		/**
		 * 获取最后修改日期
		 * 
		 * @return 最后修改日期
		 */
		public Date getLastmod() {
			return lastmod;
		}

		/**
		 * 设置最后修改日期
		 * 
		 * @param lastmod
		 *            最后修改日期
		 */
		public void setLastmod(Date lastmod) {
			this.lastmod = lastmod;
		}

		/**
		 * 获取更新频率
		 * 
		 * @return 更新频率
		 */
		public Changefreq getChangefreq() {
			return changefreq;
		}

		/**
		 * 设置更新频率
		 * 
		 * @param changefreq
		 *            更新频率
		 */
		public void setChangefreq(Changefreq changefreq) {
			this.changefreq = changefreq;
		}

		/**
		 * 获取权重
		 * 
		 * @return 权重
		 */
		public float getPriority() {
			return priority;
		}

		/**
		 * 设置权重
		 * 
		 * @param priority
		 *            权重
		 */
		public void setPriority(float priority) {
			this.priority = priority;
		}

	}

}