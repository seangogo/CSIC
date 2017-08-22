<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">领券资格管理</h3>
        <!--tableTools-container工具组-->
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
                           role="grid" aria-describedby="dynamic-table_info" style="width:100%">
                        <thead>
                        <tr role="row">
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Domain: activate to sort column ascending">领取用户
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">领取次数
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">领取对象
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">创建时间
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

            <form class="form-horizontal" id="couponQualifiedForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/> <input type="hidden" id="resourceIds" name="resourceIds"/>

                    <div class="form-group">
                        <label for="userId" class="col-lg-3 control-label">领取用户</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="userId"
                                    id="userId" data-rule="required;">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="count" class="col-lg-3 control-label">领取次数</label>
                        <div class="col-lg-7">
                            <input type="text" id="count" name="count"
                                   class="form-control" placeholder="请输领取次数"
                                   data-rule="required;digits"/>
                            </div>
                        </div>
                    <div class="form-group">
                        <label for="couponComId" class="col-lg-3 control-label">领取对象</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="couponComId"
                                    id="couponComId" data-rule="required;">
                                <option value="0">--请选择--</option>
                            </select>
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
    var couponQualified = {
        v: {
            id: "couponQualified",
            list: [],
            dTable: null,
            property: {
                "count": "领取次数", "createTime": "注册时间",
                "couponCom": {"name": "领券对象名"},
                "user": {"userInfo": {"nickName": "用户名称"}}
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                couponQualified.fn.dataTableInit();
                couponQualified.fn.validator();
                couponQualified.fn.selectInit("couponQualified/allUser", $('#userId'), ["userInfo", "nickName"]);
                couponQualified.fn.selectInit("couponQualified/allCouponCom", $('#couponComId'), "name");
                $("#create").click(function () {
                    if (couponQualified.v.last_gritter) $.gritter.remove(couponQualified.v.last_gritter);
                    couponQualified.fn.showModal("新建领卷资格");
                });
                $("#save").click(function () {
                    couponQualified.fn.save();
                });

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    couponQualified.fn.deleteRow(ids)
                });
                $("#ui-search-btn").on("click", function () {
                    couponQualified.fn.search();//查询
                });
                $dh.tableSearch(couponQualified.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            validator: function () {
                $('#couponQualifiedForm').validator({
                    messages: {
                        integer: {
                            '+': "请选择一项",
                        }
                    },
                    fields: {
                        userId: "integer[+]",
                        couponComId: "integer[+]"
                    }
                });
            },
            selectChoose: function (elements, result) {
                $.each(elements, function () {
                    $(this).prop('selected', false);
                    if ($(this).val() == result.id) {
                        $(this).prop("selected", true);
                    }
                })
            },
            selectInit: function (url, element, attr) {
                var html = '';
                $.post(url, function (result) {
                    $.each(result, function (index, item) {
                        var name = $dh.navigation(item, attr);
                        html += '<option value=' + item.id + '>' + name + '</option>';
                    });
                    element.append(html);
                });
            },
            dataTableInit: function () {
                couponQualified.v.dTable = $dh.dataTable($('#dataTables'), {
                	"aaSorting": [ 4, "desc" ],
                	"processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "searching": false,
                    "ajax": {
                        "url": "couponQualified/page",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": null},
                        {"data": "user","defaultContent": "无"},
                        {"data": "count", "defaultContent": "无"},
                        {"data": "couponCom","defaultContent": "无"},
                        {"data": "createTime","defaultContent": "无"},
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
                                return "<td>" + a.userInfo.nickName + "</td>";
                            }
                        },
                        {
                            targets: -3,
                            render: function (a) {
                                return "<td>" + a.name + "</td>";
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
                    "createdRow": function (row, data, index) {
                        couponQualified.v.list.push(data);
                    },
                    "rowCallback": function (row, data) {
                    	  $('td', row).eq(2).addClass('lr');
                        $('td', row).last().find(".delete").click(function () {
                            couponQualified.fn.deleteRow([data.id]);
                        })

                        $('td', row).last().find(".edit").click(function () {
                            couponQualified.fn.edit(data);
                            $('#save').show();
                        })

                        $('td', row).last().find(".look-up").click(function () {
                            couponQualified.fn.look_up(data);//查看信息
                            $('#myModalLabel').html('查看领卷资格');
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
                    "fnDrawCallback": function (row) {
                        $dh.uiform();
                    },
                    "ordering": true, // 字段排序
                    "serverSide": true, // ajax请求时必须写本项,
                    "destroy": true//重置dataTable
                });
                //datatables工具组初始化
                var buttonId = new Array("search", "create", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(couponQualified.v.dTable, buttonId);
            },
            search: function () {
                couponQualified.v.dTable.ajax.reload();
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("couponQualified/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                couponQualified.v.dTable.ajax.reload();

                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (item) {
                if (couponQualified.v.last_gritter) $.gritter.remove(couponQualified.v.last_gritter);
                $.post("couponQualified/findUserAndCouponCom", {id: item.id}, function (result) {
                    couponQualified.fn.selectChoose($('#userId option'), result.user);
                    couponQualified.fn.selectChoose($('#couponComId option'), result.couponCom);
                        },"json"
                );
                var inputs=$("#modal-form input");
                for(var i=0;i<inputs.length;i++){
                        inputs.eq(i).attr('disabled',false);
                }
                var selects = $("#modal-form select");
                for (var i = 0; i < selects.length; i++) {
                    selects.eq(i).attr('disabled', false);
                }
                couponQualified.fn.showModal("修改领卷资格");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == "serviceAppointedType") {
                        $("#modal-form :input[name='serviceAppointedType']").val(item[key].name)
                    }
                }
            },
            look_up: function (data) {
                //var title=data['name'];
                var html = "";
                for (var key in data) {
                    if (couponQualified.v.property[key] && typeof (couponQualified.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        html += couponQualified.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (couponQualified.v.property[key] && couponQualified.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'string') {
                                    if (key2 == 'name') {
                                        html += couponQualified.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                    }
                                } else {
                                    for (var key3 in data[key][key2]) {
                                        if (couponQualified.v.property[key][key2] && couponQualified.v.property[key][key2][key3]) {
                                            if (typeof (data[key][key2][key3]) == 'string') {
                                                if (key3 == 'nickName') {
                                                    html += couponQualified.v.property[key][key2][key3] + ":" + data[key][key2][key3] + "<br/>";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (couponQualified.v.last_gritter) $.gritter.remove(couponQualified.v.last_gritter);
                couponQualified.v.last_gritter = $.gritter.add({
                    //title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            save: function () {
                if (!$('#couponQualifiedForm').isValid()) {  //$('#userForm')
                    return false;
                };
                $("#couponQualifiedForm").ajaxSubmit({
                    url: $('#id').val() != "" ? 'couponQualified/update' : 'couponQualified/create',
                    dataType: "json",
                    data:{},
                    success: function (result) {
                        couponQualified.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        couponQualified.v.dTable.ajax.reload(null, false);
                    } else {
                        couponQualified.v.dTable.ajax.reload();
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
        couponQualified.fn.init();
    });

</script>
</body>
</html>
