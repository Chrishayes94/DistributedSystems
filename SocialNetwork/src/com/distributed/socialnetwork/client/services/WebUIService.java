package com.distributed.socialnetwork.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("webUI")
public interface WebUIService extends RemoteService {
	boolean prepareHomeScreen();
}
