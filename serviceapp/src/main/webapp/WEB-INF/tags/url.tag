<%@ tag import="com.dh.common.UploadFileUtils" %>
<%@ tag pageEncoding="UTF-8"%>
<%
    String imgUrl= UploadFileUtils.getPathProperties("config","img.url");
   /* String imgPath= UploadFileUtils.getPathProperties("config","img.path");*/
    String fileUrl= UploadFileUtils.getPathProperties("config","file.url");
   /* String filePath= UploadFileUtils.getPathProperties("config","file.path");*/
    request.setAttribute("imgUrl",imgUrl);
    request.setAttribute("fileUrl",fileUrl);
%>
