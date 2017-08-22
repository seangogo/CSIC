<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page"
	required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer"
	required="true"%>
<%@ attribute name="reqParam" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int current = page.getNumber() + 1;
	//int begin = Math.max(1, current - paginationSize / 2);
	//int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());
	/*
	 * 分页页码数，从begin到end页展示 最多展示5个页码
	 * begin页为当前页减两页若减2页 end页为当前页加2页 若是小于第一页和最后一页则直接展示  
	 */
	int begin = (current - 2) < 1 ? 1 : (current - 2);
	int end = (current + 2) > page.getTotalPages() ? page.getTotalPages() : (current + 2);
	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
	request.setAttribute("reqParam", reqParam);
%>

<script type="text/javascript">
//TODO a标签页码跳转 换成post请求 （原因是get请求中文乱码）

</script>
<div class="row" style="margin-top: 12px;">
	<div class="col-sm-6" style="margin-top: 10px; color: #DA4747;">
		<div class="dataTables_info" id="sample-table-2_info">
			<span style="color: #393939;">总条数:&nbsp;<span
				style="color: #6faed9;">${page.totalElements}</span>&nbsp;条
			</span>
			<span style="color: #393939;">每页:&nbsp;<span
				style="color: #6faed9;">${page.size}</span>&nbsp;条
			</span>
			<span style="color: #393939;">第
			</span> <span style="color: #6faed9;">${current} / ${page.totalPages}</span><span
				style="color: #393939;"> 页</span>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_bootstrap">
			<ul class="pagination">
				<%
					if (page.hasPreviousPage()) {
				%>
				<li><a href="?page=1&${reqParam}">首页</a></li>
				<li><a href="?page=${current-1}&${reqParam}">&lt;</a></li>
				<%
					} else {
				%>
				<li class="disabled"><a href="javascript:void(0);">首页</a></li>
				<li class="disabled"><a href="javascript:void(0);">&lt;</a></li>
				<%
					}
				%>

				<c:forEach var="i" begin="${begin}" end="${end}">
					<c:choose>
						<c:when test="${i == current}">
							<li class="active"><a href="?page=${i}&${reqParam}">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="?page=${i}&${reqParam}">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<%
					if (page.hasNextPage()) {
				%>
				<li><a href="?page=${current+1}&${reqParam}">&gt;</a></li>
				<li><a href="?page=${page.totalPages}&${reqParam}">尾页</a></li>
				<%
					} else {
				%>
				<li class="disabled"><a href="javascript:void(0);">&gt;</a></li>
				<li class="disabled"><a href="javascript:void(0);">尾页</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</div>
</div>



