/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Exception - 错误验证码
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public class IncorrectCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = -2452816550297381913L;

}