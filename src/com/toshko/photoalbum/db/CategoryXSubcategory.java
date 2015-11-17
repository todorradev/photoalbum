package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.toshko.photoalbum.dto.Category;

public class CategoryXSubcategory {
	private static final String INSERT_CATEGORYXSUBCATEGORY  = "INSERT INTO CategoryXSubcategory (categoryId, subcategoryId) VALUES(?, ?)";
	private static final String DELETE_CATEGORYXSUBCATEGORY  = "DELETE FROM CategoryXSubcategory WHERE categoryId = ?";
	private static final String DELETE_BY_SUBCATEGORY  = "DELETE FROM CategoryXSubcategory WHERE subCategoryId = ?";
	private static final String SELECT_ALLSUBCATEGORY  = "SELECT id, name, description from Category JOIN CategoryXSubcategory ON Category.id = CategoryXSubcategory.subcategoryId WHERE categoryId = ? and name like ?";
	private static final String SELECT_PARENTCATEGORY  = "SELECT categoryId FROM CategoryXSubcategory WHERE subcategoryId = ?";
	
	public Collection<Category> getSubcategories(int categoryId, String searchCriteria) {
		Connection conn = null;
		Collection<Category> categories = new ArrayList<Category>();
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALLSUBCATEGORY);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setString(2, "%" + searchCriteria + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Category category = new Category(id, name, description);
				categories.add(category);
			}
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
		return categories;
	}
	
	public int getParentCategory (int subcategoryId) {
		Connection conn = null;
		int parentCategoryId = -1;
		
		try {
			
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PARENTCATEGORY);
			preparedStatement.setInt(1, subcategoryId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				parentCategoryId = resultSet.getInt("categoryId");
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
		return parentCategoryId;
	}
	
	public boolean createSubcategory(int parentCategory, int subCategory) {
		Connection conn = null;
		boolean isUserSubcategoryCreated = false;
		
		try {
			
			// insert in CategoryXSubcategory record
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CATEGORYXSUBCATEGORY);
			preparedStatement.setInt(1, parentCategory);
			preparedStatement.setInt(2, subCategory);
			preparedStatement.executeUpdate();
			
			isUserSubcategoryCreated = true;
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
		return isUserSubcategoryCreated;
	}
	
	public boolean removeAllSubcategoriesInCategory(int categoryId) {
		boolean isAllCategoriesRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CATEGORYXSUBCATEGORY);
			preparedStatement.setInt(1, categoryId);
			int rowDeleted = preparedStatement.executeUpdate();
			isAllCategoriesRemoved = rowDeleted > 0 ;
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
		return isAllCategoriesRemoved;
	}

	public boolean removeSubCategory(int subcategoryId) {
		boolean isAllSubcategoriesRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BY_SUBCATEGORY);
			preparedStatement.setInt(1, subcategoryId);
			int rowDeleted = preparedStatement.executeUpdate();
			isAllSubcategoriesRemoved = rowDeleted > 0 ;
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
		return isAllSubcategoriesRemoved;
	}
}
