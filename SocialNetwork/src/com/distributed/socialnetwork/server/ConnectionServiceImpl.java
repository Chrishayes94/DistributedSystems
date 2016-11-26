package com.distributed.socialnetwork.server;

import java.io.IOException;

import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements ConnectionService {

	@Override
	public String login(String loginInfo) {
		/** Connection to database here **/
		
		return "";
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
