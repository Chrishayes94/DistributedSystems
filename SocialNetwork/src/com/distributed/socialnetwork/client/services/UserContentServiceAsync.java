package com.distributed.socialnetwork.client.services;

import java.io.DataInputStream;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.PostObject;

/**
 * @author Alex & Chris
 */

public interface UserContentServiceAsync {

	void upload(AsyncCallback<Void> callback);
	
	void getImageUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<ClientInfo> callback);

	void getRecentlyUploaded(int offset, AsyncCallback<Collection<PostObject>> callback);

	void search(String keyword, AsyncCallback<List<ClientInfo>> callback);
	
	void post(PostObject obj,AsyncCallback<Boolean> callback);
	
	void deleteImage(long key, AsyncCallback<Void> callback);
}
