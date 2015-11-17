package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.CategoryXPictures;

public class DeletePictureServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		Object objPictureId = aRequest.getParameter("pictureId");
		String strPictureId = objPictureId.toString();
		int pictureId = Integer.parseInt(strPictureId);
		
		Object objCategoryId = aRequest.getParameter("categoryId");
		String strCategoryId = objCategoryId.toString();
		int categoryId = Integer.parseInt(strCategoryId);
	
		CategoryXPictures categoryPicture = new CategoryXPictures();
		categoryPicture.removeCategoryByPicture(pictureId);
		
		aResponse.sendRedirect("showCategories.do?categoryId=" + categoryId);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
