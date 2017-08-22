<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="navbar" class="navbar navbar-default  ace-save-state">

	<div class="navbar-container ace-save-state" id="navbar-container">
		<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

		<div class="navbar-header pull-left">
			<a href="index.html" class="navbar-brand">
				<small>
                    <i class="fa fa-leaf"></i>
                    易修后台
                </small>
			</a>
		</div>

		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<li class="green dropdown-modal">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
						<span class="badge badge-success" ng-show="data.totalUnreadCount>0">{{data.totalUnreadCount}}</span>
					</a>
					<ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
						<li class="dropdown-header">
							<i class="ace-icon fa fa-envelope-o"></i>
							联系人列表
						</li>
						<li class="dropdown-content ace-scroll" style="position: relative;">
							<div class="scroll-track" style="display: none;">
								<div class="scroll-bar"></div>
							</div>
							<div class="scroll-content" style="max-height: 200px;overflow: auto;">
								<ul class="dropdown-menu dropdown-navbar"  id="contactpeoplelist">
									<li ng-click="showbtn()">
										<a href="#" class="clearfix">
											<span class="msg-title">
												查看最新消息<span>({{data.totalUnreadCount}}</span>条未读)
											</span>
										</a>
									</li>
									<li style="color:#333;font-family: 'microsoft yahei'; padding-left: 10px;"><input  style="font: 12px;line-height:0px;width: 100%;" type="text" name="" id="contactlist" value="" placeholder="快速搜索联系人..." /></li>
									<li class="contactpeoplelist" ng-repeat="x in httpnames" ng-click="setconversation(x.id,x.name)" ng-hide="x.id==user" style="cursor: pointer;"><img class="msg-photo" ng-src="{{x.portraitUri}}" alt="" /><span style="color:#333;font-family: 'microsoft yahei'; padding-left: 10px;">{{x.name}}</span>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</li>
				<li class="light-blue dropdown-modal">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="${ctx}/static/images/avatars/user.jpg" alt="Jason's Photo" />
						<span class="user-info">
									<small>Welcome,</small>
									${currentUser.nickName}
								</span>

						<i class="ace-icon fa fa-caret-down"></i>
					</a>

					<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li>
							<a href="#">
								<i class="ace-icon fa fa-cog"></i> 设置
							</a>
						</li>
						<li>
							<a id="update-password">
								<i class="ace-icon fa fa-user"></i> 修改
							</a>
						</li>
						<li>
							<a href="${ctx}/logout">
								<i class="ace-icon fa fa-power-off"></i> 退出
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>

		<div class="ace-settings-container" id="ace-settings-container">

			<!--<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                <i class="ace-icon fa fa-cog bigger-180" style="line-height:30px;"></i>
            </div>-->

			<div class="ace-settings-box clearfix" id="ace-settings-box">
				<div class="pull-left width-50">
					<div class="ace-settings-item">
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="no-skin" value="#438EB9">#438EB9</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-2" value="#C6487E">#C6487E</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
							<div class="dropdown dropdown-colorpicker">
								<a data-toggle="dropdown" class="dropdown-toggle" style="opacity: 1.88;"><span class="btn-colorpicker" style="background-color:#438EB9"></span></a>
								<ul class="dropdown-menu dropdown-caret">
									<li>
										<a class="colorpick-btn selected" style="background-color:#438EB9;" data-color="#438EB9"></a>
									</li>
									<li>
										<a class="colorpick-btn" style="background-color:#222A2D;" data-color="#222A2D"></a>
									</li>
									<li>
										<a class="colorpick-btn" style="background-color:#C6487E;" data-color="#C6487E"></a>
									</li>
									<li>
										<a class="colorpick-btn" style="background-color:#D0D0D0;" data-color="#D0D0D0"></a>
									</li>
								</ul>
							</div>
						</div>
						<span>&nbsp;选择主题</span>
					</div>
					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-navbar" autocomplete="off">
						<label class="lbl" for="ace-settings-navbar"> 固定导航</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-sidebar" autocomplete="off">
						<label class="lbl" for="ace-settings-sidebar">固定菜单</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-breadcrumbs" autocomplete="on">
						<label class="lbl" for="ace-settings-breadcrumbs">固定导航菜单</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off">
						<label class="lbl" for="ace-settings-rtl">切换左右布局</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off">
						<label class="lbl" for="ace-settings-add-container">
                            居中
                        </label>
					</div>
				</div>
				<!-- /.pull-left -->

				<div class="pull-left width-50">
					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" autocomplete="off">
						<label class="lbl" for="ace-settings-hover">收起所有子菜单</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" autocomplete="off">
						<label class="lbl" for="ace-settings-compact">菜单瘦身</label>
					</div>

					<div class="ace-settings-item">
						<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" autocomplete="on">
						<label class="lbl" for="ace-settings-highlight">导航书签</label>
					</div>
				</div>
				<!-- /.pull-left -->
			</div>
			<!-- /.ace-settings-box -->
		</div>
		<!-- /.ace-settings-container -->
	</div>
</div>

<script>
	$(document).ready(function() {	
		$("#update-password").on("click", function() {
			$("#page-content").load("manager/profile");
		})
		

		$('#contactlist').keyup(function(){
			$('.contactpeoplelist').hide().filter(":contains('" +($(this).val()) + "')").show();
		}).keyup();
			
	
	})
</script>