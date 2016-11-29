package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Handler for updates (ie new uploads) to the gallery
 * @author Alex
 */
public class GalleryUpdatedEvent extends GwtEvent<GalleryUpdatedEventHandler> {

    public static Type<GalleryUpdatedEventHandler> TYPE = new Type<GalleryUpdatedEventHandler>();

	@Override
	public Type<GalleryUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GalleryUpdatedEventHandler handler) {
		handler.onGalleryUpdated(this);
	}

}
