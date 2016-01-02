package com.toshko.photoalbum.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.toshko.photoalbum.admin.AdminDB;
import com.toshko.photoalbum.data.UserUtils;
import com.toshko.photoalbum.dto.Picture;

public class AddPictureServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String nameOfPicture = aRequest.getParameter("nameOfPicture");
		String descriptionOfPicture = aRequest.getParameter("descriptionOfPicture");
		String strParentDirectoryId = null;
		String strUserId = null;
		byte[] fileContent = null;

		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(aRequest);
			for (FileItem item : items) {
				if (item.isFormField()) {
					// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					if ("nameOfPicture".equals(item.getFieldName())) {
						nameOfPicture = item.getString();
					} else if ("descriptionOfPicture".equals(item.getFieldName())) {
						descriptionOfPicture = item.getString();
					} else if ("parent".equals(item.getFieldName())) {
						strParentDirectoryId = item.getString();
					}else if ("userId".equals(item.getFieldName())) {
						strUserId = item.getString();
					}
				} else {
					//Process form file field (input type="file").
					if ("pictureFile".equals(item.getFieldName())) {
						InputStream is = item.getInputStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int read = -1;
						byte[] cnt = new byte[1024];
						while (true) {
							read = is.read(cnt);
							if (read == -1) {
								break;
							}
							baos.write(cnt, 0, read);
						}
						is.close();
						fileContent = baos.toByteArray();	
					}
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException("Cannot parse multipart request.", e);
		}

		int parentDirectoryId = Integer.parseInt(strParentDirectoryId);
		int userId = Integer.parseInt(strUserId);
		
		Picture picture = new Picture(nameOfPicture, descriptionOfPicture, UserUtils.getCurrentTime(), fileContent);
		AdminDB admin = new AdminDB();
		admin.createPicture(userId, parentDirectoryId, picture);

		aRequest.setAttribute("picture", picture);
		aResponse.sendRedirect("showCategories.do?categoryId=" + parentDirectoryId);
	}

	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
