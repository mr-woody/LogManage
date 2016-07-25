<%@page import="lms.common.SiteConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-StaffManage-StaffInfo.js"></script>
</head>
<body>
	<s:form action="staffActions.action" namespace="/actions/staff" cssClass="form-horizontal" theme="simple">
	<input type="hidden" name="method" value="completeStaffInfo"/>
		<fieldset>
			<div class="control-group"></div>
			<div class="control-group">
				<label class="control-label" >联系电话</label>
				<div class="controls">
					<input type="text" name="tel" value="${staffInfo.tel}" msg-ctrl-id="phone-error" form-val="phone" val-phone-msg="手机格式不正确!"  placeholder="请填写您的联系电话" class="input-xlarge"  maxlength="11" />
					<div id="phone-error" class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >紧急电话</label>
				<div class="controls">
					<input type="text" name="crashTel" value="${staffInfo.crashTel}" placeholder="请填写您的紧急联系人电话" class="input-xlarge"  maxlength="11" />
					<p class="help-block" style="font-size:12px">您身边的亲属、爱人联系方式</p>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >电子邮箱</label>
				<div class="controls">
					<input type="text" name="email" value="${staffInfo.email}" msg-ctrl-id="email-error" form-val="email" val-email-msg="电子邮箱格式不正确!" placeholder="请填写您的邮箱地址" class="input-xlarge" maxlength="50" />
					<div id="email-error" class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">联系地址</label>
				<div class="controls">
					<div class="textarea">
						<textarea name="contactAddress"
							style="margin: 0px; width: 271px; height: 49px;" form-val="maxlength(200)" >${staffInfo.contactAddress}</textarea>
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label"></label>
				<div class="controls">
					<button id="btnSubmit" type="submit" class="btn btn-primary">提交</button>
				</div>
			</div>
		</fieldset>
	</s:form>
	<%@ include file="/templets/bootstrap-bottom.jsp"%>
	${actionScript}
</body>
</html>
