package com.distributed.socialnetwork.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConnectionServiceAsync {
	void login(String loginInfo, AsyncCallback<Boolean> callback);
}
