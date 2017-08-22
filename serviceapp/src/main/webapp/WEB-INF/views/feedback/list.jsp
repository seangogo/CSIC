<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>意见反馈</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<style>
		.feedback-content{
		    text-overflow: ellipsis;
		    white-space: nowrap;
		    overflow: hidden;
		    width: 350px;
		    margin: 0px;
		}
	</style>
	<div class="page-header">
		<h1>意见反馈</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/system/feedback" method="post">
			<div class="col-xs-12 ">
				<div class="input-group pull-left" style="margin-top: 5px;">
					<input type="text" class="form-control search-query search-input"
						placeholder="手机号码" name="loginName" value="${loginName}">
					<span class="input-group-btn"> </span> <input type="text" id="userName"
						class="form-control search-query search-input" placeholder="用户名"
						name="userName" value="${userName}"> <span
						class="input-group-btn"> </span>
				</div>
				<button type="submit" class="btn btn-info" style="margin-top:5px">
					<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
				</button>
				<button type="button" class="btn btn-info "  style="margin-top:5px" onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
			</div>
		</form>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>用户手机</th>
				<th>设备型号</th>
				<th>设备系统</th>
				<th>设备分辨率</th>
				<th>设备网络</th>
				<th>APP版本</th>
				<th style="width: 350px;">反馈内容</th>
				<th>创建时间</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${feedbacks.content}" var="feedback">
				<tr>
					<td>${feedback.user.userName}</td>
					<td>${feedback.user.loginName}</td>
					<td>${feedback.deviceVersion}</td>
					<td>${feedback.deviceSystem}</td>
					<td>${feedback.deviceSize}</td>
					<td>${feedback.deviceNetwork}</td>
					<td>${feedback.appVersion}</td>
					<td><p class="feedback-content">${feedback.content}</p></td>
					<td><fmt:formatDate value="${feedback.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<div class="hidden-sm hidden-xs action-buttons">
							 <a class="red" href="javascript:void(0);" onclick="deleteNews(${feedback.id})"> <i
								class="ace-icon fa fa-trash-o bigger-130"></i>
							</a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${ctx}/static/js/ie.js"></script>
	<script type="text/javascript">

		$("tr").hover(function () {
			$(this).find(".feedback-content").css('white-space', 'normal');
		}, function () {
			$(this).find(".feedback-content").css('white-space', 'nowrap');
		});
		function deleteNews(id) {
			layer.confirm("删除后不可恢复，确认删除吗？", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/system/feedback/delete/" + id;
			});
	</script>
	<style type="text/css">
	.sp{float: left;margin-top:8px;margin-left: -148px;}
	</style>
	<!-- 分页  -->
	<tags:pagination page="${feedbacks}" paginationSize="20" reqParam=""/>
</body>
</html>
