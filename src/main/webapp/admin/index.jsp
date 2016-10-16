<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="cn.mario256.blog.entity.Admin"%>
<%@page import="cn.mario256.blog.service.AdminService"%>
<%@page import="cn.mario256.blog.util.SpringUtils"%>
<%@page import="cn.mario256.blog.util.WebUtils"%>
<%
String base = request.getContextPath();
ApplicationContext applicationContext = SpringUtils.getApplicationContext();
if (applicationContext != null) {
	AdminService adminService = SpringUtils.getBean("adminServiceImpl", AdminService.class);
	WebUtils.addCookie(request, response, Admin.LOGIN_TOKEN_COOKIE_NAME, adminService.getLoginToken());
	response.sendRedirect(base + "/admin/login.jhtml");
	return;
} else {
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>提示信息 - Powered By TURINGROBOT</title>
<meta name="author" content="TURINGROBOT Team" />
<meta name="copyright" content="TURINGROBOT" />
<link href="<%=base%>/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=base%>/resources/admin/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<fieldset>
		<legend>系统出现异常</legend>
		<p>请检查TURINGROBOT程序是否已正确安装 [<a href="<%=base%>/install/">点击此处进行安装</a>]</p>
		<p>
			<strong>提示: TURINGROBOT安装完成后必须重新启动WEB服务器</strong>
		</p>
	</fieldset>
</body>
</html>
<%
}
%>