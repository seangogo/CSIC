<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>字典管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>字典管理</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class=" form-inline no-footer">
		<div class="row" style="padding: 5px 0px;">
			<form class="form-search" action="${ctx}/system/wordBook"
				method="post">
				<div class="col-xs-12" style="padding: 0px;"> 
					<div class="col-sm-3" style="margin-top: 5px;">
						<input type="text" style="width: 100%;"
							placeholder="类型" name="type" value="${type}"> 
					</div>
					<div class="col-sm-9" style="margin-top: 5px;">
						<button type="submit" class="btn btn-info">
							<span class="ace-icon fa fa-search  bigger-115 hidden-"></span> 搜索
						</button>
						<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");' >
							<span class="ace-icon fa fa-refresh bigger-115"></span> 重置
						</button>
						<shiro:hasPermission name="wordBook:update">
							<button type="button" class="btn btn-info"
								onclick="showCreateModal()">
								<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
							</button>
						</shiro:hasPermission>
					
					</div>
				</div>
			</form>
		</div>
		<table id="contentTable"
			class="table table-striped table-bordered table-hover dataTable no-footer">
			<thead>
				<tr>
					<th>类型</th>
					<th>键</th>
					<th>值</th>
					<th class="hidden-xs">操作人</th>
					<th class="hidden-xs">操作时间</th>
					<th>排序</th>
					<th class="hidden-xs">描述</th>
					<th>管理</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wordBooks.content}" var="wordBook">
					<tr>
						<td>${wordBook.type}</td>
						<td>${wordBook.wordIndex}</td>
						<td>${wordBook.wordValue}</td>
						<td class="hidden-xs">${wordBook.updater.userName}</td>
						<td class="hidden-xs"><fmt:formatDate value="${wordBook.updateTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${wordBook.orderBy}</td>
						<td class="hidden-xs">${wordBook.description}</td>
						<td>
							<div class="hidden-sm hidden-xs action-buttons">
								<shiro:hasPermission name="wordBook:update">
									<a class="green" href="javascript:void(0);"
										onclick='showUpdateModal(${wordBook.id})'> <i
										class="ace-icon fa fa-pencil bigger-115"></i>修改
									</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="wordBook:delete">
									<a class="red" href="javascript:void(0);"
										onclick="deleteWordBook(${wordBook.id})"> <i
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
											<shiro:hasPermission name="wordBook:update">
											<a class="green" href="javascript:void(0);"
												onclick='showUpdateModal(${wordBook.id})'> <i
												class="ace-icon fa fa-pencil bigger-115"></i>
											</a>
										</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="wordBook:delete">
												<a class="red" href="javascript:void(0);"
													onclick="deleteWordBook(${wordBook.id})"> <i
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
	</div>
	<!-- Modal 新增/修改-->
	<div class="modal fade" id="wordBookView" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form class="form-horizontal" id="wordBookForm" method="post"
					action="${ctx}/system/wordBook/update" role="form">
					<div class="modal-header">
						<h4 class="modal-title text-center" id="myModalLabel">修改</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="id" name="id" /> <input type="hidden"
							id="parentId" name="parentId" /> <input type="hidden"
							name="page" value="${page}" />
						<div class="form-group">
							<label for="type" class="col-lg-2 control-label">字典类型</label>
							<div class="col-lg-8">
								<input type="text" id="type" name="type" class="form-control"
									placeholder="请输入字典类型" required />
							</div>
						</div>
						<div class="form-group">
							<label for="wordIndex" class="col-lg-2 control-label">键</label>
							<div class="col-lg-8">
								<input type="text" id="wordIndex" name="wordIndex"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									class="form-control" placeholder="请输入键" required />
							</div>
						</div>
						<div class="form-group">
							<label for="wordValue" class="col-lg-2 control-label">值</label>
							<div class="col-lg-8">
								<input type="text" id="wordValue" name="wordValue"
									class="form-control" placeholder="请输入值" required />
							</div>
						</div>
						<div class="form-group">
							<label for="orderBy" class="col-lg-2 control-label">排序</label>
							<div class="col-lg-8">
								<input type="text" id="orderBy" name="orderBy"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									class="form-control" placeholder="请输入排序" required />
							</div>
						</div>
						<div class="form-group">
							<label for="description" class="col-lg-2 control-label">描述</label>
							<div class="col-lg-8">
								<textarea id="description" name="description"
									style="padding: 5px 4px;" class="form-control"
									placeholder="请输入描述"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-info" type="button"
							onclick="onSubmitCheck()">
							<i class="ace-icon fa fa-check bigger-110"></i> 确认
						</button>
						<button class="btn" type="reset" data-dismiss="modal">
							<i class="ace-icon fa fa-undo bigger-110"></i> 取消
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
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
		function showCreateModal(){
			$("#myModalLabel").text("新增");
			$("#wordBookView").find(':input').not(':button, :submit, :reset').val('');
			$("#description").val("");
			$("#orderBy").val("0");
			$("#wordBookView").modal();
		}
		
		//显示修改字典的view
		function showUpdateModal(id){
			$.post('${ctx}/system/wordBook/detail',{id:id},function(data){
				if(data){
					$("#wordIndex").val(data.wordIndex);
					$("#wordValue").val(data.wordValue);
					$("#orderBy").val(data.orderBy);
					$("#type").val(data.type);
					$('#id').val(data.id);
					$("#description").val(data.description);
					$("#myModalLabel").text("修改");
					$("#wordBookView").modal();
				}
			});			
			
		}
		
		
		//字符串去空
		function Trim(str) { 
			if(!str) return "";
	        return str.replace(/(^\s*)|(\s*$)/g, ""); 
		}
		//表单提交校验
		function onSubmitCheck(){
			if (!Trim($('#type').val())) {
				layer.alert("字典类型不能为空", { icon : 2, title : '提示' });
				return false;
			}
			if (!Trim($('#wordIndex').val())) {
				layer.alert("字典键不能为空", { icon : 2, title : '提示' });
				return false;
			}
			if (!Trim($('#wordValue').val())) {
				layer.alert("字典值不能为空", { icon : 2, title : '提示' });
				return false;
			}
			showLoadingToast();
			$("#wordBookForm").submit();
			//hideLoadingToast();
		}
	
	
		function deleteWordBook(id){
			layer.confirm("删除后不可恢复，确认删除吗？", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/system/wordBook/delete/"+id+"?page=${page}";
			});
		}
	</script>
	<script src="${ctx}/static/js/ie.js"></script>
	<style type="text/css">
	.sp{top:10px;left:16px;}
	</style>
	<!-- 分页  -->
	<tags:pagination page="${wordBooks}" paginationSize="20"
		reqParam="type=${type }" />
</body>
</html>
