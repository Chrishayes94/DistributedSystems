package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.distributed.socialnetwork.shared.ClientInfo;

/**
 * @author Alex
 */

public interface UserContentServiceAsync {

	void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<ClientInfo> callback);

	void getRecentlyUploaded(AsyncCallback<List<ClientInfo>> callback);

	void deleteImage(String key, AsyncCallback<Void> callback);
}
