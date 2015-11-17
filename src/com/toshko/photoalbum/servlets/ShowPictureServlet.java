package com.toshko.photoalbum.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.db.PictureDB;
import com.toshko.photoalbum.dto.Category;
import com.toshko.photoalbum.dto.Picture;

public class ShowPictureServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		
		Object objCategoryId = aRequest.getParameter("categoryId");
		Object objPictureId = aRequest.getParameter("pictureId");
		Object objUserId = aRequest.getParameter("userId");
		
		int pictureId = Integer.parseInt(objPictureId.toString());
		int categoryId = Integer.parseInt(objCategoryId.toString());
		int userId = Integer.parseInt(objUserId.toString());
		
		CategoryDB categoryDb = new CategoryDB();
		Category category = categoryDb.getCategory(categoryId);
		Collection<Category> parentCategories = categoryDb.getFullPathOfDirectories(userId, categoryId);
		
		aRequest.setAttribute("category", category);
		aRequest.setAttribute("parentCategories", parentCategories);
		
		PictureDB pictureDb = new PictureDB();
		Picture picture = pictureDb.getPicture(userId, categoryId, pictureId);
		
		aRequest.setAttribute("picture", picture);
		aRequest.setAttribute("category", category);
		aRequest.setAttribute("userId", userId);
		aRequest.getRequestDispatcher("ShowPicture.jsp").forward(aRequest, aResponse);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
