package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.toshko.photoalbum.dto.Category;
import com.toshko.photoalbum.dto.User;

public class UserRegistry {

	private static final String UPDATE_USERS = "UPDATE Users SET firstName = ?, lastName = ?, email = ? WHERE username= ?";
	private static final String UPDATE_USERPASS = "UPDATE Users SET password = ? WHERE password = ?";
	private static final String SELECT_USERS = "SELECT id, username, password, firstName, lastName, email FROM Users";
	private static final String SELECT_USER = "SELECT id FROM Users Where username = ?";
	private static final String SELECT_CURRENTUSER = "SELECT username, password, firstName, lastName, email FROM Users WHERE id = ?";
	private static final String IS_VALID_USER = "SELECT username, password FROM Users where username=? and password=?";
	private static final String DELETE_USER  = "DELETE FROM Users WHERE id = ?";
	private static final String INSERT_USER  = "INSERT INTO Users (username, password, firstName, lastName, email) " +
						"VALUES(?, ?, ?, ?, ?)";

	public int getUser(String username) {
		int userId = 0;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				userId = resultSet.getInt("id");
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
		return userId;
	}
	
	
	
	
	public boolean createUser(User user) {
		boolean isUserCreated = false;
		Connection conn = null;
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));

			UserXCategoryDB userCategory = new UserXCategoryDB();
			Category category = new Category(user.getUsername(), "Automatically created");
			boolean isUserCategoryCreated = userCategory.createUserCategory(user.getId(), category);
			if(isUserCategoryCreated) {
				List<Category> categories = new ArrayList<Category>();
				categories.add(category);
				User userCategories = new User();
				userCategories.setCategories(categories);
			}
			
			isUserCreated = true;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				
			}
		}
		return isUserCreated;
	}
	
	public boolean removeUser(int userId) {
		boolean result = false;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER);
			preparedStatement.setInt(1, userId);
			int rowDeleted = preparedStatement.executeUpdate();
			result = rowDeleted > 0;
			
			if(result) {
				CategoryDB categoryDb = new CategoryDB();
				categoryDb.removeAllUserCategory(userId);
				UserXCategoryDB userCategory = new UserXCategoryDB();
				userCategory.removeUserCategory(userId);
				
			}
		}		
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}		
		finally {
			try {
				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException ex) {
				
			}
		}
		return result;
	}
	
	public Collection<User> getUsers() {
		Collection<User> users = new ArrayList<User>();
		Connection conn = null;
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USERS);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				
				User user = new User(id, username, password, firstName, lastName, email);
				users.add(user);
			}
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}
		return users;
	}
	
	public User getCurrentUser(int userId) {
		User user = null;
		Connection conn = null;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CURRENTUSER);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String username = resultSet.getString("username");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				user = new User(username, password, firstName, lastName, email);
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
		return user;
	}
	
	public boolean editUser(User user) {
		Connection conn = null;
		boolean result = false;
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection(); 
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USERS);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUsername());
			int rowUpdated = preparedStatement.executeUpdate();
			result = rowUpdated > 0;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}
		return result;
	}
	
	public boolean editUserPass(String oldPass, String newPassword) {
		Connection conn = null;
		boolean isPassChanged = false;
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection(); 
			PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_USERPASS);
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, oldPass);
			int rowUpdated = preparedStatement.executeUpdate();
			isPassChanged = rowUpdated > 0;
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}
		return isPassChanged;
	}
	
	public boolean isValidCredentials(String username, String password) {
		Connection conn = null;
		boolean result = false;
		
		try {
			DataBaseConnector dbc = DataBaseConnector.getInstance();
			conn = dbc.getConnection();
			PreparedStatement preparedStatement = conn.prepareStatement(IS_VALID_USER);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			result = resultSet.next();
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex);
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
		}
		return result;
	}
}
