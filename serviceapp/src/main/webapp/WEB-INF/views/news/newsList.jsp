<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<tags:url></tags:url>
<html>
<head>
<title>新闻管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>新闻管理</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/system/news" method="post">
			
				<div class="col-xs-12" style="padding: 0px;"> 
					<div class="col-sm-3" style="margin-top: 5px;">
						<input type="text" class=""
						style="width: 100%;" placeholder="新闻标题" name="newsTitle" value="${newsTitle}"> 
					</div>
					<div class="col-sm-9" style="margin-top: 5px;">
							<button type="submit" class="btn btn-info">
							<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
						</button>
						<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");' >
							<span class="ace-icon fa fa-refresh bigger-115"></span> 重置
						</button>
						<shiro:hasPermission name="news:create">
							<button type="button" class="btn btn-info"
								onclick="window.location.href='${ctx}/system/news/create'">
								<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
							</button>
						</shiro:hasPermission>
					</div>
				</div>
				
				
		</form>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>新闻图片</th>
				<th>新闻标题</th>
				<th class="hidden-xs">新闻描述</th>
				<th>新增时间</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${news.content}" var="n">
				<tr>
					<td><img alt="80*60" width="80" height="60" src="${imgUrl}/${n.newsImg}"></td>
					<td><div class="newstitle">${n.newsTitle}</div></td>
					<td class="hidden-xs">${n.newsDesc}</td>
					<td><fmt:formatDate value="${n.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<div class="hidden-sm hidden-xs action-buttons">
							<div>
							<shiro:hasPermission name="news:view">
							<a class="blue" href="${ctx}/system/news/detail/${n.id}"> <i
								class="ace-icon fa fa-search-plus bigger-130"></i>
							</a>
							</shiro:hasPermission>
							</div>
							<div>
							<shiro:hasPermission name="news:update">
							 <a class="green" href="${ctx}/system/news/update/${n.id}"> <i
								class="ace-icon fa fa-pencil bigger-130"></i>
							</a>
							</shiro:hasPermission>
							</div>
							<div>
							<shiro:hasPermission name="news:delete">
							 <a class="red" href="javascript:void(0);" onclick="deleteNews(${n.id})"> <i
								class="ace-icon fa fa-trash-o bigger-130"></i>
							</a>
							</shiro:hasPermission>
							</div>
						</div>
						
						<div class="visible-xs visible-sm hidden-md hidden-lg hiddenbtn">
								<div class="inline position-relative">
									<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
										<i class="glyphicon glyphicon-cog icon-only bigger-100"></i>
									</button>
									<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
										<li>
											<shiro:hasPermission name="news:view">
											<a class="blue" href="${ctx}/system/news/detail/${n.id}"> <i
												class="ace-icon fa fa-search-plus bigger-130"></i>
											</a>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="news:update">
											 <a class="green" href="${ctx}/system/news/update/${n.id}"> <i
												class="ace-icon fa fa-pencil bigger-130"></i>
											</a>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="news:delete">
											 <a class="red" href="javascript:void(0);" onclick="deleteNews(${n.id})"> <i
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
	<script src="${ctx}/static/js/ie.js"></script>
	<script type="text/javascript">
		
		function deleteNews(id){
			layer.confirm("确认删除吗？", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/system/news/delete/"+id;
			});
		}
	</script>
	<style type="text/css">
	.sp{left: 17px;top: 8px;}
	</style>
	<!-- 分页  -->
	<tags:pagination page="${news}" paginationSize="20" reqParam="newsTitle=${newsTitle }"/>
</body>
</html>
