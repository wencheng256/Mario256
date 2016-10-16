/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog;

import java.io.Writer;


import cn.mario256.blog.util.SystemUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * FreeMarker异常处理
 *
 * @author TURINGROBOT Team
 * @version 4.0
 */
public class FreeMarkerExceptionHandler implements TemplateExceptionHandler {

    /**
     * 默认模板异常处理
     */
    private static final TemplateExceptionHandler DEFAULT_TEMPLATE_EXCEPTION_HANDLER = TemplateExceptionHandler.HTML_DEBUG_HANDLER;

    /**
     * 模板异常处理
     *
     * @param te  模板异常
     * @param env 环境变量
     * @param out 输出
     */
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        if (SystemUtils.getSetting().getIsDevelopmentEnabled()) {
            DEFAULT_TEMPLATE_EXCEPTION_HANDLER.handleTemplateException(te, env, out);
        } else {
            TemplateExceptionHandler.IGNORE_HANDLER.handleTemplateException(te, env, out);
        }
    }

}