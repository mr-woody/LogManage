<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="/page-tags" prefix="p"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-datetimepicker/css/datetimepicker.css" />
<link rel="stylesheet" href="<%=SiteConfig.DoMain%>bootstrap-select/css/bootstrap-select.css">
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-LogManage-LogList.js"></script>   
</head>
<body>
	<s:form action="newLogActions" namespace="/actions/log" theme="simple">
		<input type="hidden" name="method" value="getLogsByPage" />
		<input type="hidden" id="hidSelectDate" name="selectDate" value="${selectDate}" />
		<input type="hidden" id="hidAppVersionData" value="${actionModelDetail.appVersion}" />
		<div id="header_nav_container" class="container-fluid">
			<div class="row-fluid">
				<div id="header_nav" class="span12">
					<a
						href="<%=SiteConfig.DoMain%>actions/log/newLogActions.action?method=initializeSelectDays"
						class="nav_prep"><i class="icon-list-alt"></i>日期选择</a> <a href="#">日志列表</a>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
				<h3>
					<s:property
						value="@lms.common.ToolUtil@doGetLogListTitle(actionModelDetail.startTime)" />
						<s:if test="%{@dev.frame.util.StringUtil@isNullOrEmpty(actionModelDetail.endTime)}"></s:if>
						<s:else>-<s:property value="@lms.common.ToolUtil@doGetLogListTitle(actionModelDetail.endTime)" /></s:else>
				</h3>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">搜索条件</h3>
				</div>
				<div class="panel-body">
					<div id="txtStartTime" class="input-append date form_datetime" style="float:left;">
						<span class="div_input">查询日期：</span>
						<input id="startDateValue" size="16" name="startTime" style="width:185px" type="text" form-val="empty" placeholder="开始时间" value="${actionModelDetail.startTime}" />
						<span class="add-on"><i class="icon-remove"></i></span>
   						<span class="add-on"><i class="icon-th"></i></span>
					</div>
					<span class="div_input" style="float:left;"> ~ </span>
					<div id="txtEndTime" class="input-append date form_datetime" style="float:left;">
						<input id="endDateValue" size="16" name="endTime" style="width:185px" type="text"
							value="${actionModelDetail.endTime}" placeholder="结束时间"/>
						<span class="add-on"><i class="icon-remove"></i></span>
   						<span class="add-on"><i class="icon-th"></i></span>
					</div>
					<div class="input-append" style="clear:both;">
						<span class="div_input" style="line-height: 30px;">应用版本：</span>
						<select class="selectpicker show-tick" name="appVersion"  multiple data-hide-disabled="true" data-size="4">
							<option value="1.8.2">1.8.2</option>
						    <option value="2.0.0">2.0.0</option>
						    <option value="2.0.1">2.0.1</option>
						    <option value="2.0.2">2.0.2</option>
						    <option value="2.0.3">2.0.3</option>
						    <option value="2.0.4">2.0.4</option>
						    <option value="2.1.0">2.1.0</option>
						</select>
						<p class="help-block" style="clear:both;"></p>
					</div>
					<div class="input-append">
						<span class="div_input">错误信息：</span>
						<input id="errorDataID" name="errorData" style="width:60%;" type="text" class="input-xlarge" value="${actionModelDetail.errorData }"/>
						<div class="div_center"><button class="btn btn-block" id="btnSubmit" style="width:100px;" type="submit">搜索</button></div>
						<p class="help-block" style="clear:both;"></p>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th  style="width:80px;text-align: center;">创建时间</th>
								<th  style="width:100px">日志类型</th>
								<th  style="width:550px">异常数据</th>
								<th  style="width:80px">请求方法</th>
								<th  style="width:80px">用户UID</th>
								<th  style="width:70px">用户渠道</th>
								<th  style="width:70px">设备平台</th>
								<th style="width:100px">返回提示信息</th>
								<th style="width:70px">使用时间</th>
								<th  style="width:70px">APP版本</th>
								<th style="width:80px">厂商</th>
								<th style="width:100px">手机型号</th>
								<th  style="width:60px">网络</th>
								<th style="width:90px">屏幕(宽x高)</th>
								<th style="width:130px">系统版本</th>
								<th style="width:80px">CPU个数</th>
								<th style="width:80px">空闲内存</th>
								<th style="width:100px">可使用总内存</th>
							</tr>
						</thead>
						<p:pages pageIndex="${pageIndex }" pageCount="${pageCount}"
							styleClass="page" recordCount="${recordCount}" theme="text">
							<tbody>
								<s:iterator id="log" value="logList" status="status">
									<s:if test="%{#status.count!=0}">
										<tr>
											<td class="warptd">
											<a text-warp="class,warptd" style="text-align: center;" href="#"
												operate="workContentDetail" logid="${logID}"> <s:property
														value="@lms.common.ToolUtil@convertTimeStampLine(ct,'yyyy-MM-dd hh:mm:ss')"  escape="false"/> </a></td>
											<td ><s:property value="@lms.common.Utils@getErrorType(type)" />
											</td>
											<td>
												<div class="panel-heading">
													<a data-toggle="collapse"
														logid="${logID}" href="#errorData_${logID}"> <s:property
															value="@lms.common.Utils@getLimitLengthString(errorData,90)" />
													</a>
												</div>
												<div id="errorData_${logID}" class="panel-collapse collapse">
													<div class="panel-body">
														<s:property value="#log.errorData" />
													</div>
												</div></td>
											<td ><s:if
													test="%{@dev.frame.util.StringUtil@isNullOrEmpty(requestMethod)}">get</s:if>
												<s:else>post</s:else>
											</td>
											<td ><s:property value="#log.uid" />
											</td>
											<td ><s:property value="#log.channel" />
											</td>
											<td ><s:if test="%{#log.devicePlatform!=1}">Android</s:if> <s:else>Ios</s:else>
											</td>
											<td><s:if test="%{#log.deviceMessage=='查看信息详情'}">
											<a text-warp="class,warptd" style="text-align: center;" href="#"
												operate="deviceMessageDetail" messageId="${messageId}"> <s:property value="#log.deviceMessage" escape="false"/> </a></s:if> <s:else><s:property value="#log.deviceMessage" /></s:else>
											</td>
											<td><s:property value="#log.useTime" />
											</td>
											<td ><s:property value="#log.appVersion" />
											</td>
											<td><s:property value="#log.deviceBrand" />
											</td>
											<td><s:property value="#log.deviceModel" />
											</td>
											<td ><s:property value="#log.access" />
											</td>
											<td><s:property value="#log.width" /> x <s:property
													value="#log.height" />
											</td>
											<td>
												<div class="panel-heading">
													<a data-toggle="collapse" logid="${logID}" href="#osVersion_${logID}"> <s:property
															value="@lms.common.Utils@getLimitLengthString(osVersion,9)" />
													</a>
												</div>
												<div id="osVersion_${logID}" class="panel-collapse collapse">
													<div class="panel-body">
														<s:property value="#log.osVersion" />
													</div>
												</div></td>
											<td><s:property value="#log.cpuCount" />
											</td>
											<td><s:property value="#log.freeMemory" />
											</td>
											<td><s:property value="#log.totalMemory" />
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
