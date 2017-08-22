<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	Object menus = request.getSession()
			.getAttribute("userMenu");
%>
<!-- leftMenu start -->
<shiro:user>
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success" onclick="tableCut(1);">
				<i class="ace-icon fa fa-signal"></i>
			</button>
			<button class="btn btn-info" onclick="tableCut(2);">
				<i class="ace-icon fa fa-pencil"></i>
			</button>

			<!-- #section:basics/sidebar.layout.shortcuts -->
			<button class="btn btn-warning" onclick="tableCut(3);">
				<i class="ace-icon fa fa-users"></i>
			</button>

			<button class="btn btn-danger" onclick="tableCut(4);">
				<i class="ace-icon fa fa-cogs"></i>
			</button>
		</div>
		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span> <span class="btn btn-info"></span>
			<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
		</div>
	</div>
	<ul class="nav nav-list hidden" id="table1">
		<c:forEach items="<%=menus%>" var="menu">
			<c:if test="${menu.type == 1}">
				<c:if test="${menu.level == 1}">
					<c:choose>
						<c:when test="${menu.hasChild == '0' || empty menu.hasChild}">
							<li class="
								<c:if test="${not empty index && fn:contains(menu.resUrl, index) }">
									active
								</c:if>
							"><a href="${ctx}/${menu.resUrl}"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span>
							</a><b class="arrow"></b></li>
						</c:when>
						<c:otherwise>
							<li class="open"><a href="${ctx}/${menu.resUrl}"
								class="dropdown-toggle"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span><b
									class="arrow fa fa-angle-down"></b>
							</a><b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="<%=menus%>" var="menu2">
										<c:if test="${menu2.parentId == menu.id}">
											<li class="
											<c:if test="${not empty index &&  fn:contains(menu2.resUrl, index) }" >
												active
											</c:if>
											"><a href="${menu2.resUrl}"> <i
													class="menu-icon fa fa-caret-right"></i>${menu2.name}<b
													class="arrow "></b>
											</a></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>
	</ul>
	<ul class="nav nav-list hidden" id="table2">
		<c:forEach items="<%=menus%>" var="menu">
			<c:if test="${menu.type == 2}">
				<c:if test="${menu.level == 1}">
					<c:choose>
						<c:when test="${menu.hasChild == '0' || empty menu.hasChild}">
							<li class="
								<c:if test="${not empty index && fn:contains(menu.resUrl, index) }">
									active
								</c:if>
							"><a href="${ctx}/${menu.resUrl}"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span>
							</a><b class="arrow"></b></li>
						</c:when>
						<c:otherwise>
							<li class="open"><a href="${ctx}/${menu.resUrl}"
								class="dropdown-toggle"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span><b
									class="arrow fa fa-angle-down"></b>
							</a><b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="<%=menus%>" var="menu2">
										<c:if test="${menu2.parentId == menu.id}">
											<li class="
											<c:if test="${not empty index &&  fn:contains(menu2.resUrl, index) }" >
												active
											</c:if>
											"><a href="${menu2.resUrl}"> <i
													class="menu-icon fa fa-caret-right"></i>${menu2.name}<b
													class="arrow "></b>
											</a></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>
	</ul>
	<ul class="nav nav-list hidden" id="table3">
		<c:forEach items="<%=menus%>" var="menu">
			<c:if test="${menu.type == 3}">
				<c:if test="${menu.level == 1}">
					<c:choose>
						<c:when test="${menu.hasChild == '0' || empty menu.hasChild}">
							<li class="
								<c:if test="${not empty index && fn:contains(menu.resUrl, index) }">
									active
								</c:if>
							"><a href="${ctx}/${menu.resUrl}"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span>
							</a><b class="arrow"></b></li>
						</c:when>
						<c:otherwise>
							<li class="open"><a href="${ctx}/${menu.resUrl}"
								class="dropdown-toggle"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span><b
									class="arrow fa fa-angle-down"></b>
							</a><b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="<%=menus%>" var="menu2">
										<c:if test="${menu2.parentId == menu.id}">
											<li class="
											<c:if test="${not empty index &&  fn:contains(menu2.resUrl, index) }" >
												active
											</c:if>
											"><a href="${menu2.resUrl}"> <i
													class="menu-icon fa fa-caret-right"></i>${menu2.name}<b
													class="arrow "></b>
											</a></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>
	</ul>
	<ul class="nav nav-list hidden" id="table4">
		<c:forEach items="<%=menus%>" var="menu">
			<c:if test="${menu.type == 4}">
				<c:if test="${menu.level == 1}">
					<c:choose>
						<c:when test="${menu.hasChild == '0' || empty menu.hasChild}">
							<li class="
								<c:if test="${not empty index && fn:contains(menu.resUrl, index) }">
									active
								</c:if>
							"><a href="${ctx}/${menu.resUrl}"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span>
							</a><b class="arrow"></b></li>
						</c:when>
						<c:otherwise>
							<li class="open"><a href="${ctx}/${menu.resUrl}"
								class="dropdown-toggle"> <i
									class="menu-icon fa ${menu.iconCls}"></i><span
									class="menu-text">${menu.name}</span><b
									class="arrow fa fa-angle-down"></b>
							</a><b class="arrow"></b>
								<ul class="submenu">
									<c:forEach items="<%=menus%>" var="menu2">
										<c:if test="${menu2.parentId == menu.id}">
											<li class="
											<c:if test="${not empty index &&  fn:contains(menu2.resUrl, index) }" >
												active
											</c:if>
											"><a href="${menu2.resUrl}"> <i
													class="menu-icon fa fa-caret-right"></i>${menu2.name}<b
													class="arrow "></b>
											</a></li>
										</c:if>
									</c:forEach>
								</ul></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:if>
		</c:forEach>
	</ul>
	<!-- /.nav-list -->

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left"
			data-icon1="ace-icon fa fa-angle-double-left"
			data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
</shiro:user>
<!-- leftMenu end -->

<script>
	var tableType = '${tableType}';
	if(tableType){
		tableCut(tableType);
	}
	function tableCut(index) {
		if (!index) {
			return;
		}
		$(".nav-list").addClass("hidden");
		$("#table" + index).removeClass("hidden");
	}
	jQuery.extend({
		clearInput: function(obj) {
			if($(obj).hasClass("clearBtn_")){
				$(".clearInput_").find("input").val("");
			}
		}
	});
</script>
