package com.distributed.socialnetwork.client.services;


import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConnectionServiceAsync {
	
	void login(String loginInfo, AsyncCallback<ClientInfo> callback);
	void loginFromSessionServer(AsyncCallback<ClientInfo> callback);
	void logout(AsyncCallback<Void> callback);

	void register(String info, AsyncCallback<ClientInfo> callback);
}
