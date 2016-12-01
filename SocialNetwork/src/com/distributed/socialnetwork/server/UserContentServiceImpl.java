package com.distributed.socialnetwork.server;

import java.util.List;

import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserContentServiceImpl extends RemoteServiceServlet implements UserContentService {

	@Override
	public String getBlobstoreUploadUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientInfo get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientInfo> getRecentlyUploaded() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImage(String key) {
		// TODO Auto-generated method stub
		
	}

}
