<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">意见反馈管理</h3>
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>
        <!-- div.table-responsive -->
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">  <%--signupForm--%>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid" aria-describedby="dynamic-table_info" style="width: 100%;">
                        <thead>
                        <tr role="row">
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace" id="checkAll">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">用户名
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">用户类型
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">设备型号
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">设备系统
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">反馈内容
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">审核是否通过
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">
                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>反馈时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">管理
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 弹框表单（Modal） -->
<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <form class="form-horizontal" id="feedbackForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="nickName" class="col-lg-3 control-label">用户名</label>
                        <div class="col-lg-7">
                            <input type="text" id="nickName" name="nickName"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-lg-3 control-label">用户类型</label>
                        <div class="col-lg-7">
                            <input type="text" id="type" name="type"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deviceId" class="col-lg-3 control-label">设备型号</label>
                        <div class="col-lg-7">
                            <input type="text" id="deviceId" name="deviceId"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="deviceOs" class="col-lg-3 control-label">设备系统</label>
                        <div class="col-lg-7">
                            <input type="text" id="deviceOs" name="deviceOs"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="content" class="col-lg-3 control-label">反馈内容</label>
                        <div class="col-lg-7">
                            <input type="text" id="content" name="content"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pass" class="col-lg-3 control-label">是否通过</label>
                        <div class="col-lg-1">
                            <input type="checkbox" id="pass" name="pass"
                                   class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn" type="reset" data-dismiss="modal">
                        <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 弹框表单ENDS -->

<!--查询弹框-->
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
                <tr></tr>
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
<script type="text/javascript">
    var _this = {
        v: {
            id: "_this",
            list: [],
            dTable: null,
            property: {
                "user": {"userInfo": {"nickName": "用户名称"}},
                "content": "反馈内容", "createTime": "注册时间",
                "pass": "是否通过"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                _this.fn.dataTableInit();
                $('#checkAll').on('click', function () {
                    $dh.checkAll($(this));
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    _this.fn.deleteRow(ids)
                });
                $("#ui-search-btn").on("click", function () {
                    _this.fn.search();//查询
                });
                $dh.tableSearch(_this.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },

            dataTableInit: function () {
                _this.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "feedback/page",
                        "data": function (d) {
                            // 搜索参数
                            d.name = $('#title_search').val();
                            d.createTime = $('#createTimeS').val();
                            d.endTime = $('#endTimeS').val();
                            return d;
                        }
                    },
                    "columns": [
                        {"data": null},
                        {"data": "user.userInfo.nickName"},
                        {"data": "user.type"},
                        {"data": "user.userLoginInfo.deviceId"},
                        {"data": "user.userLoginInfo.deviceOs"},
                        {"data": "content"},
                        {"data": "pass", "defaultContent": ""},
                        {"data": "createTime", "defaultContent": ""},
                        {"data": null}
                    ],
                    "columnDefs": [/*{
                        orderable: false,//禁用排序
                        targets: [0, 8]   //指定的列
                     },*/
                        {
                            targets: 0,
                            "bSortable": false,
                            render: function (a) {
                                return "<label class='pos-rel'><input type='checkbox' class='ace'  value=" + a.id + " /> <span class='lbl'></span></label>";
                            }
                        },
                        {
                            targets: 2,
                            render: function (a) {
                                var typeStr = "";
                                if (a == '0') {
                                    typeStr = "用户";
                                } else if (a == '1') {
                                    typeStr = "工程师";
                                }
                                return "<td>" + typeStr + "</td>";
                            }
                        },
                        {
                            targets: 4,
                            render: function (a) {
                                var typeStr = "";
                                if (a == '0') {
                                    typeStr = "安卓";
                                } else if (a == '1') {
                                    typeStr = "IOS";
                                }
                                return "<td>" + typeStr + "</td>";
                            }
                        },
                        {
                            targets: 6,
                            render: function (a) {
                                return "<td>" + a ? "通过" : "不通过" + "</td>";
                            }
                        },
                        {
                            targets: -1,
                            render: function (a) {
                                return "<div class='hidden-sm hidden-xs action-buttons'><a class='look-up'>" +
                                        "<i class='ace-icon fa fa-search-plus bigger-130 blue'></i></a><a class='delete'>" +
                                        "<i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>";
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            _this.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            var checkbox = $('td', row).first().find("input[type='checkbox']");
                            _this.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            _this.fn.edit(data);//修改单条记录
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
                    "aaSorting": [ 7, "desc" ],
                    "processing": true, // 显示loading
                    "searching": false, // 取消搜索框
                    "ordering": true, // 字段排序
                    "serverSide": true, // ajax请求时必须写本项,
                    "destroy": true,//重置dataTable
                    "language": {
                        "lengthMenu": "", // 每页显示多少条
                        "zeroRecords": "没有记录！", // 没有记录时显示的内容
                        "info": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项", // 当前页信息
                        "infoEmpty": "显示第 0 至 0 项结果，共 0 项", // 当前页信息-没有记录时
                        "paginate": {
                            "previous": " << ",
                            "next": " >> "
                        }
                    }
                });
                //datatables工具组初始化
                var buttonId = new Array("search", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(_this.v.dTable, buttonId);
            },
            search: function () {
                _this.v.dTable.ajax.reload();
            },

            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("feedback/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                _this.v.dTable.ajax.reload();

                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            look_up: function (data) {
                var html = "";
                for (var key in data) {
                    if (_this.v.property[key] && typeof (_this.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (typeof (data[key]) == 'boolean') {
                            propertyVal = data[key] ? '已通过' : '未通过';
                        }
                        html += _this.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (_this.v.property[key] && _this.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'string') {
                                    if (key2 == 'name') {
                                        html += _this.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                    }
                                } else {
                                    for (var key3 in data[key][key2]) {
                                        if (_this.v.property[key][key2] && _this.v.property[key][key2][key3]) {
                                            if (typeof (data[key][key2][key3]) == 'string') {
                                                if (key3 == 'nickName') {
                                                    html += _this.v.property[key][key2][key3] + ":" + data[key][key2][key3] + "<br/>";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                _this.v.last_gritter = $.gritter.add({
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            edit: function (item) {
                if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                _this.fn.showModal("查看");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == "user") {
                        var typeStr = "";
                        if (item[key].type == '0') {
                            typeStr = "用户";
                        } else if (item[key].type == '1') {
                            typeStr = "工程师";
                        }
                        $("#feedbackForm :input[name='nickName']").val(item[key].userInfo.nickName);
                        $("#feedbackForm :input[name='type']").val(typeStr);
                        $("#feedbackForm :input[name='deviceId']").val(item[key].userLoginInfo.deviceId);
                        $("#feedbackForm :input[name='deviceOs']").val(item[key].userLoginInfo.deviceOs);
                    } else if (key == 'pass') {
                        if (item[key]) {
                            $('#pass').prop("checked", "checked");
                        } else {
                            $('#pass').removeAttr("checked");
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
                $("#modal-form").modal("show");
                $dh.clearForm($("#modal-form"));
                if (title) {
                    $("#modal-form .modal-title").text(title);
                }
            }
        }
    }
    $(document).ready(function () {
        _this.fn.init();
    });
</script>
</body>
</html>
