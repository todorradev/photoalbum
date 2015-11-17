package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.admin.AdminDB;
import com.toshko.photoalbum.db.PictureDB;

public class AdminDeletePictureServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String strPictureId = aRequest.getParameter("pictureId");
		int pictureId = Integer.parseInt(strPictureId);
		AdminDB admin = new AdminDB();
		admin.removePicture(pictureId);
		
		PictureDB pictureDb = new PictureDB();
		pictureDb.removePicture(pictureId);
		
		aResponse.sendRedirect("AdminPageServlet.do");
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
