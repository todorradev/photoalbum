package com.toshko.photoalbum.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.dto.User;

public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String username = aRequest.getParameter("user");
		String password = aRequest.getParameter("pass");
		String confirmPassword = aRequest.getParameter("confirmPass");
		String firstName = aRequest.getParameter("firstName");
		String lastName = aRequest.getParameter("lastName");
		String email = aRequest.getParameter("email");
		String errorUser = "";
		String errorPass = "";
		String errorConfirmPass = "";
		String errorFirstName = "";
		String errorLastName = "";
		String errorEmail = "";
		String patternEmail = "^[a-zA-Z0-9.!#$%&amp;'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(patternEmail);
		Matcher matcher = pattern.matcher(email);
		
		boolean isAllFieldsCorrect = true;
		boolean isUsernameAccepted = true;
		UserRegistry userReg = new UserRegistry();
		Collection<User> allRegisterdUsers = userReg.getUsers();
		
		for (User user : allRegisterdUsers) {
			if(user.getUsername().equals(username)) {
				errorUser = "Потребителското име е заето.";
				isUsernameAccepted = false;
				break;
			}
		}
		if(isUsernameAccepted) {
			
			if(username.length() < 5) {
				errorUser = "Това поле не може да съдържа по-малко от 5 символа.";
				isAllFieldsCorrect=false;
			}
			
			if(password.length() < 6) {
				errorPass = "Това поле не може да съдържа по-малко от 6 символа.";
				isAllFieldsCorrect=false;
			}
			
			if(!(confirmPassword.equals(password))) {
				errorConfirmPass="Потвърждението на паролата не съвпада.";
				isAllFieldsCorrect=false;
			}
			
			if(firstName.length() < 2) {
				errorFirstName = "Това поле не може да съдържа по-малко от 2 символа.";
				isAllFieldsCorrect=false;
			}
			
			if(lastName.length() < 2) {
				errorLastName = "Това поле не може да съдържа по-малко от 2 символа.";
				isAllFieldsCorrect=false;
			}
			
			if (!matcher.find()){
				errorEmail = "Пример за валиден имейл - me@example.com";
			}
			
			if(isAllFieldsCorrect) { 
				User user = new User(username, password, firstName, lastName, email);
				UserRegistry userRegistry = new UserRegistry();
				boolean isUserCreated = userRegistry.createUser(user);
				if(isUserCreated) {
					aRequest.getRequestDispatcher("Login.jsp").forward(aRequest, aResponse);
					return;
				}
			}
		}
		aRequest.setAttribute("errorUser", errorUser);
		aRequest.setAttribute("errorPass", errorPass);
		aRequest.setAttribute("errorConfirmPass", errorConfirmPass);
		aRequest.setAttribute("errorFirstName", errorFirstName);
		aRequest.setAttribute("errorLastName", errorLastName);
		aRequest.setAttribute("errorEmail", errorEmail);
		aRequest.getRequestDispatcher("Register.jsp").forward(aRequest, aResponse);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
