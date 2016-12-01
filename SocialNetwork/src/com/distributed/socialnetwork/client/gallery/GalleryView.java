package com.distributed.socialnetwork.client.gallery;

import java.util.List;

import com.distributed.socialnetwork.client.services.UserContentService;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creating docklayoutpanel layout for main gallery view page after login
 * @author Alex
 */

public class GalleryView extends Composite implements GalleryUpdatedEventHandler {
	
	interface GalleryViewUiBinder extends UiBinder<Widget, GalleryView> {}

	private VerticalPanel vPanel = new VerticalPanel();
	
	private UploadContent uploadWidget;
	private GalleryView galleryView;
	
	private GalleryViewUiBinder uiBinder = GWT.create(GalleryViewUiBinder.class);

	UserContentServiceAsync userContentService = GWT.create(UserContentService.class);

	private static final int GALLERY_WIDTH = 5;

	@UiField
	FlexTable galleryTable;
	
	public GalleryView() {	
				
		initWidget(uiBinder.createAndBindUi(this));
		refreshGallery();
		
	    DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.EM);
	    dockPanel.setStyleName("dockpanel");
	
	    // Add text all around
	    dockPanel.add(new HTML("This is the first north component."));
	    dockPanel.add(new HTML("This is the first south component."));
	    dockPanel.add(new HTML("This is the west component."));
	
	    uploadWidget.addGalleryUpdatedEventHandler(galleryView);
	    // Add scrollable text in the center
	    galleryTable.setSize("100%", "100%");
	    galleryTable.setWidget(0, 0, galleryView);
	    galleryTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
	    galleryTable.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    dockPanel.add(galleryTable);
	
	    // Add the widgets to the root panel.
        vPanel.add(dockPanel);
	}

	public void refreshGallery() {
		userContentService.getRecentlyUploaded(new AsyncCallback<List<ClientInfo>>() {

					@Override
					public void onSuccess(List<ClientInfo> contents) {
						
						int currentColumn = 0;
						int currentRow = 0;
						for (final ClientInfo content : contents) {

							Image imageWidget = createContentWidget(content);

							galleryTable.setWidget(currentRow, currentColumn, imageWidget);

							currentColumn++;
							if (currentColumn >= GALLERY_WIDTH) {
								currentColumn = 0;
								currentRow++;
							}
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	private Image createContentWidget(final ClientInfo image) {
		final Image imageWidget = new Image();
		imageWidget.setUrl(image.getServingUrl() + "=s200");
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);

		imageWidget.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget source = (Widget) event.getSource();
				int left = source.getAbsoluteLeft() + 10;
				int top = source.getAbsoluteTop() + source.getOffsetHeight() + 10;

				simplePopup.setWidth("150px");
				simplePopup.setWidget(new HTML("Uploaded: "+ image.getCreatedAt()));
				simplePopup.show();
				simplePopup.setPopupPosition(left, top);
			}
		});

		imageWidget.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				simplePopup.hide();
			}
		});

		return imageWidget;
	}

	public void onGalleryUpdated(GalleryUpdatedEvent event) {
		refreshGallery();
	}

	public VerticalPanel getMainPanel() {
		return this.vPanel;
	}
}
