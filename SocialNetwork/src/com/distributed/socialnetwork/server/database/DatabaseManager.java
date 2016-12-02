package com.distributed.socialnetwork.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.distributed.socialnetwork.shared.ClientInfo;

public class DatabaseManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	protected static final String DB_URL = "jdbc:mysql://82.7.208.210:3306/socialnetwork";
	protected static final String username = "server";
	protected static final String password = "S.erver123";
	
	/** Database Column Variables -- User Table **/
	private static final int ID_COLUMN = 1;
	private static final int USERID_COLUMN = 2;
	private static final int EMAIL_COLUMN = 3;
	private static final int PASSWORD_COLUMN = 4;
	private static final int FULLNAME_COLUMN = 5;
	
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
	
	public static String getPassword(String email) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Users where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.first())
				return rs.getString(PASSWORD_COLUMN);
			conn.close();
		} catch (SQLException e) {
		}
		return "error";
	}
	
	public static boolean put(ClientInfo client) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO Users VALUES " + client.toString());
			conn.close();
		} catch (SQLException e) {
			System.out.print("SQLException: " + e.getMessage());
			System.out.print("SQLState: " + e.getSQLState());
			System.out.print("VendorError: " + e.getErrorCode());
			return false;
		}
		
		return false;
	}
	
	private static boolean check(String email) {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT email FROM Users where usernsme='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			conn.close();
			if (rs.getFetchSize() > 0) {
				return true; // The email exists
			}
		} catch (SQLException e) {
			System.out.print("SQLException: " + e.getMessage());
			System.out.print("SQLState: " + e.getSQLState());
			System.out.print("VendorError: " + e.getErrorCode());
		}
		return false;
	}
}
