package com.distributed.socialnetwork.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.xerces.impl.dv.util.Base64;

import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.FieldVerifier;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements ConnectionService {

	@Override
	public ClientInfo login(String loginInfo) {
		String[] info = loginInfo.split(":");
		if (loginInfo.length() <= 1) return null;
		else return DatabaseManager.getUser(info[0], info[1]);
	}
	
	@Override
	public ClientInfo register(String loginInfo) {
		String[] info = loginInfo.split(":");
		if (loginInfo.length() <= 1) return null;
		else {
			ClientInfo client = ClientInfo.createClient(info[0], info[1], info[2]);
			if (DatabaseManager.put(client)) return client;
		}
		return null;
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
