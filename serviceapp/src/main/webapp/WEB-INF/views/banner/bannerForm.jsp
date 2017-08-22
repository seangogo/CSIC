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
				${title}
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
						<input id="bannerImgStr" name="bannerImgStr" type="hidden"  value="${banner.bannerImg }">
						<div class="container" id="crop-avatar">
							<!-- Current avatar -->
							<c:if test="${not empty banner.bannerImg }">
								<div class="avatar-view" title="Change the avatar"
									style="    height: 175px;width: 400px;" id="showImgDiv">
									<img style="width: 100%;" src="${ctx }/${banner.bannerImg}">
								</div>
							</c:if>
							<c:if test="${ empty banner.bannerImg}">
								<div class="avatar-view" title="Change the avatar"
									id="showImgDiv">
									<label class="ace-file-input ace-file-multiple"><span
										class="ace-file-container" data-title="Click to choose"><span
											class="ace-file-name" data-title="No File ..."><i
												class=" ace-icon ace-icon fa fa-cloud-upload"></i></span></span><a
										class="remove" href="#"><i class=" ace-icon fa fa-times"></i></a></label>
								</div>
							</c:if>

							<!-- Cropping modal -->
							<div class="modal fade" id="avatar-modal" aria-hidden="true"
								aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
								<div class="modal-dialog modal-lg">
									<div class="modal-content">
										<div class="modal-header">
											<button class="close" data-dismiss="modal" type="button">&times;</button>
											<h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
										</div>
										<div class="modal-body">
											<div class="avatar-body">

												<!-- Upload image and data -->
												<div class="avatar-upload">
													<input class="avatar-data" id="imgData" type="hidden">
												</div>
												<label class="ace-file-input" for="avatarInput"><input
													type="file" id="avatarInput" class="avatar-input"><span
													class="ace-file-container" data-title="Choose"><span
														class="ace-file-name" data-title="选择文件"><i
															class=" ace-icon fa fa-upload"></i></span></span><a class="remove"
													href="#"><i class=" ace-icon fa fa-times"></i></a></label>

												<!-- Crop and preview -->
												<div class="row">
													<div class="col-md-12">
														<div class="avatar-wrapper" img-data="2.2511"></div>
													</div>
												</div>

												<div class="row avatar-btns">
													<div class="col-md-9"></div>
													<div class="col-md-3">
														<button class="btn btn-primary btn-block avatar-save"
															onclick="selectUpload()" type="reset"
															data-dismiss="modal">Done</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /.modal -->
							<!-- Loading state -->
							<div class="loading" aria-label="Loading" role="img"
								tabindex="-1"></div>
						</div>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">标题</label>
					<div class="col-sm-9">
						<input type="text" id="form-field-1" name="bannerTitle" id="bannerTitle" maxlength="20"
							placeholder=请输入标题 value="${banner.bannerTitle}"
							class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">排序</label>
					<div class="col-sm-9">
						<input type="text" id="form-field-1" name="orderBy" id="orderBy" maxlength="6"
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
						<label class="radio-inline"> <input type="radio" data-isFull="${showIsFull}"
															<c:if test="${banner.isShow==1 }">checked</c:if>
															name="isShow" id="isShow"  value="1"> 是
						</label>
						<label class="radio-inline"> <input type="radio"
																	 <c:if test="${banner.isShow==0 }">checked</c:if>
																	 checked="checked" name="isShow" value="0"> 否
					</label>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group">
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">banner内容:</label>
					<div class="col-sm-9">
						<label class="radio-inline"> <input type="radio" onclick="$('#neilian').show();$('#wailian').hide();"
							name="bannerType" <c:if test="${action == 'create'}">checked</c:if> <c:if test="${banner.bannerType==0 }">checked</c:if>  value="0"> 新闻链接 
						</label> <label class="radio-inline"> <input type="radio" onclick="$('#neilian').hide();$('#wailian').show();"
							name="bannerType"  <c:if test="${banner.bannerType ==1 }">checked</c:if> value="1"> 外部链接
						</label>
					</div>
				</div>
				<div class="space-4"></div>
				<div class="form-group" id="neilian"  <c:if test="${banner.bannerType== 1 }">style="display: none;"</c:if> >
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">选择新闻:</label>
					<div class="col-sm-9">
						<div class="col-xs-10 col-sm-5">
							<select class="selectpicker " name="newsId" id="newsId">
							</select>
						</div>

					</div>
				</div>
				<div class="form-group" id="wailian" <c:if test="${action == 'create'}">style="display: none;"</c:if> <c:if test="${banner.bannerType== 0 }">style="display: none;"</c:if> >
					<label class="col-sm-3 control-label no-padding-right"
						for="form-field-1">外部链接:</label>
					<div class="col-sm-9">
							<input type="text"  name="bannerTargetUrl" id="bannerTargetUrl"
							placeholder=请输入外部链接，如：https://www.baidu.com  value="${banner.bannerTargetUrl}"
							class="col-xs-10 col-sm-5" />
					</div>
				</div>
				<div class="space-4"></div>

				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button class="btn btn-info" type="button" onclick="checkForm()" >
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
			<div class="my_loading_toast" >
				<div style="margin-top:1.3em;">
				<i class="ace-icon fa fa-spinner fa-spin  bigger-250"></i>
					<p id="my_loading_content">数据提交中..</p>
				</div>
			</div>
		</div>
		<script src="${ctx}/static/js/loading.js"></script>
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
		$(document).ready(function () {
			$("#isShow").on("change",function () {
				if($("#isShow")[0].checked&&$("#isShow").data("isfull")==1){
					layer.alert("超过首页banner显示刷,请先关闭其他banner", { icon : 2, title : '提示' });
					$("#isShow")[0].checked=false;
				}
			})
		});

		//表单校验
		function checkForm() {
			if ($('#bannerImgStr').val()=="") {
				layer.alert("请选择图片", { icon : 2, title : '提示' });
				return false;
			}
			if(1 == $("input[name='bannerType']:checked").val() && $("#bannerTargetUrl").val() == ""){
				layer.alert("外部链接不能为空", { icon : 2, title : '提示' });
				return false;
			}else if(0== $("input[name='bannerType']:checked").val() && $("#newsId").val() == ""){
				layer.alert("请选择新闻纪录", { icon : 2, title : '提示' });
				return false;
			}
			
			if($("#bannerTitle").val() == ""){
				layer.alert("请输入标题", { icon : 2, title : '提示' });
				return false;
			}
			
			showLoadingToast();
			$("#bannerForm").submit();
			//hideLoadingToast();
		}

		$('input[type=file]').change(function() {
			var file = this.files[0];
			var reader = new FileReader();
			reader.onload = function() {
				// 通过 reader.result 来访问生成的 DataURL
				var url = reader.result;
				setImageURL(url);
			};
			reader.readAsDataURL(file);
		});

		var image = new Image();
		function setImageURL(url) {
			image.src = url;
		}

		//裁剪图片选择后
		function selectUpload() {
			var dataJson = $('#imgData').val();
			console.log("dataJson: " + dataJson);
			var dataObj = eval('(' + dataJson + ')');

			// 以下四个参数由第三步获得
			var x = dataObj.x;
			var y = dataObj.y;
			var width = dataObj.width;
			var height = dataObj.height;

			var canvas = $('<canvas width="'+width+'" height="'+height+'"></canvas>')[0], ctx = canvas
					.getContext('2d');
			ctx.drawImage(image, x, y, width, height, 0, 0, width, height);
			$("#showImgDiv").html(canvas).css("width", "375").css("height",
					"167");
			var data = canvas.toDataURL();
			// dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
			data = data.split(',')[1];
			$('#bannerImgStr').val(data);
		}
	</script>
	<script src="${ctx}/static/cropper/jquery.min.js"></script>
	<script src="${ctx}/static/cropper/cropper.min.js"></script>
	<script src="${ctx}/static/cropper/main.js"></script>
</body>
</html>
