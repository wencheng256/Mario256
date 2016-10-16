/*
 * Copyright 2005-2015 tuling123.com. All rights reserved.
 * Support: http://www.tuling123.com
 * License: http://www.tuling123.com
 */
package cn.mario256.blog.controller.admin;

import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.mario256.blog.util.SystemUtils;
import cn.mario256.blog.Message;
import cn.mario256.blog.Setting;
import cn.mario256.blog.entity.Admin;
import cn.mario256.blog.service.AdminService;
import cn.mario256.blog.service.RSAService;
import cn.mario256.blog.util.WebUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 管理员登录
 *
 * @author TURINGROBOT Team
 * @version 4.0
 */
@Controller("adminLoginController")
@RequestMapping("/admin/login")
public class LoginController extends BaseController {

    @Resource(name = "rsaServiceImpl")
    private RSAService rsaService;
    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    /**
     * 登录
     */
    @RequestMapping
    public String index(HttpServletRequest request, ModelMap model) {
        String loginToken = WebUtils.getCookie(request, Admin.LOGIN_TOKEN_COOKIE_NAME);
        if (!StringUtils.equalsIgnoreCase(loginToken, adminService.getLoginToken())) {
            return "redirect:/";
        }
        if (adminService.isAuthenticated()) {
            return "redirect:common/main.jhtml";
        }
        Message failureMessage = null;
        String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (StringUtils.isNotEmpty(loginFailure)) {
            if (loginFailure.equals("IncorrectCaptchaException")) {
                failureMessage = Message.error("admin.captcha.invalid");
            } else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {
                failureMessage = Message.error("admin.login.unknownAccount");
            } else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {
                failureMessage = Message.error("admin.login.disabledAccount");
            } else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {
                failureMessage = Message.error("admin.login.lockedAccount");
            } else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {
                Setting setting = SystemUtils.getSetting();
                if (ArrayUtils.contains(setting.getAccountLockTypes(), Setting.AccountLockType.admin)) {
                    failureMessage = Message.error("admin.login.accountLockCount", setting.getAccountLockCount());
                } else {
                    failureMessage = Message.error("admin.login.incorrectCredentials");
                }
            } else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {
                failureMessage = Message.error("admin.login.authentication");
            }
        }
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        model.addAttribute("captchaId", UUID.randomUUID().toString());
        model.addAttribute("failureMessage", failureMessage);
        return "/admin/login/index";
    }

}