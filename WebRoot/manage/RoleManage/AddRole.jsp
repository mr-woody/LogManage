<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/page-tags" prefix="p"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain %>scripts/manage-RoleManage-AddRole.js"></script>
</head>
<body>
	<s:form action="roleActions" namespace="/actions/role" cssClass="form-horizontal" theme="simple">
		<input type="hidden" name="method" value="addOneRole" />
		<fieldset>
		<div class="control-group">
		</div>
			<div class="control-group">
				<label class="control-label" for="input01">角色名</label>
				<div class="controls">
					<input name="roleName" type="text" placeholder="请填写角色名称" form-val="empty" class="input-xlarge" maxlength="20">
					<div id="roleNameInfo" class="input-error-info"></div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">权限</label>
				<div class="controls">
					<s:select name="authors" list="authorList" theme="simple" listKey="key" listValue="value" multiple="true" cssClass="input-xlarge" style="width:283px;height:150px">
						
					</s:select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls" style="float:right;margin-right:95px">
					<button id="btnSubmit" class="btn btn-primary" type="submit" style="margin-right:10px">提交</button>
					<button class="btn btn-primary" type="reset">重置</button>
				</div>
			</div>
		</fieldset>
	</s:form>
	<%@ include file="/templets/bootstrap-bottom.jsp"%>
	${actionScript }
</body>
</html>
