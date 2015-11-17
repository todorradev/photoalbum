package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.dto.User;

public class ChangePasswordServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String strUserId = aRequest.getParameter("userId");
		String categoryId = aRequest.getParameter("categoryId");
		int userId = Integer.parseInt(strUserId);
		String currentPass = aRequest.getParameter("currentPass");
		String passwordError = "";
		String passwordSuccess = "";
		
		UserRegistry userReg = new UserRegistry();
		User user = userReg.getCurrentUser(userId);
		
		String userPass = user.getPassword();
		if(currentPass.equals(userPass)) {
			String newPass = aRequest.getParameter("newPass");
			String confirmNewPass = aRequest.getParameter("confirmNewPass");
			if(newPass.length() < 6) {
				passwordError = "Новата парола не може да съдържа по-малко от 6 символа.";
				aRequest.setAttribute("passwordError", passwordError);
				aRequest.setAttribute("userId", userId);
				aRequest.setAttribute("categoryId", categoryId);
				aRequest.getRequestDispatcher("ChangePassword.jsp").forward(aRequest, aResponse);
			}
			else if(newPass.equals(confirmNewPass)) {
				boolean isPassChanged = userReg.editUserPass(userPass, newPass);
				if(isPassChanged) {
					passwordSuccess = "Паролата Ви беше сменена успешно!";
					aRequest.setAttribute("passwordSuccess", passwordSuccess);
					aRequest.setAttribute("userId", userId);
					aRequest.setAttribute("categoryId", categoryId);
					aRequest.getRequestDispatcher("ChangePassword.jsp").forward(aRequest, aResponse);
				}
			}
			else {
				passwordError = "Потвърждението на паролата не съвпада.";
				aRequest.setAttribute("passwordError", passwordError);
				aRequest.setAttribute("userId", userId);
				aRequest.setAttribute("categoryId", categoryId);
				aRequest.getRequestDispatcher("ChangePassword.jsp").forward(aRequest, aResponse);
			}
		}
		else {
			passwordError = "Грешна парола!";
			aRequest.setAttribute("passwordError", passwordError);
			aRequest.setAttribute("userId", userId);
			aRequest.setAttribute("categoryId", categoryId);
			aRequest.getRequestDispatcher("ChangePassword.jsp").forward(aRequest, aResponse);
		}
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
