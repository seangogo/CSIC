<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">积分和基础参数管理</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm"> <%--userForm--%>
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

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">途径
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">参数A
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">操作
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

            <form class="form-horizontal" id="discountForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="numOne" class="col-lg-3 control-label">参数A</label>
                        <div class="col-lg-7">
                            <input type="text" id="numOne" name="numOne"
                                   class="form-control" placeholder="请输入参数A" data-rule="required"/>
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

<%--<script src="static/sean/global.js"></script>--%>
<script type="text/javascript">
    var discount = {
        v: {
            id: "discount",
            list: [],
            dTable: null,
            property: {
                "type": "类型",
                "numOne": "参数A",
                "numTwo": "预留字段",
                "createTime": "注册时间"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                discount.fn.dataTableInit();
                $("#create").click(function () {
                    if (discount.v.last_gritter) $.gritter.remove(discount.v.last_gritter);
                    discount.fn.showModal("新增积分");

                })
                $('#checkAll').click(function(){
                   $dh.checkAll($(this));
                });
                $("#save").click(function () {
                    discount.fn.save();
                })

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    discount.fn.deleteRow(checkBox, ids);
                }),
                        $("#ui-search-btn").on("click", function () {
                            discount.fn.search();//查询
                        });
                $dh.tableSearch(discount.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            dataTableInit: function () {
                discount.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "discount/page",
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
                        /*{"data": "id"},*/
                        {"data": "type"},
                        {"data": "numOne"},
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
                            targets: 1,
                            render: function (a) {
                                var typeStr = "";
                                switch (a) {
                                    case 0:
                                        typeStr = '注册获取积分';
                                        break;
                                    case 1:
                                        typeStr = '分享';
                                        break;
                                    case 2:
                                        typeStr = '签到';
                                        break;
                                    case 3:
                                        typeStr = '交易';
                                        break;
                                }
                                return "<td>" + typeStr + "</td>";
                            }
                        },
                        {
                            targets: -1,
                            "bSortable": false,
                            render: function (a) {
                                return  "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>" +
                                        "<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a></div>" ;
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".look-up").click(function () {
                            discount.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            discount.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            if (discount.v.last_gritter) $.gritter.remove(discount.v.last_gritter);
                            discount.fn.edit(data);//修改单条记录
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
                    "aaSorting": [ 1, "asc" ],
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
                var buttonId = new Array("search", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(discount.v.dTable, buttonId);
            },
            search: function () {
                discount.v.dTable.ajax.reload();
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    console.log(JSON.stringify(ids));
                    $dh.optNotify(function () {
                        $dh.ajax("discount/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                discount.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (item) {
                discount.fn.showModal("编辑参数");
                    for (var key in item) {
                        var element = $("#modal-form :input[name=" + key + "]");
                        if (element.length > 0) {
                            element.val(item[key]);
                        }
                    }
            },
            look_up: function (data) {
                var html = "";
                for (var key in data) {
                    if (discount.v.property[key] && typeof (discount.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (key == "type") {
                            switch (data[key]) {
                                case 0:
                                    propertyVal = "注册";
                                    break;
                                case 1:
                                    propertyVal = "分享";
                                    break;
                                case 2:
                                    propertyVal = "签到";
                                    break;
                                case 3:
                                    propertyVal = "交易";
                                    break;
                                default:
                                    propertyVal = "无";
                                    break;
                            }
                        }
                        html += discount.v.property[key] + ":" + propertyVal + "<br/>";
                    }
                }
                if (discount.v.last_gritter) $.gritter.remove(discount.v.last_gritter);
                discount.v.last_gritter = $.gritter.add({
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            save: function () {
                if (!$('#discountForm').isValid()) {
                    return false;
                };
                $("#discountForm").ajaxSubmit({
                    url: "discount/update",
                    dataType: "json",
                    success: function (result) {
                        discount.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {   /*=="0"*/
                    if (action) {
                        discount.v.dTable.ajax.reload(null, false);
                    } else {
                        discount.v.dTable.ajax.reload();
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
    };
    $(document).ready(function () {
        discount.fn.init();
    });

</script>
</body>
</html>
