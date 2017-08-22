<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<title>IT工程师</title>
<meta name="description" content="User login page">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/bootstrap.css">
<link rel="stylesheet"
	href="${ctx}/static/ace/assets/css/font-awesome.css">

<!-- text fonts -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-fonts.css">

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace.css">

<!--[if lte IE 9]>
			<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-part2.css" />
		<![endif]-->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-rtl.css">

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-ie.css" />
		<![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${ctx}/static/ace/assets/js/html5shiv.js"></script>
		<script src="${ctx}/static/ace/assets/js/respond.js"></script>
		<![endif]-->

<style type="text/css">
.alert-error {
	color: #b94a48;
	background-color: #f2dede;
	border-color: #eed3d7;
	border-radius: 4px;
	padding: 8px 35px 8px 14px;
	margin-bottom: 20px;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	border: 1px solid #fbeed5;
	width: 100%;
}
</style>
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row" style="    margin-top: 40px;">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container" id="login-container">
						<div class="center">
							<h1>
								<i class="ace-icon fa fa-leaf green"></i> <span class="red"> I T </span>
								<span class="white" id="id-text2">服务工程师</span>
							</h1>
							<h4 class="blue" id="id-company-text">© 武汉大海信息系统科技有限公司</h4>
						</div>
						<div class="space-6"></div>
						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h5 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee green"></i> 请输入您的信息
										</h5>
										<%
											String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
											String customError = (String) request.getSession()
													.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
											if (customError != null) {
										%>
										
										<div class="alert alert-error input-medium controls">
											<button class="close" data-dismiss="alert">×</button>
											<%=customError%>
										</div>
										<%
											} else if (error != null) {
										%>
										<div class="alert alert-error input-medium controls">
											<button class="close" data-dismiss="alert">×</button>
											密码错误，请重试 
										</div>
										<%
											}
										%>

										<div class="space-6"></div>

										<form id="loginForm" action="${ctx}/login" method="post"
											class="form-horizontal">
											<fieldset>
												<label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="text" class="form-control" id="username" name="username" value="${username}" placeholder="用户名"><i class="ace-icon fa fa-user"></i>
		</span>
												</label> 
											<label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="password" class="form-control" id="password" name="password" placeholder="密码"> <i class="ace-icon fa fa-lock"></i></span></label>
			<script>
					$().ready(function(){
						
						if (screen && screen.width < 920) {		
							var wHeight=$(window).height();
							$(window).resize(function(){
								var nwHeight=$(window).height();
								if(nwHeight<wHeight){
									$("#login-container").css("margin-top","-50px");	
								}
								else{
									$("#login-container").css("margin-top","0px");	
								}
								
							})
						}
					})
			</script>
				


												<div class="space"></div>

												<div class="clearfix">
													<label class="inline"> <input type="checkbox" class="ace" id="rememberMe" name="rememberMe"> <span
class="lbl"> 记住我</span></label><button type="submit" class="width-35 pull-right btn btn-sm btn-primary"><i class="ace-icon fa fa-key"></i> <span
class="bigger-110">登录</span></button>
												</div>
												<div class="space-4"></div>
											</fieldset>
										</form>

									</div>
									<!-- /.widget-main -->
								</div>
								<!-- /.widget-body -->
							</div>
							<!-- /.login-box -->
						</div>
						<!-- /.position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='${ctx}/static/ace/assets/js/jquery.js'>"
								+ "<"+"/script>");
	</script>
	<script src="${ctx}/static/ace/assets/js/jquery.js"></script>
	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${ctx}/static/ace/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='${ctx}/static/ace/assets/js/jquery.mobile.custom.js'>"
							+ "<"+"/script>");
	</script>
	<script
		src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"
		type="text/javascript"></script>
		<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"
	type="text/javascript"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$("#username").focus();
		$('body').attr('class', 'login-layout');
		$('#id-text2').attr('class', 'white');
		$('#id-company-text').attr('class', 'blue');
		$("#loginForm").validate();
	</script>
</body>
</html>
