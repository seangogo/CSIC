<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">订单类别与工程师类别关联管理</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>

        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">  <%--signupForm--%>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid" aria-describedby="dynamic-table_info" style="width:100%">
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
                                aria-label="Price: activate to sort column ascending">服务类型
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">工程师
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
<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <form class="form-horizontal" id="serviceTypeAreaForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="serviceTypeId" class="col-lg-3 control-label">服务类型</label>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="serviceTypeId"
                                    id="serviceTypeId" data-rule="required;">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="serviceAreaId" class="col-lg-3 control-label">工程师</label>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="serviceAreaId"
                                    id="serviceAreaId" data-rule="required;">
                                <option value="0">--请选择--</option>
                            </select>
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
<script src="static/js/util.js"></script>
<script type="text/javascript">
    var serviceTypeArea = {
        v: {
            id: "serviceTypeArea",
            list: [],
            dTable: null,
            property: {
                "serviceTypeName": "订单服务类别", "serviceAreaName": "工程师服务领域"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                serviceTypeArea.fn.dataTableInit();
                serviceTypeArea.fn.validator();
                serviceTypeArea.fn.selectInit("serviceTypeArea/allServiceType", $('#serviceTypeId'), "serviceName");
                serviceTypeArea.fn.selectInit("serviceTypeArea/allServiceArea", $('#serviceAreaId'), "name");
                $("#create").click(function () {
                    if (serviceTypeArea.v.last_gritter) $.gritter.remove(serviceTypeArea.v.last_gritter);
                    serviceTypeArea.fn.showModal("新建关联");
                });
                $('#checkAll').on('click', function () {
                    $dh.checkAll($(this));
                });
                $("#save").click(function () {
                    serviceTypeArea.fn.save();
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    serviceTypeArea.fn.deleteRow(ids)
                });
                $("#ui-search-btn").on("click", function () {
                    serviceTypeArea.fn.search();//查询
                });
                $dh.tableSearch(serviceTypeArea.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            selectChoose: function (elements, result) {
                $.each(elements, function () {
                    $(this).prop('selected', false);
                    if ($(this).val() == result) {
                        $(this).prop("selected", true);
                    }
                })
            },
            selectInit: function (url, element, attr) {
                var html = '';
                $.post(url, function (result) {
                    $.each(result, function (index, item) {
                        var name = util.navigation(item, attr);
                        html += '<option value=' + item.id + '>' + name + '</option>';
                    });
                    element.append(html);
                });
            },
            validator: function () {
                $('#serviceTypeAreaForm').validator({
                    messages: {
                        integer: {
                            '+': "请选择一项",
                        }
                    },
                    fields: {
                        serviceTypeId: "integer[+]",
                        serviceAreaId: "integer[+]"
                    }
                });
            },
            dataTableInit: function () {
                serviceTypeArea.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "serviceTypeArea/page",
                        "data": function (d) {
                            // 搜索参数
                            d.name = $('#title_search').val();
                            d.createTime = $('#createTimeS').val();
                            d.endTime = $('#endTimeS').val();
                            return d;
                        }
                    },
                    /*"bRetrieve": true,*/
                    "columns": [
                        {"data": null},
                        /*{"data": "id", "defaultContent": "无"},*/
                        {"data": "serviceTypeName", "defaultContent": "无"},
                        {"data": "serviceAreaName", "defaultContent": "无"},
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
                            targets: -1,
                            render: function (a) {
                                return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>" +
                                        "<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='delete'><i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>";
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            serviceTypeArea.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            serviceTypeArea.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            serviceTypeArea.fn.edit(data);//修改单条记录
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
                    },
                    /*"retrieve": true,
                     "destroy": true*/
                });
                //datatables工具组初始化
                var buttonId = new Array("search", "create", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(serviceTypeArea.v.dTable, buttonId);
            },
            search: function () {
                serviceTypeArea.v.dTable.ajax.reload();
            },
            deleteRow: function (ids) {
                if (serviceTypeArea.v.last_gritter) $.gritter.remove(serviceTypeArea.v.last_gritter);
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("serviceTypeArea/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                serviceTypeArea.v.dTable.ajax.reload();

                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (item) {
                if (serviceTypeArea.v.last_gritter) $.gritter.remove(serviceTypeArea.v.last_gritter);
                $.post("serviceTypeArea/findServiceTypeAndServiceArea", {id: item.id}, function (result) {
                            serviceTypeArea.fn.selectChoose($('#serviceAreaId option'), result.serviceAreaId);
                            serviceTypeArea.fn.selectChoose($('#serviceTypeId option'), result.serviceTypeId);
                        }, "json"
                );
                serviceTypeArea.fn.showModal("修改关联");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                }
            },
            look_up: function (data) {
                var html = "";
                for (var key in data) {
                    if (serviceTypeArea.v.property[key] && typeof (serviceTypeArea.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        html += serviceTypeArea.v.property[key] + ":" + propertyVal + "<br/>";
                    }
                }
                if (serviceTypeArea.v.last_gritter) $.gritter.remove(serviceTypeArea.v.last_gritter);
                serviceTypeArea.v.last_gritter = $.gritter.add({
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            save: function () {
                if (!$('#serviceTypeAreaForm').isValid()) {  //$('#userForm')
                    return false;
                }
                ;
                $("#serviceTypeAreaForm").ajaxSubmit({
                    url: $('#id').val() != null ? 'serviceTypeArea/update' : 'serviceTypeArea/create',
                    dataType: "json",
                    success: function (result) {
                        serviceTypeArea.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        serviceTypeArea.v.dTable.ajax.reload(null, false);
                    } else {
                        serviceTypeArea.v.dTable.ajax.reload();
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
        serviceTypeArea.fn.init();
    });

</script>
</body>
</html>
