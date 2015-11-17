package com.toshko.photoalbum.image.processing;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import com.toshko.photoalbum.data.UserUtils;

public class RotatePictureServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		String userIdStr = aRequest.getParameter("userId");
		String categoryIdStr = aRequest.getParameter("categoryId");
		String pictureIdStr = aRequest.getParameter("pictureId");
		
		int userId = Integer.parseInt(userIdStr);
		int categoryId = Integer.parseInt(categoryIdStr);
		int pictureId = Integer.parseInt(pictureIdStr);
		
		rotateImage(userId, categoryId, pictureId);
		
		RequestDispatcher rd = aRequest.getRequestDispatcher("showCategories.do");
		rd.forward(aRequest,aResponse);
	}
	
	private void rotateImage(int userId, int categoryId, int pictureId) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		File file = UserUtils.getPicturePath(userId, categoryId, pictureId);
		Mat cvImage = Imgcodecs.imread(file.getAbsolutePath());
		
		int i = 0;
		while (i < 1 ) {
			Core.transpose(cvImage, cvImage);
			Core.flip(cvImage, cvImage, 1);
			i++;
		}
		String path = file.getAbsolutePath();
		//file.delete();
		Imgcodecs.imwrite(path, cvImage);
		
		
		cvImage.release();
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
