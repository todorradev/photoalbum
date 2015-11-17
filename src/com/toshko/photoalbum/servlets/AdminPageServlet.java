package com.toshko.photoalbum.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.admin.AdminDB;
import com.toshko.photoalbum.db.NotApprovedPicture;

public class AdminPageServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		AdminDB admin = new AdminDB();
		Collection<NotApprovedPicture> notApprovedPictures = admin.getUnapprovedPictures();
		aRequest.setAttribute("pictures", notApprovedPictures);
		aRequest.getRequestDispatcher("Admin.jsp").forward(aRequest, aResponse);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
