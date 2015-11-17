package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.toshko.photoalbum.dto.Category;

public class CategoryDB {
	private static final int NO_PARENT = -1;
	private static final String INSERT_CATEGORY  = "INSERT INTO Category (name, description) VALUES(?, ?)";
	private static final String SELECT_CATEGORY  = "SELECT name, description FROM Category WHERE id=?";
	private static final String DELETE_CATEGORY  = "DELETE FROM Category WHERE id = ?";
	private static final String UPDATE_CATEGORY  = "UPDATE Category SET name = ?, description = ? WHERE id = ?";
	
	public boolean createCategory(Category category) {
		return createCategory(category, NO_PARENT);
	}
	
	public boolean createCategory(Category category, int parentCategory) {
		boolean isCategoryCreated = false;
		Connection conn = null;
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			category.setId(rs.getInt(1));
			
			CategoryXSubcategory subCategoryUtils = new CategoryXSubcategory();
			isCategoryCreated = subCategoryUtils.createSubcategory(parentCategory, category.getId());
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch (SQLException e) {
				
			}
			
		}
		return isCategoryCreated;
	}
	
	public Collection<Category> getDirectSubCategories(int userId, int parentCategory) {
		return getDirectSubCategories(userId, parentCategory, "");
	}
	public Collection<Category> getDirectSubCategories(int userId, int parentCategory, String searchCategoriesAndPictures) {
		Collection<Category> allCategories = new ArrayList<Category>();
		if (parentCategory == NO_PARENT) {
			UserXCategoryDB utils = new UserXCategoryDB();
			int rootCategory = utils.getRootCategory(userId);
			if (rootCategory != -1) {
				Category category = getCategory(rootCategory);
				allCategories.add(category);
			}
		} else {
			CategoryXSubcategory utils = new CategoryXSubcategory();
			allCategories = utils.getSubcategories(parentCategory, searchCategoriesAndPictures);	
		}
		
		return allCategories;
	}
	
	public Collection<Category> getFullPathOfDirectories(int userId, int categoryId) {
		Collection<Category> allCategories = new ArrayList<Category>();
		if (categoryId == NO_PARENT) {
			return allCategories;
		} else {
			CategoryXSubcategory utils = new CategoryXSubcategory();
			int parentCategory = utils.getParentCategory(categoryId);
			if(parentCategory != -1){
				Category category = getCategory(parentCategory);
				allCategories = getFullPathOfDirectories(userId, parentCategory);
				allCategories.add(category);
			}	
		}
		return allCategories;
	}
	
	public Category getCategory(int id) {
		Category category = null;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CATEGORY);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				
				category = new Category(id, name, description);	
			}
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException e) {
				
			}
		}
		return category;
	}
	
	private boolean removeCategoryFromDB(int categoryId) {		
		boolean isCategoryRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CATEGORY);
			preparedStatement.setInt(1, categoryId);
			int rowDeleted = preparedStatement.executeUpdate();
			
			CategoryXPictures categoryPictures = new CategoryXPictures();
			categoryPictures.removeAllPicturesInCategory(categoryId);
			CategoryXSubcategory utils = new CategoryXSubcategory();
			utils.removeAllSubcategoriesInCategory(categoryId);
			
			isCategoryRemoved = rowDeleted > 0 ;
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException e) {
				
			}
		}
		return isCategoryRemoved;
	}
	
	public boolean removeCategory(int userId, int categoryId) {
		Collection<Category> subCategories = getDirectSubCategories(userId, categoryId);
		CategoryXPictures categoryPictures = new CategoryXPictures();
		categoryPictures.removeAllPicturesInCategory(categoryId);
		for (Category c : subCategories) {
			removeCategory(userId, c.getId());
		}
		
		return removeCategoryFromDB(categoryId);
	}
	
	public boolean removeAllUserCategory(int userId) {
		Collection<Category> rootCategories = getDirectSubCategories(userId, NO_PARENT);
		for (Category c : rootCategories){
			boolean succ = removeCategory(userId, c.getId());
			CategoryXSubcategory utils = new CategoryXSubcategory();
			utils.removeSubCategory(c.getId());
			if (!succ) {
				return false;
			}
		}
		
		UserXCategoryDB utils = new UserXCategoryDB();
		boolean result = utils.removeUserCategory(userId);
		return result;
	}
	
	public boolean editCategory(Category category) {
		Connection conn = null;
		boolean isCategoryEdited = false;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CATEGORY);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());
			
			preparedStatement.setInt(3, category.getId());
			
			int rowUpdated = preparedStatement.executeUpdate();
			isCategoryEdited = rowUpdated > 0;
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			}
			catch(SQLException e) {
				
			}
		}
		return isCategoryEdited;
	}
}
