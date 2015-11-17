package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.admin.AdminDB;
import com.toshko.photoalbum.db.CategoryXPictures;

public class AdminApprovedPictureServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String strCategoryId = aRequest.getParameter("categoryId");
		String strPictureId = aRequest.getParameter("pictureId");
		int categoryId = Integer.parseInt(strCategoryId);
		int pictureId = Integer.parseInt(strPictureId);
		
		CategoryXPictures categoryPicture = new CategoryXPictures();
		categoryPicture.createCategoryWithPicture(categoryId, pictureId);
		
		AdminDB admin = new AdminDB();
		admin.removePicture(pictureId);
		
		aResponse.sendRedirect("AdminPageServlet.do");
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
