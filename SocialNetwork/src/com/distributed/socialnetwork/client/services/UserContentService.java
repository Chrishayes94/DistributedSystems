package com.distributed.socialnetwork.client.services;

import java.io.DataInputStream;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.Image;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.PostObject;

@RemoteServiceRelativePath("images")
public interface UserContentService extends RemoteService  {
	
	public String findName(long id);
	
	public void upload();
	
	public String getImageUploadUrl();
	public String get(long key);
	public List<PostObject> getRecentlyUploaded(int offset);
	public List<PostObject> getUsersPosts(int id);
	
	public List<ClientInfo> search(String keyword);
	
	
	public List<String> get();

	public boolean post(PostObject obj);
	public String getImage(String name);
	
	public void deleteImage(long key);
}
