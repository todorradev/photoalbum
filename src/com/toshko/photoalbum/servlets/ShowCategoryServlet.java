package com.toshko.photoalbum.servlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.db.CategoryXPictures;
import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.db.UserXCategoryDB;
import com.toshko.photoalbum.dto.Category;
import com.toshko.photoalbum.dto.Picture;
import com.toshko.photoalbum.dto.User;
import com.toshko.photoalbum.image.processing.FaceDetection;

public class ShowCategoryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws IOException, ServletException {
		HttpSession session = aRequest.getSession(false);
		String faceDection = aRequest.getParameter("faceDetection");
		if (faceDection != null) {

			String userIdStr = aRequest.getParameter("userId");
			int userId = Integer.parseInt(userIdStr);
			String searchedText = getSearchText(aRequest);
			int categoryId = getCategoryId(aRequest, userId);

			CategoryXPictures categoryXPicture = new CategoryXPictures();
			Collection<Picture> filteredPictures = categoryXPicture.getPicturesByCategory(categoryId, searchedText);

			Collection<Picture> pictures = FaceDetection.scanImages(aRequest, userId, filteredPictures);

			aRequest.setAttribute("userId", userId);
			aRequest.setAttribute("pictures", pictures);
			aRequest.setAttribute("searchCategoriesAndPictures", "");
			aRequest.getRequestDispatcher("MainPage.jsp").forward(aRequest, aResponse);

			return;
		}

		Object objUsername = session.getAttribute("USER");
		String username = objUsername.toString();

		UserRegistry userReg = new UserRegistry();
		Collection<User> users = userReg.getUsers();
		int userId = 0;
		for (User user : users) {
			if (username.equals(user.getUsername())) {
				userId = user.getId();
				break;
			}
		}

		int categoryId = getCategoryId(aRequest, userId);

		CategoryDB categoryDb = new CategoryDB();
		Category category = categoryDb.getCategory(categoryId);

		String searchCategoriesAndPictures = getSearchText(aRequest);

		Collection<Category> childCategories = categoryDb.getDirectSubCategories(userId, categoryId,
				searchCategoriesAndPictures);
		Collection<Category> parentCategories = categoryDb.getFullPathOfDirectories(userId, categoryId);
		aRequest.setAttribute("category", category);
		aRequest.setAttribute("categoryId", category.getId());
		session.setAttribute("parentCategories", parentCategories);

		if (childCategories.size() > 0) {
			aRequest.setAttribute("childCategories", childCategories);
		}

		CategoryXPictures categoryXPicture = new CategoryXPictures();
		Collection<Picture> pictures = categoryXPicture.getPicturesByCategory(categoryId, searchCategoriesAndPictures);

		aRequest.setAttribute("pictures", pictures);
		aRequest.setAttribute("filteredPictures", searchCategoriesAndPictures);
		aRequest.setAttribute("userId", userId);
		aRequest.setAttribute("username", username);
		aRequest.setAttribute("searchCategoriesAndPictures", searchCategoriesAndPictures);
		aRequest.getRequestDispatcher("MainPage.jsp").forward(aRequest, aResponse);
	}
	
	private String getSearchText(HttpServletRequest aRequest) {
		Object objSearch = aRequest.getParameter("searchCategoriesAndPictures");
		String searchCategoriesAndPictures = "";
		if (objSearch != null) {
			searchCategoriesAndPictures = objSearch.toString();
		}

		return searchCategoriesAndPictures;
	}
	
	private int getCategoryId(HttpServletRequest aRequest, int userId) {
		Object objCategoryId = aRequest.getParameter("categoryId");
		int categoryId = -2;

		if (objCategoryId != null) {
			categoryId = Integer.parseInt(objCategoryId.toString());
		}

		else {
			UserXCategoryDB userCategory = new UserXCategoryDB();
			categoryId = userCategory.getRootCategory(userId);
		}

		return categoryId;
	}

	public void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws IOException, ServletException {
		doGet(aRequest, aResponse);
	}
}
