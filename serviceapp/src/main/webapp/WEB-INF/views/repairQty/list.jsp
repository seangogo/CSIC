<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>订单统计报表</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<link rel="stylesheet" href="${ctx}/static/ace/assets/css/daterangepicker.css">
	<div class="page-header">
		<h1>
			<a href="${ctx}/repairQty/list">设备维修统计报表</a>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row order-list-form" style="padding: 5px 0px;">
		<form class="form-search clearInput_" action="${ctx}/repairQty/list" method="post"
			id="searchForm">
			<div class="col-xs-12">
				<div class=" col-md-4" style="margin-top:5px;">
				<!--日期选择-->
				<div class="input-daterange input-group">
					<span class="input-group-addon" style="font-size: inherit;border-left:1px solid #ccc">
						日期范围
					</span>	
					<input type="text" class="form-control search-query search-input" name="orderTimeStart" placeholder="起始时间" id="orderTimeStart" value="${orderTimeStart}"/>
					<span class="input-group-addon">
					<i class="fa fa-exchange"></i>
					</span>
						<input type="text" class="form-control search-query search-input" name="orderTimeEnd" placeholder="结束时间" id="orderTimeEnd" value="${orderTimeEnd}"/>
				<!--日期选择-->		
				</div>		

			</div>	
				<div class="col-md-8" style="margin-top:5px;">
					<input type="text" class="pull-left sform-control search-query search-input" placeholder="工程师名称" name="repairName" value="${repairName}">
					<button type="submit" class="btn btn-info">
						<span class="ace-icon fa fa-search bigger-115"></span> 搜索
					</button>
					<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
				</div>
			</div>
		</form>
	</div>
	<shiro:hasPermission name="repairQty:export">
		<a class="blue btn btn-white btn-info btn-bold" href="${ctx}/repairQty/export">
			<i class="ace-icon fa fa-file-excel-o bigger-120 blue"></i>Export
		</a>
	</shiro:hasPermission>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工程师名称</th>
				<th>数量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${repairQtyList}" var="orderCount">
				<tr>
					<td>${orderCount.user_name}</td>
					<td>${orderCount.orderCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${ctx}/static/ace/assets/js/date-time/moment.js"></script>
	<script src="${ctx}/static/ace/assets/js/date-time/daterangepicker.js"></script>
	<script src="${ctx}/static/js/ie.js"></script>
	<script type="text/javascript">
		
		$(function() {
			$("#orderTime").daterangepicker().prev().on(ace.click_event,
					function() {
						$(this).next().focus();
					});
		});
	</script>
	<style type="text/css">
	.sp{float: left;margin-top:8px;margin-left: -115px;}
	</style>
</body>
</html>
