package com.distributed.socialnetwork.client.gallery;

import java.util.Collection;
import java.util.logging.Level;

import com.distributed.socialnetwork.client.SocialNetwork;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.server.UploadContentServlet;
import com.distributed.socialnetwork.server.database.DatabaseManager;
import com.distributed.socialnetwork.shared.ClientInfo;
import com.distributed.socialnetwork.shared.PostObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.distributed.socialnetwork.server.UploadContentServlet.download;

/**
 * Creating docklayoutpanel layout for main gallery view page after login
 * @author Alex
 */

public class GalleryView extends Composite implements GalleryUpdatedEventHandler {
	
	private static Image get(String name) {
		
		if (name == null) return null;
		
		if (name == "") return null;
		
		return download(name);
	}
	
	@UiTemplate("GalleryView.ui.xml")
	interface GalleryViewUiBinder extends UiBinder<Widget, GalleryView> {}

	private VerticalPanel vPanel = new VerticalPanel();
	
	private UploadContent uploadWidget;
	private GalleryView galleryView;
	
	private GalleryViewUiBinder uiBinder = GWT.create(GalleryViewUiBinder.class);

	private UserContentServiceAsync userContentService;
	
	private static final int GALLERY_WIDTH = 5;

	private SocialNetwork parent;
	
	@UiField
	DockLayoutPanel dockPanel;
	
	@UiField
	FlexTable galleryTable;
	
	private int currentColumn = 0;
	private int currentRow = 0;
	private int posts = 0;
	
	public GalleryView(SocialNetwork main) {
		this.parent = main;
		initWidget(uiBinder.createAndBindUi(this));
		
		refreshGallery();
	}

	public void refreshGallery() {
		userContentService.getRecentlyUploaded(posts, new AsyncCallback<Collection<PostObject>>() {

					@Override
					public void onSuccess(Collection<PostObject> contents) {
						
						for (final PostObject content : contents) {
							posts++;
							Object imageWidget = createContentWidget(content);
							galleryTable.setWidget(currentRow, currentColumn, (Widget) imageWidget);

							currentColumn++;
							if (currentColumn >= GALLERY_WIDTH) {
								currentColumn = 0;
								currentRow++;
							}
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						//Window.alert("Refresh failed! Please try again...");
						SocialNetwork.rootLogger.log(Level.WARNING, "Unable to refresh, error reported:\n" +
										caught.getMessage());
					}
				});
	}

	private Object createContentWidget(final PostObject image) {
		Image imageWidget = new Image();
		boolean isImage = false;
		
		// We know that the post is an image post.
		if ((imageWidget = get(image.getImages()[0])) != null) {
			isImage = true;
			
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	
			imageWidget.addMouseOverHandler(new MouseOverHandler() {
	
				@Override
				public void onMouseOver(MouseOverEvent event) {
					Widget source = (Widget) event.getSource();
					int left = source.getAbsoluteLeft() + 10;
					int top = source.getAbsoluteTop() + source.getOffsetHeight() + 10;
	
					simplePopup.setWidth("150px");
					simplePopup.setWidget(new HTML("Uploaded by: " + DatabaseManager.get(image.getID())));
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
			
			imageWidget.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					String[] images = image.getImages();
					
					simplePopup.add(get(images[0]));
				}
			});
		} else {
			// We know that the post is only a text post.
			// So populate the information.
			String s = image.getPosts();
		}

		return imageWidget;
	}

	public void onGalleryUpdated(GalleryUpdatedEvent event) {
		refreshGallery();
	}

	public FlexTable getGalleryTable() {
		return this.galleryTable;
	}
	
	public DockLayoutPanel getDockPanel() {
		return this.dockPanel;
	}
}
