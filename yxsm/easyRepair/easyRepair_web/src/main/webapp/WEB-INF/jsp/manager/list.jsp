<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">后台用户</h3>
        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>

        <!-- div.table-responsive -->
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">
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
                                aria-label="Domain: activate to sort column ascending">登录名
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">昵称
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">身份
                            </th>--%>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">公司
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">邮箱
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">头像
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">在线状态
                            </th>--%>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">
                                <i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>注册时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">管理
                            </th>

                        </tr>
                        </thead>
                        <tbody></tbody>
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

            <form class="form-horizontal" id="managerForm" method="post" action="manager/saveManager" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <input type="hidden" id="userId" name="userId"/>
                    <div class="form-group">
                        <label for="nickName" class="col-lg-3 control-label">昵称</label>
                        <div class="col-lg-7">
                            <input type="text" id="nickName" name="nickName"
                                   class="form-control" placeholder="请输入昵称"
                                   data-rule="required;<%--remote[manager/checkNickName]--%>"/>
                        </div>
                    </div>
                    <div class="form-group" id="mobile-box">
                        <label for="mobile" class="col-lg-3 control-label">用户账号</label>
                        <div class="col-lg-7">
                            <input type="text" id="mobile" name="mobile"
                                   class="form-control" placeholder="请输入用户账号"
                                   data-rule="required;remote[manager/checkMobile];username"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="userIco" name="userIco"/>
                        <label for="headImage" class="col-lg-3 control-label">头像</label>
                        <div class="col-lg-8">
                            <div id="headImage" class="form-group img_tooltip" name="imagePath">
                                <div class="col-sm-7" style="padding-left: 10px;">
                                    <input type="hidden" id="image" name="imagePath" value="">
                                    <div class="image_show" style="display: none">

                                    </div>
                                    <div class="image_handle" data-toggle="tooltip" data-placement="top"
                                         title="" data-original-title="建议上传宽480px高320px的图片">
                                        <div class="dropped"></div>
                                    </div>
                                </div>
                                <a class="remove-img" href="#"><i class=" ace-icon fa fa-times"></i></a>
                                <%-- <a href="javascript:void(0)" id="removeImg" class="btn btn-info"
                                    role="button">删除图片</a>--%>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" id="create-widget">
                        <label for="roleId" class="col-lg-3 control-label">角色分类</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="roleId"
                                    id="roleId" data-rule="required;">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <div id="passGroup">
                            <div class="form-group">
                                <label for="password" class="col-lg-3 control-label">密码</label>
                                <div class="col-lg-7">
                                    <input type="password" id="password" name="password"
                                           class="form-control" placeholder="请输入密码" data-rule="required;length[5~12]"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="rPassword" class="col-lg-3 control-label">确认密码</label>
                                <div class="col-lg-7">
                                    <input type="password" id="rPassword" name="rPassword"
                                           class="form-control" placeholder="请输入确认密码"
                                           data-rule="required;match[password]"/>
                                </div>
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

