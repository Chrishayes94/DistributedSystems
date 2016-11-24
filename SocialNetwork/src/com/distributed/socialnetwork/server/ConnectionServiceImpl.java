package com.distributed.socialnetwork.server;

import java.io.IOException;

import com.distributed.socialnetwork.client.ConnectionService;
import com.distributed.socialnetwork.client.GreetingService;
import com.distributed.socialnetwork.shared.Entity;
import com.distributed.socialnetwork.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements GreetingService, ConnectionService {
	
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
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
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