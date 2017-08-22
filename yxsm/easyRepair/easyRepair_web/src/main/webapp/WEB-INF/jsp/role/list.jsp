<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        .tree .tree-branch > .tree-branch-header > .tree-branch-name > .icon-item {
            color: #F9E8CE;
            width: 13px;
            height: 13px;
            line-height: 13px;
            font-size: 11px;
            text-align: center;
            border-radius: 3px;
            -webkit-box-sizing: content-box;
            -moz-box-sizing: content-box;
            box-sizing: content-box;
            background-color: #FAFAFA;
            border: 1px solid #CCC;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
        }

        .tree .tree-selected > .tree-branch-header > .tree-branch-name > .icon-item {
            background-color: #F9A021;
            border-color: #F9A021;
            color: #FFF;
        }

    </style>
</head>
<body>
        <div class="row">
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-12">
		               <h3 class="header smaller lighter blue">角色列表</h3>
		               <div class="clearfix">
		                   <div class="pull-right tableTools-container"></div>
		               </div>

                        <div>
                            <table id="dataTables"
                                   class="table table-striped table-bordered table-hover dataTable no-footer" style="width:100%;">
                                <thead>
                                <tr>
                                    <th class="center sorting_disabled" rowspan="1" colspan="1"
                                        aria-label="">
                                        <label class="pos-rel">
                                            <input type="checkbox" class="ace checkall">
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="hidden-480">简介
                                    </th>
                                    <th>名称
                                    </th>
                                    <th><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>修改时间
                                    </th>
                                    <th class="col-lg-2">
                                        管理
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <form class="form-horizontal" id="roleForm" method="post" role="form">
                        <div class="modal-header">
                            <h4 class="modal-title text-center" id="myModalLabel"></h4>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" id="id" name="id"/>
                            <div class="form-group">
                                <label for="name" class="col-lg-2 control-label">角色名称</label>
                                <div class="col-lg-8">
                                    <input type="text" id="name" name="name"
                                           class="form-control" data-rule="required " placeholder="请输入角色名称"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="description" class="col-lg-2 control-label">角色描述</label>
                                <div class="col-lg-8">
                                    <input type="text" id="description" name="description"
                                           class="form-control" data-rule="required" placeholder="请输入角色描述"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="treeResources" class="col-lg-2 control-label">权限</label>
                                <div class="col-lg-8">
                                    <div class="widget-box widget-color-blue2">
                                        <div class="widget-header">
                                            <h4 class="widget-title lighter smaller">请选择</h4>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main padding-8">
                                                <ul id="treeResources"></ul>
                                            </div>
                                        </div>
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
                                   id="ui-search-btn"><span
                                        class="ace-icon fa fa-search"></span>搜索</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
