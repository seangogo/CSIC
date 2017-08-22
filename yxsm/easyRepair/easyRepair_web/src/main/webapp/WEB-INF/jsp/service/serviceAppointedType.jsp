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
<%--<%@ include file="../inc/nav.jsp" %>--%>
<%--<div class="main-container ace-save-state" id="main-container">
  &lt;%&ndash;  <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {
        }
    </script>&ndash;%&gt;
    &lt;%&ndash;<%@ include file="../inc/menu.jsp" %>&ndash;%&gt;

    <div class="main-content">--%>
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
                                        <form id="userForm"> <%--userForm--%>
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

                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">服务领域名称
                                                    </th>

                                                    <th class="hidden-480 sorting" tabindex="0"
                                                        aria-controls="dynamic-table" rowspan="1" colspan="1"
                                                        aria-label="Clicks: activate to sort column ascending">图标链接
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
                        <%--<div id="modal-form"  class="modal fade" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header no-padding">
                                        <div class="table-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                <span class="white">×</span>
                                            </button>
                                            <span class=" modal-title">修改用户信息</span>
                                        </div>
                                    </div>

                                    <div class="modal-body no-padding">
                                        <form action="/service/saveServiceAppointedType" id="updateUserForm" method="post">
                                            服务领域名称：<input name="name" type="text"><br>
                                            图标地址：<input name="iconUrl" type="text"><br>
                                            &lt;%&ndash;服务名称：
                                            <select name="serviceType">
                                                <option>计算机软件故障诊断</option>
                                                <option>计算机硬件故障诊断</option>
                                                <option>台式电脑电脑清灰</option>
                                                <option>笔记本电脑清灰</option>
                                                <option>台式电脑清灰</option>
                                                <option>系统重装（windows-xp）</option>
                                                <option>系统重装（windows-7）</option>
                                                <option>系统重装（windows-10）</option>
                                                <option> 优化加速</option>
                                                <option> 搭建编程环境（java）</option>
                                                <option> 搭建编程环境（c++）</option>
                                                <option> 搭建编程环境（.net ）</option>
                                                <option> 手机换屏</option>
                                                <option> 二手手机估卖</option>
                                                <option> 手机维修</option>
                                                <option> 手机ROOT</option>
                                                <option> 电脑故障维修</option>
                                                <option> 优化加速</option>
                                                <option> ipad换屏</option>
                                                <option> 手机救砖</option>
                                                <option> 优化加速</option>
                                                <option> 优化加速</option>
                                                <option> 优化加速</option>
                                                <option> 优化加速</option>
                                            </select><br>&ndash;%&gt;

                                            <input type="hidden" name="id">
                                            <button id="save">提交了</button>
                                        </form>

                                    </div>

                                    &lt;%&ndash;<div class="modal-footer no-margin-top">
                                        <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                            <i class="ace-icon fa fa-times"></i>
                                            Close
                                        </button>
                                        <ul class="pagination pull-right no-margin">
                                            <li class="prev disabled">
                                                <a href="#">
                                                    <i class="ace-icon fa fa-angle-double-left"></i>
                                                </a>
                                            </li>

                                            <li class="active">
                                                <a href="#">1</a>
                                            </li>

                                            <li>
                                                <a href="#">2</a>
                                            </li>

                                            <li>
                                                <a href="#">3</a>
                                            </li>

                                            <li class="next">
                                                <a href="#">
                                                    <i class="ace-icon fa fa-angle-double-right"></i>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>&ndash;%&gt;
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal-dialog -->
                        </div>--%>

                        <div id="modal-form"  class="modal fade" tabindex="-1"  role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">

                                    <form class="form-horizontal" id="serviceAppointedTypeForm" method="post" action="/service/saveServiceAppointedType" role="form">
                                        <div class="modal-header">
                                            <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                                        </div>
                                        <div class="modal-body">
                                            <input type="hidden" id="id" name="id" /> <input type="hidden" id="resourceIds" name="resourceIds" />
                                            <div class="form-group">
                                                <label for="name" class="col-lg-3 control-label">服务领域名称</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="name" name="name"
                                                           class="form-control" placeholder="请输入服务领域名称" data-rule="required" />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="iconUrl" class="col-lg-3 control-label">图标链接</label>
                                                <div class="col-lg-7">
                                                    <input type="text" id="iconUrl" name="iconUrl"
                                                           class="form-control" placeholder="请输入图标链接"<%-- required--%> data-rule="required" />
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
                        </div><!-- /.col -->
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div>
        </div>
    <%--</div><!-- /.main-content -->
    &lt;%&ndash;<%@ include file="../inc/footer.jsp" %>&ndash;%&gt;
