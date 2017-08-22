<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="inc/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>首页</title>
    <meta name="description" content="home"/>
    <link type="image/x-icon" href="static/images/favicon.ico" rel="shortcut icon">
     <!--<script src="static/rongyun/js/angular-1.4.8.js"></script>-->
    <%@ include file="inc/meta.jsp" %>
</head>
<body class="no-skin" ng-app="demo"  ng-controller="main">
<%@ include file="inc/js.jsp" %>
<%@ include file="inc/nav.jsp" %>
<div class="main-container ace-save-state" id="main-container">
    <%@ include file="inc/menu.jsp" %>
</div>
<%@ include file="inc/footer.jsp" %>
<rong-widget></rong-widget>
</body>
</html>