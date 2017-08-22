<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

	<head></head>
<style type="text/css">
	
/*图标管理*/
#iconCls{padding-left: 30px; width: 89%;}
.iconinsert{position: absolute;left:20px;top:8px;}
.uploadicon{cursor: pointer; padding-left: 10px; position: relative;top: 4px;}
.modal-wrap{width: 1000px; margin: 0 auto;}
.modal-dialog{float: left;}
.changeiconbox{ float: right; width: 400px; height: 200px; position: relative; background: #fff; display: block;top:-300px;opacity: 0;}


.iconboxclosebtn{cursor: pointer;}
.previewtitle{font-weight: 600; text-align: center;}
.commonicon{font-size: 16px;}
.icontext{ padding-left: 7px;}
.commoniconcontent{overflow: hidden;}
.commoniconcontent ul{ display: block; padding: 10px;margin: 0px;}
.commoniconcontent ul li{ float: left; padding-left: 10px; cursor: pointer; list-style: none;}

.iconcontent{  background: #fff; height: 90px; overflow: auto;}
.iconcontent ul{ display: block;padding: 10px;margin: 0px;}
.iconcontent ul li{ float: left; padding-left: 10px; cursor: pointer; list-style: none; }
.modal-body{background: #fff;}

.colorcontent{  background: #fff; height: 90px; overflow: auto;}
.colorcontent ul{ display: block;padding: 10px;margin: 0px;}
.colorcontent ul li{ float: left;width: 38px;height: 24px; border:1px solid #ccc; cursor: pointer; list-style: none;}


.darkback{ background: #333!important;}
.whiteback{ background: #FFF!important;}
.redback{ background:#DD5A43!important}
.redback2{ background:#E08374!important}
.light-redback{ background:#F77!important}
.blueback{ background:#478FCA!important}
.light-blueback{ background:#93CBF9!important}
.greenback{ background:#69AA46!important}
.light-greenback{ background:#B0D877!important}
.orangeback{ background:#FF892A!important}
.orangeback2{ background:#FEB902!important}
.light-orangeback{ background:#FCAC6F!important}
.purpleback{ background:#A069C3!important}
.pinkback{ background:#C6699F!important}
.pinkback2{ background:#D6487E!important}
.brownback{ background:brown!important}
.greyback{ background:#777!important}
.light-greyback{ background:#BBB!important}

/*图标管理*/
</style>
	<body>
		<div class="main-content-inner">
			<div class="page-content">
				<!-- /.page-header -->
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-12">
								<h3 class="header smaller lighter blue">菜单列表</h3>

								<div>
									<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
										<div class="row">
											<div class="col-xs-12">
												<div class="resource-button nav navbar-nav navbar-left">
													<button type="button" class="btn btn-success select-type btn-app">
														<i class="ace-icon fa fa-signal"></i>
													</button>
													<button type="button" class="btn btn-info select-type">
														<i class="ace-icon fa fa-pencil"></i>
													</button>
													<button type="button" class="btn btn-warning select-type">
														<i class="ace-icon fa fa-users"></i>
													</button>
													<button type="button" class="btn btn-danger select-type">
														<i class="ace-icon fa fa-cogs"></i>
													</button>
												</div>
												<div class="nav navbar-nav navbar-right">
													<a href="javascript:void(0)" id="addResource" class="btn btn-outline btn-primary btn-xm" role="button">新增菜单
													</a>
													<a href="javascript:void(0)" id="batchDel" class="btn btn-outline btn-danger btn-xm" role="button">批量删除</a>
												</div>
											</div>
										</div>
									</div>
                                    <table id="dataTables"
                           class="table table-striped table-b_thised table-hover dataTable no-footer table-bordered"
                           style="width:100%">
										<thead>
											<tr role="row">
												<th class="center sorting_disabled" rowspan="1" colspan="1" aria-label="">
												    <label class="pos-rel">
					                                    <input type="checkbox" class="ace checkall" >
					                                    <span class="lbl"></span>
					                                </label>
												</th>
												<th class="sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">排序
												</th>
												<th class="sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Price: activate to sort column ascending">资源名称
												</th>
												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">权限字符
												</th>

												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">链接
												</th>
												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">类型
												</th>
												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">分类
												</th>
												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">图标
												</th>

												<th class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">管理
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
					<!-- 弹框表单（Modal） -->
					<div id="modal-form" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">

						<div class="modal-wrap">

							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<form class="form-horizontal" id="add-form" method="post" role="form">
										<div class="modal-header">
											<h4 class="modal-title text-center"></h4>
										</div>
										<div class="modal-body">
											<input type="hidden" id="id" name="id" />
											<input type="hidden" id="parentId" name="parentId" value="" />
											<input type="hidden" id="level" name="level" value="1" />
											<input type="hidden" id="hasChild" name="hasChild" value="" />
											<div class="form-group">
												<label for="name" class="col-lg-2 control-label">资源名称</label>
												<div class="col-lg-8">
													<input type="text" id="name" name="name" class="form-control" placeholder="请输入资源名称" data-rule="required;chinese" />
												</div>
											</div>
											<div class="form-group" id="resourceType">
												<label class="col-lg-2 control-label">类型</label>
												<div class="col-lg-8">
													<label class="radio-inline">
														<input value="0" type="radio" id="menu-radio"
															   name="resourceType" checked> 菜单
													</label>
													<label class="radio-inline">
														<input value="1" type="radio" id="btn-radio"
															   name="resourceType">按钮
													</label>
												</div>
											</div>
											<div class="form-group" id="resUrlDiv">
												<label for="resUrl" class="col-lg-2 control-label">资源链接</label>
												<div class="col-lg-8">
													<input type="text" id="resUrl" name="resUrl" class="form-control" placeholder="请输入资源链接"/>
												</div>
											</div>
											<div class="form-group" id="type-div">
												<label for="type" class="col-lg-2 control-label">分类</label>
												<div class="col-lg-8">
													<select name="type" id="type">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label for="permission" class="col-lg-2 control-label">权限字符串</label>
												<div class="col-lg-8">
													<input type="text" id="permission" name="permission" class="form-control" placeholder="权限字符串，如：resource:create 或 resource:*" data-rule="required" />
												</div>
											</div>

											<div class="form-group" id="iconCls-div">

												<label for="iconCls" class="col-lg-2 control-label">图标</label>
												<div class="col-lg-8" style="position: relative;">
                                                    <span class="iconinsert"></span> <input type="text" id="iconCls"
                                                                                            name="iconCls"
                                                                                            class="form-control"
                                                                                            placeholder="请选择图标"
                                                                                            data-rule=""/>
                                                    <span class="uploadicon"><i
                                                            class="fa fa-picture-o green bigger-200"></i></span>
												</div>

											</div>

											<div class="form-group">
												<label for="sort" class="col-lg-2 control-label">排序</label>
												<div class="col-lg-8">
													<input type="text" id="sort" name="sort" class="form-control" placeholder="请输入排序" data-rule="required;integer[+0]" />
												</div>
											</div>
											<div class="form-group">
												<label for="description" class="col-lg-2 control-label">描述</label>
												<div class="col-lg-8">
													<textarea id="description" name="description" style="padding: 10px 4px;" class="form-control" placeholder="请输入描述" data-rule="required">
                                                    </textarea>
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
							<div class="changeiconbox ">
								<div class="modal-header" style="padding: 8px;">
									<h4 class="text-center">选择图标 <span class="glyphicon glyphicon-remove red pull-right iconboxclosebtn"></span></h4>
								</div>
								<div class="modal-body">
									<h5 class="previewtitle">预览 ：<span class="previewicon"></span></h5>
									<div class="commonicon"><i class="fa fa-hand-o-down green bigger-150" aria-hidden="true"></i><span class="icontext">常用图标</span></div>
									<div class="commoniconcontent">
										<ul>
											<li><i class="fa fa-search bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-plus-circle bigger-130 green" aria-hidden="true"></i></li>
											<li><i class="fa fa-pencil-square-o bigger-130 blue" aria-hidden="true"></i></li>
											<li><i class="fa fa-times-circle bigger-130 red" aria-hidden="true"></i></li>
											<li><i class="fa fa-trash-o bigger-130 red" aria-hidden="true"></i></li>
										</ul>
									</div>
									<div style="clear:both;"></div>
									<div class="commonicon"><i class="fa fa-hand-o-down bigger-150" aria-hidden="true"></i><span class="icontext">图标（360个）</span></div>
									<div class="iconcontent">
										<ul>
											<li><i class="fa fa-search bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-plus-circle bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-pencil-square-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-times-circle bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-trash-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-bar-chart-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-user bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-users bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-cloud bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-cloud-download bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-cloud-upload bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-exclamation-circle bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-eye-slash bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-eye bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-cog bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-cogs bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-book bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-key bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-tachometer bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-laptop bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-bullhorn bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-info-circle bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-comment bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-comment-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-comments bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-comments-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-barcode bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-calendar bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-bookmark bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-bookmark-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-video-camera bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-download bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-coffee bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-envelope bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-envelope-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-exchange bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-gift bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-glass bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-globe bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-flag bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-folder bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-folder-open bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-lock bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-unlock bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-camera bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-camera-retro bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-flask bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-beer bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-briefcase bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-arrow-circle-o-down bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-credit-card bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-inbox bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-heart bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-heart-o bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-film bigger-130" aria-hidden="true"></i></li>
											<li><i class="fa fa-desktop bigger-130" aria-hidden="true"></i></li>
										</ul>
									</div>
									<div style="clear:both;"></div>
									<div class="commonicon"><i class="fa fa-th" aria-hidden="true"></i><span class="icontext">颜色（18种）</span></div>
									<div class="colorcontent">
										<ul>
											<li class="darkback"></li>
											<li class="whiteback"></li>
											<li class="redback"></li>
											<li class="redback2"></li>
											<li class="light-redback"></li>
											<li class="blueback"></li>
											<li class="light-blueback"></li>
											<li class="greenback"></li>
											<li class="light-greenback"></li>
											<li class="orangeback"></li>
											<li class="orangeback2"></li>
											<li class="light-orangeback"></li>
											<li class="purpleback"></li>
											<li class="pinkback"></li>
											<li class="pinkback2"></li>
											<li class="brownback"></li>
											<li class="greyback"></li>
											<li class="light-greyback"></li>
										</ul>
									</div>
									<div style="clear:both;"></div>
								</div>
								<div class="modal-footer">
									<button class="btn btn-info" type="button" id="iconconfirm">
                                    <i class="ace-icon fa fa-check bigger-110"></i> 确认
                                </button>
									<button class="btn" type="reset" id="iconcancel">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                                </button>
								</div>
							</div>

						</div>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</body>
	<script type="text/javascript">
		var resource = {
			v: {
				id: "resource",
				list: [],
				dTable: null
			},
			fn: {
				init: function() {
					resource.fn.dataTableInit();
					
					//初始化图标选择控件
					resource.fn.showIconBox($(".uploadicon"),$("#iconCls"),$(".iconinsert"));
					//初始化图标选择控件结束
					$("#addResource").on("click", function() {
						$("parentId").val("0"); //父级id
						$("#level").val("1"); //菜单级别
						$("#type-div,#resUrlDiv").show(function() {
							$("#type option[text=1]").attr("selected", "selected");
							$("#resUrlDiv").val("");
						}); //类别
						$("#iconCls-div").val("fa-list-alt").show(); //图标
						$("#resourceType").hide(); //功能 
						resource.fn.showModal("新增一级菜单资源");
						$(".iconinsert").html("");
						$('#menu-radio').prop('checked', true);

					}); //新增一级菜单
					$("#save").click(function() {
							resource.fn.save();
						}) //保存
					$("input:radio[name='resourceType']").on("click", function() {
						resource.fn.menu_btn_style($(this).attr("id") == "menu-radio");
					})
					$(".select-type").click(function() {
						$(this).addClass("btn-app").siblings().removeClass("btn-app");
						resource.v.dTable.ajax.reload();
					})

					$("#batchDel").click(function() {
						var ids = $("#dataTables tbody tr").find('input[type=checkbox]:checked').getInputId();
						resource.fn.batchDelete(ids);
					})
				},
				dataTableInit: function() {
					resource.v.dTable = $("#dataTables").DataTable({
						"ajax": { // ajax请求
							"type": "GET",
							"url": "resource/page",
							"data": function(d) {
								// 搜索参数
								d.name = $('#title_search').val();
								d.createTime = $('#createTimeS').val();
								d.endTime = $('#endTimeS').val();
								return d;
							}
						},
						"columns": [{
							"data": null
						}, {
							"data": "sort"
						}, {
							"data": null
						}, {
							"data": "permission",
							"defaultContent": ""
						}, {
							"data": "resUrl",
							"defaultContent": ""
						}, {
							"data": null
						}, {
							"data": "type",
							"defaultContent": ""
						}, {
							"data": "iconCls",
							"defaultContent": ""
						}, {
							"data": null
						}],
						"columnDefs": [{
							targets: 0,
                            "bSortable": false,
							render: function(a) {
								return "<input type='checkbox' class='ace' value=" + a.id + "><span class='lbl'></span>";
							}
						}, {
							targets: 2,
							render: function(a) {
								var str=a.showName;
								return str.replace(new RegExp(" ", 'g'),"&nbsp;");
								/*if(a.level == 1) return "<span class='red'>" + a.showName + "</span>";
								else if(a.level == 2) return "<span class='green'>" + a.showName + "</span>";
								else if(a.level == 3) return "<span class='purple'>" + a.showName + "</span>";*/
							}
						}, {
							targets: 5,
							render: function(a) {
								if(a.resourceType == 0) {
									if(a.level == 1) return "<span class='red'>一级菜单</span>";
									if(a.level == 2) return "<span class='green'>二级菜单</span>";
									if(a.level == 3) return "<span class='light-orange'>三级菜单</span>";
								} else {
									return "<span class='purple'>按钮</span>";
								}
							}
						}, {
							targets: 8,
							render: function(a) {
								return a.resourceType == 0 ? "<div class='hidden-sm hidden-xs action-buttons'><a class='add' ><i class='ace-icon fa glyphicon-plus bigger-150'></i></a>" +
									"<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='delete'><i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>" :
									"<div class='hidden-sm hidden-xs action-buttons'>" +
									"<a class='edit' ><i class='ace-icon fa fa-pencil bigger-130 green'></i></a><a class='delete' '><i class='ace-icon fa fa-trash-o bigger-130 red'></i></a></div>";
							}
						}],
						"rowCallback": function(row, data) {
							$('td', row).last().find(".add").click(function() {
								resource.fn.add(data); //新增单条记录
							});
							$('td', row).last().find(".edit").click(function() {
								resource.fn.edit(data); //修改单条记录
							});
							$('td', row).last().find(".delete").click(function() {
								resource.fn.batchDelete([data.id]); //单条记录删除
							});
						},
						"fnServerParams": function(aoData) {
							var index= $(".btn-app").index();
							$("#type").val(index+1);
							aoData.type = index+ 1;
						},
						"fnDrawCallback": function() {
							$dh.uiform();
						},
						"processing": true, // 显示loading
						"searching": false, // 取消搜索框
						"ordering": false, // 取消字段排序
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
					var buttonId = new Array("excel","pdf","print");
					$dh.tableTools(resource.v.dTable,buttonId);
				},
				batchDelete: function(ids) {
					if(ids.length > 0) {
						$dh.optNotify(function() {
							$dh.ajax("resource/batchDelete", {
								ids: JSON.stringify(ids)
							}, function(result) {
								if(result.status == "0") {
									$dh.notify(result.msg, "success");
									resource.v.dTable.ajax.reload();
								} else {
									$dh.notify(result.msg, "error");
								}
							})
						}, "只可删除底层菜单或按键,是否确认删除？", "确定")
					}
				},
				save: function() {
                    alert(0)
					if (!$('#add-form').isValid()) {
						return false;
					}
                    alert(1)
					$("#add-form").ajaxSubmit({
						url: "resource/create",
						dataType: "json",
						success: function(result) {
							resource.v.dTable.ajax.reload();
							resource.fn.responseComplete(result, true);
							$("#modal-form").modal("hide");
						}
					})
				},
				add: function(data) {
					$("#resourceType,#iconCls-div,#resUrlDiv").show(function () {
						$("#menu-radio").trigger("click");
					}); //菜单类型,图标,链接
					$("#type-div").hide(function() {
						//设置下拉框text属性为5的选项 选中
						$("#type option:contains(" + data.type + ")").attr("selected", "selected");
					}); //分类
					resource.fn.showModal("新增资源");
					$("#parentId").val(data.id); //父ID
				},
				edit: function(data) {
					resource.fn.showModal(data.resourceType == 0 ? "修改菜单" : "修改按钮");
					$("#type-div").hide(); //分类失效
					$("#resourceType").hide(); //类别失效
					for(var key in data) {
						var element = $("#modal-form :input[name=" + key + "]");
						if(element.length > 0&&key!="resourceType") {
							element.val(data[key]);
						}
					}
					$(data.resourceType == 0 ? "#menu-radio" : "#btn-radio").trigger("click");
				},
				responseComplete: function(result, action) {
					if(result.status == "0") {
						if(action) {
							resource.v.dTable.ajax.reload(null, false);
						} else {
							resource.v.dTable.ajax.reload();
						}
						$dh.notify(result.msg, "success");
					} else {
						$dh.notify(result.msg, "error");
					}
				},
				showModal: function(title) {
					$("#modal-form").modal("show");

					$(".changeiconbox").css("opacity", "0");
					$(".changeiconbox").css("top", "-300px");

					$("#modal-form").find("textarea").val("");
					$("#modal-form").find("input[type=text]").val("");
					$("#modal-form").find("input[type=hidden]").val("");
					$("#modal-form").validator("cleanUp");
					if(title) {
						$("#modal-form .modal-title").text(title);
					}
				},
				menu_btn_style: function(type) {
					if (type) {  //是菜单时
						$('#resUrlDiv').show();
						$('#iconCls-div').show(function() {
							$('#iconCls-div').val('fa-list-alt')
						});
					} else {  //是按钮时
						$('#resUrlDiv').hide(function() {
							$('#resUrlDiv').val("");
						});
						$('#iconCls-div').hide(function() {
							$('#iconCls-div').val("");
						});
					}
				},
				//          图标选择控件
				showIconBox: function(uploadbtn,inputid,iconcontentid) {
						uploadbtn.click(function() {
							$(".changeiconbox").animate({
								top: '31px',
								opacity: '1',
							});
							iconcontentid.html("");
							inputid.val("");

						})
						var previewiconhtml = $(".previewicon").html();
						$(".commoniconcontent,.iconcontent").on("click", "li", function() {
							$(".previewicon").html($(this).html());
							previewiconhtml = $(".previewicon").html();
						})

						$(".colorcontent").on("click", "li", function() {
							switch($(this).attr("class")) {
								case "darkback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("dark");
									break;
								case "whiteback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("white");
									break;
								case "redback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("red");
									break;
								case "redback2":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("red2");
									break;
								case "light-redback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("light-red");
									break;
								case "blueback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("blue");
									break;
								case "light-blueback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("light-blue");
									break;
								case "greenback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("green");
									break;
								case "light-greenback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("light-green");
									break;
								case "orangeback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("orange");
									break;
								case "orangeback2":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("orange2");
									break;
								case "light-orangeback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("light-orange");
									break;
								case "purpleback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("purple");
									break;
								case "pinkback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("pink");
									break;
								case "pinkback2":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("pink2");
									break;
								case "brownback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("brown");
									break;
								case "greyback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("grey");
									break;
								case "light-greyback":
									$(".previewicon").html(previewiconhtml);
									$(".previewicon i").addClass("light-grey");
									break;
								default:
									break;
							}

						})

						$("#iconconfirm").click(function() {
							iconcontentid.html($(".previewicon").html());
							inputid.val($(".previewicon i").attr("class"));
						})
						$(".iconboxclosebtn,#iconcancel,#iconconfirm").click(function() {
							$(".changeiconbox").animate({
								top: '-300px',
								opacity: '0',
							});
							$(".previewicon").html("");
						})

					}
				//          图标选择控件结束
			}
		}
		$(document).ready(function() {
			resource.fn.init();
		});
	</script>