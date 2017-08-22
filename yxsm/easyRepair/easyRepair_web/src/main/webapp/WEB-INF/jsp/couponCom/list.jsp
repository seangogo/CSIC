<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">领券对象类型管理</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>


        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">
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
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">领卷对象类型
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">领券对象名
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">创建时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">开始时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">结束时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">限制金额
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

<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <form class="form-horizontal" id="couponComForm" method="post" action="/service/saveCouponCom" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group" id="create-widget">
                        <label for="type" class="col-lg-3 control-label">领券对象类型</label>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="type" id="type"
                                    data-rule="required">
                                <option value="0">新用户</option>
                                <option value="1">订单用户</option>
                                <option value="2">分享</option>
                                <option value="3">最新优惠</option>
                            </select>
                            </div>
                        </div>

                    <div class="form-group">
                        <label for="name" class="col-lg-3 control-label">领券对象名</label>
                        <div class="col-lg-7">
                            <input type="text" id="name" name="name"
                                   class="form-control" placeholder="请输入领券对象名" data-rule="required"/>
                            </div>
                        </div>
                    <div class="form-group">
                        <label for="startTime" class="col-lg-3 control-label">开始时间</label>
                        <div class="col-lg-7">
                            <input type="text" id="startTime" name="startTime"
                                   class="form-control" placeholder="请输入开始时间"
                                   data-rule="required;"/>
                            <%--<span class="input-group-addon">
                                <i class="fa fa-clock-o bigger-110"></i>
                            </span>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="endTime" class="col-lg-3 control-label">结束时间</label>
                        <div class="col-lg-7">
                            <input type="text" id="endTime" name="endTime"
                                   class="form-control" placeholder="请输入结束时间"
                                   data-rule="required;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-lg-3 control-label">限制金额</label>
                        <div class="col-lg-7">
                            <input type="text" id="price" name="price"
                                   class="form-control" placeholder="请输入限制金额"
                                   data-rule="required;digits"/>
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

