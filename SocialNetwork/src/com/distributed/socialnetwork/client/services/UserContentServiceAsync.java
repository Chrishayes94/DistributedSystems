package com.distributed.socialnetwork.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.distributed.socialnetwork.shared.UploadedContent;

/**
 * @author Alex
 */

public interface UserContentServiceAsync {

	public void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void get(String key, AsyncCallback<UploadedContent> callback);

	void getRecentlyUploaded(AsyncCallback<List<UploadedContent>> callback);

	void deleteImage(String key, AsyncCallback<Void> callback);


}
