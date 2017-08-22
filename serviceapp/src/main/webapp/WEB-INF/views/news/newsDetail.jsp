<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<tags:url></tags:url>

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
            新增
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
              action="${ctx}/system/news/create" method="post">
            <!-- #section:elements.form -->
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻标题:</label>
                <div class="col-sm-9">
                    <input type="text" id="form-field-1" readonly="readonly"
                           name="newsTitle" id="newsTitle" placeholder=请输入新闻标题
                           value="${news.newsTitle}" class="col-xs-10 col-sm-5">
                </div>
            </div>

            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻描述:</label>
                <div class="col-sm-9">
						<textarea class="col-sm-7" id="form-field-8" id="newsDesc"
                                  disabled="disabled"
                                  style="height: 190px; padding-left: 5px;">
                                  ${news.newsDesc}
                        </textarea>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">图片:</label>
                <div class="col-sm-9">
                    <div class="avatar-view" title="Change the avatar" id="showImgDiv">
                        <img style="width: 100%;height: 100%" src="${imgUrl}/${news.newsImg}">
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="form-field-1">新闻内容:</label>
                <div class="col-sm-9">
                    <div style="width: 550px; height: auto; min-height: 400px; padding: 5px; border: 1px solid #d5d5d5;"
                         id="newsContent">${news.newsContent}</div>
                </div>
            </div>

            <div class="space-4"></div>

            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
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
</html>
