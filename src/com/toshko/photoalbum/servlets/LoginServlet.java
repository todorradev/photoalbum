package com.toshko.photoalbum.servlets;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.toshko.photoalbum.data.UserUtils;
import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.password.hashing.PasswordHashing;

public class LoginServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String username = aRequest.getParameter("username");
		String password = aRequest.getParameter("password");

		if(username.equals("admin") && password.equals("admin")) {
			HttpSession session = aRequest.getSession();
			session.setAttribute("USER", username);
			session.setAttribute("PASSWORD", password);
			UserUtils.setCurrentUser(session, username);
			aResponse.sendRedirect("AdminPageServlet.do");
			return;
		}

		UserRegistry userRegistry = new UserRegistry();
		String hashedPassword = PasswordHashing.generateHash(password);//generate hashed password based on the user input for password.
		boolean result = userRegistry.isValidCredentials(username, hashedPassword);
		if (result) { 
			HttpSession session = aRequest.getSession();
			session.setAttribute("USER", username);
			session.setAttribute("PASSWORD", password);
			UserUtils.setCurrentUser(session, username);
			aResponse.sendRedirect("showCategories.do");
			return;
		}
		else {
			Collection<String> errors = new LinkedList<String>();
			errors.add("Невалиден потребител или парола.");
			aRequest.setAttribute("errors", errors);
			aRequest.getRequestDispatcher("Login.jsp").forward(aRequest, aResponse);
		}
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}