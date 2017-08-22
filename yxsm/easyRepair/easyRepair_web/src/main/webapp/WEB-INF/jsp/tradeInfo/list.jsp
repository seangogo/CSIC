<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">第三方平台</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>


        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">  <%--signupForm--%>
                    <table id="dataTables" class="table table-striped table-bordered table-hover
                     dataTable no-footer" role="grid" aria-describedby="dynamic-table_info" style="width:100%;">
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
                                aria-label="Domain: activate to sort column ascending">用户
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">支付方式
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">操作人
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">变化金额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">剩余余额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">类型
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">付款时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">备注
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">管理
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
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

            <form class="form-horizontal" id="tradeInfoForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="user" class="col-lg-3 control-label">用户</label>
                        <div class="col-lg-7">
                            <input type="text" id="user" name="user"
                                   class="form-control" placeholder="请输入用户" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="payModel" class="col-lg-3 control-label">支付方式</label>
                        <div class="col-lg-7">
                            <input type="text" id="payModel" name="payModel"
                                   class="form-control" placeholder="请输入支付方式" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="order" class="col-lg-3 control-label">操作人</label>
                        <div class="col-lg-7">
                            <input type="text" id="order" name="order"
                                   class="form-control" placeholder="请输入被操作人" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="amount" class="col-lg-3 control-label">变化金额</label>
                        <div class="col-lg-7">
                            <input type="text" id="amount" name="amount"
                                   class="form-control" placeholder="请输入被关注者" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="balance" class="col-lg-3 control-label">剩余余额</label>
                        <div class="col-lg-7">
                            <input type="text" id="balance" name="balance"
                                   class="form-control" placeholder="请输入被关注者" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-lg-3 control-label">类型</label>
                        <div class="col-lg-7">
                            <input type="text" id="type" name="type"
                                   class="form-control" placeholder="请输入被关注者" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="remarks" class="col-lg-3 control-label">备注</label>
                        <div class="col-lg-7">
                            <input type="text" id="remarks" name="remarks"
                                   class="form-control" placeholder="请输入被关注者" data-rule="required"/>
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" type="button" <%--onclick="onCheck()"--%> id="save">
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
<!-- 弹框表单 ENDS -->
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

