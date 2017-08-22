<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${news.newsTitle}</title>
<meta http-equiv="cache-control" content="no-cache">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
</head>
<body>
	${news.newsContent}

	<script>
		window.onload = function() {
			$('img').each(function() {
				var width = $(this).width();
				$(this).css("max-width", width);
				$(this).css("width", '100%');
				$(this).attr("height", "");
			});
			$('p').each(function() {
				$(this).css("width", '100%');
			});

		};
	</script>

</body>
</html>