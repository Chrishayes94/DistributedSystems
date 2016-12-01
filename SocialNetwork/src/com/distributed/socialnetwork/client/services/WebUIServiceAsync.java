package com.distributed.socialnetwork.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebUIServiceAsync {
	void prepareHomeScreen(AsyncCallback<Boolean> response);
}
