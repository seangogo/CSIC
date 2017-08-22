<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">优惠券管理</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>


        <!-- div.table-responsive -->
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm"> <%--userForm--%>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid" aria-describedby="dynamic-table_info" style="width: 100%;">
                        <thead>
                        <tr role="row">
                            <th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">优惠券名称
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">抵扣金额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">优惠券可领取数量
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">服务类型
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">限制使用金额
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">领券对象
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">领券次数
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">创建时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">是否推送
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

            <form class="form-horizontal" id="couponForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="name" class="col-lg-3 control-label">优惠券名称</label>
                        <div class="col-lg-7">
                            <input type="text" id="name" name="name"
                                   class="form-control" placeholder="请输入优惠券名称" data-rule="required"/>
                            </div>
                        </div>
                    <div class="form-group">
                        <label for="deduction" class="col-lg-3 control-label">抵扣金额</label>
                        <div class="col-lg-7">
                            <input type="text" id="deduction" name="deduction"
                                   class="form-control" placeholder="请输入抵扣金额"
                                   data-rule="required;"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="number" class="col-lg-3 control-label">优惠券可领取数量</label>
                        <div class="col-lg-7">
                            <input type="text" id="number" name="number"
                                   class="form-control" placeholder="请输入优惠券可领取数量"
                                   data-rule="required;digits"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="price" class="col-lg-3 control-label">限制使用金额</label>
                        <div class="col-lg-7">
                            <input type="text" id="price" name="price"
                                   class="form-control" placeholder="请输入限制使用金额"/>
                        </div>
                    </div>
                    <div class="row form-group">
                        <label class="col-lg-3 control-label">使用时间范围</label>
                        <div class="col-lg-7">
                            <div class="input-daterange input-group">
                                <input type="text" id="startTime" name="startTime"
                                       class="form-control" placeholder="请输入开始时间"/>
                                <span class="input-group-addon">
                                            <i class="fa fa-exchange"></i>
                                    </span>
                                <input type="text" id="endTime" name="endTime"
                                       class="form-control" placeholder="请输入结束时间"/>
                                </div>
                        </div>
                        <span class="msg-box" style="margin-left: -15px;" for="endTime"></span>
                    </div>
                    <div class="row form-group">
                        <label class="col-lg-3 control-label">领取时间范围</label>
                        <div class="col-lg-7">
                            <div class="input-daterange input-group">
                                <input type="text" id="getStartTime" name="getStartTime"
                                       class="form-control" placeholder="请输入开始时间"/>
                                <span class="input-group-addon">
                                        <i class="fa fa-exchange"></i>
                                </span>
                                <input type="text" id="getEndTime" name="getEndTime"
                                       class="form-control" placeholder="请输入结束时间"/>
                            </div>
                        </div>
                        <span class="msg-box" style="margin-left: -15px;" for="getEndTime"></span>
                    </div>

                    <div class="form-group">
                        <label for="couponComId" class="col-lg-3 control-label">领券对象</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select <%--autocomplete="off"--%> class="form-control" name="couponComId"
                                                               id="couponComId" data-rule="required">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group" <%--id="show"--%>>
                        <label for="frequency" class="col-lg-3 control-label">领券次数</label>
                        <div class="col-lg-7">
                            <input type="text" id="frequency" name="frequency"
                                   class="form-control" placeholder="请输入领券次数"
                                   data-rule="required;digits"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="serviceTypeId" class="col-lg-3 control-label">服务类型</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select <%--autocomplete="off"--%> class="form-control" name="serviceTypeId"
                                                               id="serviceTypeId" data-rule="required">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="push">
                    <%--<div class="form-group">
                        <label for="treeResources" class="col-lg-3 control-label">服务类型</label>
                        <div class="col-lg-7">
                            <div class="widget-box widget-color-blue2">
                                <div class="widget-header">
                                    <h4 class="widget-title lighter smaller">请选择</h4>
                                </div>
                                <div class="widget-body" style="height: 120px;overflow-y:auto;">
                                    <div class="widget-main padding-8">
                                        <ul id="treeResources"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>--%>
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
                <tr>
                </tr>
                <tr>
                    <td class="EditButton" style="text-align: right;">
                        <a class="fm-button ui-state-default ui-corner-all fm-button-icon-left ui-reset btn btn-sm btn-info"><span
                                class="ace-icon fa fa-retweet"></span>重置</a>
                    </td>
                    <td class="EditButton">
                        <a class="fm-button ui-state-default ui-corner-all fm-button-icon-right ui-search btn btn-sm btn-purple"
                           id="ui-search-btn"><span class="ace-icon fa fa-search"></span>搜索</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="static/js/tree.min.js"></script>
