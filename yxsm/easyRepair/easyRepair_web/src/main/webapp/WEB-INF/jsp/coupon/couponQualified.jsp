<%--
  Created by IntelliJ IDEA.
  User: Coolkid
  Date: 2016/11/14
  Time: 上午 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../inc/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>Home</title>
    <meta name="description" content="home"/>
    <%@ include file="../inc/meta.jsp" %>
</head>
<body class="no-skin">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/index">Home</a>
                    </li>
                    <li class="active">管理员列表</li>
                </ul><!-- /.breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
                    </form>
                </div><!-- /.nav-search -->
            </div>
            <div class="page-content">
                <div class="page-header">
                    <h1>
                        表
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            Static &amp; Dynamic Tables
                        </small>
                    </h1>
                </div><!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-12">
                                <h3 class="header smaller lighter blue">jQuery dataTables</h3>


                                <!-- div.table-responsive -->
                                <!-- div.dataTables_borderWrap -->
                                <div>
                                    <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="dataTables_length" id="dynamic-table_length"><label>Display
                                                    <select name="dynamic-table_length" aria-controls="dynamic-table"
                                                            class="form-control input-sm">
                                                        <option value="10">10</option>
                                                        <option value="25">25</option>
                                                        <option value="50">50</option>
                                                        <option value="100">100</option>
                                                    </select> records</label></div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div id="dynamic-table_filter" class="dataTables_filter">
                                                    <label>Search:<input type="search" class="form-control input-sm"
                                                                         placeholder=""
                                                                         aria-controls="dynamic-table"></label></div>
                                            </div>
                                        </div>
                                        <form id="userForm">  <%--signupForm--%>
                                            <table id="dataTables"
                                                   class="table table-striped table-bordered table-hover dataTable no-footer"
                                                   role="grid" aria-describedby="dynamic-table_info">
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
                                                        aria-label="Domain: activate to sort column ascending">id
                                                    </th>
                                                    <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                                        rowspan="1" colspan="1"
                                                        aria-label="Price: activate to sort column ascending">服务名称
                                                    </th>

                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">价格
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">服务指定类型
                                                    </th>
                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">上门费
                                                    </th>
                                                    <th class="col-lg-2">
                                                        <div class="hidden-sm hidden-xs action-buttons" >
                                                            <a   title="查看" id="look-up"><i class="ace-icon fa fa-search-plus bigger-130 grey"></i></a>
                                                            <a  title="新增" id="create"><i class="ace-icon fa fa-pencil bigger-130 purple"></i></a>
                                                            <a   title="批量删除" id="all-delete"><i class="ace-icon fa fa-trash-o bigger-130 red"></i></a>
                                                        </div>
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
                        <%--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modal-table">
                            弹框
                        </button>--%>
                        <!-- 弹框表单（Modal） -->
                        <div id="modal-form"  class="modal fade" tabindex="-1"  role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">

                                    <form class="form-horizontal" id="couponQualifiedForm" method="post" role="form">
                                        <div class="modal-header">
                                            <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" id="id" name="id" /> <input type="hidden" id="resourceIds" name="resourceIds" />
                                            <div class="form-group">
                                                <label for="serviceName" class="col-lg-3 control-label">服务名称</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="serviceName" name="serviceName"
                                                           class="form-control" placeholder="请输入服务名称" data-rule="required" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="price" class="col-lg-3 control-label">价格</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="price" name="price"
                                                           class="form-control" placeholder="请输入价格" data-rule="required" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="serviceAppointedType" class="col-lg-3 control-label">服务指定类型</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="serviceAppointedType" name="serviceAppointedType"
                                                           class="form-control" placeholder="请输入服务指定类型" data-rule="required" disabled />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="expenses" class="col-lg-3 control-label">上门费</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="expenses" name="expenses"
                                                           class="form-control" placeholder="请输入上门费" data-rule="required" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="seviceArea" class="col-lg-3 control-label">工程师类型</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="seviceArea" name="seviceArea"
                                                           class="form-control" data-rule="required" />
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
                            <!-- PAGE CONTENT ENDS -->
                        </div>

                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div>
        </div>
