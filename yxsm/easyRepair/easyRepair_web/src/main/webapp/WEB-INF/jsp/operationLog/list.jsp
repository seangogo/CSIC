<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<body>
<!--查询弹出框-->
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-12">
                <h3 class="header smaller lighter blue">日志列表</h3>
                <!--tableTools-container工具组-->
                <div class="clearfix">
                    <div class="pull-right tableTools-container"></div>
                </div>
                <!--tableTools-container工具组-->

                <div>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           aria-describedby="dynamic-table_info" style="width:100%">
                        <thead>
                        <tr role="row">
                            <th>类型</th>
                            <th>描述</th>
                            <th>方法</th>
                            <th>ip</th>
                            <th>操作时间</th>
                            <th>操作人</th>
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
                "description": "描述", "type": "类型",
                "method": "方法", "requestIp": "请求IP",
                "createTime": "添加时间", "creater": {
                   "nickName":"昵称","mobile":"手机号"
                },
                "exceptionCode": "异常类型", "exceptionDetail": "异常详情",
                "params": "参数"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                _this.fn.dataTableInit();
                $("#ui-search-btn").on("click", function () {
                    _this.fn.search();//查询
                })
                //添加表格内容搜索弹框
                $dh.tableSearch(_this.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            dataTableInit: function () {
                _this.v.dTable = $("#dataTables").DataTable({
                    "ajax": {
                        "type": "POST",
                        "url": "operationLog/page",
                    },
                    "columns": [{
                        "data": "type"
                    }, {
                        "data": "description"
                    }, {
                        "data": "method"
                    }, {
                        "data": "requestIp"
                    }, {
                        "data": "createTime",
                        "defaultContent": ""
                    }, {
                        "data": "creater.nickName",
                        "defaultContent": ""
                    }, {
                        "data": null
                    }],
                    "columnDefs": [{
                        targets: 0,
                        "bSortable": false,
                        render: function (a) {
                            return a == 0 ? "异常" : "正常";
                        }
                    },{
                        targets: 6,
                        render: function (a) {
                            return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a></div>"
                        }
                    }],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            _this.fn.look_up(data);//查看信息
                        });
                    },
                    "fnServerParams": function (aoData) {
                        if ($("#searchbox .input-elm")) {
                            $.each($(".input-elm"), function () {
                                console.log($(this).val());
                                console.log(this.name);
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
                var buttonId = new Array("search", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(_this.v.dTable, buttonId);
            },
            search: function () {
                _this.v.dTable.ajax.reload();
            },
            look_up: function (data) {
                var title = data['description'];
                var html = "";
                var styleClass;
                for (var key in data) {
                    if (_this.v.property[key] && typeof (_this.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (key == "type") {
                            propertyVal = data[key] ? '正常' : '异常';
                            styleClass = data[key] ? "gritter-success" : "gritter-error";
                        }
                        html += _this.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (_this.v.property[key] && _this.v.property[key][key2]) {
                                html += _this.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                            }
                        }
                    }

                }
                if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                _this.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: styleClass,
                    sticky: true
                })
            },
            showModal: function (title) {
                $dh.clearForm($("#modal-form"));
                _this.fn.clearImageView();
                $("#modal-form").modal("show");
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

</html>