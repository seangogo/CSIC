<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
<!--数据呈现-->
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <h3 class="header smaller lighter blue">订单管理</h3>
                <div class="clearfix">
                    <div class="pull-right tableTools-container"></div>
                </div>

                <div>
                    <table id="dataTables"
                           class="table table-striped table-b_thised table-hover dataTable no-footer table-bordered"
                           style="width:100%">
                        <thead>
                        <tr>
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace checkall" >
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="hidden-480">订单编号</th>
                            <th>订单状态</th>
                            <th>订单类型</th>
                            <th>发单人</th>
                            <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>发布时间</th>
                            <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>预约时间</th>
                            <th>接单人</th>
                            <th>管理</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <!--查询弹出框呼出按钮
                    <div style="b_this-bottom: 1px solid #e0e0e0; padding: 12px;background-color: #EFF3F8;">
                        <div class="ui-pg-div" id="searchTableBtn" style="width:80px"><span
                                class="ui-icon ace-icon fa fa-search orange"></span><span
                                style="cursor: pointer;">查询</span></div>
                    </div>
                    	查询弹出框呼出按钮-->
                </div>
            </div>
        </div>
    </div>
</div>
<!--数据修改弹出框 -->
<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <form class="form-horizontal" id="_thisForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel"></h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="name" class="col-lg-2 control-label">角色名称</label>
                        <div class="col-lg-8">
                            <input type="text" id="name" name="name"
                                   class="form-control" data-rule="required " placeholder="请输入角色名称"
                                   required/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-lg-2 control-label">角色描述</label>
                        <div class="col-lg-8">
                            <input type="text" id="description" name="description"
                                   class="form-control" data-rule="required" placeholder="请输入角色描述"
                                   required/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="treeResources" class="col-lg-2 control-label">权限</label>
                        <div class="col-lg-8">
                            <div class="widget-box widget-color-blue2">
                                <div class="widget-header">
                                    <h4 class="widget-title lighter smaller">请选择</h4>
                                </div>

                                <div class="widget-body">
                                    <div class="widget-main padding-8">
                                        <ul id="treeResources"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" type="button" id="save">
                        <i class="ace-icon fa fa-check bigger-110"></i> 确认
                    </button>
                    <button class="btn" type="reset" data-dismiss="modal">
                        <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--查询弹出框 -->
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
            dTable: null
        },
        fn: {
            init: function () {
                _this.fn.dataTableInit();
                //排序时需要传递的th的索引index和点击次数thisCount属性，将这两个参数传递到后台，后台获取index和thisCount即可以判断当前点击的是哪个th和点击的次数是单数还是双数。
                $("#dataTables th").each(function () {
                    $(this).attr("count", "0");
                })

                var index; //    当前被点击的th索引值,如果被禁止排序的索引返回null，其他的是其正常索引值。
                var thisCount; //     当前被点击的th被点击次数，单数为1，双数为0。

                $("#dataTables").on('click', "th", function () {

                    index = $(this).index();

                    $(this).attr("count", (parseInt($(this).attr("count")) + 1) % 2);

                    thisCount = $(this).attr("count");

                    if ($(this).hasClass('sorting_disabled')) {

                        $(this).removeAttr('count');

                        index = null;

                    }

                });

                //添加表格内容搜索弹框
                $dh.tableSearch(_this.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            dataTableInit: function () {
                _this.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "GET",
                        "async": true,
                        "url": "order/page",
                        "data": function (d) {
                            // 搜索参数
                            /* 	d.name = $('#title_search').val();
                             d.createTime = $('#createTimeS').val();
                             d.endTime = $('#endTimeS').val(); */
                            return d;
                        }
                    },
                    "columns": [{
                        "data": "id",
                        "defaultContent": ""
                    },
                        {
                            "data": "orderNum",
                            "defaultContent": ""
                        },
                        {
                            "data": "status",
                            "defaultContent": ""
                        },
                        {
                            "data": "serviceType.serviceName",
                            "defaultContent": ""
                        },
                        {
                            "data": "user.userInfo.nickName",
                            "defaultContent": ""
                        },
                        {
                            "data": "orderInfo.createTime",
                            "defaultContent": ""
                        },
                        {
                            "data": "appointmentTime",
                            "defaultContent": ""
                        },
                        {
                            "data": "repair.userInfo.nickName",
                            "defaultContent": ""
                        },
                    ],
                    "columnDefs": [
                        {
                            targets: 2,
                            render: function (a) {
                                var arr = ["已下单", "已接单", "预付款", "确认支付", "待评价", "已完成", "已取消"];
                                return arr[a - 1];
                            }
                        },
                        {
                            targets: 8,
                            render: function (a) {
                                return '<div class="hidden-sm hidden-xs action-buttons">' +
                                        '<a class="look-up"><i class="ace-icon fa fa-search-plus bigger-130 blue"></i></a></div>';
                            }
                        },
                        {
                            targets: 0,
                            "orderable": false
                        }
                    ],

                    "createdRow": function (row, data, index) {
                        $('td', row).eq(0).html("<label class='pos-rel'><input type='checkbox' class='ace'  value=" + data.id + " /> <span class='lbl'></span></label>");
                    },
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            //_this.fn.lookUp(data); //单条记录查看
                            var da = JSON.stringify(data);
                            sessionStorage.obj = da;
                            $("#page-content").load("order/detail");
                        })
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
                    "aaSorting": [ 5, "desc" ],
                    "processing": true, // 显示loading
                    "searching": false, // 取消搜索框
                    "ordering": true, // 字段排序
                    "serverSide": true, // ajax请求时必须写本项
                    "language": {
                        "sProcessing": "处理中...",
                        "sLengthMenu": "显示 _MENU_ 项结果",
                        "sZeroRecords": "没有匹配结果",
                        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                        "sInfoPostFix": "",
                        "sSearch": "搜索:",
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
                });

                //datatables工具组初始化
                var buttonId = new Array("search","colvis", "copy", "excel", "pdf", "print");
                $dh.tableTools(_this.v.dTable, buttonId);

            },
            edit: function (data) {
                _this.fn.showModal("修改用户信息");
                for (var key in data) {
                    var element = $("#modal-form :input[name=" + key + "]");
                    if (element.length > 0 && key != "password") {
                        element.val(data[key]);
                    }
                    if (key == 'type') {
                        if (data[key] == '0') {
                            $("#add-form :input[name=" + key + "]").prop('checked', false);
                            $("#add-form :input[name=" + key + "]").eq(0).prop('checked', true);
                        } else if (data[key] == '1') {
                            $("#add-form :input[name=" + key + "]").prop('checked', false);
                            $("#add-form :input[name=" + key + "]").eq(1).prop('checked', true);
                        }
                    }
                    if (key == "userInfo") {
                        $("#modal-form :input[name='userInfo.score']").val(data[key].score)//积分
                        $("#modal-form :input[name='userInfo.address']").val(data[key].address);//地址
                        $("#modal-form :input[name='userInfo.honor']").val(data[key].honor);//荣誉值
                        $("#modal-form :input[name='userInfo.nickName']").val(data[key].nickName);//昵称
                        $("#modal-form :input[name='userInfo.photo']").val(data[key].photo);//头像
                        $("#modal-form :input[name='userInfo.email']").val(data[key].email);//邮箱
                        $("#modal-form :input[name='userInfo.gender']").val(data[key].gender);//邮箱
                        if (data[key].photo != "") {
                            var lastName = data[key].photo.split("/");
                            var mockFile = {name: lastName[lastName.length - 1], accepted: true};
                            if (_this.v.myDropzone.options.maxFiles > 0) {
                                _this.v.myDropzone.emit("addedfile", mockFile);
                                _this.v.myDropzone.emit("thumbnail", mockFile, data[key].photo);
                                _this.v.myDropzone.emit("complete", mockFile);
                                console.log(_this.v.myDropzone.options.maxFiles);
                                _this.v.myDropzone.options.maxFiles -= 1;
                                _this.v.myDropzone.on("removedfile", function () {
                                    //删除文件时触发的方法
                                    _this.v.myDropzone.options.maxFiles = 1;
                                });
                            }
                        }
                    }
                }
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
                $dh.clearForm($("#modal-form"));
                if (title) {
                    $("#modal-form .modal-title").text(title);
                }
                $("#modal-form").modal("show");
            }
        }
    }
    $(document).ready(function () {
        _this.fn.init();
    });
</script>
</html>
