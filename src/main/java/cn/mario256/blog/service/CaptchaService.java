/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.service;

import java.awt.image.BufferedImage;

import cn.mario256.blog.Setting;

/**
 * Service - 验证码
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public interface CaptchaService {

	/**
	 * 生成验证码图片
	 * 
	 * @param captchaId
	 *            验证ID
	 * @return 验证码图片
	 */
	BufferedImage buildImage(String captchaId);

	/**
	 * 验证码验证
	 * 
	 * @param captchaType
	 *            验证码类型
	 * @param captchaId
	 *            验证ID
	 * @param captcha
	 *            验证码(忽略大小写)
	 * @return 验证码验证是否通过
	 */
	boolean isValid(Setting.CaptchaType captchaType, String captchaId, String captcha);

}