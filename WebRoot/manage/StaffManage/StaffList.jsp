<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/page-tags" prefix="p"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-StaffManage-StaffList.js"></script>
<title></title>
</head>
<body>
<s:form action="staffActions" namespace="/actions/staff" theme="simple">
<input type="hidden" name="method"  id="hidMethodName" value="deleteOneStaffInfo" />
<input type="hidden" name="staffID" id="hidStaffID" value="0" />
	<div id="header_nav_container" class="container-fluid">
	    <div class="row-fluid">
		    <div id="header_nav" class="span12">
		    	 <a href="#"><i class="icon-user"></i>用户管理</a>
		    </div>
	   	</div>
	</div>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12"></div>
	</div>
	<div class="row-fluid">
		<div class="span12" style="height:50px">
			<div class="btn-group">
				 <button id="btnNewStaff" class="btn" type="button"><em class="icon-plus"></em>&nbsp;&nbsp;新用户</button>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
				<table  class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>姓名</th>
							<th>联系电话</th>
							<th>邮箱</th>
							<th>角色</th>
							<th>操作</th>
						</tr>
					</thead>
					<p:pages pageIndex="${pageIndex }" pageCount="${pageCount}" styleClass="page" recordCount="${recordCount}" theme="text">
						<tbody>
						<s:iterator value="staffList" status="status">
							<s:if test="%{#status.count!=0}">
								<tr>
									<td><s:property value="name" /></td>
									<td><s:property value="tel" /></td>
									<td><s:property value="email" /></td>
									<td><s:property value="role.name" /></td>
									<td>
										<button operate="resetPwd" class="btn" staffid="${staffID}" type="button" title="重置用户密码"><em class="icon-lock"></em></button>
										<button operate="editStaff" class="btn" staffid="${staffID}" type="button" title="编辑用户信息"><em class="icon-edit"></em></button>
										<button operate="deleteStaff" class="btn" staffid="${staffID}" type="button" title="删除用户相关资料"><em class="icon-remove"></em></button>
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
${actionScript} 
</body>
</html>
