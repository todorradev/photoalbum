package com.toshko.photoalbum.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import com.toshko.photoalbum.data.UserUtils;
import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.db.CategoryXPictures;
import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.db.UserXCategoryDB;
import com.toshko.photoalbum.dto.Category;
import com.toshko.photoalbum.dto.Picture;
import com.toshko.photoalbum.dto.User;

public class ShowCategoryServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		HttpSession session = aRequest.getSession(false);
		String faceDection = aRequest.getParameter("faceDetection");
		if(faceDection != null) {
			String userIdStr = aRequest.getParameter("userId");
			String categoryIdStr = aRequest.getParameter("categoryId");
			String pictureIdStr = aRequest.getParameter("pictureId");
			
			int userId = Integer.parseInt(userIdStr);
			int categoryId = Integer.parseInt(categoryIdStr);
			int pictureId = Integer.parseInt(pictureIdStr);
			
			File[] allImages = UserUtils.getPicturesPathByCategory(userId, categoryId, pictureId);
			Collection <Picture> pictures = faceDetection(allImages, categoryId);
			
			aRequest.setAttribute("pictures", pictures);
			aRequest.setAttribute("userId", userId);
			//aRequest.setAttribute("username", username);
			aRequest.setAttribute("searchCategoriesAndPictures", "");
			aRequest.getRequestDispatcher("MainPage.jsp").forward(aRequest, aResponse);
			return;
		}
		Object objUsername = session.getAttribute("USER");
		String username = objUsername.toString();
		Object objSearch = aRequest.getParameter("searchCategoriesAndPictures");
		String searchCategoriesAndPictures = "";
		if (objSearch != null) {
			searchCategoriesAndPictures = objSearch.toString();
		}
		
		UserRegistry userReg = new UserRegistry();
		Collection<User> users = userReg.getUsers();
		int userId = 0;
		for (User user : users) {
			if(username.equals(user.getUsername())) {
				userId = user.getId();
				break;
			}
		}
		
		Object objCategoryId = aRequest.getParameter("categoryId");
		int categoryId = -2;
		if(objCategoryId != null) {
			categoryId = Integer.parseInt(objCategoryId.toString());
		}
		else {
			UserXCategoryDB userCategory = new UserXCategoryDB();
			categoryId = userCategory.getRootCategory(userId);
		}
		CategoryDB categoryDb = new CategoryDB();
		Category category = categoryDb.getCategory(categoryId);
		Collection<Category> childCategories = categoryDb.getDirectSubCategories(userId, categoryId, searchCategoriesAndPictures);
		Collection<Category> parentCategories = categoryDb.getFullPathOfDirectories(userId, categoryId);
		aRequest.setAttribute("category", category);
		aRequest.setAttribute("categoryId", category.getId());
		session.setAttribute("parentCategories", parentCategories);
		if(childCategories.size() > 0) {
			aRequest.setAttribute("childCategories", childCategories);
		}
		
		CategoryXPictures categoryXPicture = new CategoryXPictures();
		Collection<Picture> pictures  = categoryXPicture.getPicturesByCategory(categoryId, searchCategoriesAndPictures);
		aRequest.setAttribute("pictures", pictures);
		aRequest.setAttribute("userId", userId);
		aRequest.setAttribute("username", username);
		aRequest.setAttribute("searchCategoriesAndPictures", searchCategoriesAndPictures);
		aRequest.getRequestDispatcher("MainPage.jsp").forward(aRequest, aResponse);
	}
	
	public void doPost(HttpServletRequest aRequest,HttpServletResponse aResponse) throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
	
	private Collection<Picture> faceDetection(File[] listOfImages, int categoryId) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CategoryXPictures categoryXPicture = new CategoryXPictures();
		Collection<Picture> pictures  = categoryXPicture.getPicturesByCategory(categoryId, "");
		
		CascadeClassifier cascade1 = new CascadeClassifier();
		cascade1.load("C:\\Program Files\\OpenCV\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
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

}
