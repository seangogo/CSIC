<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>订单管理</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
	</head>
	<body>
		<link rel="stylesheet" href="${ctx}/static/css/main.css">
		<link rel="stylesheet" href="${ctx}/static/ace/assets/css/daterangepicker.css">
		<div class="page-header">
			<h1>
			<a href="${ctx}/order/list">订单管理</a>
		</h1>
		</div>
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success">
				<button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
		<div class="row order-list-form" style="padding: 5px 0px;">
			<form class="form-search" action="#" id="searchForm">
			<!--日期选择-->		
			<div class="col-md-4 dateinp">

				<div class="input-daterange input-group">
					<span class="input-group-addon" style="font-size: inherit;border-left:1px solid #ccc">
						日期范围
					</span>	
					<input type="text" class="form-control search-query search-input" name="startDate" placeholder="起始时间" id="startDate" value="${startDate}" />
					<span class="input-group-addon">
					<i class="fa fa-exchange"></i>
					</span>
						<input type="text" class="form-control search-query search-input" name="endDate" placeholder="结束时间" id="endDate" value="${endDate}"/>
				</div>		
			</div>
	<!--日期选择-->
				<div class="col-md-8">
					<div class="col-md-4 midinput">
						<input type="text" class="form-control search-query search-input" placeholder="订单编号" name="search_EQ_orderNum" value="${param.search_EQ_orderNum}">
					</div>
					<div class="col-md-4 midinput">
						<input type="text" class="form-control search-query search-input text" placeholder="发单人" name="search_LIKE_user.userName" value="${param_search_LIKE_user_userName}">
					</div>
					<div class="col-md-4 midinput">
						<input type="text" class="form-control search-query search-input" placeholder="接单工程师" name="search_LIKE_repair.userName" value="${param_search_LIKE_repair_userName}">
					</div>
				</div>

				<div class="col-xs-12 btninput">
					<div style="margin-top:5px;" class="btnselect">
						<select class="selectpicker" name="search_EQ_orderState" onchange="$('#searchForm').submit();">
							<option value="">全部</option>
							<option value="0" <c:if test='${param.search_EQ_orderState == "0"}'>selected</c:if>>待处理</option>
							<option value="1" <c:if test='${param.search_EQ_orderState == "1"}'>selected</c:if>>已接单</option>
							<option value="2" <c:if test='${param.search_EQ_orderState == "2"}'>selected</c:if>>已完成</option>
							<option value="3" <c:if test='${param.search_EQ_orderState == "3"}'>selected</c:if>>已取消</option>
							<option value="4" <c:if test='${param.search_EQ_orderState == "4"}'>selected</c:if>>已评价</option>
							<option value="5" <c:if test='${param.search_EQ_orderState == "5"}'>selected</c:if>>已付款</option>
						</select>
					</div>

					<div style="margin-left:1px; margin-top:5px;">
						<button type="submit" class="btn btn-info ">
						<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
					</button>
						<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");'>
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
					
					<shiro:hasPermission name="order:create">
					<c:if test="${null != currentUser && (currentUser.userType==0 ||  currentUser.userType== 1 || currentUser.userType==2) }">
						<button type="button" class="btn btn-info right" onclick="window.location.href='${ctx}/order/create'">
						<i class="ace-icon fa glyphicon-plus bigger-115"></i>发布</button>
					</c:if>
				</shiro:hasPermission>
					
					</div>
				</div>

				

				<div class="col-xs-12" style="margin-top: 5px;">

					<div class="pull-right">
						<div class="widget-toolbar">
							<label>
								<small class="orange"> <b>再次上门</b></small>
								<input type="checkbox" name="search_EQ_hasAgain" id="again" value="${param_search_EQ_hasAgain}"
							     <c:if test="${param_search_EQ_hasAgain==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-4"><span
							class="lbl middle"></span>
						</label>
						</div>
						<div class="widget-toolbar">
							<label> <small class="red"> <b>投诉</b>
					</small> <input type="checkbox" name="search_EQ_hasComplain" id="complain" value="${param_search_EQ_hasComplain }"
						<c:if test="${param_search_EQ_hasComplain==1}">checked="checked"</c:if> 
						class="ace ace-switch ace-switch-4"> <span
						class="lbl middle"></span>
					</label>
						</div>
					</div>
				</div>

			</form>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th></th>
					<th>订单编号</th>
					<th>订单状态</th>
					<th>订单类型</th>
					<th>发单人</th>
					<th class="hidden-xs">发布时间</th>
					<th >联系人</th>
					<th>接单人</th>
					<th class="hidden-xs">备注</th>
					<th class="hidden-xs">接单人是否已读</th>
					<th>管理</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orderList.content}" var="order">
					<tr>
						<td class="center">
							<c:if test="${order.orderSource == 0 }">
								<i class="ace-icon fa fa-mobile" style="font-size: 20px;"></i>
							</c:if>
							<c:if test="${order.orderSource != 0 }">
								<i class="ace-icon fa fa-laptop bigger-115"></i>
							</c:if>
						</td>
						<td>${order.orderNum}</td>
						<td>
							<c:if test="${order.orderState == 0}">
								<span style="color:red">待处理</span>
							</c:if>
							<c:if test="${order.orderState == 1}">
								<span style="color:green">已接单</span>
							</c:if>
							<c:if test="${order.orderState == 2}">已完成</c:if>
							<c:if test="${order.orderState == 3}">
								<span style="color:#aaaaaa">已取消</span>
							</c:if>
							<c:if test="${order.orderState == 4}">
								<span style="color:orange">已评价</span>
							</c:if>
							<c:if test="${order.orderState == 5}">
								<span style="color:green">已付款</span>
							</c:if>
						</td>
						<td>
							<c:if test="${order.repairType == 0}">设备报修</c:if>
							<c:if test="${order.repairType == 1}">安装实施</c:if>
						</td>
						<td>${order.user.userName}</td>
						<td class="hidden-xs">
							<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>${order.contactUser}</td>
						<td>${order.repair.userName}</td>
						<td class="hidden-xs">
							<c:if test="${order.hasComplain == 1}">
								<font class="red">投诉</font>
							</c:if>
							<c:if test="${order.hasAgain == 1}">
								<font class="orange">再次上门</font>
							</c:if>
						</td>
						<td class="hidden-xs">
							<c:if test="${order.orderState == 1 || order.orderState == 2 }">
								<c:if test="${order.isRead == 0 }">
									<center class="red">未读</center>
								</c:if>
								<c:if test="${order.isRead == 1 }">
									<center>已读</center>
								</c:if>
							</c:if>
						</td>
						<td>
							<div class="action-buttons">
								<shiro:hasPermission name="order:view">
									<a class="blue" href="${ctx}/order/detail/${order.id}"> <i class="ace-icon fa fa-search-plus bigger-115"></i>
										<span class="hidden-xs">
										查看
									</span>

									</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:update">
									<c:if test="${null != currentUser && order.orderState == 0 && currentUser.id == order.user.id}">
										<a class="green" href="${ctx}/order/update/${order.id}"> <i class="ace-icon fa fa-pencil bigger-115"></i>修改
										</a>
									</c:if>
								</shiro:hasPermission>
								<shiro:hasPermission name="order:cancel">
									<c:if test="${null != currentUser && order.orderState == 0 && currentUser.id == order.user.id}">
										<a class="inverse" href="javascript:void(0);" onclick="cancelOrder(${order.id})"> <i class="ace-icon  glyphicon glyphicon-remove "></i>取消
										</a>
									</c:if>
								</shiro:hasPermission>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<script src="${ctx}/static/ace/assets/js/date-time/moment.js"></script>
		<script src="${ctx}/static/ace/assets/js/date-time/daterangepicker.js"></script>
		<script type="text/javascript">
		
			//取消订单
			function cancelOrder(id) {
				layer.confirm("确认取消吗？", {
					icon: 5,
					title: '提示'
				}, function(index) {
					layer.close(index);
					window.location.href = "${ctx}/order/cancel/" + id;
				});
			}

			$(function() {
				$("#orderTime").daterangepicker().prev().on(ace.click_event,
					function() {
						$(this).next().focus();
				});
				$("#complain,#again").change(function(){
					if(this.checked){
						this.value='1';
					}else{
						this.value='';
					}
					$('#searchForm').submit();
				}) 
				
			});
			$(function(){
				if(!placeholderSupport()){ // 判断浏览器是否支持 placeholder
					$('input').focus(function() {
						$(this).next().remove();
				   }).blur(function() {
						var input = $(this);
						var ph = input.attr('placeholder');
						input.attr("position","relative");
							if (input.val() == '' ) {
								if(ph == '结束时间' || ph == '起始时间'){
									input.after("<span style='float: left;margin-top: 8px;margin-left: -115px;position: absolute;z-index: 9;color:#999'>"+ ph +"</span>");
								}else if(ph == '订单编号' || ph == '发单人' ||  ph == '接单工程师'){
									input.after("<span style='position: absolute;top:20px;left:17px;z-index: 9;color:#999'>"+ ph +"</span>");
								}
							}
				   }).blur();
				};
			})
			function placeholderSupport() {
				return 'placeholder' in document.createElement('input');
			}
		</script>
		<!-- 分页  -->
		<tags:pagination page="${orderList}" paginationSize="20" reqParam="${searchParams}" />
	</body>

</html>