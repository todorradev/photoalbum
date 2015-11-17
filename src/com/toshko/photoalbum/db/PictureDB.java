package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.toshko.photoalbum.data.UserUtils;
import com.toshko.photoalbum.dto.Picture;

public class PictureDB {
	private static final String INSERT_PICTURE  = "INSERT INTO Pictures (name, description, date, size) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_SIZE     = "UPDATE Pictures SET size=? Where ID=?";
	private static final String SELECT_PICTURE  = "SELECT name, description, date, size FROM Pictures WHERE id=?";
	private static final String DELETE_PICTURE  = "DELETE FROM Pictures WHERE id = ?";
	private static final String UPDATE_PICTURE  = "UPDATE Pictures SET name = ?, description = ?, date =? WHERE id = ?";
	
	public boolean createPicture(int userId, int categoryId, Picture picture) {
		boolean isPictureCreated = false;
		Connection conn = null;
		try {			
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, picture.getName());
			preparedStatement.setString(2, picture.getDescription());
			preparedStatement.setDate(3, picture.getDate());
			preparedStatement.setInt(4, picture.getSize());
			
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			picture.setId(rs.getInt(1));
			
			int imageSize = UserUtils.savePicture(userId, categoryId, picture.getId(), picture.getContent());
			if (imageSize != picture.getSize()) {
				preparedStatement = conn.prepareStatement(UPDATE_SIZE);
				preparedStatement.setInt(1, imageSize);
				preparedStatement.setInt(2, picture.getId());
				
				preparedStatement.executeUpdate();
			}
			
			isPictureCreated = true;
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
		return isPictureCreated;
	}
	
	public boolean removePicture(int id) {
		boolean isPictureRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PICTURE);
			preparedStatement.setInt(1, id);
			int rowDeleted = preparedStatement.executeUpdate();
			isPictureRemoved = rowDeleted > 0 ;
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
		return isPictureRemoved;
	}
		
	public boolean editPicture(Picture picture) {
		Connection conn = null;
		boolean isPictureEdited = false;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PICTURE);
			preparedStatement.setString(1, picture.getName());
			preparedStatement.setString(2, picture.getDescription());
			preparedStatement.setDate(3, picture.getDate());
			preparedStatement.setInt(4, picture.getId());
			
			int rowUpdated = preparedStatement.executeUpdate();
			isPictureEdited = rowUpdated > 0;
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
		return isPictureEdited;
	}
	
	public Picture getPicture(int pictureId) {
		return getPicture(0, 0, pictureId);
	}
	public Picture getPicture(int userId, int categoryId, int pictureId) {
		Picture picture = null;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PICTURE);
			preparedStatement.setInt(1, pictureId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date date = resultSet.getDate("date");
				int size = resultSet.getInt("size");
				byte[] imageContent = UserUtils.loadPicture(userId, categoryId, pictureId);
				picture = new Picture(pictureId, name, description, date, size, imageContent);	
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
		return picture;
	}
}