<%--<script src="static/sean/global.js"></script>--%>
<%--<script src="static/js/jquery.fs.dropper.js"></script>--%>
<script type="text/javascript">
    var manager = {
        v: {
            id: "manager",
            list: [],
            dTable: null,
            validator: null,
            property: {
                "nickName": "名称",
                "mobile": "手机号",
                "role": {"name": "角色名称"},
                "createTime": "注册时间"
                //"user": {'userInfo': {"nickName": "昵称"}}
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                manager.fn.dataTableInit();
                manager.fn.dropperInit();//图片插件初始化
                manager.fn.validator();
                $(".remove-img").click(function () {
                    manager.fn.clearImageView();
                });
                $("#ui-search-btn").on("click", function () {
                    manager.fn.search();//查询
                })
                manager.fn.selectInit("manager/allRole", $('#roleId'), "name");


                $("#create").click(function () {
                    if (manager.v.last_gritter) $.gritter.remove(manager.v.last_gritter);
                    manager.fn.clearImageView();
                    $('#passGroup').show();
                    $('#mobile-box').show();
                    $('#nickName').prop('disabled', false);
                    $('#rPassword').prop('disabled', false);
                    $('#password').prop('disabled', false);
                    manager.fn.showModal("新建后台用户");
                });
                $('#checkAll').on('click', function () {
                    $dh.checkAll($(this));
                });
                $("#save").click(function () {
                    manager.fn.save();
                })

                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    manager.fn.deleteRow(ids);
                });

                $dh.tableSearch(manager.v.dTable);
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
                        var name = $dh.navigation(item, attr);
                        html += '<option value=' + item.id + '>' + name + '</option>';
                    });
                    element.append(html);
                });
            },
            validator: function () {
                $('#managerForm').validator({
                    messages: {
                        integer: {
                            '+': "请选择一项",
                        }
                    },
                    fields: {
                        roleId: "integer[+]",
                        'nickName': 'required;checkNickName'
                    },
                    rules: {
                        checkNickName: function (element, params, field) {
                            var str = null;
                            var flag = null;
                            $.ajax({
                                url: "manager/checkNickName",
                                dataType: "json",
                                type: "post",
                                async: false,
                                data: {nickName: $(element).val(), id: $('#id').val()},
                                success: function (result) {
                                    flag = result.status;
                                    str = result.msg;
                                }
                            })
                            if (flag) {
                                return str;
                            }
                        }
                    }
                });
            },
            dataTableInit: function () {
                manager.v.dTable = $("#dataTables").DataTable({
                    "ajax": {
                        "type": "post",
                        "url": "manager/page",
                    },
                    "columns": [
                        {"data": null},
                        {"data": "mobile", "defaultContent": ""},
                        {"data": "nickName", "defaultContent": ""},
                        {"data": "userIco", "defaultContent": ""},
                        {"data": "createTime", "defaultContent": ""},
                        {"data": ""}
                    ],
                    "columnDefs": [
                        {
                            targets: 0,
                            "bSortable": false,
                            render: function (a) {
                                return "<label class='pos-rel'><input type='checkbox' class='ace'  value=" + a + "><span class='lbl'></span></label>";
                            }
                        },
                        {
                            targets: -3,
                            render: function (a) {
                                return "<td><img style='height:5%;' src='" + a + "'/></td>";
                            }
                        },
                        {
                            targets: -1,
                            render: function (a) {
                                return "<div class='hidden-sm hidden-xs action-buttons'><a class='blue look-up' href='javascript:void(0);'><i class='ace-icon fa fa-search-plus bigger-130'></i></a>" +
                                        "<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='delete'><i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>"
                            }
                        }
                    ],
                    "rowCallback": function (row, data) {
                        $('td', row).last().find(".delete").click(function () {
                            manager.fn.deleteRow([data.id]);
                        })

                        $('td', row).last().find(".edit").click(function () {
                            manager.fn.edit(data);
                            $('#passGroup').hide();
                            $('#mobile-box').hide()
                        });

                        $('td', row).last().find(".look-up").click(function () {
                            manager.fn.look_up(data);//查看信息
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
                    "aaSorting": [ 4, "desc" ],
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
                var buttonId = new Array("search", "create", "batchDel", "colvis", "copy", "csv", "excel", "pdf", "print");
                $dh.tableTools(manager.v.dTable, buttonId);
            },
            search: function () {
                manager.v.dTable.ajax.reload();
            },
            dropperInit: function () {
                $("#headImage  .dropped").dropper({
                    postKey: "file",
                    action: "utils/uploadFile",
                    postData: {thumbSizes: '480x320'},
                    label: "把文件拖拽到此处"
                }).on("fileComplete.dropper", manager.fn.onFileComplete)
                        .on("fileError.dropper", manager.fn.onFileError)
            },
            onFileError: function (e, file, error) {
                $dh.notify(error, "error");
            },
            onFileComplete: function (e, file, response) {
                if (response != '' && response != null) {
                    manager.fn.viewImage(response.url);
                } else {
                    $dh.notify("抱歉上传失败", "error");
                }
            },
            viewImage: function (image) {
                if (image) {
                    $("#headImage").find(".dropper-input").val("");
                    $("#headImage").find(".image_handle").hide();
                    $("#headImage").find(".image_show").show();
                    $("#headImage").find(".image_show").html("<img src='" + image + "' class='img-responsive'>");//回显
                    $("#userIco").val(image);
                }
            },
            clearImageView: function () {
                $("#headImage").find(".image_show").html("");
                $("#headImage").find(".image_handle").show();
                $("#headImage").find(".dropper-input").val("");//原始名称
                $("#userIco").val("");
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("manager/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                manager.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            look_up: function (data) {
                var title = data['nickName'] ? data['nickName'] : "无";
                var photo = data['userIco'];
                var html = "";
                for (var key in data) {
                    if (manager.v.property[key] && typeof (manager.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        html += manager.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                    }
                }
                var str = "无";
                $.ajax({
                    url: "manager/findRole",
                    data: {id: data.id},
                    success: function (result) {
                        str = result.roleName;
                    },
                    dataType: "json",
                    async: false
                });
                html += manager.v.property['role']['name'] + ":" + str + "<br/>";
                if (manager.v.last_gritter) $.gritter.remove(manager.v.last_gritter);
                manager.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    image: photo,
                    sticky: true
                })
            },
            edit: function (item) {
                manager.fn.clearImageView();
                if (manager.v.last_gritter) $.gritter.remove(manager.v.last_gritter);
                $.post("manager/findRole", {id: item.id}, function (result) {
                    manager.fn.selectChoose($('#roleId option'), result.roleId);
                }, "json");
                manager.fn.showModal("修改后台用户");
                $('#nickName').prop('disabled', true);
                $('#rPassword').prop('disabled', true);
                $('#password').prop('disabled', true);
                for (var key in item) {
                    var element = $("#managerForm :input[name=" + key + "]");
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == 'userIco') {
                        if (item[key] != "") {
                            manager.fn.viewImage(item[key]);
                        }
                    }
                }
            },
            save: function () {
                if ($('#id').val() == "") {
                    if (!$('#managerForm').isValid()) {
                        return false;
                    }
                } else {
                	if ($('#nickName').val() == "" || $('#mobile').val() == "" || $('#roleId').val() == 0) {
                        return false;
                 	}
                }
               	$("#nickName").attr("disabled",false);
                $("#managerForm").ajaxSubmit({
                    url: $('#id').val() != "" ? "manager/update" : "manager/create",
                    dataType: "json",
                    data:{},
                    success: function (result) {
                        manager.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {   /*=="0"*/
                    if (action) {
                        manager.v.dTable.ajax.reload(null, false);
                    } else {
                        manager.v.dTable.ajax.reload();
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
        manager.fn.init();
    });

</script>
</body>
</html>
