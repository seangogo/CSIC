<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
<script src="static/js/moment.min.js"></script>
<script src="static/js/fullcalendar.min.js"></script>
<!--查询弹出框-->
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <h3 class="header smaller lighter blue">用户列表</h3>
                <!--tableTools-container工具组-->
                <div class="clearfix">
                    <div class="pull-right tableTools-container"></div>
                </div>
                <!--tableTools-container工具组-->

                <div>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           aria-describedby="dynamic-table_info" style="width:100%;">
                        <thead>
                        <tr role="row">
                            <th class="center" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th>登录名</th>
                            <th>昵称</th>
                            <th>身份</th>
                            <th>邮箱</th>
                            <th>状态</th>
                            <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>注册时间</th>
                            <th>所在城市</th>
                            <th>状态</th>
                            <th>管理</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 弹框表单（Modal） -->
<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form class="form-horizontal" id="seanForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center"></h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="loginName" class="col-lg-2 control-label">登录名</label>
                        <div class="col-lg-8">
                            <input type="text" id="loginName" name="loginName" class="form-control"
                                   placeholder="请输入手机号"
                                   data-rule="required;mobile"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">密码</label>
                        <div class="col-lg-8">
                            <input type="password" id="password" name="password" class="form-control"
                                   placeholder="请输入密码"
                                   data-rule="required;password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-lg-2 control-label">确认密码</label>
                        <div class="col-lg-8">
                            <input type="password" id="password-for" name="password-for" class="form-control"
                                   placeholder="请确认密码" data-rule="required;match[password]"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">用户类型</label>
                        <div class="col-lg-8">
                            <label class="radio-inline">
                                <input value="0" type="radio"
                                       name="type" >用户
                            </label>
                            <label class="radio-inline">
                                <input value="1" type="radio"
                                       name="type">工程师
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">认证</label>
                        <div class="col-lg-8">
                            <label class="radio-inline">
                                <input value="0" type="radio"
                                       name="isAuthentication" checked>未认证
                            </label>
                            <label class="radio-inline">
                                <input value="1" type="radio"
                                       name="isAuthentication">已认证
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userInfo.nickName" class="col-lg-2 control-label">昵称</label>
                        <div class="col-lg-8">
                            <input type="text" id="userInfo.nickName" name="userInfo.nickName"
                                   class="form-control"
                                   placeholder="请输入昵称" data-rule="required;chinese"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userInfo.address" class="col-lg-2 control-label">所在城市</label>
                        <div class="col-lg-8">
                            <input type="text" id="userInfo.address" name="userInfo.address"
                                   class="form-control"
                                   placeholder="请输入所在城市" data-rule="required;chinese"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userInfo.email" class="col-lg-2 control-label">邮箱</label>
                        <div class="col-lg-8">
                            <input type="text" id="userInfo.email" name="userInfo.email" class="form-control"
                                   placeholder="请输入邮箱" data-rule="required;email"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">性别</label>
                        <div class="col-lg-8">
                            <label class="radio-inline">
                                <input value="0" type="radio"
                                       name="userInfo.gender" checked>男
                            </label>
                            <label class="radio-inline">
                                <input value="1" type="radio"
                                       name="userInfo.gender">女
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="photo" name="userInfo.photo"/>
                        <label for="headImage" class="col-lg-2 control-label">头像</label>
                        <input type="hidden" id="image" name="imagePath" value="">
                        <div class="col-lg-8">
                            <div id="headImage" class="form-group img_tooltip" name="imagePath">
                                <div class="col-sm-8" style="padding-left: 10px;">
                                    <div class="image_show" style="display: none">
                                    </div>
                                    <div class="image_handle" data-toggle="tooltip" data-placement="top"
                                         title="" data-original-title="建议上传宽480px高320px的图片">
                                        <div class="dropped"></div>
                                    </div>
                                </div>
                                <a class="remove-img" href="#"><i class=" ace-icon fa fa-times"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" type="button" id="save" >
                        <i class="ace-icon fa fa-check bigger-110"></i> 确认
                    </button>
                    <button class="btn" type="reset" data-dismiss="modal">
                        <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                    </button>
                </div>
            </form>

        </div>
    </div>
    <!-- PAGE CONTENT ENDS -->
