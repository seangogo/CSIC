<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<tags:url></tags:url>

<html>
<head>
<title>用户管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>
			用户管理<small> <i class="ace-icon fa fa-angle-double-right"></i>列表</small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/admin/user" method="post">
			<div class="col-xs-12" style="padding:0px">
				<div class="col-sm-3" style="margin-top:5px;">
					<input type="text" class="form-control search-query search-input" placeholder="手机号码" name="loginName" value="${loginName}">
				</div>
				<div class="col-sm-3" style="margin-top:5px">
					<input type="text" class="form-control search-query search-input" placeholder="用户名" name="userName" value="${userName}"> 
				</div>
				<div class="col-sm-6" style="margin-top:5px">

					<select class="selectpicker" name="userType">
						<option value="">全部</option>
						<option value="0" <c:if test='${userType == 0}'>selected</c:if>>用户</option>
						<option value="1" <c:if test='${userType == 1}'>selected</c:if>>工程师</option>
						<option value="2" <c:if test='${userType == 2}'>selected</c:if>>部门经理</option>
					</select>
					<%-- <select class="selectpicker" name="groupId">
						<option value="">全部</option>
						<c:forEach items="${groups}" var="group">
							<option value="${group.id}">${group.name}</option>
						</c:forEach>
					</select> --%>
				</div>
			</div>
			<div class="col-xs-12" style="margin:5px 0px;">
				
					<button type="submit" class="btn btn-info">
						<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
					</button>
				
				
					<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
							<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
				
					<shiro:hasPermission name="user:create">
						<button type="button" class="btn btn-info right" style="display: block;" onclick="showCreateModal()">
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
				<th>登录名</th>
				<th>用户名</th>
				<th>身份</th>
				<th class="hidden-xs">公司</th>
				<th class="hidden-xs">邮箱</th>
				<th>在线状态</th>
				<th class="hidden-xs">注册时间</th>
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users.content}" var="user">
				<tr>
					<td><a href="javascript:void(0);"
						onclick='showDetailModal(${user.id})'>${user.loginName}</a></td>
					<td>${user.userName}</td>
					<td><c:if test="${user.userType == 0}">普通用户</c:if> <c:if
							test="${user.userType == 1}">工程师</c:if> <c:if
							test="${user.userType == 2}">部门经理</c:if></td>
					<td class="hidden-xs">${user.userCompany}</td>
					<td class="hidden-xs">${user.userMail}</td>
					<td><c:if test="${user.isLogin ==0 }">
							<i class="ace-icon fa fa-circle grey"></i>
							<font color="gray">离线</font>
						</c:if> <c:if test="${user.isLogin ==1 }">
							<i class="ace-icon fa fa-circle light-green"></i>
							<font class="light-green">在线</font>
						</c:if></td>
					<td class="hidden-xs"><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<div class="action-buttons visible-md visible-lg hidden-sm hidden-xs btn-group">
							<shiro:hasPermission name="user:view">
								<a class="blue" href="javascript:void(0);"
									onclick='showDetailModal(${user.id})'> <i
									class="ace-icon fa fa-search-plus bigger-115"></i>查看
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="user:update">
								<a class="green" href="javascript:void(0);"
									onclick='showUpdateModal(${user.id})'> <i
									class="ace-icon fa fa-pencil bigger-115"></i>修改
								</a>
							</shiro:hasPermission>
							<%-- <shiro:hasPermission name="user:delete">
								<a class="red" href="javascript:void(0);"
									onclick="onDelete(${user.id})"> <i
									class="ace-icon fa fa-trash-o bigger-115"></i>删除
								</a>
							</shiro:hasPermission> --%>
							<shiro:hasPermission name="user:isLocked">
								<c:if test="${user.isLocked == 0 }">
									<a class="inverse" href="javascript:void(0);"
										onclick='onLocked(${user.id},1)'> <i
										class="ace-icon fa fa-lock bigger-115"></i>停用
									</a>
								</c:if>
								<c:if test="${user.isLocked != 0 }">
									<a class="inverse" href="javascript:void(0);"
										onclick='onLocked(${user.id},0)'> <i
										class="ace-icon fa fa-unlock bigger-115"></i>启用
									</a>
								</c:if>
							</shiro:hasPermission>
							<shiro:hasPermission name="user:resetPwd">
								<a class="inverse" href="javascript:void(0);"
									onclick='onResetPwd(${user.id})'> <i
									class="ace-icon glyphicon glyphicon-repeat "></i>重置密码
								</a>
							</shiro:hasPermission>
						</div>
				
						<div class="visible-xs visible-sm hidden-md hidden-lg hiddenbtn">
								<div class="inline position-relative">
									<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
										<i class="glyphicon glyphicon-cog icon-only bigger-100"></i>
									</button>
									<ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close" style="min-width:59px;">
										<li>
											<shiro:hasPermission name="user:view">
												<a class="blue" href="javascript:void(0);"
													onclick='showDetailModal(${user.id})'> <i
													class="ace-icon fa fa-search-plus bigger-115"></i><div>查看</div>
												</a>
											</shiro:hasPermission>
										</li>
										<li>
											<shiro:hasPermission name="user:update">
												<a class="green" href="javascript:void(0);"
													onclick='showUpdateModal(${user.id})'> <i
													class="ace-icon fa fa-pencil bigger-115"></i><div>修改</div>
												</a>
											</shiro:hasPermission>
										</li>
										<li>
											
										<shiro:hasPermission name="user:isLocked">
											<c:if test="${user.isLocked == 0 }">
												<a class="inverse" href="javascript:void(0);"
													onclick='onLocked(${user.id},1)'> <i
													class="ace-icon fa fa-lock bigger-115"></i><div>停用</div>
												</a>
											</c:if>
											<c:if test="${user.isLocked != 0 }">
												<a class="inverse" href="javascript:void(0);"
													onclick='onLocked(${user.id},0)'> <i
													class="ace-icon fa fa-unlock bigger-115"></i><div>启用</div>
												</a>
											</c:if>
										</shiro:hasPermission>
										</li>
										<li >
											
											<shiro:hasPermission name="user:resetPwd">
												<a class="inverse" href="javascript:void(0);"
													onclick='onResetPwd(${user.id})'> <i
													class="ace-icon glyphicon glyphicon-repeat "></i><div style="margin-left:-10px;font-size:9px;">重置密码</div>
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
	<!-- Modal 新增用户-->
	<div class="modal fade" id="userDetailView" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form class="form-horizontal" id="userDetailForm" method="post"
					action="" role="form">
					<div class="modal-header">
						<h4 class="modal-title text-center" id="myModalLabel">新增</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="userId" name="id" />
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">用户手机</label>
							<div class="col-lg-8">
								<input type="text" id="loginName" name="loginName"
									onkeyup="this.value=this.value.replace(/\D/g,'')"
									class="form-control" onblur="checkLoginName()"
									placeholder="请输入手机号码" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">用户姓名</label>
							<div class="col-lg-8">
								<input type="text" id="userName" name="userName"
									class="form-control" placeholder="请输入用户姓名" required />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">用户类型</label>
							<div class="col-lg-8">
								<select class="selectpicker" name="userType" id="userType"
									onChange="if(this.value==1) {$('#repairTypeDiv').show()} else {$('#repairTypeDiv').hide()}">
									<c:if test="${not empty roleList}">
										<c:forEach items="${roleList}" var="role">
											<option value="${role.wordIndex}">${role.wordValue}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="form-group" id="repairTypeDiv" style="display: none;">
							<label for="up_classyear" class="col-lg-2 control-label">维修类型</label>
							<div class="col-lg-8">
								<select class="selectpicker" name="repairType" id="repairType"></select>
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">公司名称</label>
							<div class="col-lg-8">
								<input type="text" id="userCompany" name="userCompany"
									value="武汉大海信息系统科技有限公司" class="form-control"
									placeholder="请输入公司名称" />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">性别</label>
							<div class="col-lg-8">
								<label class="radio-inline"> <input type="radio"
									name="userSex" checked value="0"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="userSex" value="1"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">邮箱</label>
							<div class="col-lg-8">
								<input type="text" id="userMail" name="userMail"
									class="form-control" placeholder="请输入邮箱" />
							</div>
						</div>
						<div class="form-group">
							<label for="up_classyear" class="col-lg-2 control-label">地址</label>
							<div class="col-lg-8">
								<textarea id="userAddress" name="userAddress"
									style="padding: 5px 4px;" class="form-control"
									placeholder="请输入地址"></textarea>
							</div>
						</div>
						<div class="form-group" id="userTip">
							<label for="up_classyear" class="col-lg-6 red control-label">提示：新增用户初始密码默认为123456</label>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-info" type="button"
							onclick="onSubmitCheck()" id="submitBtn">
							<i class="ace-icon fa fa-check bigger-110"></i> 确认
						</button>
						<button class="btn" type="reset" data-dismiss="modal">
							<i class="ace-icon fa fa-undo bigger-110"></i> 返回
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="userDetailView2" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content" style="width: 700px;">
				<div class="row">
				<button style="z-index: 9;" type="button" id="cboxClose" onclick='$("#userDetailView2").modal("hide");'>×</button>
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->
						<div class="hr dotted"></div>
						<div class="">
							<div id="user-profile-1" class="user-profile row" style="padding-left: 20px;">
								<div class="col-xs-12 col-sm-4 center">
									<div>
										<!-- #section:pages/profile.picture -->
										<span class="profile-picture"><img id="userIco2" width="200"
											class="img-responsive" src="../assets/avatars/profile-pic.jpg" onerror="this.src='${ctx }/static/ace/assets/avatars/avatar2.png'"/>
										</span>
										<!-- /section:pages/profile.picture -->
										<div class="space-4"></div>
										<div
											class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
											<div class="inline position-relative">
												<span class="user-title-label dropdown-toggle" > <i id="isLogin2"
													class="ace-icon fa fa-circle  gray"></i> &nbsp; <span
													class="white userName2">测试用户</span>
												</span>
											</div>
										</div>
									</div>
									<div class="space-6"></div>
								</div>
								<div class="col-xs-12 col-sm-8">
									<div class="profile-user-info profile-user-info-striped">
										<div class="profile-info-row">
											<div class="profile-info-name">用户姓名</div>
											<div class="profile-info-value">
												<span class="editable editable-click userName2" >测试用户</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">用户手机</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="loginName2">13500000000</span>
											</div>
										</div>

										<div class="profile-info-row">
											<div class="profile-info-name">用户类型</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="userType2">普通用户</span>
											</div>
										</div>

										<div class="profile-info-row">
											<div class="profile-info-name">活跃时间</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="lastLocationTime2"></span>
											</div>
										</div>

										<div class="profile-info-row">
											<div class="profile-info-name">用户性别</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="userSex2">男</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">用户邮箱</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="userMail2">test123@163.com</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">公司名称</div>

											<div class="profile-info-value">
												<span class="editable editable-click" id="userCompany2">武汉大海科技</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">联系地址</div>
											<div class="profile-info-value">
												<i class="fa fa-map-marker light-orange bigger-110"></i> <span
													class="editable editable-click" id="userAddress2">武汉市武昌区中山路450号</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="hr dotted"></div>
					</div>
					<!-- /.col -->
				</div>

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
	<script>
		var repairTypeList = ${repairTypeList};
		var repairTypeHtml = "";
		for(var i =0; i<repairTypeList.length;i++){
			var repairtype = repairTypeList[i];
			console.log(repairtype['wordValue']);
			repairTypeHtml += "<option value='"+repairtype['wordIndex']+"'>"+repairtype['wordValue']+"</option>"
		}
		$("#repairType").html(repairTypeHtml);
		
		//字符串去空
		function Trim(str) { 
			if(!str) return "";
	        return str.replace(/(^\s*)|(\s*$)/g, ""); 
		}	
		
		//表单验证
		function onSubmitCheck(){
			if(Trim($('#loginName').val()).length != 11){
				layer.alert("请输入正确的手机号码", { icon : 2, title : '提示' });
				return;
			}
			if(!Trim($('#userName').val())){
				layer.alert("用户姓名不能为空", { icon : 2, title : '提示' });
				return false;
			}
			showLoadingToast();
			//console.log($('#userDetailForm').serialize());
			$('#userDetailForm').submit();
			//hideLoadingToast();
		}
		
		
		//显示新增用户的view
		function showCreateModal(){
			$("#myModalLabel").text("新增");
			$("#userDetailView").find(':input').not(':button, :submit, :reset').val('');
			$("#userCompany").val("武汉大海信息系统科技有限公司");
			$("#repairType").selectpicker('val',"0");
			$("#userType").selectpicker('val',"0");
			$("#repairTypeDiv").hide();
			$('#userTip').show();
			$("#userSex").val(0);
			$("#userAddress").val("");
			$("#userDetailForm").attr("action","${ctx}/admin/user/saveUser");
			$("#userDetailView").find(':input').not(':button, :submit, :reset').removeAttr("disabled");
			$('#submitBtn').show();
			$("#userDetailView").modal();
		}
		var oldLoginName = "";
		//显示修改用户的view
		function showUpdateModal(id){
			showLoadingToast();
			$.post('${ctx}/admin/user/getUser',{userId:id},function(data){
				hideLoadingToast();
				if(!data){
					layer.alert("用户不存在", {icon: 7, title: '提示'}, function (index) {
			            layer.close(index);
			        });
				}else{
					data = eval("(" + data + ")");
					$("#myModalLabel").text("修改");
					$('#userTip').hide();
					$("#userId").val(data.userId);
					$("#loginName").val(data.loginName);
					oldLoginName = data.loginName;
					$("#userName").val(data.userName);
					//$('#userType').val(data.userType);
					$("#userType").selectpicker('val',data.userType);
					if(data.userType == 1){
						$("#repairTypeDiv").show();
						//$('#repairType').val(data.repairType);
						$("#repairType").selectpicker('val',data.repairType);
					}else{
						$("#repairTypeDiv").hide();
						$("#repairType").selectpicker('val',"0");
					}
					$("input[type=radio][name='userSex'][value="+data.userSex+"]").attr("checked",'checked'); 
					$("#userCompany").val(data.userCompany);
					$("#userMail").val(data.userMail);
					$("#userAddress").val(data.userAddress);
					$("#userDetailView").find(':input').not(':button, :submit, :reset').removeAttr("disabled");
					$("#userDetailForm").attr("action","${ctx}/admin/user/updateUser");
					$('#submitBtn').show();
					$("#userDetailView").modal();
				}
			}).error(function() {
				hideLoadingToast();
				layer.alert("请求失败，请重新再试", {icon: 2, title: '提示'}, function (index) {
					window.location.reload();
		            layer.close(index);
		        });
			 });
		}
		
		//显示查看用户的view
		function showDetailModal(id){
			showLoadingToast();
			$.post('${ctx}/admin/user/getUser',{userId:id},function(data){
				hideLoadingToast();
				if(!data){
					layer.alert("用户不存在", {icon: 7, title: '提示'}, function (index) {
			            layer.close(index);
			        });
				}else{
					data = eval("(" + data + ")");
					$("#loginName2").html(data.loginName);
					$(".userName2").html(data.userName);
					if(data.isLogin == 1){
						$('#isLogin2').removeClass('gray').addClass('light-green');
					}else{
						$('#isLogin2').removeClass('light-green').addClass('gray');
					}
					$('#userIco2').attr("src","${imgUrl}/"+data.userIco);
					if(data.lastLocationTime){
						var lastLocationTime = new Date(data.lastLocationTime.replace(/-/g, '/'));
		                var nowTime = new Date().getTime();
		                if ((nowTime - lastLocationTime) / 86400000 > 1) {
		                    if ((nowTime - lastLocationTime) / 86400000 > 30) {
		                        $('#lastLocationTime2').html(lastLocationTime.getMonth() + 1 + "-" + lastLocationTime.getDate());
		                    } else {
		                        $('#lastLocationTime2').html(parseInt((nowTime - lastLocationTime) / 86400000) + "天前");
		                    }
		                } else if ((nowTime - lastLocationTime) / 3600000 > 1) {
		                    $('#lastLocationTime2').html(parseInt((nowTime - lastLocationTime) / 3600000) + "小时前");
		                } else if ((nowTime - lastLocationTime) / 60000 > 1) {
		                    $('#lastLocationTime2').html(parseInt((nowTime - lastLocationTime) / 60000) + "分钟前");
		                } else if ((nowTime - lastLocationTime) / 60000 < 1) {
		                    $('#lastLocationTime2').html("刚刚");
		                }
					}
					
					var userTypeStr = "其他";
					if(data.userType == 0){
						userTypeStr = "普通用户";
					} else if(data.userType == 1){
						userTypeStr = "工程师";
					}else if(data.userType == 2){
						userTypeStr = "部门经理";
					}else if(data.userType == 3){
						userTypeStr = "管理员";
					}
					$("#userType2").html(userTypeStr);
					if(data.userSex == 0){
						$("#userSex2").html("男");
					}else if(data.userSex == 1){
						$("#userSex2").html("女");
					}else {
						$("#userSex2").html("未知");
					}
					$("#userCompany2").html(data.userCompany);
					$("#userMail2").html(data.userMail);
					$("#userDetailView2").modal();
				}
			}).error(function() {
				hideLoadingToast();
				layer.alert("请求失败，请重新再试", {icon: 2, title: '提示'}, function (index) {
					window.location.reload();
		            layer.close(index);
		        });
			 });
		}		
		
		
		
		
		//检查手机是否重复
		function checkLoginName(){
			var loginName = $('#loginName').val();
			if(oldLoginName == loginName){
				return;
			}
			if(loginName.length != 11){
				layer.alert("请输入正确的手机号码", { icon : 2, title : '提示' });
				return;
			}
			showLoadingToast();
			$.post('${ctx}/admin/user/checkLoginName',{loginName:loginName},function(data){
				hideLoadingToast();
				if(data){
					layer.alert(data, {icon: 7, title: '提示'}, function (index) {
						$('#loginName').val('');
			            layer.close(index);
			        });
				}
			});
		}
		
		
		//删除用户
		function onDelete(userId){
			layer.confirm("确认删除吗？删除后不可恢复，请谨慎操作", {icon: 5, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/admin/user/delete/"+userId;
			});
		}
		
		//重置密码
		function onResetPwd(userId){
			layer.confirm("确认重置密码吗？重置后密码为初始默认密码：${defaultPwd}", {icon: 3, title: '提示'}, function (index) {
				layer.close(index);
				window.location.href = "${ctx}/admin/user/resetPwd/"+userId;
			});
		}
	
		//锁定/解锁用户
		function onLocked(userId,type){
			var str = "";
			if(type == 1){
				str = "确认锁定该用户吗？";
			}else{
				str = "确认解锁该用户吗？";
			}
			layer.confirm(str, {icon: 4, title: '提示'}, function (index) {
				showLoadingToast();
				$.post('${ctx}/admin/user/isLocked',{type:type,userId:userId},function(data){
					hideLoadingToast();
					if(data){
						layer.alert('操作失败', {icon: 7, title: '提示'}, function (index) {
							window.location.reload();
				            layer.close(index);
				        });
					}else{
						window.location.reload();
					}
				});
	            layer.close(index);
	        });
		}
	
	</script>
	<script src="${ctx}/static/js/ie.js"></script>
	<style type="text/css">
	.sp{top:10px;left:16px;}
	</style>
	<!-- 分页  -->
	<tags:pagination page="${users}" paginationSize="5"
		reqParam="loginName=${loginName}&userName=${userName}&userType=${userType}" />
</body>
</html>
