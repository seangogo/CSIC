<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>意见反馈</title>
</head>
<body>
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<link rel="stylesheet"
		href="${ctx}/static/ace/assets/css/daterangepicker.css">
	<div class="page-header">
		<h1>操作日志</h1>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row log-list-form" style="padding: 5px 0px;">
		<form class="form-search" action="${ctx}/system/log" method="post">
			<input type="hidden" name="type" id="type"/>
			<div class="col-xs-12 " style="padding: 0px;">
				<!------------------------------------------->
				<!--<div class="left datepicker">
					<div class="input-group input-group-sm">
						<input type="text" placeholder="操作时间" id="createTime"
							name="createTime" value="${createTime}"
							class="form-control hasDatepicker" readonly="readonly"> <span
							class="input-group-addon"> <i
							class="ace-icon fa fa-calendar"></i>
						</span>
					</div>
				</div>-->

					<div class="col-sm-4 dateinp" style="margin-top: 5px;">	
						<div class="input-daterange input-group">
							<span class="input-group-addon" style="font-size: inherit;border-left:1px solid #ccc">
								日期范围
							</span>	
							<input type="text" class="form-control search-query search-input" name="logTimeStart" placeholder="起始时间" id="logTimeStart" value="${logTimeStart}" />
							<span class="input-group-addon">
							<i class="fa fa-exchange"></i>
							</span>
								<input type="text" class="form-control search-query search-input" name="logTimeEnd" placeholder="结束时间"id="logTimeEnd" value="${logTimeEnd}"/>
						</div>	
					</div>
				<!------------------------------------------->
					
					
						<div class="col-sm-2" style="margin-top: 5px;">
							<input type="text" class="form-control search-query search-input"
							placeholder="操作人" name="userName" value="${userName }">
						</div>
						<div class="col-sm-2" style="margin-top: 5px;">
						<input type="text" class="form-control search-query search-input"
							placeholder="操作" name=description value="${description}">
						</div>
					<div class="col-sm-4" style="margin-top: 5px;">
						<select class="selectpicker" name="descriptionType"
							id="descriptionType"></select>
					</div>
			</div>
				
				<div class="col-xs-12" style="margin-top: 5px;">
					<button type="submit" class="btn btn-info ">
						<span class="ace-icon fa fa-search  bigger-115"></span> 搜索
					</button>

					<button type="button" class="btn btn-info " onclick='$(".form-search").find(":input").val("");$(".selectpicker").selectpicker("val","");' >
						<span class="ace-icon fa fa-refresh  bigger-115"></span> 重置
					</button>
				</div>
				
			
		</form>
	</div>
	<ul id="inbox-tabs"
		class="inbox-tabs nav nav-tabs  tab-size-bigger tab-space-1">
		<li class="<c:if test='${type == 0}'>active</c:if>"><a data-toggle="tab" href="javascript:void(0);" onclick="$('#type').val(0);$('.form-search').submit();"
			data-target="inbox" aria-expanded="true">  <span
				class="">操作记录</span>
		</a></li>
		<li class="<c:if test='${type == 1}'>active</c:if>"><a data-toggle="tab" href="javascript:void(0);" onclick="$('#type').val(1);$('.form-search').submit();"
			aria-expanded="false"> <span
				class="">异常记录</span>
		</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="hidden-xs">操作</th>
				<th>操作人</th>
				<th>操作时间</th>
				<c:if test="${type == 1}">
				<th>异常信息</th>
				</c:if>
				<th class="hidden-xs">请求ip</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${logs.content}" var="log">
				<tr>
					<td class="hidden-xs">${log.description}</td>
					<td>${log.creater.userName}</td>
					<td><fmt:formatDate value="${log.createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<c:if test="${type == 1}">
						<td>${log.exceptionCode}</td>
					</c:if>
					<td class="hidden-xs">${log.requestIp}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${ctx}/static/ace/assets/js/date-time/moment.js"></script>
	<script src="${ctx}/static/ace/assets/js/date-time/daterangepicker.js"></script>
	<script type="text/javascript">
		var descList = ${descList};
		var descTypeHtml = "";
		descTypeHtml += "<option value=''>全部</option>";
		for (var i = 0; i < descList.length; i++) {
			var descType = descList[i];
			descTypeHtml += "<option value='"+descType+"'>" + descType
					+ "</option>"
		}
		var descriptionType = "${descriptionType}";
		$("#descriptionType").html(descTypeHtml);
		$("#descriptionType").val(descriptionType);

		function deleteNews(id) {
			layer.confirm("删除后不可恢复，确认删除吗？", {
				icon : 5,
				title : '提示'
			}, function(index) {
				layer.close(index);
				window.location.href = "${ctx}/system/log/delete/" + id;
			});
		}

		$("#createTime").daterangepicker().prev().on(ace.click_event,
				function() {
					$(this).next().focus();
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
							}else if(ph == '操作人' || ph == '操作'){
								input.after("<span style='position: absolute;top:9px;left:17px;z-index: 9;color:#999'>"+ ph +"</span>");
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
	<tags:pagination page="${logs}" paginationSize="20" reqParam="logTimeStart=${logTimeStart}&logTimeEnd=${logTimeEnd}&userName=${userName}&description=${description}&descriptionType=${descriptionType}" />
</body>
</html>
