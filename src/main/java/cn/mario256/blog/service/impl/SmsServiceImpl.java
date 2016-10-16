/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service.impl;

import cn.emay.sdk.client.api.Client;
import cn.mario256.blog.service.SmsService;
import cn.mario256.blog.Setting;
import cn.mario256.blog.service.SmsService;
import cn.mario256.blog.util.SystemUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Service - 短信
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;

	/**
	 * 添加短信发送任务
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void addSendTask(final String[] mobiles, final String content, final Date sendTime) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				send(mobiles, content, sendTime);
			}
		});
	}

	/**
	 * 发送短信
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            内容
	 * @param sendTime
	 *            发送时间
	 */
	private void send(String[] mobiles, String content, Date sendTime) {
		Assert.notEmpty(mobiles);
		Assert.hasText(content);

		Setting setting = SystemUtils.getSetting();
		String smsSn = setting.getSmsSn();
		String smsKey = setting.getSmsKey();
		if (StringUtils.isEmpty(smsSn) || StringUtils.isEmpty(smsKey)) {
			return;
		}
		try {
			Client client = new Client(smsSn, smsKey);
			if (sendTime != null) {
				client.sendScheduledSMS(mobiles, content, DateFormatUtils.format(sendTime, "yyyyMMddhhmmss"));
			} else {
				client.sendSMS(mobiles, content, 5);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void send(String[] mobiles, String content, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles);
		Assert.hasText(content);

		if (async) {
			addSendTask(mobiles, content, sendTime);
		} else {
			send(mobiles, content, sendTime);
		}
	}

	public void send(String[] mobiles, String templatePath, Map<String, Object> model, Date sendTime, boolean async) {
		Assert.notEmpty(mobiles);
		Assert.hasText(templatePath);

		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			send(mobiles, content, sendTime, async);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void send(String mobile, String content) {
		Assert.hasText(mobile);
		Assert.hasText(content);

		send(new String[] { mobile }, content, null, true);
	}

	public void send(String mobile, String templatePath, Map<String, Object> model) {
		Assert.hasText(mobile);
		Assert.hasText(templatePath);

		send(new String[] { mobile }, templatePath, model, null, true);
	}

	public long getBalance() {
		Setting setting = SystemUtils.getSetting();
		String smsSn = setting.getSmsSn();
		String smsKey = setting.getSmsKey();
		if (StringUtils.isEmpty(smsSn) || StringUtils.isEmpty(smsKey)) {
			return -1L;
		}
		try {
			Client client = new Client(smsSn, smsKey);
			double result = client.getBalance();
			if (result >= 0) {
				return (long) (result * 10);
			}
		} catch (Exception e) {
		}
		return -1L;
	}

}