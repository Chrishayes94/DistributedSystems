package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.distributed.socialnetwork.shared.ClientInfo;

/**
 * @author Alex
 */

public interface UserContentServiceAsync {

	void getImageUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<ClientInfo> callback);

	void getRecentlyUploaded(AsyncCallback<List<ClientInfo>> callback);

	void deleteImage(long key, AsyncCallback<Void> callback);
}
