package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.dto.User;

public class EditProfileServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		HttpSession session = aRequest.getSession();
		Object obj = session.getAttribute("USER");
		String username = obj.toString();
		String firstName = aRequest.getParameter("firstName");
		String lastName = aRequest.getParameter("lastName");
		String email = aRequest.getParameter("email");
		
		User user = new User(username, firstName, lastName, email);
		
		UserRegistry userRegistry = new UserRegistry();
		userRegistry.editUser(user);
		
		aRequest.getRequestDispatcher("showCategories.do").forward(aRequest, aResponse);
		
	}
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
