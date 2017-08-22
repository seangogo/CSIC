<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>任务管理</title>
</head>
<body>
	<div class="page-header">
		<h1>
			任务管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
				列表
			</small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="#">
			<div class="col-xs-12 col-sm-3">
				<div class="input-group">
					<input type="text" class="form-control search-query"
						style="font-size: 12px;" onblur="$('.form-search').submit();"
						placeholder="任务名称" name="search_LIKE_title"
						value="${param.search_LIKE_title}"> <span
						class="input-group-btn">
						<button type="submit" class="btn btn-info btn-sm">
							<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
							Search
						</button>
					</span>
				</div>
			</div>
		</form>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tasks.content}" var="task">
				<tr>
					<td>${task.title}</td>
					<td>
						<div class="hidden-sm hidden-xs action-buttons">
							<a class="blue" href="${ctx}/task/update/${task.id}"> 
								<i class="ace-icon fa fa-search-plus bigger-130"></i>
							</a> 
							<a class="green" href="${ctx}/task/update/${task.id}"> 
								<i class="ace-icon fa fa-pencil bigger-130"></i>
							</a>
							<a class="red" href="${ctx}/task/delete/${task.id}"> 
								<i class="ace-icon fa fa-trash-o bigger-130"></i>
							</a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页  -->
	<tags:pagination page="${tasks}" paginationSize="5" reqParam="sortType="/>
</body>
</html>
