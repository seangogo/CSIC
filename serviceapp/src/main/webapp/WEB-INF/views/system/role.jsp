<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>角色管理</title>
</head>

<body>
<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<link rel="stylesheet" href="${ctx}/static/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/static/zTree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctx}/static/zTree/js/jquery.ztree.excheck.js"></script>
	<div class="page-header">
		<h1>
			角色管理<small> </small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="col-sm-12 form-inline no-footer">
		<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx }/system/resource" method="post">
			<div class="col-xs-12 ">
				<shiro:hasPermission name="role:create">
					<button type="button" class="btn btn-info right"
						onclick="showAddView()">
						<i class="ace-icon fa glyphicon-plus bigger-115"></i>新增
					</button>
				</shiro:hasPermission>
			</div>
		</form>
	</div>
		<table id="contentTable"
			class="table table-striped table-bordered table-hover dataTable no-footer">
			<thead>
				<tr>
					<th>id</th>
					<th>角色</th>
					<th>名称描述</th>
					<th>修改时间</th>
					<th>管理</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${roles}" var="r">
					<tr>
						<td>${r.id}</td>
						<td>${r.roleName}</td>
						<td>${r.description}</td>
						<td>${r.updateTime}</td>
						<td>
							<div class="action-buttons hidden-sm hidden-xs">
								<shiro:hasPermission name="role:update">
								<a class="green" onclick="showUpdateView(${r.id})"
									href="javascript:void(0);"> <i
									class="ace-icon fa fa-pencil bigger-130"></i>
								</a> 
								</shiro:hasPermission>
								<shiro:hasPermission name="role:delete">
								<a class="red" onclick="deleteRole(${r.id})"
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
											<shiro:hasPermission name="role:update">
											<a class="green" onclick="showUpdateView(${r.id})"
												href="javascript:void(0);"> <i
												class="ace-icon fa fa-pencil bigger-130"></i>
											</a> 
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="role:delete">
												<a class="red" onclick="deleteRole(${r.id})"
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
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- Modal 修改菜单-->
	<div class="modal fade" id="updateView" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form class="form-horizontal" id="roleForm" method="post" action=""
					role="form">
					<div class="modal-header">
						<h4 class="modal-title text-center" id="myModalLabel">修改</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="id" name="id" /> <input type="hidden"
							id="resourceIds" name="resourceIds" />
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">角色名称</label>
							<div class="col-lg-8">
								<input type="text" id="roleName" name="roleName"
									class="form-control" placeholder="请输入角色名称" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">角色描述</label>
							<div class="col-lg-8">
								<input type="text" id="description" name="description"
									class="form-control" placeholder="请输入角色描述" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">权限选择</label>
							<div class="col-lg-8">
								<div class="widget-box"
									style="max-height: 400px; overflow: auto;">
									<div class="left">
										<ul id="treeResources" class="ztree"></ul>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="modal-footer">
					<button class="btn btn-info" type="button" onclick="onCheck()">
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
	<script type="text/javascript">
	var treeObj = $.fn.zTree;
	var zNodes =[];
	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
	
	function onCheck(){
		var zTree = treeObj.getZTreeObj("treeResources"),
        nodes = zTree.getCheckedNodes(true),
		        id = "";
		console.log(zTree);
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
		    id += nodes[i].id + ",";
		}
		if (id.length > 0 ) id = id.substring(0, id.length-1);
		console.log(id);
		$("#resourceIds").val(id);
		$("#roleForm").submit();
	}
	
	
		function showUpdateView(id){
			$("#myModalLabel").html("修改");
			$("#roleForm").attr("action","${ctx}/system/role/update");
			$.post('${ctx}/system/role/resource',{roleId:id},function(data){
				var role = data.roleInfo;
				console.log(data)
				console.log(role);
				$("#id").val(role.id);
				$("#resourceIds").val(role.resourceIds);
				$("#description").val(role.description);
				$("#roleName").val(role.roleName);
				zNodes = data.roleResource;
				setTree();
				$('#updateView').modal('show');
			});
		}
		
		function showAddView(){
			$("#myModalLabel").html("新增");
			$("#roleForm").attr("action","${ctx}/system/role/create");
			$.post('${ctx}/system/role/allResource',{},function(data){
				$("#id").val("");
				$("#resourceIds").val("");
				$("#description").val("");
				$("#roleName").val("");
				zNodes = data;
				setTree();
				$('#updateView').modal('show');
			});
		}
		
		
		function deleteRole(id){
			layer.confirm('确认删除吗？', {icon: 3, title: '提示'}, function (index) {
	            window.location.href="${ctx}/system/role/delete/"+id;
	            layer.close(index);
	        });
		}
		
		
		function setTree() {
			treeObj.destroy("treeResources");
			treeObj.init($("#treeResources"), setting, zNodes);
			var zTree = treeObj.getZTreeObj("treeResources"), type = { "Y":"ps", "N":"ps"};
			zTree.setting.check.chkboxType = { "Y":"ps", "N":"ps"};
		}
		
	</script>
</body>
</html>
