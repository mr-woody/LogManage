<%@page import="lms.common.SiteConfig"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>页面未找到</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <style type="text/css">
    	*{margin:0 auto;padding:0;}
    	body{background-color:#2E363F;}
    	.main{font-size:24px;font-weight:bold;position:absolute;top:50%;left:50%;width:480px;height:200px;margin:-100px 0 0 -200px;color:#FFFFFF}
    	.main-center{position:relative;top:30px;text-align: center}
    	.main-center a{color:#ffffff;text-decoration:none}
    </style>
  </head>
  <script>
  	function gotoSignInPage(){
  		window.parent.parent.parent.location.href = "<%=SiteConfig.DoMain%>SignIn.jsp";
  	};
  </script>
  <body>
  	<div class="main">
  		对不起，您请求的页面不存在！
  		<div class="main-center">
 				<a  href="javascript:gotoSignInPage()">返回首页</a>
  		</div>
  	</div>
  </body>
</html>

