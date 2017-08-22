<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">新闻管理</h3>

        <!--tableTools-container工具组-->
        <div class="clearfix">
            <div class="pull-right tableTools-container"></div>
        </div>
        <!--tableTools-container工具组-->


        <!-- div.table-responsive -->
        <!-- div.dataTables_borderWrap -->
        <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                <form id="userForm">  <%--signupForm--%>
                    <table id="dataTables"
                           class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid" aria-describedby="dynamic-table_info" style="width:100%;">
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
                                aria-label="Domain: activate to sort column ascending">所属订单
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">评论时间
                            </th>
                            <th class="sorting" tabindex="0" aria-controls="dynamic-table"
                                rowspan="1" colspan="1"
                                aria-label="Price: activate to sort column ascending">服务星级
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">上门速度星级
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">技术星级
                            </th>
                            <%--<th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">是否审核
                            </th>--%>
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

            <form class="form-horizontal" id="commentsForm" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="newsTitle" class="col-lg-3 control-label">新闻标题</label>
                        <div class="col-lg-7">
                            <input type="text" id="newsTitle" name="newsTitle"
                                   class="form-control" placeholder="请输入新闻标题" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newsDesc" class="col-lg-3 control-label">新闻描述</label>
                        <div class="col-lg-7">
                                <textarea style="resize:none;height:80px;" id="newsDesc" name="newsDesc"
                                          class="form-control" placeholder="请输入新闻描述"
                                          data-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="newsImg" name="newsImg"/>
                        <label for="headImage" class="col-lg-3 control-label">新闻图片</label>
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
                    <div class="form-group">
                        <label for="show" class="col-lg-3 control-label">是否展示</label>
                        <div class="col-lg-1">
                            <input type="checkbox" id="show" name="show"
                                   class="form-control" value=""
                                   onclick="this.checked==true? this.value='true':this.value='false';"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="source" class="col-lg-3 control-label">来源</label>
                        <div class="col-lg-7">
                            <input type="text" id="source" name="source"
                                   class="form-control" placeholder="请输入来源" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sort" class="col-lg-3 control-label">排序</label>
                        <div class="col-lg-7">
                            <input type="text" id="sort" name="sort"
                                   class="form-control" placeholder="请输入排序" data-rule="required;integer[+]"/>
                        </div>
                    </div>
                    <div class="form-group" id="create-widget">
                        <label for="newsTypeId" class="col-lg-3 control-label">新闻分类</label><%--form-field-select-1--%>
                        <div class="col-lg-7">
                            <select autocomplete="off" class="form-control" name="newsTypeId"
                                    id="newsTypeId" data-rule="required;integer[+]">
                                <option value="0">--请选择--</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newsContent" class="col-lg-2 control-label"></label>
                        <div class="col-lg-8">
                            <div class="wysiwyg-editor" id="newsContent"></div>
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
<link href="static/css/jquery.fs.dropper.css" rel="stylesheet">
<script type="text/javascript">
    var _this = {
        v: {
            id: "news",
            list: [],
            dTable: null,
            property: {
                "newsTitle": "新闻标题",
                "newsDesc": "新闻描述",
                "newsContent": "新闻内容",
                "sort": "排序",
                "source": "来源",
                "createTime": "注册时间",
                "newsType": {'name': '新闻分类', 'sort': '排序'}
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                _this.fn.dataTableInit();
                _this.fn.dropperInit();//图片插件初始化
                //_this.fn.editorInit();
                _this.fn.validator();
                //_this.fn.selectInit("comments/allNewsType", $('#newsTypeId'));

                $(".remove-img").click(function () {
                    _this.fn.clearImageView();
                });
                $("#create").click(function () {
                    if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                    $('#newsContent').html("");
                    $('#newsDesc').val("");
                    _this.fn.clearImageView();
                    _thiss.fn.showModal("新建新闻");
                });
                $("#save").click(function () {
                    _this.fn.save();
                });

                $("#batchDel").click(function () {
                    if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    _this.fn.deleteRow(ids)
                });
                $("#ui-search-btn").on("click", function () {
                    _this.fn.search();//查询
                });
                $dh.tableSearch(_this.v.dTable);
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
            selectInit: function (url, element) {
                var html = '';
                $.post(url, function (result) {
                    $.each(result, function (index, item) {
                        html += '<option value=' + item.id + '>' + item.name + '</option>';
                    });
                    element.append(html);
                });
            },
            validator: function () {
                $('#commentsForm').validator({
                    messages: {
                        integer: {
                            '+': "请选择一项",
                        }
                    },
                    fields: {
                        newsTypeId: "integer[+]"
                    }
                });
            },
            dataTableInit: function () {
                _this.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "post",
                        "url": "comments/page",
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
                        {"data": "order.orderNum"},
                        {"data": "createTime", "defaultContent": ""},
                        {"data": "service_star"},
                        {"data": "speed_star", "defaultContent": ""},
                        {"data": "technology_star", "defaultContent": ""},
                        /*{"data": "updateTime", "defaultContent": ""},
                         /!*{"data": "isShow", "defaultContent": ""},*!/
                         {"data": "source", "defaultContent": ""},
                         {"data": "newsType", "defaultContent": ""},*/
                        {"data": null}
                    ],
                    "columnDefs": [
                        {
                            targets: 0,
                            "bSortable": false,
                            render: function (a) {
                                return "<input type='checkbox' value=" + a.id + ">";
                            }
                        },
                        /*{
                         targets: 3,
                         render: function (a) {
                         return "<td><img src='" + a + "' style='height:5%;'/></td>";
                         }
                         },
                         {
                         targets: -2,
                         render: function (a) {
                         return "<td>" + a.name + "</td>";
                         }
                         },*/
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
                            _this.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            _this.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                            _this.fn.edit(data);//修改单条记录
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
                    "aaSorting": [4, "desc"],
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
                $dh.tableTools(_this.v.dTable, buttonId);
            },
            search: function () {
                _this.v.dTable.ajax.reload();
            },
            dropperInit: function () {
                $("#headImage  .dropped").dropper({
                    postKey: "file",
                    action: "utils/uploadFile",
                    postData: {thumbSizes: '480x320'},
                    label: "把文件拖拽到此处"
                }).on("fileComplete.dropper", _this.fn.onFileComplete)
                        .on("fileError.dropper", _this.fn.onFileError)
            },
            onFileError: function (e, file, error) {
                $dh.notify(error, "error");
            },
            onFileComplete: function (e, file, response) {
                if (response != '' && response != null) {
                    _this.fn.viewImage(response.url);
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
                    $("#newsImg").val(image);
                }
            },
            clearImageView: function () {
                $("#headImage").find(".image_show").html("");
                $("#headImage").find(".image_handle").show();
                $("#headImage").find(".dropper-input").val("");//原始名称
                $("#newsImg").val("");
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("comments/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                _this.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            look_up: function (data) {
                var title = data['newsTitle'];
                var photo = data['newsImg'];
                var html = "";
                for (var key in data) {
                    if (_this.v.property[key] && typeof (_this.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        if (typeof (data[key]) == 'boolean') {
                            propertyVal = data[key] ? 'YES' : 'NO';
                        }
                        if (key == "type") {
                            propertyVal = data[key] == '0' ? '用户' : '工程师';
                        }
                        html += _this.v.property[key] + ":" + propertyVal + "<br/>";
                    } else {
                        for (var key2 in data[key]) {
                            if (_this.v.property[key] && _this.v.property[key][key2]) {
                                if (typeof (data[key][key2]) == 'string') {
                                    if (key2 == 'name') {
                                        html += _this.v.property[key][key2] + ":" + data[key][key2] + "<br/>";
                                    }
                                }
                            }
                        }
                    }
                }
                if (_this.v.last_gritter) $.gritter.remove(_this.v.last_gritter);
                _this.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    image: photo,
                    sticky: true
                })
            },
            edit: function (item) {
                _this.fn.clearImageView();
                $.post("comments/findNewsType", {id: item.id}, function (result) {
                            _this.fn.selectChoose($('#newsTypeId option'), result);
                        }, "json"
                );
                _this.fn.showModal("修改新闻");
                for (var key in item) {
                    var element = $("#commentsForm :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == 'newsImg') {
                        if (item[key] != "") {
                            _this.fn.viewImage(item[key]);
                        }
                    }
                    if (key == 'isShow') {
                        if (item[key]) {
                            $('#isShow').prop("checked", "checked");
                        } else {
                            $('#isShow').removeAttr("checked");
                        }
                    }
                    if (key == 'newsContent') {
                        $('#newsContent').html(item[key]);
                    }
                }
            },
            save: function () {
                if (!$('#commentsForm').isValid()) {
                    return false;
                }
                ;

                $("#commentsForm").ajaxSubmit({
                    url: $('#id').val() != "" ? 'comments/update' : 'comments/create',
                    dataType: "json",
                    data: {newsContent: $('#newsContent').html()},
                    success: function (result) {
                        _this.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {
                    if (action) {
                        _this.v.dTable.ajax.reload(null, false);
                    } else {
                        _this.v.dTable.ajax.reload();
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
        _this.fn.init();
    });

</script>
</body>
</html>