</body>
<script type="text/javascript">
    var _this = {
        v: {
            dTable: null,
            treeDate: null,
            tree:null
        },
        fn: {
            init: function () {
                /*初始化树*/
                _this.fn.initiateTree()
                $("#ui-search-btn").on("click", function () {
                    _this.fn.search();//查询
                })
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
                                  $.each($(data.item).parents('.tree-branch'),function () {
                                      if($(this).hasClass("tree-selected")&&!$(this).find("li").hasClass("tree-selected")){
                                          $("#treeResources").tree("selectFolder",$(this))
                                      }else{
                                          return false;
                                      }

                                  })
                            }
                        }
                    }
                }).on("loaded.fu.tree",function () {
                    $('#treeResources').tree("discloseAll");
                    _this.v.tree=$("#treeResources").tree("allItems");
                }).on('exceededDisclosuresLimit.fu.tree', function () {
                    $('#treeResources').data('ignore-disclosures-limit', true);
                    $('#treeResources').tree('discloseAll');
                });
                _this.fn.dataTableInit();
                $("#create").click(function () {
                    _this.fn.showModal("新建角色");
                })

                $("#save").click(function () {
                    _this.fn.save();
                })
                $("#batchDel").click(function () {
                    var checkBox = $("#dataTables tbody tr").find('input[type=checkbox]:checked');
                    var ids = checkBox.getInputId();
                    _this.fn.deleteRow(ids);
                })
				$dh.tableSearch(_this.v.dTable);
                $("#searchmodfbox_grid-table").draggable();
            },
            search: function () {
                _this.v.dTable.ajax.reload();
            },
          	dataTableInit: function() {

					_this.v.dTable = $("#dataTables").DataTable({

						"ajax": { // ajax请求
							"type": "POST",
							"async": true,
							"url": "role/page",
							"data": function(d) {
								// 搜索参数
								d.name = $('#title_search').val();
								d.createTime = $('#createTimeS').val();
								d.endTime = $('#endTimeS').val();
								return d;

							}
						},
						"aaSorting": [],
						"columns": [{
							"data": "id",
							"defaultContent": ""
						}, {
							"data": "description",
							"defaultContent": ""
						}, {
							"data": "name",
							"defaultContent": ""
						}, {
							"data": "updateTime"
						}, {
							"data": null
						}],
						"columnDefs": [{
							targets: 4,
							render: function(a) {
								return '<div class="hidden-sm hidden-xs action-buttons">' +
									'<a class="look-up"><i class="ace-icon fa fa-search-plus bigger-130 blue"></i></a>' +
									'<a class="edit"><i class="ace-icon fa fa-pencil bigger-130 green"></i></a>' +
									'<a class="delete"><i class="ace-icon fa fa-trash-o bigger-130 red"></i></a></div>';
							}
						}, {
							targets: 0,
                            //"bSortable": false,
							"orderable": false
						}, {
							targets: 4,
							"orderable": false
						}],

						"createdRow": function(row, data, index) {
							$('td', row).eq(0).html("<label class='pos-rel'><input type='checkbox' class='ace'  value=" + data.id + " /> <span class='lbl'></span></label>");
						},
						"rowCallback": function(row, data) {
							$('td', row).last().find(".look-up").click(function() {
								_this.fn.lookUp(data); //单条记录查看
							})
							$('td', row).last().find(".edit").click(function() {
								_this.fn.edit(data); //修改单条记录
							})
							$('td', row).last().find(".delete").click(function() {
								_this.fn.deleteRow([data.id]); //单条记录删除
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
						"fnDrawCallback": function() {
							$dh.uiform();
						},
                        "aaSorting": [ 3, "desc" ],
						"processing": true, // 显示loading
						"searching": false, // 取消搜索框
						"ordering": true, // 字段排序
						"serverSide": true, // ajax请求时必须写本项
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
					var buttonId = new Array("search","create","batchDel","colvis","copy","csv","excel","pdf","print");
					$dh.tableTools(_this.v.dTable,buttonId);

				},
			deleteRow: function (id) {
                if (id) {
                    $dh.optNotify(function () {
                        $dh.ajax("role/delete", {ids: JSON.stringify(id)}, function (result) {
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
            save: function () {
                if (!$('#roleForm').isValid()) {
                    return false;
                }
                var ids = "";
                var items = $('#treeResources').tree('selectedItems');
                for (var i in items) if (items.hasOwnProperty(i)) {
                    var item = items[i];
                    ids += item.additionalParameters['id'] + ",";
                }
                if(ids==""){
                    $dh.notify("权限不可为空", "error");
                    return false;
                }
                $("#roleForm").ajaxSubmit({
                    dataType: "json",
                    url: "role/save",
                    data: {ids: ids},
                    success: function (result) {
                        _this.fn.responseComplete(result, true);
                        $("#modal-form").modal("hide");
                    }
                })
            },
            edit: function (data) {
                _this.fn.showModal("修改角色");
                for (var key in eval(data)) {
                    var element = $("#roleForm :input[name=" + key + "]")
                    if (element.length > 0) {
                        element.val(data[key]);
                    }
                }
                _this.fn.ajaxFindResource(data.id);
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
            initiateTree: function () {
                _this.v.treeDate = function (options, callback) {
                    var $data = null
                    if (!("text" in options) && !("type" in options)) {
                        $dh.ajax("role/allResource", {}, function (result) {
                            callback({data: result})
                        })
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
                    dataSource: _this.v.treeDate,
                    multiSelect: true,
                    cacheItems: true,
                    'open-icon': 'ace-icon tree-minus hide',
                    'close-icon': 'ace-icon tree-plus hide',
                    'selectable': false,
                    'selected-icon':'ace-icon fa fa-check',
                    'unselected-icon':'ace-icon fa fa-check',
                    'itemSelect': true,
                    'folderSelect': true,
                    'folder-open-icon' :'ace-icon tree-plus',
                    'folder-close-icon':'ace-icon tree-minus',
                    loadingHTML: '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
                });
            },
            showModal: function (title) {
                $dh.clearForm($("#modal-form"));
                if (title) {
                    $("#modal-form .modal-title").text(title);
                }
                //清理数
                if($('#treeResources .tree-branch').eq(3).hasClass("tree-selected")){
                    $("#treeResources").tree("selectFolder",$('#treeResources .tree-branch').eq(1).first());
                }
                $("#modal-form").modal("show");
            },
            ajaxFindResource:function (id) {
                $dh.ajax("role/resource", {roleId:id}, function (result) {
                    for(var i=0;i<result.length;i++){
                        var items=_this.v.tree;
                        for(var j=0;j<items.length;j++) {
                            if (items[j].data.type != undefined) {
                                if (items[j].data.additionalParameters['id'] == result[i].id) {
                                    items[j].element.addClass("tree-selected")
                                }
                            }
                        }
                    }
                })
            }
        }
    }
    $(document).ready(function () {
        _this.fn.init();
    });
</script>
</html>
