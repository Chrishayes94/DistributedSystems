package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.distributed.socialnetwork.shared.UploadedContent;

@RemoteServiceRelativePath("images")
public interface UserContentService extends RemoteService  {
	
	public String getBlobstoreUploadUrl();
	public UploadedContent get(String key);
	public List<UploadedContent> getRecentlyUploaded();
	public void deleteImage(String key);

}
