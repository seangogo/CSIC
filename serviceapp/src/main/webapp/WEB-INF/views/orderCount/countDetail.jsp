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
	<link rel="stylesheet"
		href="${ctx}/static/ace/assets/css/daterangepicker.css">
	<div class="page-header">
		<h1>
			<a href="${ctx}/orderCount/repairCountList">工程师订单统计报表</a><small> <i class="ace-icon fa fa-angle-double-right"></i>
				明细
			</small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row order-list-form" style="padding: 5px 0px;">
		<form class="form-search clearInput_" action="${ctx}/orderCount/repairCountDetail/${id}" method="post"
			id="searchForm">
			<div class="col-xs-12 ">
				<div class=" col-md-4" style="margin-top:5px;">
				<!--日期选择-->
				<div class="input-daterange input-group">
					<span class="input-group-addon" style="font-size: inherit;border-left:1px solid #ccc">
						日期范围
					</span>	
					<input type="text" class="form-control search-query search-input" name="start" placeholder="起始时间" id="orderTimeStart"/>
					<span class="input-group-addon">
					<i class="fa fa-exchange"></i>
					</span>
						<input type="text" class="form-control search-query search-input" name="end" placeholder="结束时间" id="orderTimeEnd"/>
				<!--日期选择-->		
				</div>		
			
				
			</div>	
			
			<div class=" col-md-8" style="margin-top:5px;">
				<button type="button" class="btn btn-info clearBtn_">
					<span class="ace-icon fa fa-remove  bigger-115 "></span> 清空
				</button>
				<button type="submit" class="btn btn-info " >
					<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
				</button>
				</div>
			</div>
		</form>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>日期</th>
				<th>工程师名称</th>
				<th>电话</th>
				<th>完成订单</th>
				<th>评价数</th>
				<th>好评数</th>
				<th>差评数</th>
				<th>投诉</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orderCountDetail}" var="orderCount">
				<tr>
					<td><fmt:formatDate value="${orderCount.createTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${orderCount.repairName}</td>
					<td>${orderCount.repairPhone}</td>
					<td class="text-right">${orderCount.orderCount}</td>
					<td class="text-right">${orderCount.comment}</td>
					<td class="text-right">${orderCount.praise}</td>
					<td class="text-right">${orderCount.bad}</td>
					<td class="text-right">${orderCount.complaint}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${ctx}/static/ace/assets/js/date-time/moment.js"></script>
	<script src="${ctx}/static/ace/assets/js/date-time/daterangepicker.js"></script>
	<script type="text/javascript">

		$(function() {
			$("#orderTime").daterangepicker().prev().on(ace.click_event,
					function() {
						$(this).next().focus();
					});
		});
	</script>
</body>
</html>
