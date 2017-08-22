<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>菜单管理</title>
</head>

<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>
			菜单管理<small> </small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class=" form-inline no-footer">
		<div class="row" style="padding: 5px 0px;">
			<form class="form-search" action="${ctx }/system/resource"
				method="post">
				<input id="selectType" type="hidden" name="type" />
				<div class="col-xs-12 ">
					<div class="resource-button left">
						<button type="button" class="btn btn-success"
							onclick="selectedType(1)">
							<i class="ace-icon fa fa-signal"></i>
						</button>
						&nbsp;
						<button type="button" class="btn btn-info"
							onclick="selectedType(2)">
							<i class="ace-icon fa fa-pencil"></i>
						</button>
						&nbsp;
						<button type="button" class="btn btn-warning"
							onclick="selectedType(3)">
							<i class="ace-icon fa fa-users"></i>
						</button>
						&nbsp;
						<button type="button" class="btn btn-danger"
							onclick="selectedType(4)">
							<i class="ace-icon fa fa-cogs"></i>
						</button>
					</div>
					<shiro:hasPermission name="resource:create">
					<button type="button" class="btn btn-info right"
						onclick="showAddView('')">
						<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
					</button>
					</shiro:hasPermission>
				</div>
			</form>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-hover dataTable no-footer">
			<thead>
				<tr>
					<th class="hidden-xs">排序</th>
					<th>资源名称</th>
					<th>权限字符</th>
					<th class="hidden-xs">链接</th>
					<th>类型</th>
					<th class="hidden-xs">分类</th>
					<th class="hidden-xs">图标</th>
					<th>管理</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resources}" var="r">
					<c:if test="${r.level == 1}">
						<tr>
							<td class="hidden-xs">${r.orderBy}</td>
							<td>${r.name}</td>
							<td>${r.permission }</td>
							<td class="hidden-xs">${r.resUrl}</td>
							<td>
								<c:if test="${r.resourceType == 0 }">
									菜单
								</c:if>
								<c:if test="${r.resourceType == 1 }">
									按钮
								</c:if>
							</td>
							<td class="hidden-xs">${r.type}</td>
							<td class="hidden-xs">${r.iconCls}</td>
							<td>
								<div class="hidden-sm hidden-xs action-buttons">
									<shiro:hasPermission name="resource:create">
										<c:if test="${r.level < 2}">
											<a class="blue" onclick="showAddView(${r.id})"
												href="javascript:void(0);"> <i
												class="ace-icon fa glyphicon-plus bigger-150"></i>
											</a>
										</c:if>
									</shiro:hasPermission>
									<shiro:hasPermission name="resource:update">
									<a class="green" onclick="showUpdateView(${r.id},${r.resourceType})"
										href="javascript:void(0);"> <i
										class="ace-icon fa fa-pencil bigger-130"></i>
									</a> 
									</shiro:hasPermission>
									<shiro:hasPermission name="resource:delete">
										<a class="red" onclick="deleteMenu(${r.id})"
											href="javascript:void(0);"> <i
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
											<shiro:hasPermission name="resource:create">
												<c:if test="${r.level < 2}">
													<a class="blue" onclick="showAddView(${r.id})"
														href="javascript:void(0);"> <i
														class="ace-icon fa glyphicon-plus bigger-150"></i>
													</a>
												</c:if>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="resource:update">
												<a class="green" onclick="showUpdateView(${r.id},${r.resourceType})"
													href="javascript:void(0);"> <i
													class="ace-icon fa fa-pencil bigger-130"></i>
												</a> 
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="resource:delete">
												<a class="red" onclick="deleteMenu(${r.id})"
													href="javascript:void(0);"> <i
													class="ace-icon fa fa-trash-o bigger-130"></i>
												</a>
											</shiro:hasPermission>
										</li>
									</ul>									
								</div>
							</div>
								
							
							</td>
						</tr>
						<c:forEach items="${resources}" var="r2">
							<c:if test="${r.id == r2.parentId}">
								<tr>
									<td class="hidden-xs">${r2.orderBy}</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;|—${r2.name}</td>
									<td>${r2.permission }</td>
									<td class="hidden-xs">${r2.resUrl}</td>
									<td>
										<c:if test="${r2.resourceType == 0 }">
											菜单
										</c:if>
										<c:if test="${r2.resourceType == 1 }">
											按钮
										</c:if>
									</td>
									<td class="hidden-xs">${r2.type}</td>
									<td class="hidden-xs">${r2.iconCls}</td>
									<td>
										<div class="hidden-sm hidden-xs action-buttons">
											<shiro:hasPermission name="resource:create">
												<c:if test="${r2.resourceType == 0}">
													<a class="blue" onclick="showAddView2(${r2.id})"
														href="javascript:void(0);"> <i
														class="ace-icon fa glyphicon-plus bigger-150"></i>
													</a>
												</c:if>
											</shiro:hasPermission>
											<shiro:hasPermission name="resource:update">
											<a class="green" onclick="showUpdateView(${r2.id},${r2.resourceType})"
												href="javascript:void(0);"> <i
												class="ace-icon fa fa-pencil bigger-130"></i>
											</a> 
											</shiro:hasPermission>
											<shiro:hasPermission name="resource:delete">
												<a class="red" onclick="deleteMenu(${r2.id})"
													href="javascript:void(0);"> <i
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
												<shiro:hasPermission name="resource:create">
												<c:if test="${r2.resourceType == 0}">
													<a class="blue" onclick="showAddView2(${r2.id})"
														href="javascript:void(0);"> <i
														class="ace-icon fa glyphicon-plus bigger-150"></i>
													</a>
												</c:if>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="resource:update">
											<a class="green" onclick="showUpdateView(${r2.id},${r2.resourceType})"
												href="javascript:void(0);"> <i
												class="ace-icon fa fa-pencil bigger-130"></i>
											</a> 
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="resource:delete">
												<a class="red" onclick="deleteMenu(${r2.id})"
													href="javascript:void(0);"> <i
													class="ace-icon fa fa-trash-o bigger-130"></i>
												</a>
											</shiro:hasPermission>
										</li>
									</ul>									
								</div>
							</div>
								
									</td>
								</tr>
								<c:forEach items="${resources}" var="r3">
									<c:if test="${r2.id == r3.parentId}">
										<tr>
											<td class="hidden-xs">${r3.orderBy}</td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;|—${r3.name}</td>
											<td>${r3.permission }</td>
											<td>${r3.resUrl}</td>
											<td>
												<c:if test="${r3.resourceType == 0 }">
													菜单
												</c:if>
												<c:if test="${r3.resourceType == 1 }">
													按钮
												</c:if>
											</td>
											<td class="hidden-xs">${r3.type}</td>
											<td class="hidden-xs">${r3.iconCls}</td>
											<td>
												<div class="hidden-sm hidden-xs action-buttons">
													<shiro:hasPermission name="resource:create">
														<c:if test="${r3.resourceType == 0}">
															<a class="blue" onclick="showAddView2(${r3.id})"
																href="javascript:void(0);"> <i
																class="ace-icon fa glyphicon-plus bigger-150"></i>
															</a>
														</c:if>
													</shiro:hasPermission>
													<shiro:hasPermission name="resource:update">
													<a class="green" onclick="showUpdateView(${r3.id},${r3.resourceType})"
														href="javascript:void(0);"> <i
														class="ace-icon fa fa-pencil bigger-130"></i>
													</a> 
													</shiro:hasPermission>
													<shiro:hasPermission name="resource:delete">
														<a class="red" onclick="deleteMenu(${r3.id})"
															href="javascript:void(0);"> <i
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
															<shiro:hasPermission name="resource:create">
																<c:if test="${r3.resourceType == 0}">
																	<a class="blue" onclick="showAddView2(${r3.id})"
																		href="javascript:void(0);"> <i
																		class="ace-icon fa glyphicon-plus bigger-150"></i>
																	</a>
																</c:if>
															</shiro:hasPermission>
													
														</li>
														<li>
															<shiro:hasPermission name="resource:update">
																<a class="green" onclick="showUpdateView(${r3.id},${r3.resourceType})"
																	href="javascript:void(0);"> <i
																	class="ace-icon fa fa-pencil bigger-130"></i>
																</a> 
															</shiro:hasPermission>
													
														</li>
														<li>
															<shiro:hasPermission name="resource:delete">
																<a class="red" onclick="deleteMenu(${r3.id})"
																	href="javascript:void(0);"> <i
																	class="ace-icon fa fa-trash-o bigger-130"></i>
																</a>
															</shiro:hasPermission>
														</li>
													</ul>									
												</div>
											</div>
												
											</td>
										</tr>
										</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- Modal 修改菜单-->
	<div class="modal fade" id="updateView" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form class="form-horizontal" id="resourceForm" method="post"
					action="" role="form">
					<div class="modal-header">
						<h4 class="modal-title text-center" id="myModalLabel">修改</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="id" name="id" /> <input type="hidden"
							id="parentId" name="parentId" />
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">资源名称</label>
							<div class="col-lg-8">
								<input type="text" id="name" name="name" class="form-control"
									placeholder="请输入资源名称" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">类型</label>
							<div class="col-lg-8">
								<label class="radio-inline" id="addMenuLabel"> <input type="radio"
									onclick="$('#resUrlDiv').show();" name="resourceType" checked
									value="0"> 菜单
								</label> <label class="radio-inline"> <input type="radio"
									onclick="$('#resUrlDiv').hide();" name="resourceType" value="1">
									按钮
								</label>
							</div>
						</div>
						<div class="form-group" id="resUrlDiv">
							<label for="up_classyear" class="col-lg-2 control-label">资源链接</label>
							<div class="col-lg-8">
								<input type="text" id="resUrl" name="resUrl"
									class="form-control" placeholder="请输入资源链接" />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">分类</label>
							<div class="col-lg-8">
								<select name="type" id="type">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">权限字符串</label>
							<div class="col-lg-8">
								<input type="text" id="permission" name="permission"
									class="form-control"
									placeholder="权限字符串，如：resource:create 或 resource:*" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">图标</label>
							<div class="col-lg-8">
								<input type="text" id="iconCls" name="iconCls"
									class="form-control" placeholder="请输入图标" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">排序</label>
							<div class="col-lg-8">
								<input type="text" id="orderBy" name="orderBy"
									class="form-control" placeholder="请输入排序" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">描述</label>
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
	//菜单模块选择
	function selectedType(type){
		 $('#selectType').val(type);
		$('.form-search').submit();
	}
	
	
	
	//字符串去空
	function Trim(str) { 
		if(!str) return "";
        return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
	//表单提交校验
	function onSubmitCheck(){
		if (!Trim($('#name').val())) {
			layer.alert("菜单名称不能为空", { icon : 2, title : '提示' });
			return false;
		}
		var resourceType = $("input[type=radio][name='resourceType']:checked").val();
		if(resourceType == 0){
			if (!Trim($('#resUrl').val())) {
				layer.alert("菜单链接不能为空", { icon : 2, title : '提示' });
				return false;
			}
		}
		
		if (!Trim($('#permission').val())) {
			layer.alert("权限字符串不能为空", { icon : 2, title : '提示' });
			return false;
		}
		
		showLoadingToast();
		$("#resourceForm").submit();
		//hideLoadingToast();
	}
	
	
	
	
	var menuList = ${resourcesJson};
	//显示修改弹出框
	function showUpdateView(id,resourceType){
		$("#myModalLabel").html("修改");
		$("#resourceForm").attr("action","${ctx}/system/resource/update");
		for(var i=0;i<menuList.length; i++){
			var menu = menuList[i];
			if(id == menu.id){
				$('#id').val(menu.id);
				$('#parentId').val(menu.parentId);
				$('#name').val(menu.name);				
				$('#resUrl').val(menu.resUrl);
				$("#type").val(menu.type);
				$("#permission").val(menu.permission);
				$("#iconCls").val(menu.iconCls);
				$("#orderBy").val(menu.orderBy);
				$("#description").val(menu.description);
				if(resourceType == 0){
					$("input[type=radio][name='resourceType'][value=0]").attr("checked",'checked');
					$('#resUrlDiv').show();
				}else{
					$("input[type=radio][name='resourceType'][value=1]").attr("checked",'checked');
					$('#resUrlDiv').hide();
				}
			} 
		}
		$('#updateView').modal('show');
	}
	//显示新增弹出框
	function showAddView(id){
		$("#myModalLabel").html("新增");
		$("#resourceForm").attr("action","${ctx}/system/resource/create");
		$('#id').val("");
		$('#parentId').val(id);
		$('#name').val("");				
		$('#resUrl').val("");
		$("#type").val("${type}");
		$("#addMenuLabel").show();
		$('#resUrlDiv').show();
		$("#iconCls").val("fa-list-alt");
		$("#permission").val("");
		$("#orderBy").val(0);
		$('#updateView').modal('show');
	}
	//显示新增弹出框
	function showAddView2(id){
		$("#myModalLabel").html("新增");
		$("#resourceForm").attr("action","${ctx}/system/resource/create");
		$('#id').val("");
		$('#parentId').val(id);
		$('#name').val("");		
		$('#resUrl').val("");
		//二级菜单只能添加按钮 不能添加三级菜单 隐藏 菜单类型 和链接地址
		$("input[type=radio][name='resourceType'][value=1]").attr("checked",'checked');
		$("#addMenuLabel").hide();
		$('#resUrlDiv').hide();
		
		$("#type").val("${type}");
		$("#iconCls").val("fa-list-alt");
		$("#permission").val("");
		$("#orderBy").val(0);
		$('#updateView').modal('show');
	}
	
	//删除菜单
	function deleteMenu(id){
		var hasChildren = false;
		for(var i=0;i<menuList.length; i++){
			var menu = menuList[i];
			if(id == menu.parentId){
				hasChildren = true;
				break;
			} 
		}
		if(hasChildren){
			layer.alert("该目录包含子菜单，不能删除", {title: '提示', icon: 2, closeBtn: false});
			return;
		}
		layer.confirm('确认删除吗？', {icon: 3, title: '提示'}, function (index) {
            window.location.href="${ctx}/system/resource/delete/"+id+"?type=${type}";
            layer.close(index);
        });
	}
	
</script>
</body>
</html>
