package com.distributed.socialnetwork.client;

import java.io.IOException;

import com.distributed.socialnetwork.shared.Entity;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("connection")
public interface ConnectionService extends RemoteService {
	String requestLogin(String name, String password) throws IllegalArgumentException, IOException;
	String requestRegistration(Entity user) throws IllegalArgumentException;
}
