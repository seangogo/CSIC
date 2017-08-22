<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>邀请码管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>邀请码管理</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/system/inviteCode" method="post">
			<div class="col-xs-12 ">
				<shiro:hasPermission name="inviteCode:create">
					<button type="button" class="btn btn-info right"
						onclick="createInviteCode()">
						<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
					</button>
				</shiro:hasPermission>
			</div>
		</form>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>邀请码编号</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>是否使用</th>
				<th>使用人</th>
				<th>使用时间</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${inviteCodes.content}" var="inviteCode">
				<tr>
					<td>${inviteCode.codeNum}</td>
					<td>${inviteCode.fromUser.userName}</td>
					<td><fmt:formatDate value="${inviteCode.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:if test="${inviteCode.isUse == 0 }">
							未使用
						</c:if>
						<c:if test="${inviteCode.isUse == 1 }">
							已使用
						</c:if>
					</td>
					<td>${inviteCode.toUser.userName}</td>
					<td><fmt:formatDate value="${inviteCode.useTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<div class="action-buttons">
							<shiro:hasPermission name="inviteCode:delete">
							 <a class="red" href="javascript:void(0);" onclick="deleteInviteCode(${inviteCode.id})"> <i
								class="ace-icon fa fa-trash-o bigger-130"></i>
							</a>
							</shiro:hasPermission>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="my_loading" id="my_loading">
		<div class="my_loading_toast">
			<div style="margin-top: 1.3em;">
				<i class="ace-icon fa fa-spinner fa-spin  bigger-250"></i>
				<p id="my_loading_content">数据加载中..</p>
			</div>
		</div>
	</div>
	<script src="${ctx}/static/js/loading.js"></script>
	<script type="text/javascript">
	
		//显示新增字典的view
		function createInviteCode(){
			showLoadingToast();
			$.post('${ctx}/system/inviteCode/create',{},function(data){
				hideLoadingToast();
				if(data){
					layer.alert(data, {icon: 2, title: '提示'}, function (index) {
						window.location.reload();
			            layer.close(index);
			        });
				}else{
					layer.alert("生成成功", {icon: 6, title: '提示'}, function (index) {
						window.location.reload();
			            layer.close(index);
			        });
				}
			});	
		}
		
		function deleteInviteCode(id){
			layer.confirm("删除后不可恢复，确认删除吗？", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/system/inviteCode/delete/"+id;
			});
		}
	</script>
		<!-- 分页  -->
	<tags:pagination page="${inviteCodes}" paginationSize="20" reqParam=""/>
</body>
</html>