<%--<script src="static/sean/global.js"></script>--%>
<script src="static/js/moment.min.js"></script>
<script src="static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    var couponCom = {
        v: {
            id: "couponCom",
            list: [],
            dTable: null,
            validator: null,
            property: {
                "name": "领券对象名", "type": "领券对象类型", "price": "限制金额",
                "createTime": "注册时间", "startTime": "开始时间", "endTime": "结束时间"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                couponCom.fn.dataTableInit();
                couponCom.fn.validatorInit();
                couponCom.fn.datePickerInit();
                $("#create").click(function () {
                    if (couponCom.v.last_gritter) $.gritter.remove(couponCom.v.last_gritter);
                    couponCom.fn.showModal("新建领卷对象");

                });
                $('#checkAll').on('click', function () {
                    $dh.checkAll($(this));
                });

                $("#save").click(function () {
                    couponCom.fn.save();
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    couponCom.fn.deleteRow(ids);
                });
                $("#ui-search-btn").on("click", function () {
                    couponCom.fn.search();//查询
                });
                $dh.tableSearch(couponCom.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            datePickerInit: function () {
                var startTime = $('#startTime').datetimepicker({
                    //format: 'MM/DD/YYYY h:mm:ss A',//use this option to display seconds
                    format: 'YYYY-MM-DD hh:mm:ss',
                    /*icons: {
                     time: 'fa fa-clock-o',
                     date: 'fa fa-calendar',
                     up: 'fa fa-chevron-up',
                     down: 'fa fa-chevron-down',
                     previous: 'fa fa-chevron-left',
                     next: 'fa fa-chevron-right',
                     today: 'fa fa-arrows ',
                     clear: 'fa fa-trash',
                     close: 'fa fa-times'
                     }*/
                });
                var endTime = $('#endTime').datetimepicker({
                    format: 'YYYY-MM-DD hh:mm:ss',
                });

            },
            validatorInit: function () {
                couponCom.v.validator = $("#couponComForm").validator({
                    fields: {
                        'endTime': 'required;greateThanStartTime'
                    },
                    rules: {
                        greateThanStartTime: function (element) {
                            var endTime = Date.parse(element.value);
                            var startTime = Date.parse($('#startTime').val());
                            if (endTime <= startTime)return '必须大于开始日期';
                        }
                    }
                });
            },
            dataTableInit: function () {
                couponCom.v.dTable =$("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "couponCom/page",
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
                        {"data": "type", "defaultContent": ""},
                        {"data": "name","defaultContent": ""},
                        {"data": "createTime", "defaultContent": ""},
                        {"data": "startTime","defaultContent": ""},
                        {"data": "endTime","defaultContent": ""},
                        {"data": "price","defaultContent": ""},
                        /*{"data": "cut","defaultContent": ""},*/
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
                                var str = '';
                                switch (a) {
                                    case 0:
                                        str = '新用户';
                                        break;
                                    case 1:
                                        str = '订单用户';
                                        break;
                                    case 2:
                                        str = '分享';
                                        break;
                                    case 3:
                                        str = '最新优惠';
                                        break;
                                }
                                return "<td>" + str + "</td>";
                            }
                        },
                        {
                            targets: -1,
                            render: function (a) {
                                return  "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>" +
                                        "<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='delete'><i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>" ;
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                    	$("td",row).eq(6).addClass('lr');
                        $('td', row).last().find(".look-up").click(function () {
                            couponCom.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            couponCom.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            couponCom.fn.edit(data);//修改单条记录
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
                var buttonId = new Array("search", "create", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(couponCom.v.dTable, buttonId);
            },
            search: function () {
                couponCom.v.dTable.ajax.reload();
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    console.log(JSON.stringify(ids));
                    $dh.optNotify(function () {
                        $dh.ajax("couponCom/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                couponCom.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            look_up: function (data) {
                var title = data['name'];
                var html = "";
                for (var key in data) {
                    if (couponCom.v.property[key] && typeof (couponCom.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (key == "type") {
                            //propertyVal=data[key]
                            switch (propertyVal) {
                                case 0:
                                    propertyVal = "新用户";
                                    break;
                                case 1:
                                    propertyVal = "订单用户";
                                    break;
                                case 2:
                                    propertyVal = "分享";
                                    break;
                                case 3:
                                    propertyVal = "最新优惠";
                                    break;
                                default:
                                    propertyVal = "无";
                                    break;
                            }
                        }
                        html += couponCom.v.property[key] + ":" + propertyVal + "<br/>";
                    }
                }
                if (couponCom.v.last_gritter) $.gritter.remove(couponCom.v.last_gritter);
                couponCom.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            edit: function (item) {
                if (couponCom.v.last_gritter) $.gritter.remove(couponCom.v.last_gritter);
                couponCom.fn.showModal("修改领卷对象");
                for (var key in item) {
                    var input = $("#modal-form :input[name=" + key + "]");
                    if (input.length > 0) {
                        input.val(item[key]);
                    }
                    if (key == 'type') {
                        var select = $("#modal-form #type");
                        var options = select.find('option');
                        options.each(function () {
                            if ($(this).val() == item[key]) {
                                $(this).prop('selected', true);
                            }
                        })
                    }

                    if (key == 'cut') {
                        if (item[key] === true) {
                            $('#cut').prop("checked", "checked");
                        } else {
                            $('#cut').removeAttr("checked");
                        }
                    }
                }

            },
            save: function () {
                if (!$('#couponComForm').isValid()) {
                    return false;
                };
                $("#couponComForm").ajaxSubmit({
                    url: $('#id').val() != "" ? "couponCom/update" : "couponCom/create",
                    dataType: "json",
                    success: function (result) {
                        couponCom.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {   /*=="0"*/
                    if (action) {
                        couponCom.v.dTable.ajax.reload(null, false);
                    } else {
                        couponCom.v.dTable.ajax.reload();
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
        couponCom.fn.init();
    });

</script>
</body>
</html>
