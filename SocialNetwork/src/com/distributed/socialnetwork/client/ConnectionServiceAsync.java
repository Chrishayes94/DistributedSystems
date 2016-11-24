package com.distributed.socialnetwork.client;

import java.io.IOException;

import com.distributed.socialnetwork.shared.Entity;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConnectionServiceAsync {
	void requestLogin(String name, String password, AsyncCallback<String> response) throws IllegalArgumentException, IOException;
	void requestRegistration(Entity user, AsyncCallback<String> response) throws IllegalArgumentException;
}
