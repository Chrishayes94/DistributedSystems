package com.distributed.socialnetwork.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.ImageObject;
import com.distributed.socialnetwork.shared.PostObject;

/**
 * This class handles all connections to and from the Database.
 * All connections are handled here so that no security risks can arise.
 * @author Chris
 * Private classes are used so that we can keep track of column IDS for the database, so the correct information
 * is pulled.
 */
public class DatabaseManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	protected static final String DB_URL = "jdbc:mysql://192.168.0.10:3306/socialnetwork";
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

	private static class Images {
		public static int IMAGEID = 1;
		public static int CREATIONTIME = 2;
		public static int CREATIONDATE = 3;
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
				String databasePassword = rs.getString(User.PASSWORD);
				
				if (databasePassword.equals(password)) {
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
		catch (SQLException e) {
		}
		return null;
	}
	
	/**
	 * Get Method to return a list of users with the specified keyword, this can be only the fullname of the required
	 * user, however code can be put in place to check for users via email (IF SECURE).
	 * @param keyword - The phrase that will be searched under the column 'fullname' from the database.
	 * @return - The return value containing all of the users wit hthe provided name.
	 */
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
	
	public static String get(int id) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM USERS where userid='" + id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next())
				return rs.getString(User.FULLNAME);
		}
		catch (SQLException e){}
		return "";
	}
	
	/**
	 * Get Method to return a list of fullnames for users.
	 * @return - The return value containing a list of all fullname values for users.
	 */
	public static Collection<String> get() {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * fullname FROM Users";
			ResultSet rs = stmt.executeQuery(sql);
			Collection<String> clients = new ArrayList<String>();
			
			while (rs.next()) {
				clients.add(rs.getString(User.FULLNAME));
			}
			conn.close();
			return clients;
		}
		catch (SQLException e) {
		}
		return null;
	}
	
	public static Collection<?> posts(int max) {
		return posts(0, max);
	}
	
	/**
	 * 
	 * @param offset - 
	 * @param max - 
	 * @return
	 */
	public static Collection<?> posts(int offset, int max) {
		Connection conn = getConnection();
		Collection<PostObject> posts = new ArrayList<>();
		int currentIndex = 0;
		try {
			Statement stmt = conn.createStatement();
			String sql = "Select * FROM Posts";
			ResultSet rs = stmt.executeQuery(sql);
			rs.last();
			while (offset-- > 0) rs.previous();
			while (currentIndex < max) {
				posts.add(PostObject.create(
						rs.getInt(Post.USERID),
						rs.getString(Post.DATETIME),
						rs.getString(Post.IMAGES),
						rs.getString(Post.POSTS)));
			}
			conn.close();
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Pass the provided @{code} client to the database. All required information is secured e.g. Password 
	 * and possibly email address. Checks for any previous users under the same email address are performed to stop
	 * multi-user creation.
	 * @param client - The client that will be passed into the database.
	 * @return  - Return the state of the result, if any error occurs we will flag no entry created.
	 */
	public static boolean put(ClientInfo client) {
		Connection conn = getConnection();
		if (check(conn, client.getEmail())) return false;
		
		try {
			String sql = "INSERT INTO Users (userid, email, password, fullname) VALUES (?, ?, ?, ?)";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setLong(User.USERID, client.getOwnerId());
			prep.setString(User.EMAIL, client.getEmail());
			prep.setString(User.PASSWORD, DatatypeConverter.printBase64Binary(client.getPassword()));
			prep.setString(User.FULLNAME, client.getFullname());
			prep.execute();
			
			conn.close();
			return true;
		} catch (SQLException e) {
		}
		
		return false;
	}
	
	/**
	 * Pass the provided @{code} post to the database. All required information is secured e.g. Image locations.
	 * @param post - The post object to be passed into the database. 
	 * @return - Return the state of the result, any error then false will be returned.
	 */
	public static boolean put(PostObject post) {
		Connection conn = getConnection();
		
		try {
			String sql = "INSERT INTO Posts (userid, datetime, images, posts) VALUES (?, ?, ?, ?)";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setLong(Post.ID, post.getID());
			prep.setString(Post.DATETIME, post.getCreationDate().toString());
			prep.setString(Post.IMAGES, post.getImagesUnsplit());
			prep.setString(Post.POSTS, post.getPosts());
			prep.execute();
			
			conn.close();
			return true;
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static boolean put(ImageObject image) {
		Connection conn = getConnection();
		
		try {
			String sql = "INSERT INTO Images (imageid, creationtime, creationdate) VALUES (?, ?, ?)";
			PreparedStatement prep = conn.prepareStatement(sql);
			
			prep.setLong(Images.IMAGEID, image.getID());
			prep.setString(Images.CREATIONTIME, image.getCreationTime().toString());
			prep.setString(Images.CREATIONDATE, image.getCreationDate().toString());
			prep.execute();
			
			conn.close();
			return true;
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * A method that will query the table Users for any reference of the parameter @{code} email under the email column.
	 * This method is used so that users are not able to create multiple accounts using the same address.
	 * Makes the social network unique to separate users.
	 * @param conn - The current connection to database so we don't have to create multiple requests.
	 * @param email - The query that will be passed into the database.
	 * @return - Return the state of the result, true if there are any results.
	 */
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
	
	public static boolean check(long id) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Images where imageid=i" + id + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.first()) return true;
		} catch (SQLException e) {
		}
		return false;
	}
}
