package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.UserRegistry;

public class DeleteUserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		Object objUserId = aRequest.getParameter("userId");
		String strUserId = objUserId.toString();
		int userId = Integer.parseInt(strUserId);
		
		UserRegistry userReg = new UserRegistry();
		userReg.removeUser(userId);
		
		aResponse.sendRedirect("Login.jsp");
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
