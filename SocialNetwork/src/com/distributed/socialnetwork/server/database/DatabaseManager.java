package com.distributed.socialnetwork.server.database;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.bind.DatatypeConverter;


import com.distributed.socialnetwork.shared.ClientInfo;

/**
 * 
 * @author Chris
 *
 */
public class DatabaseManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	protected static final String DB_URL = "jdbc:mysql://82.7.208.210:3306/socialnetwork";
	protected static final String username = "server";
	protected static final String password = "S.erver123";
	
	/** Database Column Variables -- User Table **/
	private static class User {
		public static int ID = 1;
		public static int USERID = 2;
		public static int EMAIL = 3;
		public static int PASSWORD = 4;
		public static int FULLNAME = 5;
	}

	/** Database Column Variables -- Post Table **/
	private static class Post {
		public static int ID = 1;
		public static int USERID = 2;
		public static int DATETIME = 3;
		public static int IMAGES = 4;
		public static int POSTS = 5;
	}
	
	
	
	/**
	 * This method simply creates a new Instance of Connection to the database used for all SQL calls for INSERT, UPDATE and DELETE.
	 * @return a brand new connection to the database.
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER).newInstance();
				
			conn = DriverManager.getConnection(DB_URL, username, password);
			if (conn.isClosed()) return null;
			return conn;
		}
		catch (SQLException e) {
			System.out.print("SQLException: " + e.getMessage());
			System.out.print("SQLState: " + e.getSQLState());
			System.out.print("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.print("ClassNotFoundException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.print("InstantiationException: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.print("IllegalAccessException: " + e.getMessage());
		}
		return null;
	}
	
	public static ClientInfo getUser(String email, String password) {
		Connection conn = getConnection();
		if (!check(conn, email)) return null;
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Users where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			ClientInfo client = null;
			
			if (rs.first()) {
				// First lets check the passwords
				String encoded = DatatypeConverter.printBase64Binary(password.getBytes("UTF-8"));
				String databasePassword = rs.getString(User.PASSWORD);
				
				if (password.equals(databasePassword)) {
					String userId = rs.getString(User.USERID);
					String fullname = rs.getString(User.FULLNAME);
					client = ClientInfo.createClient(
							Long.parseLong(userId), 
							fullname, 
							email,
							password);
				}
			}
			conn.close();
			return client;
		}
		catch (SQLException e){
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public static boolean put(ClientInfo client) {
		Connection conn = getConnection();
		if (check(conn, client.getEmail())) return false;
		
		try {
			String sql = "INSERT INTO Users (userid, email, password, fullname) VALUES (?, ?, ?, ?)";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setLong(1, client.getOwnerId());
			prep.setString(2, client.getEmail());
			prep.setString(3, client.getPassword());
			prep.setString(4, client.getFullname());
			prep.execute();
			
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.print("SQLException: " + e.getMessage());
			System.out.print("SQLState: " + e.getSQLState());
			System.out.print("VendorError: " + e.getErrorCode());
		}
		
		return false;
	}
	
	private static boolean check(Connection conn, String email) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Users where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.first()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.print("SQLException: " + e.getMessage());
			System.out.print("SQLState: " + e.getSQLState());
			System.out.print("VendorError: " + e.getErrorCode());
		}
		return false;
	}
}
