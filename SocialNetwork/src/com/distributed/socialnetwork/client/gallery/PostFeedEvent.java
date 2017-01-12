package com.distributed.socialnetwork.client.gallery;

import com.distributed.socialnetwork.client.ui.PostFeed;

public interface PostFeedEvent {

	public void feedLiked(PostFeed feed, long userId, boolean liked);
}
