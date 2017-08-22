<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>修改密码</title>
</head>

<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<div class="page-header">
		<h1>
			<a href="${ctx}/admin/user">用户管理</a><small> <i class="ace-icon fa fa-angle-double-right"></i>
				修改密码
			</small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal avatar-form" id="pwdForm" action="${ctx}/profile" method="post">
				<input type="hidden" name="id" value="${user.id}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="name">用户名:</label>
					<div class="col-sm-9">
						<input type="text" name="name" id="name" value="${user.userName}"
							readonly="readonly" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="plainPassword">新密码:</label>
					<div class="col-sm-9">
						<input type="password" name="plainPassword" id="plainPassword" maxlength="15"
							placeholder="请输入新的密码" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="confirmPassword">确认密码:</label>
					<div class="col-sm-9">
						<input type="password" name="confirmPassword" id="confirmPassword" maxlength="15"
							placeholder="请确认新的密码" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button" onclick="checkForm()">
							<i class="ace-icon fa fa-check bigger-110"></i> 确定&nbsp;
						</button>

						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset" onclick="window.history.back() ">
							<i class="ace-icon fa fa-undo bigger-110"></i> 返回&nbsp;
						</button>
					</div>
				</div>
			</form>
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
		//字符串去空
		function Trim(str) { 
			if(!str) return "";
	        return str.replace(/(^\s*)|(\s*$)/g, ""); 
		}	
		//聚焦第一个输入框
		$("#plainPassword").focus();
		
		function checkForm(){
			var plainPassword = $('#plainPassword').val();
			var confirmPassword = $('#confirmPassword').val();
			if(!Trim(plainPassword)){
				layer.alert("密码不能为空", { icon : 2, title : '提示' });
				return false;
			}
			if(plainPassword.length < 6){
				layer.alert("密码不能小于6位", { icon : 2, title : '提示' });
				return false;
			}
			
			if(plainPassword != confirmPassword){
				layer.alert("两次密码不一致，请重新输入", { icon : 2, title : '提示' });
				return false
			}
			showLoadingToast();
			$('#pwdForm').submit();
		}
	</script>
</body>
</html>
