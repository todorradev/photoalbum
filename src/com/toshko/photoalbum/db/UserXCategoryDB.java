package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.toshko.photoalbum.dto.Category;

public class UserXCategoryDB {
	private static final String INSERT_USERXCATEGORY  = "INSERT INTO UserXCategory (userId, categoryId) VALUES(?, ?)";
	private static final String SELECT_USERXCATEGORY =  "SELECT categoryId FROM UserXCategory where userId=?";
	private static final String DELETE_USERXCATEGORY  = "DELETE FROM UserXCategory WHERE userId = ?";
	
	public boolean createUserCategory(int userId, Category category) {
		Connection conn = null;
		boolean isUserCategoryCreated = false;
		
		try {
			// crate category record
			CategoryDB categoryDb =  new CategoryDB();
			categoryDb.createCategory(category);
			
			// insert in UserXCategory record
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USERXCATEGORY);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, category.getId());
			preparedStatement.executeUpdate();
			
			isUserCategoryCreated = true;
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
		return isUserCategoryCreated;
	}
	
	public int getRootCategory(int userId) {
		Connection conn = null;
		int rootCategoryId = -1;
		try {			
			// insert in UserXCategory record
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USERXCATEGORY);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())  {
				rootCategoryId = resultSet.getInt(1);
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
		return rootCategoryId;		
	}
	
	public boolean removeUserCategory(int userId) {
		boolean isUserCategoryRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USERXCATEGORY);
			preparedStatement.setInt(1, userId);
			int rowDeleted = preparedStatement.executeUpdate();
			isUserCategoryRemoved = rowDeleted > 0 ;
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
		return isUserCategoryRemoved;
	}
}