<%@ include file="../inc/js.jsp" %>
<script type="text/javascript">
    var create=null;
    var update=null;
    var couponQualified = {
        v: {
            id: "couponQualified",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                couponQualified.fn.dataTableInit();
                $("#create").click(function () {
                    create=true;
                    update=false;
                    var inputs=$("#modal-form input");
                    for(var i=0;i<inputs.length;i++){
                        if(inputs.eq(i).attr('name')!='serviceAppointedType'){
                            inputs.eq(i).attr('disabled',false);
                        }
                    }
                    $('#save').show();
                    couponQualified.fn.showModal("新建领卷资格");
                })
                $("#save").click(function () {
                    couponQualified.fn.save();
                })

                $("#all-delete").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    couponQualified.fn.deleteRow(checkBox, ids)
                })
            },
            dataTableInit: function () {
                couponQualified.v.dTable = $dh.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "searching": false,
                    "ajax": {
                        "url": "coupon/couponQualifiedPage",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "","defaultContent": ""},
                        {"data": "id","defaultContent": "无"},
                        {"data": "serviceName", "defaultContent": "无"},
                        {"data": "price","defaultContent": "无"},
                        {"data": "serviceAppointedType.name","defaultContent": "无"},
                        {"data": "expenses","defaultContent": "无"},
                        {"data": "","defaultContent": "无"}
                    ],
                    "columnDefs": [
                        {
                            "data": null,
                            "defaultContent": "<button type='button'  title='编辑' class='btn btn-primary btn-circle edit'>" +
                            "<i class='fa fa-edit'></i>" +
                            "</button>" +
                            "&nbsp;&nbsp;" +
                            "<button type='button'  title='删除' class='btn btn-danger btn-circle delete'>" +
                            "<i class='fa fa-bitbucket'></i>" +
                            "</button>",
                            "targets": -1
                        }
                    ],
                    "createdRow": function (row, data, index) {
                        couponQualified.v.list.push(data);
                        $('td', row).eq(0).html("<input type='checkbox' value=" + data.id + ">");
                        var node='';
                        node+='<div class="hidden-sm hidden-xs action-buttons">';
                        node+='<a class="blue showInfo" href="javascript:void(0);">';
                        node+='<i class="ace-icon fa fa-search-plus bigger-130"></i>';
                        node+='</a>';
                        node+='<a class="green edit" href="javascript:void(0);">';
                        node+='<i class="ace-icon fa fa-pencil bigger-130"></i>';
                        node+='</a>';
                        node+='<a class="red delete" href="javascript:void(0);">';
                        node+='<i class="ace-icon fa fa-trash-o bigger-130"></i>';
                        node+='</a>';
                        node+='</div>';
                        $('td', row).last().html(node);
                    },
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".delete").click(function () {
                            var checkbox = $('td', row).first().find("input[type='checkbox']");
                            couponQualified.fn.deleteRow(checkbox, [data.id]);
                        })

                        $('td', row).last().find(".edit").click(function () {
                            update=true;
                            create=false;
                            couponQualified.fn.edit(data);
                            $('#save').show();
                        })

                        $('td', row).last().find(".showInfo").click(function () {
                            couponQualified.fn.edit(data);
                            var inputs=$("#modal-form input");
                            var selects=$("#modal-form select");
                             for(var i=0;i<inputs.length;i++){
                                inputs.eq(i).attr('disabled','disabled');
                             }
                            $('#myModalLabel').html('查看领卷资格');
                            $('#save').hide();
                        })

                    },
                    "fnDrawCallback": function (row) {
                        $dh.uiform();
                    }
                });
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("service/deleteCouponQualified", {ids: JSON.stringify(ids)}, function (result) {
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
            edit: function (id) {
                $.post("coupon/findServiceArea",{id:id},function(result){
                            $('#seviceArea').val(result.data.name);
                        },"json"
                );
                var inputs=$("#modal-form input");
                for(var i=0;i<inputs.length;i++){
                    if(inputs.eq(i).attr('name')!='serviceAppointedType'){
                        inputs.eq(i).attr('disabled',false);
                    }
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
            save: function () {
                if (!$('#modal-form').isValid()) {  //$('#userForm')
                    return false;
                };
                var url="";
                if(update){
                    url='service/updateCouponQualified';
                }else if(create){
                    url='service/createCouponQualified';
                }
                $("#couponQualifiedForm").ajaxSubmit({
                    url:url,
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
