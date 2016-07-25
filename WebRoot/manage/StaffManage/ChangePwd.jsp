<%@page import="lms.common.SiteConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-sco/css/scojs.css" ></link>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-sco/js/sco.tooltip.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-StaffManage-ChangePwd.js"></script>
<body>
	<form action="<%=SiteConfig.DoMain%>actions/staff/staffActions.action?method=staffChangePassword" class="form-horizontal" method="post">
		<fieldset>
			<div class="control-group"></div>
			<div class="control-group">
				<label class="control-label" >旧密码</label>
				<div class="controls">
					<input id="txtOldPwd" name="passWord" type="password" form-val="empty"   placeholder="请填写您的旧密码"   maxlength="20" class="input-xlarge"><br/>
					<div class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">新密码</label>
				<div class="controls">
					<input id="txtNewPwd" name="newPassWord" type="password" form-val="empty"   placeholder="请填写您的新密码"  data-trigger="tooltip" data-content="密码最大长度为20个字符。" maxlength="20"  class="input-xlarge"><br/>
					<div class="input-error-info"></div>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label">确认新密码</label>
				<div class="controls">
					<input id="txtSureNewPwd" type="password" form-val="empty"   placeholder="请再次填写您的新密码" maxlength="20" class="input-xlarge"><br/>
					<div class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls" style="float:right;margin-right:95px">
					<button id="btnSubmit" class="btn btn-primary"  type="submit" style="margin-right:10px">提交</button>
					<button class="btn btn-primary" type="reset">重置</button>
				</div>
			</div>
		</fieldset>
	</form>
<%@ include file="/templets/bootstrap-bottom.jsp"%>
${actionScript }
</body>
</html>
