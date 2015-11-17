<!DOCTYPE>
<%@page import="com.toshko.photoalbum.dto.Picture"%>
<%@page import="com.toshko.photoalbum.db.PictureDB"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
<link rel="stylesheet" href="/PhotoAlbum/EditUserOptions.css" type="text/css">
<html>
<head>
<title>Промяна на снимка</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%
	Object objUserId = request.getParameter("userId");
	String strUserId = objUserId.toString();
	int userId = Integer.parseInt(strUserId);
	
	Object objCategoryId = request.getParameter("categoryId");
	String strCategoryId = objCategoryId.toString();
	int categoryId = Integer.parseInt(strCategoryId);
	
	Object objPictureId = request.getParameter("pictureId");
	String strPictureId = objPictureId.toString();
	int pictureId = Integer.parseInt(strPictureId);
  
	PictureDB pictureDb = new PictureDB();
	Picture picture = pictureDb.getPicture(userId, categoryId, pictureId);
%>
  	<jsp:include page="header.jsp" />
  	
  	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 col-sm-9">
				<jsp:include page="showCurrentPath.jsp"/>

				<div class="row">
 		
					<form id="editUserOptions" method="POST" action="editPicture">
						<div class="editPictureDiv">
							<div class="nameOfPicture">
								<label id="labels" for="nameOfPicture" for="nameOfPicture">име</label>
								<input type="text" name="nameOfPicture" id="name" value="<%=picture.getName()%>"/>
							</div>
							
							<div class="descriptionOfPicture">
								<label id="labels" for="descriptionOfPicture" for="descriptionOfPicture"><span id="descriptionText">описание</span></label>
								
								<textarea rows="4" cols="23" name="descriptionOfPicture" id="description"><%=picture.getDescription()%></textarea>
							</div>
				
							<input type="hidden" name="userId" value="<%= request.getParameter("userId")%>" />
							<input type="hidden" name="categoryId" value="<%=request.getParameter("categoryId")%>" />
							<input type="hidden" name="pictureId" value="<%= picture.getId()%>" />
							<input type="submit" value="Запази" name="submit" id="saveButton"/>
							<div id="decline">
								<a href="showCategories.do" id="cancelButton">Отказ</a>
							</div>
						</div>
					</form>
					
				</div>
			</div>
			
			<!--  Menu column -->
			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<jsp:include page="menu.jsp">
					<jsp:param name="showCategoryMenu" value="True"/>
				</jsp:include>
			</div><!--/span-->
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>