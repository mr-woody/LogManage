<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志管理系统_登录</title>
<%@ include file="templets/bootstrap-head.jsp"%>
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-sco/css/scojs.css" ></link>
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-messenger/css/messenger.css" />
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-messenger/css/messenger-theme-block.css" />
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>css/login.css" />

<script type="text/javascript" src="<%=SiteConfig.DoMain%>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-sco/js/sco.tooltip.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-messenger/js/messenger.min.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/SignIn.js"></script>
</head>
<body>
	<form action="actions/staff/staffActions.action?method=staffSignIn" method="post">
		<div class="main">
			<div class="topline"></div>
			<div class="logoline">
				<img src="<%=SiteConfig.DoMain%>images/logo.gif" class="logo" /> <span>日志管理系统</span>
			</div>
			<div class="inputline">
				<div class="dvusername">
					<i class="icon-user icon-white"></i> <input id="txtLoginName" maxlength="20"
						name="loginName" form-val="empty" val-empty-msg="请输入您的用户名!"  data-trigger="tooltip" data-content="登录名即为您名字的全拼，全部小写。"
						type="text" />
				</div>
				<div class="dvpassword">
					<i class="icon-lock icon-white"></i> <input id="txtPassword" data-trigger="tooltip" data-content="如果您是第一次登录，那么您的密码为初始密码：8个8。"
						name="passWord" type="password" form-val="empty" maxlength="20"/>
				</div>
			</div>
			<div class="bottomline">
				<div class="loginpanel">
					<input id="btnRemeberPwd" type="checkbox"> 记住密码
					<button id="btnLogin" type="submit" class="btn btn-primary"
						style="position: relative; margin-left: 300px">登录</button>
				</div>
			</div>
		</div>
	</form>
	<%@ include file="templets/bootstrap-bottom.jsp"%>
	${actionScript}
</body>
</html>
