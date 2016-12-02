package com.distributed.socialnetwork.client.services;

import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("connection")
public interface ConnectionService extends RemoteService {
	/** User Connection Methods **/
	ClientInfo login(String loginInfo);
	ClientInfo register(String info);
	
	/** Database Connection Methods **/
}
