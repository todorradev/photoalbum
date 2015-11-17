<!DOCTYPE>
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="Login.css" type="text/css">
<html>
<head>
<title>Login</title>
</head>	
	<body>
		<div class="container">
			<section id="content">
				<form id="form" method="POST" action="login">
					<h2>Вход във Фотоалбум</h2>
					<% 
						Collection<String> errors = (Collection<String>)request.getAttribute("errors");
						if (errors != null) {
							for (String error : errors) {
					%>
								<label class="loginError"><%=error%></label>		
					<%
							}
						}
					%>
					<div>
						
						<input type="text" placeholder="Потребител" name="username" id="username" autofocus />
					</div>
					
					<div>
						<input type="password" placeholder="Парола" name="password" id="password" />
					</div>
					
					<div >
						<input type="submit" value="Вход" name="submit"/>
						<a href="Register.jsp">Регистрирай се! </a>
					</div>
				</form>
			</section>
		</div>
	</body>
</html>