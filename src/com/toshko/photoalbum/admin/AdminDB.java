package com.toshko.photoalbum.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.toshko.photoalbum.db.DataBaseConnector;
import com.toshko.photoalbum.db.NotApprovedPicture;
import com.toshko.photoalbum.db.PictureDB;
import com.toshko.photoalbum.dto.Picture;

public class AdminDB {
	private static final String INSERT_PICTURE  = "INSERT INTO admin (userId, categoryId, pictureId) VALUES(?, ?, ?)";
	private static final String DELETE_PICTURE = "DELETE FROM admin WHERE pictureId = ?";
	private static final String SELECT_IMAGES = "SELECT pictureId, userId, categoryId FROM admin";
	
	public Collection<NotApprovedPicture> getUnapprovedPictures() {
		Connection conn = null;
		Collection<NotApprovedPicture> notApprovedPictures = new ArrayList<NotApprovedPicture>();
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_IMAGES);
	
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int userId = resultSet.getInt("userId");
				int categoryId = resultSet.getInt("categoryId");
				int pictureId = resultSet.getInt("pictureId");
				NotApprovedPicture notApprovedPicture = new NotApprovedPicture(userId, categoryId, pictureId);
				notApprovedPictures.add(notApprovedPicture);
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
		return notApprovedPictures;
	}
	
	
	
	public boolean createPicture(int userId, int categoryId, Picture picture) {
		boolean isPictureCreated = false;
		Connection conn = null;
		try {
			PictureDB pictureDb = new PictureDB();
			pictureDb.createPicture(userId, categoryId, picture);
			
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PICTURE, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, categoryId);
			preparedStatement.setInt(3, picture.getId());
			preparedStatement.executeUpdate();
			
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
	
	public boolean removePicture(int pictureId) {
		boolean isPictureRemoved = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PICTURE);
			preparedStatement.setInt(1, pictureId);
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
}