<%--<%@ include file="../inc/js.jsp" %>--%>
<script src="static/sean/global.js"></script>
<script type="text/javascript">
    var tradeInfo = {
        v: {
            id: "tradeInfo",
            list: [],
            dTable: null,
            property: {
                "payModel": "支付方式",
                "user": {"userInfo": {"nickName": "用户名称"}},
                "order": {"orderNum": "订单号"}, "amount": "变化金额", "balance": "剩余余额",
                "createTime": "付款时间", "tradeNo": '流水号', "type": "类型",
                "remarks": "备注",
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                tradeInfo.fn.dataTableInit();

                /*$("#create").click(function () {
                    var inputs = $("#modal-form input");
                    for (var i = 0; i < inputs.length; i++) {
                        inputs.eq(i).attr('disabled', false);
                    }
                    $('#save').show();
                    tradeInfo.fn.showModal("新建");
                 })*/
                $('#checkAll').on('click', function () {
                    $dh.checkAll($(this));
                });
                $("#ui-search-btn").on("click", function () {
                    tradeInfo.fn.search();//查询
                });
                $dh.tableSearch(tradeInfo.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
                /*$("#save").click(function () {
                    tradeInfo.fn.save();
                })

                 $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    tradeInfo.fn.deleteRow(checkBox, ids)
                 })*/
            },
            dataTableInit: function () {
                tradeInfo.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "tradeInfo/page",
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
                        {"data": "user"},
                        {"data": "payModel"},
                        {"data": "order"},
                        {"data": "amount"},
                        {"data": "balance"},
                        {"data": "type"},
                        {"data": "createTime"},
                        {"data": "remarks"},
                        {"data": null}
                    ],
                    "columnDefs": [
                        {
                            targets: 0,
                            render: function (a) {
                                return "<label class='pos-rel'><input type='checkbox' class='ace'  value=" + a.id + " /> <span class='lbl'></span></label>";
                            }
                        },
                        {
                            targets: 1,
                            render: function (a) {
                                return "<td>" + a.userInfo.nickName + "</td>";
                            }
                        },
                        {
                            targets: 2,
                            render: function (a) {
                                var arr = ["平台", "支付宝", "微信"];
                                return "<td>" + arr[a - 1] + "</td>";
                            }
                        },
                        {
                            targets: 6,
                            render: function (a) {
                                var arr = ["充值", "提现", "交易"];
                                return "<td>" + arr[a - 1] + "</td>";
                            }
                        },
                        {
                            targets: -1,
                            render: function (a) {
                                return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>";
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                    	 $('td', row).eq(4).addClass('lr');
                    	 $('td', row).eq(5).addClass('lr');
                        $('td', row).last().find(".look-up").click(function () {
                            tradeInfo.fn.look_up(data);//查看信息
                        });
                        /*$('td', row).last().find(".edit").click(function () {
                            tradeInfo.fn.edit(data);//修改单条记录
                            $('#save').show();
                        });
                        $('td', row).last().find(".delete").click(function () {
                            tradeInfo.fn.deleteRow([data.id]);//单条记录删除
                         });*/
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
                    "ordering": true, // 字段排序
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
                var buttonId = new Array("search", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(tradeInfo.v.dTable, buttonId);
            },
            search: function () {
                tradeInfo.v.dTable.ajax.reload();
            },

            /*deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("service/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                tradeInfo.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
             },*/
            /*edit: function (item) {
                tradeInfo.fn.showModal("修改关注");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == "user") {
                        $("#modal-form :input[name='" + key + "']").val(item[key].userInfo.nickName)
                    }
                    if (key == "payModel") {
                        var model = null;
                        switch (item[key]) {
                            case 1:
                                model = '平台';
                                break;
                            case 2:
                                model = '支付宝';
                                break;
                            case 3:
                                model = '微信';
                                break;
                        }
                        $("#modal-form :input[name='" + key + "']").val(model)
                    }
             /!* if (key == "order") {
                     $("#modal-form :input[name='"+key+"']").val(item[key].orderNum);
             }*!/
                    if (key == "type") {
                        var type = null;
                        switch (item[key]) {
                            case 1:
                                type = '充值';
                                break;
                            case 2:
                                type = '提现';
                                break;
                            case 3:
                                type = '交易';
                                break;
                        }
                        $("#modal-form :input[name='" + key + "']").val(type)
                    }
                }
            },
            save: function () {
                if (!$('#modal-form').isValid()) {  //$('#userForm')
                    return false;
                }
                ;
                $("#tradeInfoForm").ajaxSubmit({
                    url: $('#id').val() != "" ? 'tradeInfo/update' : 'tradeInfo/create',
                    dataType: "json",
                    data: {},
                    success: function (result) {
                        tradeInfo.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
             },*/
            look_up: function (data) {
                //var title=data['name'];
                var html = "";
                for (var key in data) {
                    if (tradeInfo.v.property[key] && typeof (tradeInfo.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (key == "type") {
                            switch (propertyVal) {
                                case 1:
                                    propertyVal = "充值";
                                    break;
                                case 2:
                                    propertyVal = "提现";
                                    break;
                                case 3:
                                    propertyVal = "交易";
                                    break;
                                default:
                                    propertyVal = "无";
                                    break;
                            }
                        } else if (key == "payModel") {
                            switch (propertyVal) {
                                case 1:
                                    propertyVal = "平台";
                                    break;
                                case 2:
                                    propertyVal = "支付宝";
                                    break;
                                case 3:
                                    propertyVal = "微信";
                                    break;
                                default:
                                    propertyVal = "无";
                                    break;
                            }
                        }
                        html += tradeInfo.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (tradeInfo.v.property[key] && tradeInfo.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'string') {
                                    if (key2 == 'orderNum') {
                                        html += tradeInfo.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                    }
                                } else {
                                    for (var key3 in data[key][key2]) {
                                        if (tradeInfo.v.property[key][key2] && tradeInfo.v.property[key][key2][key3]) {
                                            if (typeof (data[key][key2][key3]) == 'string') {
                                                if (key3 == 'nickName') {
                                                    html += tradeInfo.v.property[key][key2][key3] + ":" + data[key][key2][key3] + "<br/>";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (tradeInfo.v.last_gritter) $.gritter.remove(tradeInfo.v.last_gritter);
                tradeInfo.v.last_gritter = $.gritter.add({
                    //title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        tradeInfo.v.dTable.ajax.reload(null, false);
                    } else {
                        tradeInfo.v.dTable.ajax.reload();
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
        tradeInfo.fn.init();
    });

</script>
</body>
</html>