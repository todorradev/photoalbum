package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.dto.Category;

public class EditCategoryServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String name = aRequest.getParameter("nameOfCategory");
		String description = aRequest.getParameter("descriptionOfCategory");
		String strChildCategoryId = aRequest.getParameter("childCategoryId");
		int childCategoryId = Integer.parseInt(strChildCategoryId);

		CategoryDB categoryDb = new CategoryDB();
		Category category= categoryDb.getCategory(childCategoryId);
		category.setName(name);
		category.setDescription(description);
		categoryDb.editCategory(category);
		aRequest.getRequestDispatcher("showCategories.do").forward(aRequest, aResponse);
		
	}
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
