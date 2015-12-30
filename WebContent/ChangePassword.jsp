<!DOCTYPE>
<%@ page contentType="text/html;charset=UTF-8" %>
<title>Промяна на парола</title>
<link rel="stylesheet" href="/PhotoAlbum/MainPage.css" type="text/css">
<link rel="stylesheet" href="/PhotoAlbum/EditUserOptions.css" type="text/css">
<body>
	<jsp:include page="header.jsp" />
  	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="col-xs-12 col-sm-9">
				<jsp:include page="showCurrentPath.jsp"/>
				<div class="row">
				<h1 id="changePasswordHeader">Промяна на парола</h1>
				<hr>
					<form id="editUserOptions" method="POST" action="changePassword">
						<div id="changePassDivs">
							<div class="currentPass">
								<label id="labels" for="firstName">Настояща парола:</label>
								<input type="password" name="currentPass" class="pass" autofocus required />
							</div>
							
							<div class="newPass">
								<label id="labels" for="newPass">Нова парола:</label>
								<input type="password" name="newPass" class="newPassword" required />
							</div>
					
							<div class="confirmNewPass">
								<%if(request.getAttribute("passwordError") != null) { %>
									<span class="changePasswordError"><%=request.getAttribute("passwordError") %></span>
								<% }%>
								
								<label id="labels" for="confirmNewPass">Повтори парола:</label>
								<input type="password" name="confirmNewPass" class="confirmPass" required />
							</div>
						</div>
						
						<%if(request.getAttribute("passwordSuccess") != null) { %>
							<span class="changePasswordSuccess"><%=request.getAttribute("passwordSuccess") %></span>
						<% }%>
						<input type="hidden" name="userId" value="<%= request.getParameter("userId")%>" />
						<input type="hidden" name="categoryId" value="<%= request.getParameter("categoryId")%>" />
						<div class="changePasswordButtons">
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
					<jsp:param name="activeMenuItem" value="changePassword"/>
				</jsp:include>
			</div><!--/span-->
		</div>
		<jsp:include page="footer.jsp"/>
	</div>
</body>
</html>