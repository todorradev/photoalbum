package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.toshko.photoalbum.dto.Picture;

public class CategoryXPictures {
	private static final String INSERT_CATEGORYXPICTURES  = "INSERT INTO CategoryXPictures (categoryId, pictureId) VALUES(?, ?)";
	private static final String DELETE_CATEGORYXPICTURES  = "DELETE FROM CategoryXPictures WHERE categoryId = ?";
	private static final String DELETE_CATEGORYBYPICTURE  = "DELETE FROM CategoryXPictures WHERE pictureId = ?";
	private static final String SELECT_PICTURES  		  = "SELECT Pictures.id, name, description, date, size from Pictures JOIN CategoryxPictures ON Pictures.id = CategoryxPictures.pictureId WHERE categoryId = ? and name like ?";
	
	public boolean createCategoryWithPicture(int categoryId, int pictureId) {
		Connection conn = null;
		boolean isUserCategoryWithPicture = false;
		
		try {
			
			// insert in CategoryXPictures record
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CATEGORYXPICTURES);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, pictureId);
			preparedStatement.executeUpdate();
			
			isUserCategoryWithPicture = true;
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
		return isUserCategoryWithPicture;
	}
	
	public boolean removeAllPicturesInCategory(int categoryId) {
		boolean isAllPicturesInCategoryRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			Collection<Picture> picturesByCategory = getPicturesByCategory(categoryId, "");
			for (Picture p : picturesByCategory) {
				removeCategoryByPicture(p.getId());
			}
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CATEGORYXPICTURES);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.executeUpdate();
			isAllPicturesInCategoryRemoved = true;
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
		return isAllPicturesInCategoryRemoved;
	}
	
	public boolean removeCategoryByPicture(int pictureId) {
		boolean isremoveCategoryByPictureRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CATEGORYBYPICTURE);
			preparedStatement.setInt(1, pictureId);
			int rowDeleted = preparedStatement.executeUpdate();
			
			PictureDB pictureDb = new PictureDB();
			pictureDb.removePicture(pictureId);
			
			isremoveCategoryByPictureRemoved = rowDeleted > 0 ;
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
		return isremoveCategoryByPictureRemoved;
	}
	
	public Collection<Picture> getPicturesByCategory (int categoryId, String searchCriteria) {
		Connection conn = null;
		Collection<Picture> pictures = new ArrayList<Picture>();
		
		try {
			
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PICTURES);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setString(2, "%" + searchCriteria + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("Pictures.id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				java.sql.Date date = resultSet.getDate("date");
				int size = resultSet.getInt("size");
				
				Picture picture = new Picture(id, name, description, date, size, null);
				pictures.add(picture);
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
		return pictures;
	}
}
