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
			<a href="${ctx}/orderCount/repairCountList">工程师订单统计报表</a>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row order-list-form" style="padding: 5px 0px;">
		<form class="form-search clearInput_" action="${ctx}/orderCount/repairCountList" method="post" id="searchForm">
				<div class=" col-md-4" style="margin-top:5px;">
					<!--<div class="input-group input-group-sm">-->
						<!--<input type="text" placeholder="发单日期" id="orderTime" name="orderTime" value="${orderTime }"
							class="form-control hasDatepicker" readonly="readonly"> <span
							class="input-group-addon"> <i
							class="ace-icon fa fa-calendar"></i>
						</span>-->
						
							
						<!--<script type="text/javascript">
							jQuery(function($) {
							
								$( "#orderTimeStart" ).datepicker({
									showOtherMonths: true,
									selectOtherMonths: false,
								})
								$( "#orderTimeEnd" ).datepicker({
									showOtherMonths: true,
									selectOtherMonths: false,
								})
							});
						</script>-->
						<!--<div class="input-group input-group-sm">
							<input type="text" class="form-control" placeholder="开始日期" id="orderTimeStart" />
							<span class="input-group-addon"><i  class="ace-icon fa fa-calendar"></i></span>
							<input type="text" class="form-control" placeholder="结束日期" id="orderTimeEnd" />
							<span class="input-group-addon"><i  class="ace-icon fa fa-calendar"></i></span>
						</div>-->
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
							<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
						</button>
					<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
				</div>

			
		</form>
	</div>
	<shiro:hasPermission name="orderCount:export">
		<a class="blue btn btn-white btn-info btn-bold" href="${ctx}/orderCount/export">
				<i class="ace-icon fa fa-file-excel-o bigger-120 blue"></i>Export
		</a>
	</shiro:hasPermission>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th >工程师名称</th>
				<th>完成订单</th>
				<th>评价数</th>
				<th>好评数</th>
				<th>差评数</th>
				<th>投诉</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderCountList}" var="orderCount">
				<tr>
					<td>${orderCount.repairName}</td>
					<td class="text-right">${orderCount.orderCount}</td>
					<td class="text-right">${orderCount.comment}</td>
					<td class="text-right">${orderCount.praise}</td>
					<td class="text-right">${orderCount.bad}</td>
					<td class="text-right">${orderCount.complaint}</td>
					<td><a class="blue" href="${ctx}/orderCount/repairCountDetail/${orderCount.repair_id}?orderTime=${orderTime}" > <i
									class="ace-icon fa fa-search-plus bigger-115"></i>
									<span class="hidden-xs">查看</span>
						</a></td>
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
	.sp{float: left;margin-top:8px;margin-left: -118px;}
	</style>
</body>
</html>
