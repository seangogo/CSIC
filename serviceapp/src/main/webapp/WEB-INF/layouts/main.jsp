<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<title>IT工程师</title>
<meta name="description" content="with draggable and editable events">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<link rel="stylesheet" href="${ctx}/static/bootstrap/select/bootstrap-select.css">
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/font-awesome.css">

<link rel="stylesheet" href="${ctx}/static/ace/assets/css/colorbox.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/jquery-ui.min.css">

<!-- text fonts -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-fonts.css">

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace.css"
	class="ace-main-stylesheet" id="main-ace-style">

<!--[if lte IE 9]>
			<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-part2.css" class="ace-main-stylesheet" />
		<![endif]-->

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-ie.css" />
		<![endif]-->

<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="${ctx}/static/ace/assets/js/ace-extra.js"></script>
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/ace/assets/js/jquery-ui-1.10.3.full.min.js"></script>
<script src="${ctx}/static/ace/assets/js/jquery.ui.touch-punch.js"></script>
<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="${ctx}/static/ace/assets/js/html5shiv.js"></script>
		<script src="${ctx}/static/ace/assets/js/respond.js"></script>
		<![endif]-->

	<link rel="stylesheet" href="${ctx}/static/css/layout.css">
</head>

<body class="no-skin" style="overflow-x: hidden;">

	<%@ include file="/WEB-INF/layouts/header.jsp"%>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<!-- leftMenu start -->
		<div id="sidebar" class="sidebar responsive">
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'fixed')
				} catch (e) {
				}
			</script>

			<%@ include file="/WEB-INF/layouts/menu.jsp"%>
		</div>
		<!-- leftMenu end -->

		<!-- body content start -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/">Home</a></li>
						<li class="active">
							<c:forEach items="<%=menus%>" var="menu3">
								<c:if test="${not empty index &&  fn:contains(menu3.resUrl, index) }">
									${menu3.name}
								</c:if>
							</c:forEach>
						</li>
					</ul>
					<!-- /.breadcrumb -->
				</div>

				<div class="page-content">
					<sitemesh:body />
				</div>
			</div>
		</div>
		<!-- body content end -->
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>


	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='${ctx}/static/ace/assets/js/jquery.js'>"
								+ "<"+"/script>");
	</script>
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
	<script src="${ctx}/static/ace/assets/js/bootstrap.js"></script>
	<!-- page specific plugin scripts -->
	<!-- ace scripts -->
	<script src="${ctx}/static/ace/assets/js/ace/elements.scroller.js"></script>
	<script src="${ctx}/static/ace/assets/js/ace/ace.js"></script>
	<script src="${ctx}/static/ace/assets/js/ace/ace.sidebar.js"></script>
	<!-- 提示框layer js和css -->
	<script type="text/javascript" src="${ctx}/static/layer/layer.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/static/layer/layer.css" id="skinlayercss">
	<script src="${ctx}/static/bootstrap/select/bootstrap-select.js"></script>
	<script src="${ctx}/static/ace/assets/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
			jQuery(function($) {
				//or change it into a date range picker
				$('.input-daterange').datepicker({autoclose:true,format:"yyyy-mm-dd"});
			});
		</script>
	<script type="text/javascript">
		//加载扩展模块
		layer.config({
			extend : 'extend/layer.ext.js'
		});
	</script>
	<script type="text/javascript">
		$('.selectpicker').selectpicker();
		/*$(".breadcrumb .active").text($(".nav-list .active a").find("span").text());*/
	</script>
</body>
</html>