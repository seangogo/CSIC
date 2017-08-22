<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<script src="static/sean/global.js"></script>
<body>
<!--查询弹出框-->

<!--查询弹出框-->
<div class="page-header">
    <h1>
        表
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            Static &amp; Dynamic Tables111
        </small>
    </h1>
</div><!-- /.page-header -->

<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <h3 class="header smaller lighter blue">修改密码</h3>
                <!--tableTools-container工具组-->
                <div class="clearfix">
                    <div class="pull-right tableTools-container"></div>
                </div>
                <!--tableTools-container工具组-->

            </div>
        </div>
    </div>
</div>
<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form class="form-horizontal avatar-form" id="pwdForm" action="/serviceapp/profile" method="post">
				<input type="hidden" name="id" value="1"/>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="name">用户名:</label>
					<div class="col-sm-9">
						<input type="text" name="name" id="name" value=""
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
</body>

<script type="text/javascript">


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

</html>