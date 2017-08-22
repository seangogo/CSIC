<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/" ;
%>
<%--<c:set var="ctx" value="${pageContext.request.contextPath}"/>--%>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<%@ include file="css.jsp" %>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>