<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/page-tags" prefix="p"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain %>scripts/manage-RoleManage-RoleList.js"></script>
</head>
<body>
	<s:form action="roleActions" namespace="/actions/role" theme="simple">
		<input type="hidden" name="method" value="deleteOneRole" />
		<input type="hidden" name="roleID" id="hidDeleteRoleID" value="0" />
		<div id="header_nav_container" class="container-fluid">
	    <div class="row-fluid">
		    <div id="header_nav" class="span12">
		    	 <a href="#"><i class="icon-eye-open"></i>角色管理</a>
		    </div>
	   	</div>
	</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12" style="height:50px">
					<div class="btn-group">
						<button id="btnNewRole" class="btn" type="button">
							<em class="icon-plus"></em>&nbsp;&nbsp;新建角色
						</button>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>编号</th>
								<th>角色名</th>
								<th>权限数</th>
								<th>人数</th>
								<th>操作</th>
							</tr>
						</thead>
						<p:pages pageIndex="${pageIndex}" pageCount="${pageCount}" styleClass="page" recordCount="${recordCount}" theme="text">
						<tbody>
							<s:iterator value="roleInfoList" status="status">
							<s:if test="%{#status.count!=0}">
								<tr>
									<td><s:property value="roleID" /></td>
									<td><s:property value="name" /></td>
									<td><s:property value="authors.split(';').length" /></td>
									<td><s:property value="staffs.size" /></td>
									<td>
										<button class="btn" operate="editRole" roleid="${roleID}" type="button" title="编辑角色权限"><em class="icon-edit"></em></button>
										<button class="btn" operate="deleteRole" roleid="${roleID}" type="button"><em class="icon-remove" title="删除角色"></em></button>
									</td>
								</tr>
							</s:if>
						</s:iterator>
						</tbody>
					</table>
					</p:pages>
				</div>
			</div>
		</div>
	</s:form>
	<%@ include file="/templets/bootstrap-bottom.jsp"%>
</body>
</html>
