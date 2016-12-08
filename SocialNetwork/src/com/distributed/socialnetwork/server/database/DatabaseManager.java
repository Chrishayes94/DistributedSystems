package com.distributed.socialnetwork.server.database;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.distributed.socialnetwork.server.posts.PostObject;
import com.distributed.socialnetwork.shared.ClientInfo;

/**
 * This class handles all connections to and from the Database.
 * All connections are handled here so that no security risks can arise.
 * @author Chris
 * Private classes are used so that we can keep track of column IDS for the database, so the correct information
 * is pulled.
 */
public class DatabaseManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	protected static final String DB_URL = "jdbc:mysql://82.7.208.210:3306/socialnetwork";
	protected static final String username = "server";
	protected static final String password = "S.erver123";
	
	/** Database Column Variables -- User Table **/
	@SuppressWarnings("unused")
	private static class User {
		public static int ID = 1;
		public static int USERID = 2;
		public static int EMAIL = 3;
		public static int PASSWORD = 4;
		public static int FULLNAME = 5;
	}

	/** Database Column Variables -- Post Table **/
	@SuppressWarnings("unused")
	private static class Post {
		public static int ID = 1; // ID of the post
		public static int USERID = 2; // The users ID that created the post
		public static int DATETIME = 3; // The date and time of creation
		public static int IMAGES = 4; // ID's of all images used on the post, Null if non are used.
		public static int POSTS = 5; // URL Links and Text posts
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
		catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		}
		return null;
	}
	
	/**
	 * This simply takes the passed in username and password from the login form. If the email is not valid then it will not continue,
	 * otherwise it will cross-reference the email with the password.
	 * @param email - Email passed in from the login box. Always in lowercase.
	 * @param password - Password passed in from the login box, gets encrypted when checking if database value.
	 * @return - Will return the client and all information if a user is found.
	 */
	public static ClientInfo get(String email, String password) {
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
				
				if (databasePassword.equals(encoded)) {
					String userId = rs.getString(User.USERID);
					String fullname = rs.getString(User.FULLNAME);
					client = ClientInfo.create(
							Long.parseLong(userId), 
							fullname, 
							email,
							password);
				}
			}
			conn.close();
			return client;
		}
		catch (SQLException | UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public static List<ClientInfo> get(String keyword) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Users where fullname='" + keyword + "'";
			ResultSet rs = stmt.executeQuery(sql);
			List<ClientInfo> clients = new ArrayList<ClientInfo>();
			
			while (rs.next()) {
				clients.add(ClientInfo.create(rs.getString(User.FULLNAME)));
			}
			conn.close();
			return clients;
		}
		catch (SQLException e) {
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
			prep.setString(3, DatatypeConverter.printBase64Binary(client.getPassword().getBytes("UTF-8")));
			prep.setString(4, client.getFullname());
			prep.execute();
			
			conn.close();
			return true;
		} catch (SQLException | UnsupportedEncodingException e) {
		}
		
		return false;
	}
	
	public static boolean put(PostObject post) {
		Connection conn = getConnection();
		
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
		}
		return false;
	}
}
