package com.distributed.socialnetwork.client.gallery;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.distributed.socialnetwork.client.gallery.ContentOverlay;
import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.shared.UploadedContent;

/**
 * Widget creation for uploading images. Implements UI binding (GWT)
 * @author Alex
 */

public class UploadContent extends Composite implements HasHandlers {

	private static UploadPhotoUiBinder uiBinder = GWT
			.create(UploadPhotoUiBinder.class);

	UserContentServiceAsync userImageService = GWT.create(UserContentService.class);

	private HandlerManager handlerManager;

	interface UploadPhotoUiBinder extends UiBinder<Widget, UploadContent> {
	}

	@UiField
	Button uploadButton;

	@UiField
	FormPanel uploadForm;

	@UiField
	FileUpload uploadField;

	public UploadContent() {
		handlerManager = new HandlerManager(this);

		initWidget(uiBinder.createAndBindUi(this));

		uploadButton.setText("Upload");
		uploadButton.setText("Loading...");
		uploadButton.setEnabled(false);
		uploadField.setName("image");

		startNewUploadSession();

		uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						uploadForm.reset();
						startNewUploadSession();

						String key = event.getResults();

						userImageService.get(key,new AsyncCallback<UploadedContent>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub

									}

									@Override
									public void onSuccess(UploadedContent result) {

										ContentOverlay overlay = new ContentOverlay(result);
										GalleryUpdatedEvent event = new GalleryUpdatedEvent();
										fireEvent(event);

										// TODO: Add something here that says,
										// hey, upload succeeded

										final PopupPanel imagePopup = new PopupPanel(true);
										imagePopup.setAnimationEnabled(true);
										imagePopup.setWidget(overlay);
										imagePopup.setGlassEnabled(true);
										imagePopup.setAutoHideEnabled(true);

										imagePopup.center();

									}
								});
					}
				});
	}

	private void startNewUploadSession() {
		userImageService.getBlobstoreUploadUrl(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				uploadForm.setAction(result);
				uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
				uploadForm.setMethod(FormPanel.METHOD_POST);
				uploadButton.setText("Upload");
				uploadButton.setEnabled(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	@UiHandler("uploadButton")
	void onSubmit(ClickEvent e) {
		uploadForm.submit();
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		handlerManager.fireEvent(event);
	}

	public HandlerRegistration addGalleryUpdatedEventHandler(GalleryUpdatedEventHandler handler) {
		return handlerManager.addHandler(GalleryUpdatedEvent.TYPE, handler);
	}
}