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
import com.distributed.socialnetwork.client.GalleryUpdatedEvent;
import com.distributed.socialnetwork.client.GalleryUpdatedEventHandler;
import com.distributed.socialnetwork.client.services.UserImageService;
import com.distributed.socialnetwork.client.services.UserImageServiceAsync;
import com.distributed.socialnetwork.shared.UploadedImage;

/**
 * 
 * This class represents the ImageOverlay that pops up when a User clicks on an
 * Image. It also provides listeners for management and other
 * functions which are considered "Menu" type functions for a given image.
 * 
 */
public class ImageOverlay extends Composite implements HasHandlers {

	private static ImageOverlayUiBinder uiBinder = GWT
			.create(ImageOverlayUiBinder.class);

	UserImageServiceAsync imageService = GWT.create(UserImageService.class);

	private HandlerManager handlerManager;

	interface ImageOverlayUiBinder extends UiBinder<Widget, ImageOverlay> {
	}

	@UiField
	Button deleteButton;

	@UiField
	Image image;

	@UiField
	Label timestamp;

	@UiField
	VerticalPanel tagPanel;

	protected UploadedImage uploadedImage;

	public ImageOverlay(UploadedImage uploadedImage) {
		handlerManager = new HandlerManager(this);

		this.uploadedImage = uploadedImage;

		initWidget(uiBinder.createAndBindUi(this));

		image.setUrl(uploadedImage.getServingUrl());
		timestamp.setText("Created at:" + uploadedImage.getCreatedAt());

		uploadedImage.getOwnerId();
		deleteButton.setText("Delete image");
		deleteButton.setVisible(true);
	}

	@UiHandler("image")
	void onClickImage(MouseDownEvent e) {
		Element imageElement = e.getRelativeElement();
		int x = e.getRelativeX(imageElement);
		int y = e.getRelativeY(imageElement);
	}

	/**
	 * 
	 * Handles clicking of the delete button if owned.
	 * 
	 * @param {{@link ClickEvent} e
	 */
	@UiHandler("deleteButton")
	void onClick(ClickEvent e) {
		final ImageOverlay overlay = this;
		imageService.deleteImage(uploadedImage.getKey(),
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
