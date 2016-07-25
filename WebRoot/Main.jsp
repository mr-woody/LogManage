<%@page import="lms.common.SiteConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志管理系统</title>
<%@ include file="templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-sco/css/scojs.css"></script>
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-messenger/css/messenger.css" />
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-messenger/css/messenger-theme-block.css" />
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>css/main.css" />
<!--[if IE]>
	<link rel="stylesheet" href="<%=SiteConfig.DoMain%>css/main-ie.css" type="text/css"></link>
<![endif]-->
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-sco/js/sco.modal.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-sco/js/sco.confirm.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-messenger/js/messenger.min.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>js/Modal.Windows.OperateConfirm.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>js/Modal.Windows.StaffManage.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>js/Modal.Windows.RoleManage.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/Main.js"></script>
</head>
<body>
	<div class="header">
		<div class="login_info">
			<img src="<%=SiteConfig.DoMain%>images/logo.gif" class="logo" /><span>日志管理系统</span>
		</div>
		<div class="menusplitOne"></div>
		<div class="menusplitTwo"></div>
		<div class="menusplitThree"></div>
		<div class="menusplitFour"></div>
		<div class="headmenu">
			<ul class="nav nav-pills">
				</li>
				<li><a id="btnStaffInfo" href="#"><i class="icon-list-alt icon-white"></i><span>个人资料</span>
				</a>
				</li>
				<li><a id="btnEditPwd" href="#"><i class="icon-lock icon-white"></i><span>修改密码</span>
				</a>
				</li>
				<li><a href="actions/staff/staffActions.action?method=staffSignOut"><i class="icon-ban-circle icon-white"></i><span>安全退出</span>
				</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="left_menu">
		<div class="well" style="padding: 8px 0;">
			<ul class="nav nav-list">
			<s:iterator value="staffAuthors" status="status">
				<s:if test="%{#status.count!=0}">
					<li>
					<a 
						author-code="<s:property value="code" />" 
						url="<s:property value="url" />" 
						href="javascript:void(0)">
						<i class="<s:property value="icon" />"></i>
						<span><s:property value="name" /></span>
					</a></li>
					<li class="divider"></li>
				</s:if>
			</s:iterator>
			</ul>
		</div> 
	</div>
	<div class="main">
	<s:iterator value="staffAuthors" status="status">
		<s:if test="%{#status.getIndex()==0}">
			<iframe id="frmContent" style="width:100%;" src="<%=SiteConfig.DoMain%><s:property value="url" />"  frameborder="0"></iframe>	
		</s:if>
	</s:iterator>
	</div>
	<%@ include file="templets/bootstrap-bottom.jsp"%>
</body>
</html>