<script src="static/js/moment.min.js"></script>
<script src="static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    var coupon = {
        v: {
            id: "coupon",
            list: [],
            dTable: null,
            treeDate: null,
            tree: null,
            validator: null,
            property: {
                "name": "优惠券名称", "serviceType": {'serviceName': "服务类型名"}, "deduction": "抵扣金额", "price": "限制使用金额",
                "number": "优惠券可领取数量", "frequency": "领券次数", "push": "推送状态",
                "createTime": "注册时间", "startTime": "使用开始时间", "endTime": "使用截止时间",
                "getStartTime": "领取开始时间", "getEndTime": "领取截止时间",
                "couponCom": {'type': '领券对象类型', 'name': '领券对象名', 'price': '限制金额'}
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                /*初始化树*/
                /*coupon.fn.initiateTree()
                //菜单
                var stopPropagation;
                $('#treeResources').on('updated.fu.tree', function (event, data) {
                    if (stopPropagation != 1) {
                        if (data.item.hasClass("tree-branch")) {
                            $.each($(data.item).find(".tree-branch,.tree-item"), function () {
                                if (data.item.hasClass("tree-selected") != $(this).hasClass("tree-selected")) {
                                    $("#treeResources").tree($(this).hasClass("tree-branch") ? "selectFolder" : "selectItem", $(this));
                                }
                            })
                        }
                        //按钮
                        if (data.item.hasClass("tree-item")) {
                            $.each($(data.item).parents(".tree-branch"), function () {
                                //子节点选取而父节点没有选取//选取
                                if ($(data.item).hasClass("tree-selected") && !$(this).hasClass("tree-selected")) {
                                    stopPropagation = 1;
                                    $("#treeResources").tree("selectFolder", $(this));
                                    stopPropagation = 0;
                                }
                            })
                            //不选取,必须有一个子按钮
                            if (!$(data.item).hasClass("tree-selected")) {
                                $.each($(data.item).parents('.tree-branch'), function () {
                                    if ($(this).hasClass("tree-selected") && !$(this).find("li").hasClass("tree-selected")) {
                                        $("#treeResources").tree("selectFolder", $(this))
                                    } else {
                                        return false;
                                    }

                                })
                            }
                        }
                    }
                }).on("loaded.fu.tree", function () {
                    $('#treeResources').tree("discloseAll");
                    coupon.v.tree = $("#treeResources").tree("allItems");
                }).on('exceededDisclosuresLimit.fu.tree', function () {
                    $('#treeResources').data('ignore-disclosures-limit', true);
                    $('#treeResources').tree('discloseAll');
                 });*/

                coupon.fn.dataTableInit();
                coupon.fn.datePickerInit();
                coupon.fn.validatorInit();

                coupon.fn.selectInit("coupon/allServiceType", $('#serviceTypeId'), "serviceName");
                coupon.fn.selectInit("coupon/allCouponCom", $('#couponComId'), "name");
                $("#ui-search-btn").on("click", function () {
                    coupon.fn.search();//查询
                });
                $("#create").click(function () {
                    if (coupon.v.last_gritter) $.gritter.remove(coupon.v.last_gritter);
                    coupon.fn.showModal("新建优惠券");
                });

                $("#save").click(function () {
                    coupon.fn.save();
                })

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    coupon.fn.deleteRow(ids);
                });
                $dh.tableSearch(coupon.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
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
            datePickerInit: function () {
                $('#startTime').datetimepicker({
                    format: 'YYYY-MM-DD hh:mm:ss',
                });
                $('#endTime').datetimepicker({
                    format: 'YYYY-MM-DD hh:mm:ss',
                });
                $('#getStartTime').datetimepicker({
                    format: 'YYYY-MM-DD hh:mm:ss',
                });
                $('#getEndTime').datetimepicker({
                    format: 'YYYY-MM-DD hh:mm:ss',
                });
            },
            validatorInit: function () {
                coupon.v.validator = $("#couponForm").validator({
                    messages: {
                        integer: {
                            '+': "请选择一项",
                        }
                    },
                    fields: {
                        couponComId: "integer[+]",
                        serviceTypeId: "integer[+]",
                        'endTime': 'required;greateThanStartTime',
                        'getEndTime': 'required;greateThanGetStartTime',
                        'price': 'required;digits;greatThanDeduction;'
                    },
                    rules: {
                        greateThanStartTime: function (element) {
                            var endTime = Date.parse(element.value);
                            var startTime = Date.parse($('#startTime').val());
                            if (endTime <= startTime)return '必须大于开始日期';
                        },
                        greateThanGetStartTime: function (element) {
                            var endTime = Date.parse(element.value);
                            var startTime = Date.parse($('#getStartTime').val());
                            if (endTime <= startTime)return '必须大于开始日期';
                        },
                        greatThanDeduction: function (element) {
                            if (parseInt($(element).val()) < parseInt($('#deduction').val()))return '必须大于抵扣金额';
                        }
                    }
                });
            },
            dataTableInit: function () {
                coupon.v.dTable =$("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "coupon/page",
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
                        {"data": "name"},
                        {"data": "deduction", "defaultContent": ""},
                        {"data": "number","defaultContent": ""},
                        {"data": "serviceType","defaultContent": ""},
                        {"data": "price","defaultContent": ""},
                        {"data": "couponCom","defaultContent": ""},
                        {"data": "frequency","defaultContent": ""},
                        {"data": "createTime"},
                        {"data": "push","defaultContent": ""},
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
                            targets: 4,
                            render: function (a) {

                                return "<td>" + a.serviceName + "</td>";
                            }
                        },
                        {
                            targets: -5,
                            render: function (a) {
                                return '<td>' + a.name + '</td>';
                            }
                        },
                        {
                            targets: -2,
                            render: function (a) {
                                var html = null;
                                if (a) {
                                    html = '<td><button type="button" class="btn btn-danger btn-sm isPush" disabled >已推送' +
                                            '</button></td>'
                                } else {
                                    html = '<td><button type="button" class="btn btn-danger btn-sm isPush" >未推送' +
                                            '<i class="ace-icon fa fa-reply icon-only"></i></button></td>'
                                }
                                return html;
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
                    	 $('td', row).eq(2).addClass('lr');
                    	 $('td', row).eq(3).addClass('lr');
                    	 $('td', row).eq(5).addClass('lr');
                    	 $('td', row).eq(7).addClass('lr');
                        $('td', row).last().find(".look-up").click(function () {
                            coupon.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            coupon.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            coupon.fn.edit(data);//修改单条记录
                        });
                        $('td', row).find('button.isPush').on('click', function () {
                            $(this).prop('disabled', true);
                            $(this).text('已推送');
                            data.push = true;
                            $('input[name=push]').val(data.push);
                            $.post('coupon/updatePush', {push: data.push, id: data.id}, function (result) {
                                $dh.notify(result.msg, "success");
                            })
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
                var buttonId = new Array("search","create", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(coupon.v.dTable, buttonId);
            },
            search: function () {
                coupon.v.dTable.ajax.reload();
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("coupon/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                coupon.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (item) {
                if (coupon.v.last_gritter) $.gritter.remove(coupon.v.last_gritter);
                $.post("coupon/findServiceType", {id: item.id}, function (result) {
                            coupon.fn.selectChoose($('#serviceTypeId option'), result);
                        }, "json"
                );
                $.post("coupon/findCouponCom", {id: item.id}, function (result) {
                            coupon.fn.selectChoose($('#couponComId option'), result);
                        }, "json"
                );
                /*coupon.fn.selectChoose($('#serviceTypeId option'), item.serviceType);
                 coupon.fn.selectChoose($('#couponComId option'), item.couponCom);*/
                $('#save').show();
                coupon.fn.showModal("修改优惠卷");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]");
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                }
                //coupon.fn.ajaxFindServiceType(item.id);
            },
            look_up: function (data) {
                var title = data['name'];
                var html = "";
                for (var key in data) {
                    if (coupon.v.property[key] && typeof (coupon.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (typeof (data[key]) == 'boolean') {
                            propertyVal = data[key] ? '已推送' : '未推送';
                        }
                        html += coupon.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (coupon.v.property[key] && coupon.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'string') {
                                    if (key2 == 'serviceName') {
                                        html += coupon.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                    }
                                }
                            }
                        }
                    }
                }
                if (coupon.v.last_gritter) $.gritter.remove(coupon.v.last_gritter);
                coupon.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    sticky: true
                })
            },
            save: function () {
                if (!$('#couponForm').isValid()) {
                    return false;
                };
                /*var ids = [];
                var items = $('#treeResources').tree('selectedItems');
                for (var i in items) if (items.hasOwnProperty(i)) {
                    var item = items[i];
                    ids.push(item.additionalParameters['id']);
                 }*/
                $("#couponForm").ajaxSubmit({
                    url: $('#id').val() != "" ? "coupon/update" : "coupon/create",
                    dataType: "json",
                    /*data: {ids: JSON.stringify(ids)},*/
                    success: function (result) {
                        coupon.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        coupon.v.dTable.ajax.reload(null, false);
                    } else {
                        coupon.v.dTable.ajax.reload();
                    }
                    $dh.notify(result.msg, "success");
                } else {
                    $dh.notify(result.msg, "error");
                }
            },
            /*initiateTree: function () {
                coupon.v.treeDate = function (options, callback) {
                    var $data = null;
                    if (!("text" in options) && !("type" in options)) {
                        $dh.ajax("coupon/allServiceType", {}, function (result) {
                            callback({data: result})
                        });
                        return;
                    }
                    else if ("type" in options && options.type == "folder") {
                        if ("additionalParameters" in options && "children" in options.additionalParameters)
                            $data = options.additionalParameters.children || {};
                        else $data = {}
                    }
                    if ($data != null)setTimeout(function () {
                        callback({data: $data});
                    }, parseInt(Math.random() * 500) + 200);
                }
                $('#treeResources').ace_tree({
                    dataSource: coupon.v.treeDate,
                    multiSelect: true,
                    cacheItems: true,
                    'open-icon': 'ace-icon tree-minus hide',
                    'close-icon': 'ace-icon tree-plus hide',
                    'selectable': true,
                    'selected-icon': 'ace-icon fa fa-check',
                    'unselected-icon': 'ace-icon fa fa-check',
                    'itemSelect': true,
                    'folderSelect': true,
                    'folder-open-icon': 'ace-icon tree-plus',
                    'folder-close-icon': 'ace-icon tree-minus',
                    loadingHTML: '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
                });
             },*/
            showModal: function (title) {
                $("#modal-form").modal("show");
                $dh.clearForm($("#modal-form"));
                //$('input[name=push]').val(false);
                if (title) {
                    $("#modal-form .modal-title").text(title);
                }
            },
            /*ajaxFindServiceType: function (id) {
                $dh.ajax("coupon/findServiceType", {id: id}, function (result) {
                    for (var i = 0; i < result.length; i++) {
                        alert(items[i].data.type)
                        var items = coupon.v.tree;
                        for (var j = 0; j < items.length; j++) {
                            if (items[j].data.type != undefined) {
                                //alert(items[j].data.additionalParameters['id'])
                                if (items[j].data.additionalParameters['id'] == result[i].id) {
                                    items[j].element.addClass("tree-selected")
                                }
                            }
                        }
                    }
                })
             }*/
        }
    }
    $(document).ready(function () {
        coupon.fn.init();
    });

</script>
</body>
</html>
