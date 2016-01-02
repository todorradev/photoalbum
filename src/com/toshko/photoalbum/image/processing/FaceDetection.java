package com.toshko.photoalbum.image.processing;

import java.io.File;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import com.toshko.photoalbum.data.UserUtils;
import com.toshko.photoalbum.db.CategoryXPictures;
import com.toshko.photoalbum.dto.Picture;

public class FaceDetection {

	static {
		System.out.println("\nRunning FaceDetector");
	}
	
	public static Collection<Picture> scanImages(HttpServletRequest aRequest, int userId, Collection<Picture> pictures) {
		String categoryIdStr = aRequest.getParameter("categoryId");
		String pictureIdStr = aRequest.getParameter("pictureId");

		int categoryId = Integer.parseInt(categoryIdStr);
		int picId = Integer.parseInt(pictureIdStr);

		File[] listOfImages = UserUtils.getPicturesPathByCategory(userId, categoryId, picId);
		
		if(listOfImages != null) {

			CategoryXPictures categoryXPicture = new CategoryXPictures();

			if(pictures == null)
				pictures  = categoryXPicture.getPicturesByCategory(categoryId, "");

			CascadeClassifier cascade1 = new CascadeClassifier();
			cascade1.load("C:/Program Files/OpenCV/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
			MatOfRect faceDetections = new MatOfRect();
	
			for(File file : listOfImages) {
				Mat image = Imgcodecs.imread(file.getAbsolutePath());
				cascade1.detectMultiScale(image, faceDetections);
				if(faceDetections.toArray().length == 0) {
					for (Picture picture : pictures) {
						StringBuilder sb = new StringBuilder();
						sb.append(picture.getId());
						String pictureId = sb.toString();
						if(file.getName().contains(pictureId)) {
							pictures.remove(picture);
							break;
						}
						
					}
				}
				System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
			}
			return pictures;
		}
		return null;
	}
}