</div><!-- /.main-container -->--%>
<script type="text/javascript">
    var serviceAppointedType = {
        v: {
            id: "serviceAppointedType",
            list: [],
            dTable: null
        },
        fn: {
            init: function () {
                serviceAppointedType.fn.dataTableInit();
                $("#create").click(function () {
                    var inputs=$("#modal-form input");
                    for(var i=0;i<inputs.length;i++){
                        inputs.eq(i).attr('disabled',false);
                    }
                    $('#save').show();
                    serviceAppointedType.fn.showModal("新建服务领域");

                })
                $("#save").click(function () {
                    serviceAppointedType.fn.save();
                })

                $("#all-delete").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    serviceAppointedType.fn.deleteRow(checkBox, ids);
                })
            },
            dataTableInit: function () {
                serviceAppointedType.v.dTable = $dh.dataTable($('#dataTables'), {
                    "processing": true,
                    "serverSide": true,
                    "ordering": false,
                    "searching": false,
                    "ajax": {
                        "url": "service/serviceAppointedTypePage",
                        "type": "POST"
                    },
                    "columns": [
                        {"data": "","defaultContent": ""},
                        {"data": "id"},
                        {"data": "name", "defaultContent": ""},
                        {"data": "iconUrl","defaultContent": ""},
                        {"data": "","defaultContent": ""}
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
                        serviceAppointedType.v.list.push(data);
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
                            serviceAppointedType.fn.deleteRow(checkbox, [data.id]);
                        })

                        $('td', row).last().find(".edit").click(function () {
                            serviceAppointedType.fn.edit(data.id);
                            $('#save').show();
                        });

                        $('td', row).last().find(".showInfo").click(function () {
                            serviceAppointedType.fn.edit(data.id);
                            var inputs=$("#modal-form input");
                            for(var i=0;i<inputs.length;i++){
                                inputs.eq(i).attr('disabled','disabled');
                            }
                            $('#myModalLabel').html('查看');
                            $('#save').hide();
                        });


                    },
                    "fnDrawCallback": function (row) {
                        $dh.uiform();
                    }
                });
            },
            deleteRow: function (checkBox, ids) {
                if (ids.length > 0) {
                    console.log(JSON.stringify(ids));
                    $dh.optNotify(function () {
                        $dh.ajax("service/deleteServiceAppointedType", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                serviceAppointedType.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            edit: function (id) {
                //$("#modal-form").modal("show");
                var inputs=$("#modal-form input");
                for(var i=0;i<inputs.length;i++){
                    inputs.eq(i).attr('disabled',false);
                }
                serviceAppointedType.fn.showModal("修改用户");
                var items = serviceAppointedType.v.list;
                $.each(items, function (index, item) {
                    if (item.id == id) {
                        for (var key in item) {
                            var element = $("#modal-form :input[name=" + key + "]");
                            if (element.length > 0) {
                                element.val(item[key]);
                            }
                        }
                    }
                });
            },
            save: function () {
                if (!$('#modal-form').isValid()) {
                    return false;
                };
                $("#serviceAppointedTypeForm").ajaxSubmit({
                    url:"service/saveServiceAppointedType",
                    dataType: "json",
                    data:{},
                    success: function (result) {
                        serviceAppointedType.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {   /*=="0"*/
                    if (action) {
                        serviceAppointedType.v.dTable.ajax.reload(null, false);
                    } else {
                        serviceAppointedType.v.dTable.ajax.reload();
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
        serviceAppointedType.fn.init();
    });

</script>
</body>
</html>
