<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>幻灯片管理</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<link rel="stylesheet" href="${ctx}/static/cropper/main.css">
	<link rel="stylesheet" href="${ctx}/static/cropper/cropper.min.css">
	<link
		href="${ctx}/static/umeditor1_2_2/themes/default/css/umeditor.css"
		type="text/css" rel="stylesheet">
	<div class="page-header">
		<h1>
			<a href="${ctx }/system/banner">幻灯片管理</a><small> <i class="ace-icon fa fa-angle-double-right"></i>
				详情
			</small>
		</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="col-xs-12">
			<form class="form-horizontal avatar-form" id="bannerForm" 
				action="${ctx}/system/banner/${action}" method="post">
				<input type="hidden" name="bannerId" value="${banner.id}" />
				<!-- #section:elements.form -->

				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">图片上传:</label>
					<div class="col-sm-9">
						<div class="avatar-view" style="width: 400px;height: 175px;" title="Change the avatar" id="showImgDiv">
							<img style="width: 100%;" src="${ctx }/${banner.bannerImg}">
						</div>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">标题:</label>
					<div class="col-sm-9">
						<input type="text" id="form-field-1" name="bannerTitle" id="bannerTitle" maxlength="20" readonly="readonly"
							placeholder=请输入排序编号 value="${banner.bannerTitle}"
							class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">排序:</label>
					<div class="col-sm-9">
						<input type="text" id="form-field-1" name="orderBy" id="orderBy" maxlength="6" readonly="readonly"
                                   onkeyup="this.value=this.value.replace(/\D/g,'')"
							placeholder=请输入排序编号 value="${banner.orderBy}"
							class="col-xs-10 col-sm-5">
					</div>
				</div>

				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">是否展示:</label>
					<div class="col-sm-9">
						<label class="radio-inline"> <input type="radio" <c:if test="${banner.isShow==1 }">checked</c:if> disabled
							name="isShow" checked value="1"> 是
						</label> <label class="radio-inline"> <input type="radio" <c:if test="${banner.isShow==0 }">checked</c:if> disabled
							name="isShow" value="0"> 否
						</label>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">banner内容:</label>
					<div class="col-sm-9">
						<label class="radio-inline"> <input type="radio" onclick="$('#neilian').show();$('#wailian').hide();" disabled
							name="bannerType" <c:if test="${banner.bannerType==0 }">checked</c:if>  value="0"> 新闻链接 
						</label> <label class="radio-inline"> <input type="radio" onclick="$('#neilian').hide();$('#wailian').show();" disabled
							name="bannerType" <c:if test="${banner.bannerType ==1 }">checked</c:if> value="1"> 外部链接 
						</label>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group" id="neilian"  <c:if test="${banner.bannerType== 1 }">style="display: none;"</c:if> >
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">选择新闻:</label>
					<div class="col-sm-9">
						<div class="col-xs-10 col-sm-5">
							<select class="selectpicker " name="newsId" id="newsId" disabled="disabled">
							</select>
						</div>

					</div>
				</div>
				<div class="form-group" id="wailian" <c:if test="${banner.bannerType== 0 }">style="display: none;"</c:if> >
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">外部链接:</label>
					<div class="col-sm-9">
							<input type="text"  name="bannerTargetUrl" id="bannerTargetUrl" readonly="readonly"
							placeholder=请输入外部链接，如：https://www.baidu.com  value="${banner.bannerTargetUrl}"
							class="col-xs-10 col-sm-5" />
					</div>
				</div>
				<div class="space-4"></div>

				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn" type="reset" onclick="window.history.back() ">
							<i class="ace-icon fa fa-undo bigger-110"></i> 返回&nbsp;
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var news = ${news};
		var newsSelectHtml = "";
		for(var i =0; i<news.length;i++){
			var n = news[i];
			newsSelectHtml += "<option value='"+n['id']+"'>"+n['newsTitle']+"</option>"
		}
		var newsId = "${banner.newsId}";
		$("#newsId").html(newsSelectHtml);
		$("#newsId").val(newsId);
		
	</script>
</body>
</html>
