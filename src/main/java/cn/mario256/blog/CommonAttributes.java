/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog;

/**
 * 公共参数
 * 
 * @author TURINGROBOT Team
 * @version 4.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** turing.xml文件路径 */
	public static final String TURING_XML_PATH = "/turing.xml";

	/** turing.properties文件路径 */
	public static final String TURING_PROPERTIES_PATH = "/turing.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}