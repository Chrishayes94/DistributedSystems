package com.distributed.socialnetwork.client.services;

import java.io.DataInputStream;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.PostObject;

/**
 * @author Alex & Chris
 */

public interface UserContentServiceAsync {
	
	void findName(int id, AsyncCallback<String> callback);

	void upload(AsyncCallback<Void> callback);
	
	void getImageUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<ClientInfo> callback);

	void getRecentlyUploaded(int offset, AsyncCallback<List<PostObject>> callback);

	void search(String keyword, AsyncCallback<List<ClientInfo>> callback);
	
	void post(PostObject obj,AsyncCallback<Boolean> callback);
	
	void getImage(String name, AsyncCallback<String> callback);
	
	void deleteImage(long key, AsyncCallback<Void> callback);
}
