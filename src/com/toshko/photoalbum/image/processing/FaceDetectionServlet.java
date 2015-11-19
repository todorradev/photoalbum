package com.toshko.photoalbum.image.processing;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import com.toshko.photoalbum.data.UserUtils;

public class FaceDetectionServlet extends HttpServlet{
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
		
		// TODO add external logic including searchCriteria
		File[] allImages = UserUtils.getPicturesPathByCategory(userId, categoryId, pictureId);
		faceDetection(allImages);
		
		RequestDispatcher rd = aRequest.getRequestDispatcher("showCategories.do");
		rd.forward(aRequest,aResponse);
	}
	
	private void faceDetection(File[] listOfImages) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CascadeClassifier cascade1 = new CascadeClassifier();
		cascade1.load("C:\\Program Files\\OpenCV\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
		MatOfRect faceDetections = new MatOfRect();
		
		for(File file : listOfImages) {
			Mat image = Imgcodecs.imread(file.getAbsolutePath());
			cascade1.detectMultiScale(image, faceDetections);
			System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
		}
		
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
