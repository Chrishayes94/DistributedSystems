package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.shared.UploadedContent;

/**
 * 
 * This class represents the ImageOverlay that pops up when a User clicks on an
 * Image. It also provides listeners for management and other
 * functions which are considered "Menu" type functions for a given image.
 * 
 */
public class ContentOverlay extends Composite implements HasHandlers {

	private static ContentOverlayUiBinder uiBinder = GWT.create(ContentOverlayUiBinder.class);

	UserContentServiceAsync imageService = GWT.create(UserContentService.class);

	private HandlerManager handlerManager;

	interface ContentOverlayUiBinder extends UiBinder<Widget, ContentOverlay> {
	}

	@UiField
	Button deleteButton;

	@UiField
	Image image;

	@UiField
	Label timestamp;

	@UiField
	VerticalPanel tagPanel;

	protected UploadedContent uploadedContent;

	public ContentOverlay(UploadedContent uploadedContent) {
		handlerManager = new HandlerManager(this);

		this.uploadedContent = uploadedContent;

		initWidget(uiBinder.createAndBindUi(this));

		image.setUrl(uploadedContent.getServingUrl());
		timestamp.setText("Created at:" + uploadedContent.getCreatedAt());

		uploadedContent.getOwnerId();
		deleteButton.setText("Delete content");
		deleteButton.setVisible(true);
	}

	@UiHandler("image")
	void onClickContent(MouseDownEvent e) {
		Element contentElement = e.getRelativeElement();
		int x = e.getRelativeX(contentElement);
		int y = e.getRelativeY(contentElement);
	}

	/**
	 * 
	 * Handles clicking of the delete button if owned.
	 * 
	 * @param {{@link ClickEvent} e
	 */
	@UiHandler("deleteButton")
	void onClick(ClickEvent e) {
		final ContentOverlay overlay = this;
		imageService.deleteImage(uploadedContent.getKey(),
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						GalleryUpdatedEvent event = new GalleryUpdatedEvent();
						fireEvent(event);
						overlay.removeFromParent();
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	public HandlerRegistration addGalleryUpdatedEventHandler(
			GalleryUpdatedEventHandler handler) {
		return handlerManager.addHandler(GalleryUpdatedEvent.TYPE, handler);
	}

}
