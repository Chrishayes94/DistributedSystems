package com.distributed.socialnetwork.client.gallery;

import java.util.List;
import java.util.logging.Level;

import com.distributed.socialnetwork.client.SocialNetwork;
import com.distributed.socialnetwork.client.services.UserContentServiceAsync;
import com.distributed.socialnetwork.client.ui.PostFeed;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creating docklayoutpanel layout for main gallery view page after login
 * @author Alex
 */

public class GalleryView extends Composite implements GalleryUpdatedEventHandler, PostFeedEvent {
	
	@UiTemplate("GalleryView.ui.xml")
	interface GalleryViewUiBinder extends UiBinder<Widget, GalleryView> {}

	private VerticalPanel vPanel = new VerticalPanel();
	
	private GalleryView galleryView;
	
	private GalleryViewUiBinder uiBinder = GWT.create(GalleryViewUiBinder.class);

	private UserContentServiceAsync userContentService;
	
	private static final int GALLERY_WIDTH = 4;

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
		this.userContentService = main.getUserService();
		initWidget(uiBinder.createAndBindUi(this));
		
		galleryTable.setCellSpacing(20);
	}
	
	public void createTest() {		
		PostFeed post = new PostFeed();
		galleryTable.setWidget(currentRow, currentColumn++, post);
		galleryTable.setStylePrimaryName("flexTable");
		
		if (currentColumn >= GALLERY_WIDTH) {
			currentColumn = 0;
			currentRow++;
		}
	}

	public void refreshGallery(int...is) {
		currentRow = currentColumn = 0;
		userContentService.getRecentlyUploaded(is == null || is.length == 0 ? posts : is[0], new AsyncCallback<List<PostObject>>() {

					@Override
					public void onSuccess(List<PostObject> contents) {
						
						if (contents.size() == 0 || contents == null)
							return;
						
						for (final PostObject content : contents) {
							posts++;
							Object imageWidget = createContentWidget(content);
							galleryTable.setWidget(currentRow, currentColumn++, (Widget) imageWidget);
							
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
	
	public void refreshGallery(int id) {
		userContentService.getUsersPosts(id, new AsyncCallback<List<PostObject>>() {

			@Override
			public void onFailure(Throwable caught) {
				SocialNetwork.rootLogger.log(Level.WARNING, "Unable to recieve the requested users posts, error reprorted:\n" + 
										caught.getMessage());
			}

			@Override
			public void onSuccess(List<PostObject> result) {
				if (result.size() == 0 || result == null)
					return;
				
				galleryTable.clear();
				posts = 0;
				currentRow = currentColumn = 0;
				
				for (final PostObject content : result) {
					posts++;
					Object ImageWidget = createContentWidget(content);
					galleryTable.setWidget(currentRow, currentColumn++, (Widget) ImageWidget);
					
					if (currentColumn >= GALLERY_WIDTH) {
						currentColumn = 0;
						currentRow++;
					}
				}
			}
			
		});
	}
	
	private String user;
	private Object createContentWidget(final PostObject image) {
		final Image imageWidget = new Image();
		final PostFeed feed = new PostFeed(image, image.getID(), user, this);

		// We know that the post is an image post.
		/**if (image.isImagePost()) {
			
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	
			imageWidget.addMouseOverHandler(new MouseOverHandler() {
	
				@Override
				public void onMouseOver(MouseOverEvent event) {
					Widget source = (Widget) event.getSource();
					final int left = source.getAbsoluteLeft() + 10;
					final int top = source.getAbsoluteTop() + source.getOffsetHeight() + 10;
					
					userContentService.findName(image.getID(), new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(String result) {
							simplePopup.setWidth("150px");
							simplePopup.setWidget(new HTML("Uploaded by: " + result));
							simplePopup.show();
							simplePopup.setPopupPosition(left, top);
						}
					});
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
		}**/

		userContentService.findName(image.getID(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				feed.setUsername(result);
			}
			
		});
		
		userContentService.getImage(image.getImagesUnsplit(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				feed.alterImage(result);
			}
			
		});
		
		return feed;
	}

	private Image get(String name) {
		final Image image = new Image();
		
		if (name.equals("") || name == null) return null;
		
		userContentService.getImage(name, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				image.setUrl("");
			}

			@Override
			public void onSuccess(String result) {
				image.setUrl(result);
			}
			
		});
		return image;
	}
	
	@Override
	public void onGalleryUpdated(GalleryUpdatedEvent event) {
		refreshGallery();
	}
	

	@Override
	public void feedLiked(PostFeed feed, long userId, boolean liked) {
		refreshGallery(posts > 25 ? posts - 25 : posts);
	}

	public FlexTable getGalleryTable() {
		return this.galleryTable;
	}
	
	public DockLayoutPanel getDockPanel() {
		return this.dockPanel;
	}
}
