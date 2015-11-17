package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.db.CategoryXSubcategory;

public class DeleteCategoryServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		Object objUserId = aRequest.getParameter("userId");
		String strUserId = objUserId.toString();
		int userId = Integer.parseInt(strUserId);
		
		Object objCategoryId = aRequest.getParameter("categoryId");
		String strCategoryId = objCategoryId.toString();
		int categoryId = Integer.parseInt(strCategoryId);
		
		CategoryXSubcategory categoryXsubcategory = new CategoryXSubcategory();
		int parentCategoryId = categoryXsubcategory.getParentCategory(categoryId);
		
		if(parentCategoryId != -1) {
			CategoryDB categoryDb = new CategoryDB();
			categoryDb.removeCategory(userId, categoryId);
			aResponse.sendRedirect("showCategories.do?categoryId=" + parentCategoryId);
		}
		else {
			aResponse.sendRedirect("showCategories.do?categoryId=" + categoryId);
		}
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
