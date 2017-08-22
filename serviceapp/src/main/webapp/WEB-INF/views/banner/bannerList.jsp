<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<tags:url></tags:url>
<html>
<head>
<title>幻灯片管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>幻灯片管理</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/admin/user" method="post">
			<div class="col-xs-12 ">
				<shiro:hasPermission name="banner:create">
					<button type="button" class="btn btn-info right"
						onclick="window.location.href='${ctx}/system/banner/create'">
						<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
					</button>
					</shiro:hasPermission>
			</div>
		</form>
	</div>
	<table id="bannerList"
		class=" bannerList table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="hidden-xs">图片</th>
				<th>标题</th>
				<th>创建时间</th>
				<th>是否展示</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${banners.content}" var="banner">
				<tr>
					<td class="hidden-xs"><img alt="200*90" width="202" height="90"
						src="${imgUrl}${banner.bannerImg}"></td>
						<td>${banner.bannerTitle}</td>
					<td><fmt:formatDate value="${banner.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><label> <input type="checkbox" name="isShow"
							value=""
							<c:if test="${banner.isShow == 1 }">checked="checked"</c:if>
							onchange="this.checked==true? this.value='1':this.value='0'; setIsShow(this.value,'${banner.id}')"
							class="ace ace-switch ace-switch-4"> <span
							class="lbl middle"></span>
					</label></td>
					<td>
						<div class="action-buttons visible-md visible-lg hidden-sm hidden-xs btn-group">
							
							
						<shiro:hasPermission name="banner:view">
							<a class="blue" href="${ctx}/system/banner/detail/${banner.id}">
								<i class="ace-icon fa fa-search-plus bigger-130"></i>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="banner:update">
							 <a class="green" href="${ctx}/system/banner/update/${banner.id}">
								<i class="ace-icon fa fa-pencil bigger-130"></i>
							</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="banner:delete">
							 <a class="red" href="javascript:void(0);"
								onclick="deleteBanner(${banner.id})"> <i
								class="ace-icon fa fa-trash-o bigger-130"></i>
							</a>
							</shiro:hasPermission>		
						</div>
						<div class="visible-xs visible-sm hidden-md hidden-lg hiddenbtn">
								<div class="inline position-relative">
									<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
										<i class="glyphicon glyphicon-cog icon-only bigger-100"></i>
									</button>
									<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
										<li>
											<shiro:hasPermission name="banner:view">
											<a class="blue" href="${ctx}/system/banner/detail/${banner.id}">
												<i class="ace-icon fa fa-search-plus bigger-130"></i>
											</a>
											</shiro:hasPermission>
										</li>
										<li>

											<shiro:hasPermission name="banner:update">
											 <a class="green" href="${ctx}/system/banner/update/${banner.id}">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="banner:delete">
											 <a class="red" href="javascript:void(0);"
												onclick="deleteBanner(${banner.id})"> <i
												class="ace-icon fa fa-trash-o bigger-130"></i>
											</a>
											</shiro:hasPermission>
										</li>
									</ul>									
								</div>
							</div>
													
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		function setIsShow(isShow,id){
			$.post('${ctx}/system/banner/show',{id:id,isShow:isShow},function(data){
				if(data && data.length < 30){
					layer.alert(data, {icon: 7, title: '提示'}, function (index) {
						window.location.reload();
			            layer.close(index);
			        });
				}
			});
		}
	
		function deleteBanner(id){
			layer.confirm("确认删除吗？", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/system/banner/delete/"+id;
			});
		}
	</script>

	<!-- 分页  -->
	<tags:pagination page="${banners}" paginationSize="20" reqParam="" />
</body>
</html>
