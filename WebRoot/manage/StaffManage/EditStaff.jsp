<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-StaffManage-EditStaff.js"></script>
</head>
<body>
<s:form action="staffActions" namespace="/actions/staff" theme="simple" cssClass="form-horizontal">
	<input type="hidden" name="method" value="editStaffInfo" />
	<input type="hidden" name="staffID" value="${staffInfo.staffID}" />
		<fieldset>
			<div class="control-group"></div>
			<div class="control-group">
				<label class="control-label">姓名</label>
				<div class="controls">
					<input name="staffName" value="${staffInfo.name}" type="text" placeholder="请填写用户姓名" form-val="empty" class="input-xlarge" maxlength="20">
					<div id="staffNameInfo" class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">角色</label>
				<div class="controls">
					<s:select name="staffRole" value="staffInfo.role.roleID" list="addStaffRoles" theme="simple" cssClass="input-xlarge" style="width:285px" listKey="roleID" listValue="name">
					</s:select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls" style="float:right;margin-right:95px">
					<button id="btnSubmit" class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</fieldset>
</s:form>
<%@ include file="/templets/bootstrap-bottom.jsp"%>
${actionScript}
</body>
</html>
