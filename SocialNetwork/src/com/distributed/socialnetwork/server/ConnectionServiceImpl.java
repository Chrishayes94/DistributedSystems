package com.distributed.socialnetwork.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.distributed.socialnetwork.client.services.ConnectionService;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConnectionServiceImpl extends RemoteServiceServlet implements ConnectionService {
	
	private static final String attribute = "user";
	
	private int noClients = 0;
	
	@Override
	public ClientInfo login(String loginInfo) {
		String[] info = loginInfo.split(":");
		if (loginInfo.length() <= 1) return null;
		else {
			ClientInfo client = DatabaseManager.get(info[0], info[1]);
			if (client != null) {
				noClients++;
				client.setSessionID(this.getThreadLocalRequest().getSession().getId());
				client.setLoggedIn(true);
				storeUserInSession(client);
				return client;
			}	
		}
		return null;
	}
	
	@Override
	public ClientInfo loginFromSessionServer() {
		return getUserAlreadyFromSession();
	}
	
	@Override
	public void logout() {
		noClients--;
		deleteUserFromSession();
	}
	
	@Override
	public ClientInfo register(String loginInfo) {
		String[] info = loginInfo.split(":");
		if (loginInfo.length() <= 1) return null;
		else {
			ClientInfo client = ClientInfo.create(info[0], info[1], info[2]);
			if (DatabaseManager.put(client)) {
				client.setSessionID(this.getThreadLocalRequest().getSession().getId());
				client.setLoggedIn(true);
				storeUserInSession(client);
				return client;
			}
		}
		return null;
	}
	
	private ClientInfo getUserAlreadyFromSession() {
		ClientInfo user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(attribute);
		if (userObj != null && userObj instanceof ClientInfo)
			user = (ClientInfo) userObj;
		noClients++;
		return user;
	}
	
	private void storeUserInSession(final ClientInfo client) {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute(attribute, client);
	}
	
	private void deleteUserFromSession() {
		HttpServletRequest httpServletRequsest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequsest.getSession();
		session.removeAttribute(attribute);
	}
	
	public int getNumberOfClients() {
		return noClients;
	}
}
