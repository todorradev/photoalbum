package com.toshko.photoalbum.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnector {
	private static DataBaseConnector dataBaseConnector = new DataBaseConnector();
	
	private void createUserTable() {
		Connection conn = null;
		Statement stm = null;
		try{
			conn = getConnection();
			stm = conn.createStatement();
			String usersTable = "CREATE TABLE IF NOT EXISTS Users" +
	                   	 "(id INTEGER NOT NULL AUTO_INCREMENT," +
	                   	 " username VARCHAR(100) unique, " +
	                   	 " password VARCHAR(100), " +
	                     " firstName VARCHAR(100), " +
	                     " lastName VARCHAR(100), " +
	                     " email VARCHAR(100), " +
	                     " PRIMARY KEY ( id ))";
			stm.execute(usersTable);
		} catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {

				}
			}
		}
	}
	
	private void createCategoryTable() {
		Connection conn = null;
		Statement stm = null;
		try{
			conn = getConnection();
			stm = conn.createStatement();
			String categoryTable = "CREATE TABLE IF NOT EXISTS Category" +
	                   	 "(id INTEGER NOT NULL AUTO_INCREMENT," +
	                   	 " name VARCHAR(100), " +
	                   	 " description VARCHAR(100), " +
	                     " PRIMARY KEY ( id ))";
			stm.execute(categoryTable);
		} catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {

				}
			}
		}
	}
	
	private void createUserXCategory() {
		Connection conn = null;
		Statement stm = null;
		
		try {
			conn = getConnection();
			stm = conn.createStatement();
			String userXCategoryTable = "CREATE TABLE IF NOT EXISTS UserXCategory" +
                  	 					"(userId INTEGER, " +
                  	 					" categoryId INTEGER unique, " +
                  	 					" PRIMARY KEY ( userId ))";
			stm.execute(userXCategoryTable);
		}
		catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
	
				}
			}
		}
	}
	
	private void createCategoryXSubcategoryTable() {
		Connection conn = null;
		Statement stm = null;
		
		try {
			conn = getConnection();
			stm = conn.createStatement();
			String categoryXCategoryTable = "CREATE TABLE IF NOT EXISTS CategoryXSubcategory" +
											"(categoryId INTEGER, " +
	             	 						" subcategoryId INTEGER)";
			stm.execute(categoryXCategoryTable);
		}
		catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
	
				}
			}
		}
	}
	
	private void createPictureTable() {
		Connection conn = null;
		Statement stm = null;
		
		try {
			conn = getConnection();
			stm = conn.createStatement();
			String createPictureTable = "CREATE TABLE IF NOT EXISTS Pictures" +
										"(id INTEGER NOT NULL AUTO_INCREMENT," +
					                  	" name VARCHAR(100), " +
					                  	" description VARCHAR(100), " +
					                  	" date DATE, " +
					                    " size INTEGER, " +
					                    " PRIMARY KEY ( id ))";
			stm.execute(createPictureTable);
		}
		catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
	
				}
			}
		}
	}
	
	//created table for admin, who will accept/reject added pictures from user.
	private void createAdminPictureTable() {
		Connection conn = null;
		Statement stm = null;
		
		try {
			conn = getConnection();
			stm = conn.createStatement();
			String createPictureTable = "CREATE TABLE IF NOT EXISTS admin" +
										"(userId INTEGER, " +
					                    " categoryId INTEGER, " +
					                    " pictureId INTEGER)";
			stm.execute(createPictureTable);
		}
		catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
	
				}
			}
		}
	}
	private void createCategoryXPicturesTable() {
		Connection conn = null;
		Statement stm = null;
		
		try {
			conn = getConnection();
			stm = conn.createStatement();
			String categoryXPicturesTable = "CREATE TABLE IF NOT EXISTS CategoryXPictures" +
											"(id INTEGER NOT NULL AUTO_INCREMENT," +
	             	 						"categoryId INTEGER, " +
	             	 						" pictureId INTEGER, " +
	             	 						"PRIMARY KEY ( id ))";
			stm.execute(categoryXPicturesTable);
		}
		catch (SQLException ex) {
		    System.err.println("SQLException: " + ex);
		    System.err.println("SQLState: " + ex.getSQLState());
		    System.err.println("VendorError: " + ex.getErrorCode());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
	
				}
			}
		}
	}
	
	private DataBaseConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			System.err.println("Mysql database is not installed");
			System.out.println("SQLException: " + ex);
		}
		
		createUserTable();
		createCategoryTable();
		createUserXCategory();
		createCategoryXSubcategoryTable();
		createPictureTable();
		createCategoryXPicturesTable();
		createAdminPictureTable();
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/photoalbum?" +
                "user=root&password=totti");
	    return conn;
	}
	
	public static DataBaseConnector getInstance() {
		return dataBaseConnector;
	}
}
