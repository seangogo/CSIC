<%@ tag import="com.dh.common.UploadFileUtils" %>
<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="imgsThumb" type="java.lang.String" required="true"%>
<%@ attribute name="imgs" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String[] imgThumbArr = imgsThumb.split(",");
	String[] imgArr = imgs.split(",");
	String imgUrl= UploadFileUtils.getPathProperties("config","img.url");
	request.setAttribute("imgUrl",imgUrl);
	int begin = 0;
	int end = imgThumbArr.length < imgArr.length ? imgThumbArr.length : imgArr.length;
%>
<ul class="ace-thumbnails clearfix">
	<%
		for (int i=0; i < end ; i++) {
	%>
	<li><a href="${imgUrl}/<%=imgArr[i] %>" data-rel="colorbox"
		class="cboxElement"> <img width="200" height="150" alt="200x150"
			src="${imgUrl}/<%=imgThumbArr[i] %>">
	</a></li>
	<%
		}
	%>
</ul>


