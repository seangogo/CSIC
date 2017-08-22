<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>工程师提成报表</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<link rel="stylesheet"	href="${ctx}/static/ace/assets/css/daterangepicker.css">
	<div class="page-header">
		<h1>
			<a href="${ctx}/engineerCommission/list">工程师提成报表</a>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row order-list-form" style="padding: 5px 0px;">
		<form class="form-search clearInput_" action="${ctx}/engineerCommission/list" method="post"
			id="searchForm">
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
							<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
						</button>
					<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
				</div>

		</form>
	</div>
	<shiro:hasPermission name="engineerCommission:export">
			<a class="btn btn-white btn-info btn-bold blue" href="${ctx}/engineerCommission/export">
				<i class="ace-icon fa fa-file-excel-o bigger-120 blue">
				</i>Export
			</a>
	</shiro:hasPermission>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th >单据编号</th>
				<th >工程师</th>
				<th>材料费</th>
				<th>改配费</th>
				<th>搬运费</th>
				<th>安装费</th>
				<th>外协费</th>
				<th>交通费</th>
				<th>送货费</th>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${engineerCommissionList}" var="orderCount">
				<tr>
					<td>${orderCount.order_num}</td>
					<td>${orderCount.user_name}</td>
					<td class="text-right">${orderCount.clf}</td>
					<td class="text-right">${orderCount.gpf}</td>
					<td class="text-right">${orderCount.byf}</td>
					<td class="text-right">${orderCount.azf}</td>
					<td class="text-right">${orderCount.wxf}</td>
					<td class="text-right">${orderCount.jtf}</td>
					<td class="text-right">${orderCount.shf}</td>
					<td class="text-right">${orderCount.hj}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${ctx}/static/ace/assets/js/date-time/moment.js"></script>
	<script src="${ctx}/static/ace/assets/js/date-time/daterangepicker.js"></script>
	<script src="${ctx}/static/js/ie.js"></script>
	<style type="text/css">
	.sp{float: left;margin-top:8px;margin-left: -115px;}
	</style>
</body>
</html>
