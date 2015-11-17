package com.toshko.photoalbum.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.toshko.photoalbum.data.UserUtils;

public class ImageDownloaderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("userId"));
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		int imageId = Integer.parseInt(req.getParameter("pictureId"));

	    // Set content type
	    resp.setContentType("image/jpeg");

	    // Set content size
		byte[] imageContent = UserUtils.loadPicture(userId, categoryId, imageId);
	    resp.setContentLength(imageContent.length);

	    // Write the image content into output streams
	    OutputStream out = resp.getOutputStream();
	    out.write(imageContent);
	    out.close();
	}
}
