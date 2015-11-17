package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.dto.Category;

public class AddCategoryServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String nameOfCategory = aRequest.getParameter("nameOfCategory");
		String descriptionOfCategory = aRequest.getParameter("descriptionOfCategory");
		Object obj = aRequest.getParameter("parent");
		
		String  parentDirectory =  obj.toString();
		int test = Integer.parseInt(parentDirectory);
		
		Category category = new Category(nameOfCategory, descriptionOfCategory);
		CategoryDB categoryDb = new CategoryDB();
		categoryDb.createCategory(category,test);
		
		aRequest.setAttribute("category", category);
		aResponse.sendRedirect("showCategories.do?categoryId=" + test);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
