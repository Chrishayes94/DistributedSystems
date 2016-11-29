package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.event.shared.EventHandler;

public interface GalleryUpdatedEventHandler extends EventHandler {
	
	void onGalleryUpdated(GalleryUpdatedEvent event);

}
