<!DOCTYPE>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
<link rel="stylesheet" href="/PhotoAlbum/EditUserOptions.css" type="text/css">
<html>
<head>
<title>Добавяне на снимка</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="header.jsp" />
  	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 col-sm-9">
				<jsp:include page="showCurrentPath.jsp"/>

				<div class="row">
					<form id="editUserOptions" method="POST" enctype="multipart/form-data" action="addingPicture">
						<div class="addPictureDiv">
							<div class="nameOfPic">
								<label id="labels" for="nameOfPicture" for="nameOfPicture">име</label>
								<input type="text" name="nameOfPicture" id="name" autofocus required/>
							</div>
							
							<div class="descriptionOfPic">
								<label id="labels" for="descriptionOfPicture" for="descriptionOfPicture"><span id="descriptionText">описание</span></label>
								<textarea rows="4" cols="23" name="descriptionOfPicture" id="description"></textarea>
							</div>
							
							<input type="file" name="pictureFile" class="upload" required/>
							<input type="hidden" name="parent" value="<%= request.getParameter("categoryId")%>" />
							<input type="hidden" name="userId" value="<%= request.getParameter("userId")%>" />
							
							<input type="submit" value="Запази" name="submit" id="saveButton"/>
							<div id="decline">
							<a href="showCategories.do?categoryId=<%=request.getParameter("categoryId")%>" id="cancelButton">Отказ</a>
							</div>
						</div>
					</form>
				</div>
			</div>
			
			<!--  Menu column -->
			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<jsp:include page="menu.jsp">
					<jsp:param name="showCategoryMenu" value="True"/>
					<jsp:param name="activeMenuItem" value="addPicture"/>
				</jsp:include>
			</div><!--/span-->
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	
</body>
</html>