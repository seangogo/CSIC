<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<div class="row">
    <div class="col-xs-12">
        <h3 class="header smaller lighter blue">工程师服务领域</h3>
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
                                    <input type="checkbox" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">服务领域
                            </th>

                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">图标链接
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">创建时间
                            </th>
                            <th class="hidden-480 sorting" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1"
                                aria-label="Clicks: activate to sort column ascending">订单服务类别管理
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

            <form class="form-horizontal" id="serviceAreaForm" method="post"
                  action="/service/saveServiceArea" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center" id="myModalLabel">修改</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="id" name="id"/>
                    <div class="form-group">
                        <label for="name" class="col-lg-3 control-label">服务领域</label>
                        <div class="col-lg-7">
                            <input type="text" id="name" name="name"
                                   class="form-control" placeholder="请输入服务领域名称" data-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" id="iconUrl" name="iconUrl"/>
                        <label for="headImage" class="col-lg-3 control-label">工程师头像</label>
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
    <!-- PAGE CONTENT ENDS -->
</div><!-- /.col -->
<!-- PAGE CONTENT ENDS -->
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
<!--树-->
<div id="modal-tree" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <form class="form-horizontal" id="serviceAreaTree" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title text-center">关联订单服务类别管理</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="serviceAreaId" name="serviceAreaId"/>
                    <div class="form-group">
                        <label for="treeResources" class="col-lg-3 control-label">订单服务类别管理</label>
                        <div class="col-lg-7">
                            <div class="widget-box widget-color-blue2">
                                <div class="widget-header">
                                    <h4 class="widget-title lighter smaller">请选择</h4>
                                </div>
                                <div class="widget-body" style="height: 240px;overflow-y:auto;">
                                    <div class="widget-main padding-8">
                                        <ul id="treeResources"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-info" type="button" id="saveTree">
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
<link href="static/css/jquery.fs.dropper.css" rel="stylesheet">
<script type="text/javascript">
    var serviceArea = {
        v: {
            id: "serviceArea",
            list: [],
            dTable: null,
            tree: null,
            treeDate: null,
            property: {
                "name": "服务领域名称", "iconUrl": "服务类别图标路径"
            },
            last_gritter: null
        },
        fn: {
            init: function () {
                /*初始化树*/
                serviceArea.fn.initiateTree();
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
                    serviceArea.v.tree = $("#treeResources").tree("allItems");
                }).on('exceededDisclosuresLimit.fu.tree', function () {
                    $('#treeResources').data('ignore-disclosures-limit', true);
                    $('#treeResources').tree('discloseAll');
                });
                serviceArea.fn.dataTableInit();

                serviceArea.fn.dropperInit();//图片插件初始化
                $(".remove-img").click(function () {
                    serviceArea.fn.clearImageView();
                });
                $("#create").click(function () {
                    if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                    serviceArea.fn.clearImageView();
                    serviceArea.fn.showModal("新建工程师服务领域");

                });

                $("#save").click(function () {
                    serviceArea.fn.save();
                });
                $("#saveTree").click(function () {
                    serviceArea.fn.saveTree();
                })

                $("#batchDel").click(function () {
                    if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();//true
                    serviceArea.fn.deleteRow(ids);
                });
                $("#ui-search-btn").on("click", function () {
                    serviceArea.fn.search();//查询
                });
                $dh.tableSearch(serviceArea.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            dataTableInit: function () {
                serviceArea.v.dTable = $("#dataTables").DataTable({
                    "ajax": { // ajax请求
                        "type": "GET",
                        "url": "serviceArea/page",
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
                        {"data": "name", "defaultContent": ""},
                        {"data": "iconUrl", "defaultContent": ""},
                        {"data": "createTime"},
                        {"data": null},
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
                            targets: 2,
                            render: function (a) {
                                return "<td><img style=' height:5%;' src='" + a + "'/></td>";
                            }
                        },
                        {
                            targets: -2,
                            render: function (a) {
                                return "<div class='hidden-sm hidden-xs action-buttons text-center'><a class='btn btn-xs btn-warning find-mapping' href='javascript:void(0);'>" +
                                        "<i class='ace-icon fa fa-flag bigger-120'></i></a></div>";
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
                            serviceArea.fn.look_up(data);//查看信息
                        });
                        $('td', row).last().find(".delete").click(function () {
                            if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                            serviceArea.fn.deleteRow([data.id]);
                        });
                        $('td', row).last().find(".edit").click(function () {
                            serviceArea.fn.edit(data);//修改单条记录
                        });
                        $('td', row).find(".find-mapping").click(function () {
                            if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                            serviceArea.fn.mapping(data);
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
                    "ordering": true, // 取消字段排序
                    "serverSide": true, // ajax请求时必须写本项
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
                $dh.tableTools(serviceArea.v.dTable, buttonId);
            },
            search: function () {
                serviceArea.v.dTable.ajax.reload();
            },
            dropperInit: function () {
                $("#headImage  .dropped").dropper({
                    postKey: "file",
                    action: "utils/uploadFile",
                    postData: {thumbSizes: '480x320'},
                    label: "把文件拖拽到此处"
                }).on("fileComplete.dropper", serviceArea.fn.onFileComplete)
                        .on("fileError.dropper", serviceArea.fn.onFileError)
            },
            onFileError: function (e, file, error) {
                $dh.notify(error, "error");
            },
            onFileComplete: function (e, file, response) {
                if (response != '' && response != null) {
                    serviceArea.fn.viewImage(response.url);
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
                    $("#iconUrl").val(image);
                }
            },
            clearImageView: function () {
                $("#headImage").find(".image_show").html("");
                $("#headImage").find(".image_handle").show();
                $("#headImage").find(".dropper-input").val("");//原始名称
                $("#iconUrl").val("");
            },
            deleteRow: function (ids) {
                if (ids.length > 0) {
                    $dh.optNotify(function () {
                        $dh.ajax("serviceArea/delete", {ids: JSON.stringify(ids)}, function (result) {
                            if (result.status == "0") {
                                $dh.notify(result.msg, "success");
                                serviceArea.v.dTable.ajax.reload();
                            } else {
                                $dh.notify(result.msg, "error");
                            }
                        })
                    });
                }
            },
            look_up: function (data) {
                var title = data['name'];
                var photo = data['iconUrl'];
                var html = "";
                for (var key in data) {
                    if (serviceArea.v.property[key] && typeof (serviceArea.v.property[key]) == "string") {
                        var propertyVal = data[key];
                        html += serviceArea.v.property[key] + ":" + propertyVal + "<br/>";
                    }
                }
                if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                serviceArea.v.last_gritter = $.gritter.add({
                    title: title,
                    text: html,
                    class_name: 'gritter-success ',
                    image: photo,
                    sticky: true
                })
            },
            edit: function (item) {
                if (serviceArea.v.last_gritter) $.gritter.remove(serviceArea.v.last_gritter);
                serviceArea.fn.clearImageView();
                serviceArea.fn.showModal("修改工程师服务领域");
                for (var key in item) {
                    var element = $("#modal-form :input[name=" + key + "]");
                    if (element.length > 0) {
                        element.val(item[key]);
                    }
                    if (key == 'iconUrl') {
                        if (item[key] != "") {
                            serviceArea.fn.viewImage(item[key]);
                        }
                    }
                }
            },
            save: function () {
                if (!$('#serviceAreaForm').isValid()) {
                    return false;
                }
                ;
                $("#serviceAreaForm").ajaxSubmit({
                    url: $('#id') != null ? "serviceArea/update" : "serviceArea/create",
                    dataType: "json",
                    success: function (result) {
                        serviceArea.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            initiateTree: function () {
                serviceArea.v.treeDate = function (options, callback) {
                    var $data = null;
                    if (!("text" in options) && !("type" in options)) {
                        $dh.ajax("serviceArea/allServiceType", {}, function (result) {
                            callback({data: result});
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
                    dataSource: serviceArea.v.treeDate,
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
            },
            mapping: function (item) {
                //清理树
                $('#treeResources .tree-item').each(function () {
                    if ($(this).hasClass("tree-selected")) {
                        $(this).removeClass("tree-selected");
                    }
                });
                $("#modal-tree").modal("show");
                $('#serviceAreaId').val(item.id);
                serviceArea.fn.ajaxFindServiceArea(item.id);
            },
            saveTree: function () {
                var ids = [];
                var items = $('#treeResources').tree('selectedItems');
                for (var i in items) if (items.hasOwnProperty(i)) {
                    var item = items[i];
                    ids.push(item.additionalParameters['id']);
                }
                $("#serviceAreaTree").ajaxSubmit({
                    url: 'serviceArea/update',
                    dataType: "json",
                    data: {serviceTypeIds: JSON.stringify(ids)},
                    success: function (result) {
                        serviceArea.fn.responseComplete(result, true);
                        $("#modal-tree").modal("hide");
                    }
                })
            },
            responseComplete: function (result, action) {
                if (result.status == "0") {   /*=="0"*/
                    if (action) {
                        serviceArea.v.dTable.ajax.reload(null, false);
                    } else {
                        serviceArea.v.dTable.ajax.reload();
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
            },
            ajaxFindServiceArea: function (id) {
                $dh.ajax("serviceArea/findServiceType", {id: id}, function (result) {
                    for (var i = 0; i < result.length; i++) {
                        var items = serviceArea.v.tree;
                        for (var j = 0; j < items.length; j++) {
                            if (items[j].data.type != undefined) {
                                if (items[j].data.additionalParameters['id'] == result[i].id) {
                                    items[j].element.addClass("tree-selected");
                                }
                            }
                        }
                    }
                })
            }
        }
    }
    $(document).ready(function () {
        serviceArea.fn.init();
    });

</script>
</body>
</html>
