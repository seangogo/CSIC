<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>部门管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/static/zTree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctx}/static/zTree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="${ctx}/static/zTree/js/jquery.ztree.exedit.js"></script>
	<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
	<div class="page-header">
		<h1>
			部门管理<small> </small>
		</h1>
	</div>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="groupTree" class="ztree"></ul>
	</div>
</div>
<script type="text/javascript">
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
				dblClickExpand: false,
				expandAll: true
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn //需要隐藏删除的节点
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRename: onRename,
				beforeDblClick: onDblClick
			}
		};

		var zNodes = ${groupsJson};
		var treeObj = $.fn.zTree;
		var onEditName = "";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function beforeEditName(treeId, treeNode) {
			onEditName = treeNode.name;
			var zTree = treeObj.getZTreeObj("groupTree");
			zTree.selectNode(treeNode);
			return true;
		}
		
		function beforeRemove(treeId, treeNode) {
			var zTree = treeObj.getZTreeObj("groupTree");
			zTree.selectNode(treeNode);
			var hasChildren = false;
			for(var i=0;i<zNodes.length; i++){
				if(treeNode.id == zNodes[i].pId){
					hasChildren = true;
				}
			}
			if(hasChildren){
				layer.alert("该目录包含子菜单，不能删除", {title: '提示', icon: 2, closeBtn: false});
				return false;
			}
			
			layer.confirm('确认删除吗？', {icon: 3, title: '提示'}, function (index) {
				$.post('${ctx}/system/group/delete',{id:treeNode.id},function(data){
						layer.alert(data, {icon: 7, title: '提示'}, function (index) {
				            window.location.href="${ctx}/system/group";
				            layer.close(index);
				        });
				});
	            layer.close(index);
				return true;
	        });
			
			return false;
		}
		
		function beforeRename(treeId, treeNode, newName, isCancel) {
			if (newName.length == 0) {
				var zTree = treeObj.getZTreeObj("groupTree");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		
		function onRename(e, treeId, treeNode, isCancel) {
			if(onEditName == treeNode.name){
				return false;
			}
			var treeObj = {id:treeNode.id,groupName:treeNode.name};
			$.post('${ctx}/system/group/update',treeObj,function(data){
				if(data == false){
					layer.alert('修改失败', {icon: 7, title: '提示'}, function (index) {
			            window.location.href="${ctx}/system/group";
			            layer.close(index);
			        });
				}
			});
		}
		
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = treeObj.getZTreeObj("groupTree");
				
				$.post('${ctx}/system/group/create',{id:treeNode.id},function(data){
					if(data == null || data == 0){
						layer.alert('新增失败', {icon: 7, title: '提示'}, function (index) {
				            window.location.href="${ctx}/system/group";
				            layer.close(index);
				        });
					}else{
						zTree.addNodes(treeNode, {id:data, pId:treeNode.id, name:"请输入部门名称"});
						return false;
					}
				});
			});
        }
        function showRemoveBtn(treeId, treeNode) {
			return (treeNode.id != 1);
		}
		
		function onDblClick(treeId,treeNode){
			onEditName = treeNode.name;
			var zTree = treeObj.getZTreeObj("groupTree");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
		}
		
		
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
        }
        treeObj.init($("#groupTree"), setting, zNodes);
		treeObj.getZTreeObj("groupTree").expandAll(true);
	</script>
</body>
</html>
