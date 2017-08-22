<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>新闻管理</title>
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
        新闻管理
        <small><i class="ace-icon fa fa-angle-double-right"></i>
            <c:choose>
            <c:when test="${empty news.id}">
                新增
            </c:when>
            <c:otherwise>
                修改
            </c:otherwise>
            </c:choose>
        </small>
    </h1>
</div>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">
        <button data-dismiss="alert" class="close">×</button>
            ${message}</div>
</c:if>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <form class="form-horizontal avatar-form" id="newsForm"
              action="${ctx}/system/news/${action}" method="post">
            <input type="hidden" name="newsId" value="${news.id}"/>
            <!-- #section:elements.form -->
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻标题:</label>
                <div class="col-sm-9">
                    <input type="text" name="newsTitle"
                           id="newsTitle" placeholder=请输入新闻标题 value="${news.newsTitle}"
                           class="col-xs-10 col-sm-5">
                </div>
            </div>

            <div class="space-4"></div>

            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻描述:</label>
                <div class="col-sm-9">
						<textarea class="col-sm-7" id="newsDesc"
                                  name="newsDesc" style="height: 190px; padding-left: 5px;"
                                  placeholder="请输入新闻描述">${news.newsDesc}</textarea>
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">图片上传</label>
                <div class="col-sm-9">
                    <input id="newsImgStr" name="newsImgStr" type="hidden" value="${imgeStr}">
                    <div class="container" id="crop-avatar">
                        <!-- Current avatar -->
                        <c:if test="${not empty news.newsImg }">
                            <div class="avatar-view" title="Change the avatar" style="height: 187px;"
                                 id="showImgDiv">
                                <img style="width: 100%;" src="${ctx }/${news.newsImg}">
                            </div>
                        </c:if>
                        <c:if test="${ empty news.newsImg }">
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
                                            <label class="ace-file-input" for="avatarInput">
                                                <input type="file" id="avatarInput" class="avatar-input">
                                                <span class="ace-file-container" data-title="Choose">
                                                    <span class="ace-file-name" data-title="选择文件">
                                                        <i class=" ace-icon fa fa-upload"></i>
                                                    </span>
                                                </span>
                                                <a class="remove" href="#">
                                                    <i class=" ace-icon fa fa-times"></i>
                                                </a>
                                            </label>

                                            <!-- Crop and preview -->
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="avatar-wrapper" img-data="1.333333"></div>
                                                </div>
                                            </div>
                                            <div class="row avatar-btns">
                                                <div class="col-md-9"></div>
                                                <div class="col-md-3">
                                                    <button class="btn btn-primary btn-block avatar-save"
                                                            onclick="selectUpload()" type="reset" data-dismiss="modal">
                                                        Done
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.modal -->
                        <!-- Loading state -->
                        <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            
            
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻内容:</label>
                <div class="col-sm-9">
				<textarea style="width: 100%; height: 400px; border-radius: 15px;" id="newsContent" name="newsContent"></textarea>
                </div>
            </div>

            <div class="space-4"></div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="button"
                            onclick="checkForm()">
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
        <div style="margin-top:1.3em;">
            <i class="ace-icon fa fa-spinner fa-spin  bigger-250"></i>
            <p id="my_loading_content">数据提交中..</p>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/loading.js"></script>
<script type="text/javascript">
    //字符串去空
    function Trim(str) {
        if (!str) return "";
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    //链接添加时间戳
	function timestamp(url){
     
       var getTimestamp=new Date().getTime();
      if(url.indexOf("?")>-1){
        url=url+"&timestamp="+getTimestamp
      }else{
        url=url+"?timestamp="+getTimestamp
      }
      return url;
    }

	var backurl=timestamp("../");

	

    function checkForm() {
        if (!Trim($('#newsTitle').val())) {
            layer.alert("标题不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!$('#newsImgStr').val()) {
            layer.alert("图片不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!Trim($("#newsContent").val())) {
            layer.alert("新闻内容不能为空", {icon: 2, title: '提示'});
            return false;
        }
        showLoadingToast();
        $('#newsForm').submit();
 	    //window.location.assign(backurl);
    }
    
    $('input[type=file]').change(function () {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function () {
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

        var canvas = $('<canvas width="' + width + '" height="' + height + '"></canvas>')[0], ctx = canvas
                .getContext('2d');

        ctx.drawImage(image, x, y, width, height, 0, 0, width, height);
        $("#showImgDiv").html(canvas).css("width", "375").css("height",
                "280");
        var data = canvas.toDataURL();
       
        // dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
        data = data.split(',')[1];
      
        $('#newsImgStr').val(data);
    }
</script>
<script src="${ctx}/static/cropper/jquery.min.js"></script>
<script src="${ctx}/static/cropper/bootstrap.min.js"></script>
<script src="${ctx}/static/cropper/cropper.min.js"></script>
<script src="${ctx}/static/cropper/main.js"></script>

<link href="${ctx}/static/umeditor1_2_2/themes/default/css/umeditor.css"
      type="text/css" rel="stylesheet">
<script type="text/javascript"
        src="${ctx}/static/umeditor1_2_2/third-party/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${ctx}/static/umeditor1_2_2/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${ctx}/static/umeditor1_2_2/umeditor.min.js"></script>
<script type="text/javascript"
        src="${ctx}/static/umeditor1_2_2/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
    var um = UM.getEditor('newsContent');
    um.addListener('blur', function () {
        $("#newsContent").val(um.getContent());
    });
    um.setContent('${news.newsContent}');
</script>
</body>
</html>
