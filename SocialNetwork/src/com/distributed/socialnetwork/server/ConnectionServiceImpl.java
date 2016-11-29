package com.distributed.socialnetwork.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.xerces.impl.dv.util.Base64;

import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements ConnectionService {

	@Override
	public Boolean login(String loginInfo) {
		// Create a new ClientInfo object, this will be used to track current loggedin users.
		if (loginInfo.length() <= 1) return false;
		ClientInfo client = ClientInfo.createClient(loginInfo.split(":")[0], loginInfo.split(":")[1]);
		Connection databaseConnection = DatabaseManager.getConnection();
		
		String password = new String(Base64.decode(DatabaseManager.getPassword(databaseConnection, client.getEmail())));
		if (password.equals(client.getPassword())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
