<!DOCTYPE>

<%@page import="com.toshko.photoalbum.dto.Picture"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.toshko.photoalbum.dto.Category"%>
<%@page import="java.util.Collection"%>
<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
<html>
<head>
<title>Промяна на снимка</title>
</head>
<body>
<%  
	String userId = request.getAttribute("userId").toString();
	Category category = (Category)request.getAttribute("category");
	Picture picture = (Picture)request.getAttribute("picture");
%>
	<jsp:include page="header.jsp" />
	<div class="picturePage">
		<div class="bigPic">
			<img class="bigPicture" src="/PhotoAlbum/downloadImage?userId=<%=userId%>&categoryId=<%= category.getId()%>&pictureId=<%= picture.getId()%>" alt="<%= picture.getName()%>">
		</div>
		<div class="pictureForm">
		
			<form id="updatePictureTest" action="editPicture">
				<div id="name">
					<label for="nameOfPicture">Име на снимка:</label>
					<input type="text" name="nameOfPicture" id="nameOfPicture" value="<%= picture.getName() %>">
				</div>
				<div id="description">
					<label for="descriptionOfPicture">Описание на снимката:</label>
					<input type="text" name="descriptionOfPicture" id="descriptionOfPicture" value="<%= picture.getDescription() %>">
				</div>
				<div id="sizeOfPicture">
					<label for="sizeOfPicture">Големина на снимката: </label>
					<input type="text" id="fields" value="<%= picture.getSize() %>" readonly>
				</div>
				<div id="dateOfPicture">
					<label for="dateOfPicture">Дата на добавяне на снимката: </label>
					<input type="text" id="fields" value="<%= picture.getDate() %>" readonly>
				</div>
				<input type="hidden" name="pictureId" value="<%= picture.getId()%>" />
				<input type="hidden" name="categoryId" value="<%= category.getId()%>" />
				
				<div class="pictureButtons">
					<input type="submit" value="Промени снимката" name="submit" id="updatePictureButton"/>
					<a href="showCategories.do?categoryId=<%=category.getId() %>" id="dontUpdatePicture">Отказ</a>
				</div>
			</form>
		</div>
	</div>
	
	<ul class="userOptions">
			<li id="options">
				<a href = "/PhotoAlbum/AddPicture.jsp?userId=<%= request.getAttribute("userId")%>&parent=<%= category.getId()%>">Добави нова снимка</a>
			</li>
			<li id="options">
				<a href = "/PhotoAlbum/AddCategory.jsp?parent=<%= category.getId()%>">Добави нова категория</a>
			</li>
			<li id="options">
				<a href = "/PhotoAlbum/deleteCategory.do?userId=<%= request.getAttribute("userId")%>&categoryId=<%= category.getId()%>">Изтрий категория</a>
			</li>
		</ul>
<jsp:include page="footer.jsp" />
</body>
</html>