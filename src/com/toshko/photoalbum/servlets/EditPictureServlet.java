package com.toshko.photoalbum.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.db.PictureDB;
import com.toshko.photoalbum.dto.Picture;

public class EditPictureServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String name = aRequest.getParameter("nameOfPicture");
		String description = aRequest.getParameter("descriptionOfPicture");
		String strPictureId = aRequest.getParameter("pictureId");
		int pictureId = Integer.parseInt(strPictureId);

		PictureDB pictureDb = new PictureDB();
		Picture picture = pictureDb.getPicture(pictureId);
		picture.setName(name);
		picture.setDescription(description);
		pictureDb.editPicture(picture);
		aRequest.getRequestDispatcher("showCategories.do").forward(aRequest, aResponse);
		
	}
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
