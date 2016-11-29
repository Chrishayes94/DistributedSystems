package com.distributed.socialnetwork.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	protected static final String DB_URL = "jdbc:mysql://192.168.0.20/EMP";
	protected static final String username = "";
	protected static final String password = "";
	
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
	
	public static String getPassword(Connection conn, String email) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT password FROM Users WHERE usename='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				// Should only be one value
				return rs.getString("password");
			}
		} catch (SQLException e) {
		}
		return "error";
	}
}