</div>
<div id="modal-form2" class="modal fade" tabindex="-2" role="dialog" aria-labelledby="modalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title text-center"></h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-9">
                            <div id="calendar">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                </div>

        </div>
    </div>
    <!-- PAGE CONTENT ENDS -->
</div>

<div>
    <div class="ui-widget-overlay"></div>
    <div class="ui-jqdialog ui-widget ui-widget-content ui-corner-all" id="searchmodfbox_grid-table">
        <div class="ui-jqdialog-titlebar ui-corner-all ui-helper-clearfix">
            <div class="widget-header">查询</div>
            <a class="ui-jqdialog-titlebar-close ui-corner-all" id="closebtn"></a>
        </div>
        <div class="ui-jqdialog-content ui-widget-content">
            <div class="searchFilter">
                <table class="group ui-widget ui-widget-content ui-search-table">
                    <tbody id="searchbox">
                    <tr>
                        <th colspan="5" align="left">
                            <input type="button" id="addbtn" value="+" title="增加查询"
                                   class="add-rule ui-add ui-state-default ui-corner-all btn btn-xs btn-primary">
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table class="EditTable">
                <tbody>
                <tr>
                </tr>
                <tr>
                    <td class="EditButton" style="text-align: right;">
                        <a class="fm-button ui-state-default ui-corner-all fm-button-icon-left ui-reset btn btn-sm btn-info"><span
                                class="ace-icon fa fa-retweet"></span>重置</a>
                    </td>
                    <td class="EditButton">
                        <a class="fm-button ui-state-default ui-corner-all fm-button-icon-right ui-search btn btn-sm btn-purple"
                           id="ui-search-btn"><span
                                class="ace-icon fa fa-search"></span>搜索</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    var _this = {
        v: {
            id: "user",
            dTable: null,
            property: {
                "loginName": "手机号", "type": "类型",
                "isAuthentication": "认证", "isLocked": "锁定", "userInfo": {
                    "score": "当前积分", "money": "账户金额", "wechat": "微信",
                    "QQ": "qq号", "sina": "新浪", "createTime": "注册时间",
                    "nickName": "昵称", "address": '居住地', 'gender': '性别',
                    'honor': '星级', 'invitationCode': '邀请码', 'fansCount': '粉丝数',
                    'email': '邮箱'
                }, "userLoginInfo": {
                    'lastLng': '最后登录经度', 'lastLat': '最后登录纬度',
                    'lastLoginTime': '最后登录时间', 'deviceId': '设备号', 'deviceOs': '设备类型',
                    'token': '融云token'
                }
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                _this.fn.dataTableInit();
                _this.fn.dropperInit();//图片插件初始化
                _this.fn.dataInit();//日历插件初始化
                $("#ui-search-btn").on("click", function () {
                    _this.fn.search();//查询
                })
                //上传插件实例化
                $(".remove-img").click(function () {
                    _this.fn.clearImageView();
                });

                $("#create").on("click", function () {
                    _this.fn.showModal("添加用户");
                }); //新增用户
                $("#save").click(function () {
                    _this.fn.save();
                })

                $("#batchDel").click(function () {
                    var ids = $("#dataTables tbody tr").find('input[type=checkbox]:checked').getInputId();
                    _this.fn.batchDelete(ids);
                })
                //添加表格内容搜索弹框
                $dh.tableSearch(_this.v.dTable);
            },
            dataTableInit: function () {
                _this.v.dTable = $("#dataTables").DataTable({
                    "ajax": {
                        "type": "post",
                        "url": "user/page",
                    },
                    "columns": [{
                        "data": null
                    }, {
                        "data": "loginName"
                    }, {
                        "data": "userInfo.nickName"
                    }, {
                        "data": "type"
                    }, {
                        "data": "userInfo.email",
                        "defaultContent": ""
                    }, {
                        "data": "userLoginInfo.isLogin"
                    }, {
                        "data": "userInfo.createTime",
                        "defaultContent": ""
                    }, {
                        "data": "userInfo.address",
                        "defaultContent": ""
                    },{
                        "data": "isLocked",
                    }, {
                        "data": null
                    }],
                    "aaSorting": [ 6, "desc" ],
                    "columnDefs": [
                        {
                            orderable:false,//禁用排序
                            targets:[0,9]   //指定的列
                        }, {
                        targets: 0,
                        render: function (a) {
                            return "<label class='pos-rel'><input type='checkbox' class='ace'  value=" + a.id + "><span class='lbl'></span></label>";

                        }
                    }, {
                        targets: 3,
                        render: function (a) {
                            return a == 0 ? "用户" : "工程师";
                        }
                    }, {
                        targets: 5,
                        render: function (a) {
                            return a == 1 ? "<i class='ace-icon fa fa-circle light-green'></i><font class='light-green'>在线</font>" :
                                    "<i class='ace-icon fa fa-circle grey'></i><font color='gray'>离线</font>"
                        }
                    },{
                            targets: 8,
                            render: function (a) {
                                var styleClass=a?"fa-lock red":"fa-unlock";
                                return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue lock-unlock' title='锁定用户'><i class='ace-icon fa "+styleClass+" bigger-130'></i></a></div>"
                            }
                     },{
                        targets: 9,
                        render: function (a) {
                            return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up'title='查看信息'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>" +
                                    "<a class='edit'title='修改信息'><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='registration' title='签到记录'><i class='fa fa-pencil-square-o bigger-130 dark'></i></a></div>"
                        }
                    }],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            _this.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".edit").click(function () {
                            _this.fn.edit(data); //修改单条记录
                        });
                        $('td', row).last().find(".registration").click(function () {
                            _this.fn.registration(data); //查看当前用户签到记录
                        });

                        $('td', row).eq(8).find(".lock-unlock").click(function () {
                            _this.fn.lock([data]); //单条记录删除
                        });
                    },
                    "fnServerParams": function (aoData) {
                        if ($("#searchbox .input-elm")) {
                            $.each($(".input-elm"), function () {
                                aoData[this.name] = $(this).val();
                            })
                        }
                        return aoData;
                    },
                    "fnDrawCallback": function () {
                        $dh.uiform();
                    },
                    "processing": true, // 显示loading
                    "searching": false, // 取消搜索框
                    "ordering": true, // 取消字段排序
                    "serverSide": true, // ajax请求时必须写本项,
                    "destroy": true,//重置dataTable
                    "language": {
                        "sProcessing": "处理中...",
                        "sLengthMenu": "显示 _MENU_ 项结果",
                        "sZeroRecords": "没有匹配结果",
                        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                        "sInfoPostFix": "",
                        "sSearch": "搜索:",
                        "bFilter": false,//去掉搜索框
                        "sUrl": "",
                        "sEmptyTable": "表中数据为空",
                        "sLoadingRecords": "载入中...",
                        "sInfoThousands": ",",
                        "oPaginate": {
                            "sFirst": "首页",
                            "sPrevious": "上页",
                            "sNext": "下页",
                            "sLast": "末页"
                        },
                        "oAria": {
                            "sSortAscending": ": 以升序排列此列",
                            "sSortDescending": ": 以降序排列此列"
                        }
                    }
                })

                //datatables工具组初始化
                var buttonId = new Array("search","create", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(_this.v.dTable, buttonId);
            },
            search: function () {
                _this.v.dTable.ajax.reload();
            },
            dropperInit: function () {
                $("#headImage  .dropped").dropper({
                    postKey: "file",
                    action: "utils/uploadFile",
                    postData: {thumbSizes: '480x320'},
                    label: "把文件拖拽到此处"
                }).on("fileComplete.dropper", _this.fn.onFileComplete)
                        .on("fileError.dropper", _this.fn.onFileError)
            }
            ,
            onFileError: function (e, file, error) {
                $dh.notify(error, "error");
            },
            onFileComplete: function (e, file, response) {
                if (response != '' && response != null) {
                    _this.fn.viewImage(response.url);
                } else {
                    $dh.notify("抱歉上传失败", "error");
                }
            },
            viewImage: function (image) {
                if (image) {
                    $("#headImage").find(".dropper-input").val("");
                    $("#headImage").find(".image_handle").hide();
                    $("#headImage").find(".image_show").show();
                    $("#headImage").find(".image_show").html("<img src='" + image + "' class='img-responsive'>");//回显
                    $("#photo").val(image);
                }
            },
            clearImageView: function () {
                $("#headImage").find(".image_show").html("");
                $("#headImage").find(".image_handle").show();
                $("#headImage").find(".dropper-input").val("");//原始名称
                $("#photo").val("");
            },
            lock: function (data) {
                var id=data[0].id;
                var isLocked=data[0].isLocked;
                if (data) {
                        $dh.ajax("user/lock", {
                            id: id,isLocked:!isLocked
                        }, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                _this.v.dTable.ajax.reload(null, false);
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })

                }
            },
            save: function () {
                if ($("#id").val()==""){
                    if (!$('#seanForm').isValid()) {
                        return false;
                    }
                }else {
                    if (!$("#seanForm input[type=text]:not(':first')").isValid()) {
                        return false;
                    }
                }
                $("#seanForm").ajaxSubmit({
                    url: "user/save",
                    dataType: "json",
                    success: function (result) {
                        _this.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            look_up: function (data) {
                var title = data['userInfo']['nickName'];
                var photo = data['userInfo']['photo'];
                var html = "";
                for (var key in data) {
                    if (_this.v.property[key] && typeof (_this.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (typeof (data[key]) == 'boolean') {
                            propertyVal = data[key] ? 'YES' : 'NO';
                        }
                        if (key == "type") {
                            propertyVal = data[key] == '0' ? '用户' : '工程师';
                        }
                        html += _this.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (_this.v.property[key] && _this.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'boolean') {
                                    if (key2 == 'gender') {
                                        var a = data[key][key2] ? '男' : '女';
                                        html += _this.v.property[key][key2] + ":" + a + "<br/>";
                                    }
                                } else {
                                    if (key2 == 'deviceOs') {
                                        data[key][key2] = deviceOs = 1 ? '安卓' : 'IOS';
                                    }
                                    html += _this.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                }
                            }
                        }
                    }
                }
                if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                _this.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success',
                    image: photo,
                    sticky: true
                })
            },
            edit: function (data) {
                _this.fn.showModal("修改用户信息");
                for (var key in data) {
                    /*var element = $("#modal-form :input[name=" + key + "]");
                    if (element.length > 0) {
                        if (key=='loginName'||key=='password'){
                            element.attr("onfocus","this.blur()");
                        }
                        $("#password-for").val(data['password']).attr("onfocus","this.blur()");
                        element.val(data[key]);
                    }
                    if (key == 'type'||key=='isAuthentication') {
                        $("input[name="+key+"]").eq(data[key]).click();
                    }*/
                    $.each($("#seanForm .form-group"),function (index,item) {
                       if (index<=4){
                           $(item).hide();
                       }
                    })
                    if (key == "userInfo") {
                        for (var key2 in data[key]) {
                            var element = $("input[name='userInfo." + key2 + "']");
                            if (element.length > 0) {
                                element.val(data[key][key2]);
                            }
                            if (key2 == 'photo') {
                                _this.fn.viewImage(data[key][key2]);
                            }
                            if (key2 == 'gender') {
                               element.eq(data[key][key2]).click();
                            }
                        }
                    }
                }
            },
            registration:function (data) {
                $("#calendar").fullCalendar('today');
                $("#modal-form2").modal("show");
                $dh.ajax("user/registration", {
                    id:data.id,
                    lastDate:"2017-3-31"
                }, function (result) {
                    if(result.status=='0'){
                        for(var i=0;i<result.data.length;i++){
                            console.log(result.data[i]);
                        }
                    }else{
                        $dh.notify(result.msg,"error");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        _this.v.dTable.ajax.reload(null, false);
                    } else {
                        _this.v.dTable.ajax.reload();
                    }
                    $dh.notify(result.msg, "success");
                } else {
                    $dh.notify(result.msg, "error");
                }
            },
            showModal: function (title) {
                $("#modal-form").modal("show");
                $("#seanForm .form-group").show();
                $dh.clearForm($("#modal-form"));
                 _this.fn.clearImageView();
                if (title) {
                    $("#modal-form .modal-title").text(title);
                }
            },
            dataInit:function () {
                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                var calendar =   $('#calendar').fullCalendar({
                    defaultView: 'month',
                    events: function(start, end, callback) {$.getJSON("user/registration", {start: start.getTime(), end: end.getTime()}, function(result) {alert(result);})}
                }); ;

            }
        }
    }

    $(document).ready(function () {
        _this.fn.init();
   	 $("#searchmodfbox_grid-table").draggable({ containment: 'body' });
    });
</script>

</html>