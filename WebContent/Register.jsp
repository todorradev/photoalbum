<!DOCTYPE>
<html>
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" %>
	<head>
		<link rel="stylesheet" href="/PhotoAlbum/Register.css" type="text/css">
		<title>Регистрирай се</title>
	</head>
	
	<body>
		<div class="container">
			<section id="content">
				<form id="form" method="POST" action="register">
					<h2>Създаване на Фотоалбум потребител</h2>
					
					<div>
						<%if(request.getAttribute("errorUser") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorUser") %></span>
						<% }%>
						<input type="text" placeholder="Потребител" name="user" id="username" autofocus  />
					</div>
					
					<div>
						<%if(request.getAttribute("errorPass") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorPass") %></span>
						<% }%>
						
						<input type="password" placeholder="Парола" name="pass" id="password"  />
					</div>
					
					<div>
						<%if(request.getAttribute("errorConfirmPass") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorConfirmPass") %></span>
						<% }%>
						
						<input type="password" placeholder="Повтори паролата" name="confirmPass" id="password" />
					</div>
					
					<div>
						<%if(request.getAttribute("errorFirstName") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorFirstName") %></span>
						<% }%>
						
						<input type="text" placeholder="Име" name="firstName" id="username" />
					</div>
					
					<div>	
						<%if(request.getAttribute("errorLastName") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorLastName") %></span>
						<% }%>
						<input type="text" placeholder="Фамилия" name="lastName" id="username"  />
						
					</div>
											
					<div>
						<%if(request.getAttribute("errorEmail") != null) { %>
							<span class="registerError"><%=request.getAttribute("errorEmail") %></span>
						<% }%>
						<input type="text" placeholder="имейл" name="email" id="username"  />
					</div>
					
					<div>
						<input type="submit" value="Създай профил" name="createProfile"/>
						<a id="cancelUserCreation"href="Login.jsp">Отказ</a>
					</div>
				</form>
			</section>
		</div>
	</body>
</html>