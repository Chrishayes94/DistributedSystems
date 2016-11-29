package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.distributed.socialnetwork.shared.UploadedImage;

@RemoteServiceRelativePath("images")
public interface UserImageService extends RemoteService  {
	
	public String getBlobstoreUploadUrl();
	public UploadedImage get(String key);
	public List<UploadedImage> getRecentlyUploaded();
	public void deleteImage(String key);

}
