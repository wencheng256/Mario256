/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

/**
 * Filter - 限制访问
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public class AccessDeniedFilter implements Filter {

	/** 错误消息 */
	private static final String ERROR_MESSAGE = "Access denied!";

	/**
	 * 初始化
	 * 
	 * @param filterConfig
	 *            过滤器配置
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * 执行
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @param filterChain
	 *            FilterChain
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
	}

	/**
	 * 销毁
	 */
	public void destroy() {
	}

}