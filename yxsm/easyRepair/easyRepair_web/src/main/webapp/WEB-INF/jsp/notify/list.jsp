<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">支付宝</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>


        <!-- div.table-responsive -->
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer" >
                <form id="userForm">  <%--signupForm--%>
                    <table id="dataTables" class="table table-striped table-bordered table-hover
                     dataTable no-footer" role="grid" aria-describedby="dynamic-table_info" style="width:100%;">
                        <thead>
                        <tr role="row">
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <%--<th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">通知
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">通知类型
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">通知校验ID
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">支付宝分配给开发者的应用Id
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">接口版本
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">签名类型
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">签名
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">支付宝交易号
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">商户订单号
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">商户业务号
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">买家支付宝用户号
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">买家支付宝账号
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">卖家支付宝用户号
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">卖家支付宝账号
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">交易状态
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">订单金额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">实收金额
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">开票金额
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">付款金额
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">集分宝金额
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">总退款金额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">订单标题
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">商品描述
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">付款时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">退款时间
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">支付金额信息
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">回传参数
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">优惠券信息
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">验签状态
                            </th>--%>
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

            <form class="form-horizontal" id="notifyForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="fansUser" class="col-lg-3 control-label">关注者</label>
                        <div class="col-lg-7">
                            <input type="text" id="fansUser" name="fansUser"
                                   class="form-control" placeholder="请输入关注者" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="followUser" class="col-lg-3 control-label">被关注者</label>
                        <div class="col-lg-7">
                            <input type="text" id="followUser" name="followUser"
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
<script type="text/javascript">
    var notify = {
        v: {
            id: "notify",
            list: [],
            dTable: null,
            property: {
                "notify_time": "通知时间", "notify_type": "通知类型",
                "trade_no": "支付宝交易号", "out_trade_no": "商户订单号",
                "out_biz_no": "商户业务号", "buyer_id": "买家支付宝用户号",
                "buyer_id": "买家支付宝账号",
                "trade_status": "交易状态", "total_amount": "订单金额",
                "receipt_amount": "实收金额",
                "buyer_pay_amount": "付款金额",
                "refund_fee": "总退款金额", "subject": "订单标题",
                "body": "商品描述",
                "gmt_create": "创建时间",
                "gmt_payment": "付款时间",
                "gmt_refund": "退款时间",
                "gmt_close": "结束时间",
                "fund_bill_list": "支付金额信息",
                "success": "验签状态"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                notify.fn.dataTableInit();
                $("#ui-search-btn").on("click", function () {
                    notify.fn.search();//查询
                });
                $dh.tableSearch(notify.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
                /*
                $("#create").click(function () {
                    var inputs = $("#modal-form input");
                    for (var i = 0; i < inputs.length; i++) {
                        inputs.eq(i).attr('disabled', false);
                    }
                    $('#save').show();
                    notify.fn.showModal("新建");
                })
                $("#save").click(function () {
                    notify.fn.save();
                })

                $("#all-delete").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    notify.fn.deleteRow(checkBox, ids)
                 })*/
            },
            dataTableInit: function () {
                notify.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "notify/page",
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
                        /*{"data": "notify_time"},*/
                        /*{"data": "notify_type"},*/
                        /*{"data": "notify_id"},*/
                        /*{"data": "app_id"},*/
                        /*{"data": "charset"},*/
                        /*{"data": "version"},
                         {"data": "sign_type"},*/
                        /*{"data": "sign"},*/
                         {"data": "trade_no"},
                        /*{"data": "out_trade_no"},*/
                        /*{"data": "out_biz_no"},*/
                        /*{"data": "buyer_id"},
                         {"data": "buyer_logon_id"},*/
                        /*{"data": "seller_id"},*/
                        /*{"data": "seller_email"},*/
                        {"data": "trade_status"},
                         {"data": "total_amount"},
                         {"data": "receipt_amount"},
                        /*{"data": "invoice_amount"},*/
                         {"data": "buyer_pay_amount"},
                        /*{"data": "point_amount"},*/
                         {"data": "refund_fee"},
                         {"data": "subject"},
                         {"data": "body"},
                        {"data": "gmt_payment"},
                        {"data": "gmt_refund"},
                        /*{"data": "fund_bill_list"},*/
                        /*{"data": "passback_params"},*/
                        /*{"data": "voucher_detail_list"},*/
                        /*{"data": "success"},*/
                        {"data": null}
                    ],
                    "columnDefs": [
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
                                var str = "";
                                if (a.indexOf("SUCCESS") >= 0) {
                                    str = "成功";
                                } else {
                                    str = "失败";
                                }
                                return "<td>" + str + "</td>";
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
                        $('td', row).last().find(".look-up").click(function () {
                            notify.fn.look_up(data);//查看信息
                        });
                        /*$('td', row).last().find(".delete").click(function () {
                         notify.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            notify.fn.edit(data);//修改单条记录
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
                    "aaSorting": [ 9, "desc" ],
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
                $dh.tableTools(notify.v.dTable, buttonId);
            },
            search: function () {
                news.v.dTable.ajax.reload();
            },
            /*deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("service/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                notify.v.dTable.ajax.reload();

                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (item) {
                notify.fn.showModal("修改关注");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == "fansUser" || key == "followUser") {
                        $("#modal-form :input[name='" + key + "']").val(item[key].userInfo.nickName)
                    }
                }
            },
            save: function () {
                if (!$('#notifyForm').isValid()) {  //$('#userForm')
                    return false;
                }
                ;
                $("#notifyForm").ajaxSubmit({
                    url: $('#id').val() != "" ? 'notify/update' : 'notify/create',
                    dataType: "json",
                    data: {},
                    success: function (result) {
                        notify.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
             },*/
            look_up: function (data) {
                var title = data['subject'];
                var html = "";
                for (var key in data) {
                    if (notify.v.property[key] && typeof (notify.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (typeof (data[key]) == 'boolean') {
                            propertyVal = data[key] ? '成功' : '失败';
                        }
                        if (key == "trade_status") {
                            if (data[key].indexOf("SUCCESS") >= 0) {
                                propertyVal = "成功";
                            } else {
                                propertyVal = "失败";
                            }
                        }
                        propertyVal = propertyVal == null ? "无" : propertyVal;
                        html += notify.v.property[key] + ":" + propertyVal + "<br/>";
                    }
                }
                if (notify.v.last_gritter) $.gritter.remove(notify.v.last_gritter);
                notify.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        notify.v.dTable.ajax.reload(null, false);
                    } else {
                        notify.v.dTable.ajax.reload();
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
        notify.fn.init();
    });

</script>
</body>
</html>