package com.distributed.socialnetwork.client.services;

import javax.servlet.http.HttpSession;

import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("connection")
public interface ConnectionService extends RemoteService {
	/** User Connection Methods **/
	ClientInfo login(String loginInfo);
	ClientInfo loginFromSessionServer();
	void logout();
	
	ClientInfo register(String info);
	
	/** Database Connection Methods **/
}
