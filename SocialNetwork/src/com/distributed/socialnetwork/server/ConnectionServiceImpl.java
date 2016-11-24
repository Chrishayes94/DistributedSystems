package com.distributed.socialnetwork.server;

import java.io.IOException;

import com.distributed.socialnetwork.client.ConnectionService;
import com.distributed.socialnetwork.shared.Entity;
import com.distributed.socialnetwork.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements ConnectionService {
	
	@Override
	public String requestLogin(String name, String password) throws IllegalArgumentException, IOException {
		// TODO Auto-generated method stub
		
		if ((name.isEmpty() || name == null) || (password.isEmpty() || password == null)) {
			throw new IOException("No username or password was provided");
		}
		
		if (!FieldVerifier.isNameAndPasswordCompatible(name, password)) {
			throw new IllegalArgumentException("The provided username and password are incorrect");
		}
		
		return null;
	}
	
	@Override
	public String requestRegistration(Entity user) throws IllegalArgumentException {
		
		
		
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
