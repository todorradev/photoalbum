<!DOCTYPE>

<%@page import="com.toshko.photoalbum.db.NotApprovedPicture"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.Collection"%>
<html>
<head>
</head>
<link rel="stylesheet" href="/PhotoAlbum/Admin.css" type="text/css">
<body>
	<a class="logoutAdmin" href="/PhotoAlbum/logoutAdmin">Изход</a>
	<%
	Collection<NotApprovedPicture> pictures = (Collection<NotApprovedPicture>)request.getAttribute("pictures");
	for (NotApprovedPicture notApprovedPicture : pictures) {%>
	<div style="display: inline-block">
		<div style="display: inline-block" >
			<a href="/PhotoAlbum/downloadImage?userId=<%= notApprovedPicture.getUserId()%>&categoryId=<%= notApprovedPicture.getCategoryId()%>&pictureId=<%= notApprovedPicture.getPictureId()%>">
				<img class="waitingForApprovingPicture" src="/PhotoAlbum/downloadImage?userId=<%= notApprovedPicture.getUserId()%>&categoryId=<%= notApprovedPicture.getCategoryId()%>&pictureId=<%= notApprovedPicture.getPictureId()%>" height="240" width="275">
			</a>
		</div>
		<div>
			<a id="approvedPicture" href = "/PhotoAlbum/AdminApprovedPicture.do?userId=<%=notApprovedPicture.getUserId() %>&categoryId=<%=  notApprovedPicture.getCategoryId()%>&pictureId=<%=notApprovedPicture.getPictureId()%>">Одобри снимка</a>
			<a id="notApprovedPicture" href = "/PhotoAlbum/AdminDeletePicture.do?pictureId=<%=notApprovedPicture.getPictureId()%>">Изтрий снимка</a>
		</div>
	</div>
	<% } %>
	
	<%if(pictures.size() == 0) { %>
		<div class="adminMessage">Няма нови снимки за преглеждане!!!</div>
	<%}%>	
	</body>
</html>