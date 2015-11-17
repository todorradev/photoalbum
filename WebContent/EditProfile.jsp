<!DOCTYPE>
<%@page import="com.toshko.photoalbum.dto.User"%>
<%@page import="com.toshko.photoalbum.db.UserRegistry"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
<link rel="stylesheet" href="/PhotoAlbum/EditUserOptions.css" type="text/css">
<html>
<head>
<title>Промяна на профила</title>
</head>
<body>
<%UserRegistry userReg = new UserRegistry();
  User user = userReg.getCurrentUser(Integer.parseInt(request.getParameter("userId")));%>
	<jsp:include page="header.jsp" />
  	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 col-sm-9">
				<jsp:include page="showCurrentPath.jsp"/>

				<div class="row">
					<form id="editUserOptions" method="POST" action="editProfileForm">
						<div class="firstName">
							<label id="labels" for="firstName">Име:</label>
							<input type="text" name="firstName" id="name" value = "<%=user.getFirstName() %>" required />
						</div>
						
						<div class="lastName">
							<label id="labels" for="lastName">Фамилия:</label>
							<input type="text" name="lastName" id="lastName" value = "<%=user.getLastName() %>" required />
						</div>
				
						<div class="email">
							<label id="labels" for="email">Имейл:</label>
							<input type="text" name="email" id="email" value = "<%=user.getEmail()%>" required />
						</div>
						
						<input type="submit" value="Запази" name="submit" id="saveButton"/>
						<div id="decline">
							<a href="showCategories.do?categoryId=<%=request.getParameter("categoryId")%>" id="cancelButton">Отказ</a>
						</div>
					</form>
				</div>
			</div>
			
			<!--  Menu column -->
			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<jsp:include page="menu.jsp">
					<jsp:param name="showCategoryMenu" value="True"/>
					<jsp:param name="activeMenuItem" value="editProfile"/>
				</jsp:include>
			</div><!--/span-->
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>