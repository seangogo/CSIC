<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<script src="static/sean/global.js"></script>
<script src="static/js/dropzone.min.js"></script>
<body>
<!--查询弹出框-->
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
			<form class="form-horizontal avatar-form" id="pwdForm"  method="post">
				<input type="hidden" name="id" value="${currentUser.id}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="name">用户名:</label>
					<div class="col-sm-9">
						<input type="text" name="nickName" id="nickName" value="${currentUser.nickName}"
							readonly="readonly" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="plainPassword">新密码:</label>
					<div class="col-sm-9">
						<input type="password" name="password" id="password" maxlength="15"
							placeholder="请输入新的密码" class="col-xs-10 col-sm-5" data-rule="required;password">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right" for="confirmPassword">确认密码:</label>
					<div class="col-sm-9">
						<input type="password" name="confirmPassword" id="confirmPassword" maxlength="15"
							placeholder="请确认新的密码" class="col-xs-10 col-sm-5" data-rule="required;match[password]">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button" onclick="checkForm()">
							<i class="ace-icon fa fa-check bigger-110"></i> 确定&nbsp;
						</button>

						&nbsp; &nbsp; &nbsp;
						<button class="btn" type="reset" id="backBtn">
							<i class="ace-icon fa fa-undo bigger-110"></i> 返回&nbsp;
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
//字符串去空
function Trim(str) { 
	if(!str) return "";
    return str.replace(/(^\s*)|(\s*$)/g, ""); 
}	
//聚焦第一个输入框
$("#password").focus();

function checkForm(){
	var plainPassword = $('#password').val();
	var confirmPassword = $('#confirmPassword').val();
	if(!Trim(plainPassword)){
		return false;
	}
	if(plainPassword.length < 6){
		return false;
	}
	
	if(plainPassword != confirmPassword){
		return false
	}
	$("#pwdForm").ajaxSubmit({
        url: "manager/update",
        dataType: "json",
        success: function (result) {
        	$dh.notify(result.msg, "success");
        	location.href="logout";
        },
        error:function(){
        	$dh.notify(result.msg, "error");
        }
    })
}
$(function(){
	$("#backBtn").click(function(){
		//alert("url="+$("#update-password").data("url"));
		$("#page-content").load($("#update-password").data("url"));
	})
})
</script>

</html>