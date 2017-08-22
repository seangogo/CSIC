<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<tags:url></tags:url>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
</head>
<body>
<link rel="stylesheet" href="${ctx}/static/css/main.css">
<link rel="stylesheet" href="${ctx}/static/cropper/main.css">
<link rel="stylesheet" href="${ctx}/static/cropper/weui-toast.min.css">
<link rel="stylesheet" href="${ctx}/static/cropper/cropper.min.css">
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/jquery-ui.css">
<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>
<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-part2.css" class="ace-main-stylesheet"/>
<![endif]-->
<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/static/ace/assets/css/ace-ie.css"/>
<![endif]-->


<style>
	
	
</style>
<div class="page-header">
    <h1>
        <a href="${ctx }/order/list">订单管理</a>
        <small><i class="ace-icon fa fa-angle-double-right"></i>
            ${title}
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
        <form class="form-horizontal avatar-form" id="orderForm" enctype="multipart/form-data"  action="${ctx}/order/${action}" method="post"  target="_self">
            <input type="hidden" name="orderId" value="${order.id}" id="orderId"/>
            <input type="hidden" name="orderFile" value="" id="orderFile"/>
            <!-- #section:elements.form -->
            <div class="form-group" id="neilian">
                <label class="col-sm-3 control-label no-padding-right"
                       for="repairType">订单类型：</label>
                <div class="col-sm-9" style="padding: 0px;">
                    <div class="col-xs-10 col-sm-5">
                        <select class="selectpicker " data-style="btn-primary" name="repairType" id="repairType">
                        </select>
                    </div>
                </div>
            </div>
            <c:if test="${ not empty costList}">
                <c:forEach items='${costList}' var='cost'>
                    <div class="form-group costImp">
                        <label class="col-sm-3 control-label no-padding-right" for="repairType">实施费用：</label>
                        <div class="col-sm-9" style="padding: 0px;">
                            <div class="col-xs-8 col-sm-5">
                                <select class="selectpicker form-control" name="costType" data-style="btn-danger"
                                        id="costType">
                                    <c:if test="${ not empty costList}">
                                        <c:forEach items='${cost.orderCostTypes}' var='costType'>
                                            <c:if test="${cost.costType.id==costType.id}">
                                                <option value='${costType.id}' selected="selected">
                                                        ${costType.costName}
                                                </option>
                                            </c:if>

                                            <c:if test="${cost.costType.id!=costType.id}">
                                                <option value='${costType.id}'>
                                                        ${costType.costName}
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <button style="z-index: 9;  right: 0px;top:3px" type="button" class="cboxCloseC" onclick="elRomove(this)" id="cboxClose">
                                ×
                            </button>
                            <input type="text" name="money" maxlength="8" placeholder=金额 onblur="elTest(this)"
                                   value="${cost.cost}"
                                   class="col-xs-4 money"/>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <div class="space-4"></div>
            
            <div class="form-group costImpBtn" id="addCostBtn" style="display:
            <c:if test="${ not empty costList}">block</c:if>
            <c:if test="${empty costList}">none</c:if>" ;>
                <label class="col-sm-3 control-label no-padding-right"
                       for="repairType"></label>
                <div class="col-sm-9">
                    <button class="btn btn-info" id="addBtn" type="button">添加费用</button>
                </div>
            </div>
            
            <div class="space-4 costImpBtn" style="display: none" ;></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="contactUser">联系人：</label>
                <div class="col-sm-9">
                    <input type="text" name="contactUser" id="contactUser" maxlength="12"
                           placeholder=请输入联系人姓名 value="${order.contactUser}"
                           class="col-xs-10 col-sm-5">
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="contactPhone">联系电话：</label>
                <div class="col-sm-9">
                    <input type="text" name="contactPhone" id="contactPhone" maxlength="11"
                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                           placeholder=请输入联系电话 value="${order.contactPhone}"
                           class="col-xs-10 col-sm-5">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="contactAddress">联系地址：</label>
                <div class="col-sm-9">
						<textarea class="col-sm-5" id="contactAddress"
                                  name="contactAddress" style="height: 60px; padding-left: 5px;"
                                  placeholder="请输入联系地址">${order.contactAddress}</textarea>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group costSum" style=
                    "display:
                    <c:if test='${ not empty costList}'>none</c:if>
                    <c:if test='${empty costList}'>block</c:if>">
                <label class="col-sm-3 control-label no-padding-right" for="spinner">台数：</label>
                <div class="col-sm-9">
                    <input id="spinner" name="qty" type="text" value="1"/>
                </div><!-- ./span -->
            </div>
            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="orderDesc">问题描述：</label>
                <div class="col-sm-9">
						<textarea class="col-sm-5" id="orderDesc"
                                  name="orderDesc" style="height: 90px; padding-left: 5px;"
                                  placeholder="请输入订单描述">${order.orderDesc}</textarea>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"
                       for="orderImgStr">图片上传：</label>
                <div class="col-sm-9">
                    <input id="orderImgStr" name="imgStrs" type="hidden" value="">
                    <div class="container" id="crop-avatar">

                        <div class="avatar-view" title="Change the avatar">
                            <label class="ace-file-input ace-file-multiple"><span
                                    class="ace-file-container" data-title="Click to choose"><span
                                    class="ace-file-name" data-title="No File ..."><i
                                    class=" ace-icon ace-icon fa fa-cloud-upload"></i></span></span><a
                                    class="remove" href="#"><i class=" ace-icon fa fa-times"></i></a></label>
                        </div>
                        <div id="showImgDiv">
                        </div>
                        <div id="tempImgDiv">
                            <c:if test="${ not empty orderImgs}">
                                <c:forEach items='${orderImgs}' var='img'>
                                    <img src="${imgUrl}/${img}" width="150"/>
                                </c:forEach>
                            </c:if>
                        </div>

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
                                                    type="file" id="avatarInput" class="avatar-input upFile"><span
                                                    class="ace-file-container" data-title="Choose"><span
                                                    class="ace-file-name" data-title="选择文件"><i
                                                    class=" ace-icon fa fa-upload"></i></span></span><a class="remove"
                                                                                                        href="#"><i
                                                    class=" ace-icon fa fa-times"></i></a></label>

                                            <!-- Crop and preview -->
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="avatar-wrapper" img-data="1"></div>
                                                </div>
                                            </div>

                                            <div class="row avatar-btns">
                                                <div class="col-md-9"></div>
                                                <div class="col-md-3">
                                                    <button class="btn btn-primary btn-block avatar-save"
                                                            onclick="selectUpload()" type="reset"
                                                            data-dismiss="modal">Done
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
                        <div class="loading" aria-label="Loading" role="img"
                             tabindex="-1"></div>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group" id="uploadTable">
                <label class="col-sm-3 control-label no-padding-right">文件列表：</label>
                <div class="col-sm-5">
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th style="width: 30%;text-align: center">文件名</th>
                            <th style="width: 15%;text-align: center">类型</th>
                            <th style="width: 20%;text-align: center">文件大小</th>
                            <th style="width: 25%;text-align: center">上传时间</th>
                            <th style="width: 10%;text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${ not empty fileList}">
                            <c:forEach items='${fileList}' var='file'>
                                <tr class="success" style="text-align: center">
                                    <td>${fn:split(file.docName,".")[0]}</td>
                                    <td>${fn:split(file.docName,".")[1]}</td>
                                    <td>${file.fileSize}</td>
                                    <td>${fn:substring(file.createTime,0 , 10)}</td>
                                    <td>
                                        <a class='glyphicon glyphicon-remove' data-fid="${file.id}">
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="space-4"></div>
            <div class="form-group" id="addUpFile">
                <label class="col-sm-3 control-label no-padding-right"></label>
                <div class="col-sm-9">
                    <div id="uploadFileDlg" class="easyui-dialog"
                         closed="true">
                        <form id="uploadFileForm" method="post" style="width:100%;text-align:center;padding:20px 0;">
                            <input id="lefile" type="file" style="display:none">
                            <div class="input-append">
                                <a class="btn btn-info addUpBtn">添加附件</a>
                                <div class="btn btn-info" id="uploadBtn"
                                     style="width:auto;display:inline-block;">上传
                                </div>
                            </div>
                        </form>
                        <div class="progress progress-bar-striped active" style="display:none;">
                            <div id="progressBar" class="progress-bar progress-bar-info" role="progressbar"
                                 aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
                                 style="width:0%;">
                            </div>
                        </div>
                        <table id="uploadBatchDg"></table>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <button class="btn btn-info" type="button" onclick="checkForm()">
                        <i class="ace-icon fa fa-check bigger-110"></i> 确定&nbsp;  </button>
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
    /*费用栏*/
    var alloptionHtml = '<div class="form-group costImp">' +
            '<label class="col-sm-3 col-xs-12 control-label no-padding-right"for="repairType">实施费用：</label>' +
            '<div class="col-sm-9 col-xs-12" style="padding: 0px;"><div class="col-xs-8 col-sm-5">' +
            '<select  class="selectpicker form-control payselect"  name="costType" data-style="btn-danger" id="costType"></select></div>' +
            '<button style="z-index: 9; right: 0px;top:3px" type="button" class="cboxCloseC" id="cboxClose">×</button>' +
            '<input type="text" name="money"  maxlength="20" placeholder=金额 value="" class="col-xs-4 money"/></div></div>';
    var allCostList = [];//所选类型的全部费用类型
    var repairTypeList = ${repairTypeList};
    var repairTypeHtml = "";
    var uploadFilePath;
    var image = new Image();
    function setImageURL(url) {
        image.src = url;
    }
    var imgStrArr = [];
    var num = 0;
    for (var i = 0; i < repairTypeList.length; i++) {
        var repairtype = repairTypeList[i];
        repairTypeHtml += "<option value='" + repairtype['wordIndex'] + "'>" + repairtype['wordValue'] + "</option>"
    }
    var repairType = "${order.repairType}";
    $("#repairType").html(repairTypeHtml);
    $("#repairType").val(repairType);
    if($("#orderId").val()!=""){
        findAllCostByType(repairType);
    }
    $(document).ready(function () {
        $(".addUpBtn").on("click", function () {
            $('#lefile').click();
        });
        /*通过type查找所有费用类型*/
        removeFn();
        /*添加附件按钮事件*/
        $('#lefile').change(function () {
            uploadFilePath = $(this).val();
            if (uploadFilePath == null || uploadFilePath == "") {
                layer.msg("请先选择文件");
                return false;
            }
            var obj = $("#lefile");
            var photoExt = obj.val().substr(obj.val().lastIndexOf(".")).toLowerCase();//获得文件后缀名
            if (photoExt != ".xls" && photoExt != ".xlsx" && photoExt != ".docx" && photoExt != ".doc") {
                layer.msg("请选择xls(x)或是doc(s)格式的文件，不支持其他格式文件");
                return false;
            }
            var fileSize = 0;
            var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
            if (isIE && !obj.files) {
                var filePath = obj.val();
                var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                var file = fileSystem.GetFile(filePath);
                fileSize = file.Size;
            } else {
                fileSize = obj.get(0).files[0].size;
            }
            fileSize = Math.round(fileSize / 1024 * 100) / 100; //单位为KB
            if (fileSize > 5000) {
                layer.msg("文件不能大于5M");
                return false;
            }
            var classString = ["danger", "success"];
            var i = Math.round(Math.random() * 1);
            var path1 = uploadFilePath.lastIndexOf("\\");
            var name = uploadFilePath.substring(path1 + 1);
            var realName = name.replace(photoExt, "");
            var html = "<tr class='" + classString[i] + "'style='text-align: center'><td>" + realName + "</td><td>" + photoExt + "</td><td>" + fileSize + "KB</td><td></td>"
                    + "<td><a class='glyphicon glyphicon-remove'></a></td></tr>";
            $("#uploadTable tbody").show().append(html);
            $(".addUpBtn").off("click").on("click", function () {
                layer.msg("请先上传!");
            });
            /*取消文件上传事件*/
            removeFn();
            $("#uploadBtn").on("click", function () {
                uploadFile();
            })
        });
        /*添加费用事件*/
        $("#addBtn").on("click", function () {
            /*如果费用达到上限，讲不再添加*/
            if ($(".selectpicker").size() > allCostList.length) {
                layer.alert("费用类别以达到上限!", {icon: 2, title: '警告'});
                return false;
            } else {
                /*添加多选框*/
                addCostOption();
            }
        });
    });
    $("#repairType").on("change", function () {
        /*通过typeId找到所有的的费用类型*/
        findAllCostByType($(this).val());
        /*通过返回的数据是否为空去判断是否给予添加费用的按钮*/
        if (allCostList.length > 0) {
            $("#addCostBtn").show();
            $(".costSum").hide();
        } else {
            $(".costImp").remove();
            $(".costImpBtn").hide().off("click");
            $(".costSum").show();
        }
    });
    /*增加费用栏事件*/
    function addCostOption() {
        var html = "";
        /*将已经选择的元素转为数组*/
        var selectedObj = $.makeArray($(".costImp option:selected"));
        /*提取元素value*/
        var valArray = $.map(selectedObj, function (item) {
            return $(item).val();
        });
        /*循环出不包括已选择的option*/
        $.each(allCostList, function (index, item) {
            /*判断是否包含已选择的*/
            if ($.inArray(item.id + "", valArray) == -1) {
                html += "<option  value='" + item.id + "'>" + item.costName + "</option>";
            }
        });
        /*填充数据*/
        if (html != "") {
            /*插入空的选项*/
            $("#addCostBtn").before(alloptionHtml);
            $(".selectpicker :last").empty().html(html).selectpicker('refresh');
        }
        /*其他动态事件*/
        $(".cboxCloseC").on("click", function () {
            $(this).parents(".costImp").remove();
        });
        /*验证金额*/
        $(".money").on("blur", function () {
            if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test($(this).val())) {
                layer.alert("金额有误！", {icon: 2, title: '警告'});
                $(this).val(0)
            }
        })
    }
    /*修改页面删除option*/
    function elRomove(_this) {
        $(_this).parents(".costImp").remove();
    }
    /*金额验证*/
    function elTest(_this) {
        if (!/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test($(_this).val())) {
            layer.alert("金额有误！", {icon: 2, title: '警告'});
            $(_this).val(0)
        }
    }
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


			// 这个脚本是 ie6和ie7 通用的脚本
			function custom_close(){
			if 
			(confirm("您确定要关闭本页吗？")){
			window.opener=null;
			window.open('','_self');
			window.close();
			}
			else{}
			}



    //表单校验
    function checkForm() {
        var imgStr = "";
        if (imgStrArr.length > 0) {
            for (var i = 0; i < imgStrArr.length; i++) {
                imgStr += (imgStr == "" ? "" : ",") + imgStrArr[i].value;
            }
        }
        $('#orderImgStr').val(imgStr);
        /*验证金额*/
        var emptyNum=0;
        $.each($(".money"),function (index,items) {
            if(!Trim($(items).val())){
                layer.alert("费用不能为空", {icon: 2, title: '提示'});
                emptyNum++;
                return false;
            }
            if($(items).val()<=0){
                layer.alert("费用必须大于0元", {icon: 2, title: '提示'});
                emptyNum++;
                return false;
            }
        });
        if(emptyNum>0){
            return false;
        }
        if ($(".money").length==0&&$("#repairType").val()==1) {
            layer.alert("安装实施费用不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!Trim($("#contactUser").val())) {
            layer.alert("联系人不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!Trim($("#contactPhone").val())) {
            layer.alert("联系电话不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!Trim($("#contactAddress").val())) {
            layer.alert("联系地址不能为空", {icon: 2, title: '提示'});
            return false;
        }
        if (!Trim($("#orderDesc").val())) {
            layer.alert("问题描述不能为空", {icon: 2, title: '提示'});
            return false;
        }

        /*取出所有上传文件的ID*/
        var fileIds = "";
        /*验证文件上传是否为空*/
        var emptyFile=0;
        $.each($(".glyphicon-remove"), function (index, item) {
            var sizeTo = index + 1;
            fileIds += $(this).data("fid");
            if ($(".glyphicon-remove").size() != sizeTo) {
                fileIds += ",";
            }
           if($(this).data("fid")!=undefined){
               emptyFile++;
         }
        });
        if(emptyFile!=$("#uploadTable tbody tr").size()){
            layer.alert("有未上传文件", {icon: 2, title: '提示'});
            return false;
        }
    		$("#orderFile").val(fileIds);
	        showLoadingToast();
	        $("#orderForm").submit();
        var p = isPhone();
        if(!p){
        	window.opener=null;    
			window.open("","_self");    
			window.close(); 
        }
    	
    }
    //上传文件
    $('.upFile').change(function () {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function () {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            setImageURL(url);
        };
        reader.readAsDataURL(file);
    });
    
    function isPhone() {
    	var flag = true;
   	    var sUserAgent = navigator.userAgent.toLowerCase();
   	    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
   	    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
   	    var bIsMidp = sUserAgent.match(/midp/i) == "midp";
   	    var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
   	    var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
   	    var bIsAndroid = sUserAgent.match(/android/i) == "android";
   	    var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
   	    var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
   	    if (!(bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) ){
   	    	flag = false;
   	    }
        return flag;
    }
    //裁剪图片选择后
    function selectUpload() {
        if (imgStrArr.length >= 4) {
            layer.alert("图片选择最多4张", {icon: 2, title: '提示'});
            return;
        }
        var dataJson = $('#imgData').val();
        var dataObj = eval('(' + dataJson + ')');
        // 以下四个参数由第三步获得
        var x = dataObj.x;
        var y = dataObj.y;
        var width = dataObj.width;
        var height = dataObj.height;
        var canvas = $('<canvas img-num="img' + num + '" style="width:150px;"  width="' + width + '" height="' + height + '" onclick="deleteImg(this)"></canvas>')[0], ctx = canvas
                .getContext('2d');
        ctx.drawImage(image, x, y, width, height, 0, 0, width, height);
        $("#tempImgDiv").remove();
        $("#showImgDiv").append(canvas);
        $("#showImgDiv").append("&nbsp;");
        var data = canvas.toDataURL();
        // dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
        data = data.split(',')[1];
        //$('#orderImgStr').val() == "" ? $('#orderImgStr').val(data) : $('#orderImgStr').val($('#orderImgStr').val()+","+data);
        var img = {};
        img = {key: "img" + num, value: data};
        imgStrArr.push(img);
        num++;
    }

    //删除图片
    function deleteImg(imgObj) {
        layer.confirm("确认删除该图片吗", {icon: 3, title: '提示'}, function (index) {
            for (var i = 0; i < imgStrArr.length; i++) {
                if (imgStrArr[i].key == $(imgObj).attr("img-num")) {
                    imgStrArr.splice(i, 1);//删除该下标元素
                }
            }
            $(imgObj).remove();
            layer.close(index);
        });
    }

    function uploadFile() {
        var fileObj = $("#lefile").get(0).files[0]; // js 获取文件对象
        var FileController = "${ctx}/order/upload"; // 接收上传文件的后台地址
        // FormData 对象
        var form = new FormData();
        // form.append("author", "hooyes"); // 可以增加表单数据
        form.append("file", fileObj); // 文件对象
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function () {
            if (this.status == 200) {
                var blob = eval("(" + xhr.responseText + ")");
                $("#uploadTable tbody tr:last").find("td").eq(3).text(blob.o.createTime.substring(5, 16));
                $(".glyphicon-remove:last").data("fid", blob.o.id);
                layer.msg(blob.m);
            }
            $("#progressBar").parent().removeClass("active");
            $("#progressBar").parent().hide();
        };
        xhr.upload.addEventListener("progress", progressFunction, false);
        xhr.send(form);
        $(".addUpBtn").off("click").on("click", function () {
            $('#lefile').click();
        });
        $("#uploadBtn").off("click");
    }

    function progressFunction(evt) {
        var progressBar = $("#progressBar");
        if (evt.lengthComputable) {
            var completePercent = Math.round(evt.loaded / evt.total * 100) + "%";
            progressBar.width(completePercent);
            $("#uploadBtn").val("正在上传 " + completePercent);
        }
    }
    /*删除上传文件*/
    function ajaxDeleteFile(orderId) {
        $.ajax({
            type: 'post', url: "/serviceapp/order/ajaxDeleteFile", data: {id: orderId},
            dataType: "json",
            success: function (result) {
                result.c == 1 ? layer.msg(result.m) : layer.alert(result.m, {icon: 2, title: '警告'})
            }
        })
    }
    /*删除附件按钮事件*/
    function removeFn() {
        $(".glyphicon-remove").on("click", function () {
            $(".addUpBtn").off("click").on("click", function () {
                $('#lefile').click();
            });
            $("#uploadBtn").off("click");
            if ($(this).data("fid") != undefined) {
                ajaxDeleteFile($(this).data("fid"));
            }
            $(this).parent().parent().remove();
            if($(this).parent().prev().text()==''){
                var lefile = $("#lefile");
                lefile.after(lefile.clone(true).val(""));
                lefile.remove();
            }
        })
    }
    /*通过TYPE查找所有的费用类型*/
    function findAllCostByType(typeId) {
        $.ajax({
            type: 'post', url: "/serviceapp/order/ajaxGetAllCost", data: {typeId: typeId},
            dataType: "json",
            async: false,
            success: function (result) {
                if (result.c == "1") {
                    allCostList = [];
                    for (var i = 0; i < result.o.length; i++) {
                        var cost = result.o[i].orderCostType;
                        allCostList.push(cost);
                    }
                }
            }
        })
    }
</script>
<script type="text/javascript">
    jQuery(function ($) {
        var spinner = $("#spinner").spinner({
            create: function (event, ui) {
                $(this)
                        .next().addClass('btn btn-success').html('<i class="ace-icon fa fa-plus"></i>')
                        .next().addClass('btn btn-danger').html('<i class="ace-icon fa fa-minus"></i>');

                //larger buttons on touch devices
                if ('touchstart' in document.documentElement)
                    $(this).closest('.ui-spinner').addClass('ui-spinner-touch');
            }
        });
    });
</script>
<script src="${ctx}/static/ace/assets/js/jquery-ui.min.js"></script>
<script src="${ctx}/static/ace/assets/js/jquery.ui.touch-punch.js"></script>
<script src="${ctx}/static/ace/assets/js/ace/elements.spinner.js"></script>
<script src="${ctx}/static/cropper/main.js"></script>
<script src="${ctx}/static/cropper/cropper.min.js"></script>
</body>
</html>
