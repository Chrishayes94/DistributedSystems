package com.distributed.socialnetwork.client.services;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("connection")
public interface ConnectionService extends RemoteService {
	String login(String loginInfo);
}
