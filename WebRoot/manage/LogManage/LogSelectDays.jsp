<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@ include file="/templets/bootstrap-head.jsp"%>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>jcal/jquery.animate.clip.js"></script>
<script type="text/javascript" src="<%=SiteConfig.DoMain%>jcal/jCal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=SiteConfig.DoMain%>jcal/jCal.css" />
<script type="text/javascript" src="<%=SiteConfig.DoMain%>scripts/manage-LogManage-SelectDays.js"></script>
<style type="text/css">#calendar_top *{margin-left:10px;}</style>
</head>
<body>
	<div id="header_nav_container" class="container-fluid">
	    <div class="row-fluid">
		    <div id="header_nav" class="span12">
		    	 <a href="#"><i class="icon-list-alt"></i>日期选择</a>
		    </div>
	   	</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<form id="getLogsByPage"
					action="<%=SiteConfig.DoMain%>actions/log/newLogActions.action?method=getLogsByPage"
					method="post" class="form-horizontal">
					<input type="hidden" id="startDateValue" name="startTime" value="" />
					<input type="hidden" id="endDateValue" name="endTime" value="" />
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="span12">
								<div class="row-fluid">
									<div class="span12">
										<div class="row-fluid">
											<div class="span1" style="width:40px">
												<button id="btnMyLogs" class="btn" type="button"
													title="日志列表">
													<em class="icon-list-alt"></em>
												</button>
											</div>
											<div id="calendar_top" class="span11">
												<span style="line-height:29px;">尺寸选择</span>
												<select id="selControlSize" class="input-xlarge"
													style="width:100px">
													<option value="20">20x20</option>
													<option value="30">30x30</option>
													<option value="40" selected="selected">40x40</option>
													<option value="50">50x50</option>
													<option value="60">60x60</option>
													<option value="70">70x70</option>
													<option value="80">80x80</option>
												</select>
												<span style="line-height:29px">选择天数</span>
												<select id="selControlDays" class="input-xlarge"
													style="width:60px;">
													<option value="1" selected="selected">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid" style="margin-top:10px">
									<div class="span12">
										<table>
											<tr>
												<td align=left id="calOne" valign=top style="padding:10px; background:#E3E3E3;"></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="/templets/bootstrap-bottom.jsp"%>
</body>
</html>
