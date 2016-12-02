package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.distributed.socialnetwork.shared.ClientInfo;

@RemoteServiceRelativePath("images")
public interface UserContentService extends RemoteService  {
	
	public String getImageUploadUrl();
	public ClientInfo get(String key);
	public List<ClientInfo> getRecentlyUploaded();
	public void deleteImage(long key);

}
