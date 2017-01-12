package com.distributed.socialnetwork.client.ui;

import com.distributed.socialnetwork.client.gallery.PostFeedEvent;
import com.distributed.socialnetwork.shared.PostObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class PostFeed extends Composite {

	private static PostFeedUiBinder uiBinder = GWT.create(PostFeedUiBinder.class);

	interface PostFeedUiBinder extends UiBinder<Widget, PostFeed> {
	}
	
	private class LikeButton implements ClickHandler {

		private PostFeed post;
		
		public LikeButton(PostFeed post) {
			this.post = post;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			// Make a call back to change the database
			post.callback.feedLiked(post, post.userID,	post.alreadyLiked = !post.alreadyLiked);
			
			if (!post.alreadyLiked == true) {
				post.getPost().getIDS().replace(post.userID + ",", "");
			} else {
				post.getPost().addIDS(post.userID);
			}
			post.refresh(post.getPost());
		}
	}
	
	private static final int MAX_SHORTTEXT_LENGTH = 256;
	private static final String LIKE_LABEL = "Like";
	private static final String LIKED_LABEL = "Liked";

	@UiField
	HTMLPanel panel;
	
	@UiField
	Image avatarImage;
	
	@UiField
	HTML userName;
	
	@UiField
	HTML time;
	
	@UiField
	Image image;
	
	@UiField
	HTML content;
	
	@UiField
	Anchor likeArea;
	
	@UiField
	HTML likeNo;
	
	@UiField
	HTML breakLine;
	
	private PostFeedEvent callback;
	
	private PostObject post;
	private long userID = 0;
	private String name;
	
	private boolean alreadyLiked = false;
	
	/**
	 * Create a empty post view.
	 */
	public PostFeed() {
		initWidget(uiBinder.createAndBindUi(this));
		likeArea.addClickHandler(new LikeButton(this));
		breakLine.setHTML("<br /> <br /> <br />");
		panel.setSize("200px", "150px");
	}
	
	public PostFeed(PostObject object, long id, String name, PostFeedEvent callback) {
		this();
		this.callback = callback;
		this.name = name;
		this.content.setHTML(object.getPosts());
		create(object, id);
	}
	
	/** 
	 * 	Create a post view with just an image.
	 * @param image
	 */
	public PostFeed(Image image, PostObject object, long id, String name, PostFeedEvent callback) {
		this();
		this.callback = callback;
		this.image = image;
		this.name = name;
		create(object, id);
	}
	
	public void refresh(PostObject object) {
		
		this.post = object;
		
		if (alreadyLiked) {
			this.likeArea.setHTML(LIKED_LABEL);
		} else {
			this.likeArea.setHTML(LIKE_LABEL);
		}
		
		if (post.getLikes() > 0) {
			likeNo.setHTML(String.valueOf(object.getLikes()) + " people have " + LIKED_LABEL + " this.");
		}
	}
	
	private void create(PostObject object, long id) {
		
		this.post = object;
		this.userID = object.getPostID();
		
		if (object.getIDS().contains(String.valueOf(id))) {
			this.likeArea.setHTML(LIKED_LABEL);
			this.alreadyLiked = true;
		}
		else
			this.likeArea.setHTML("<a>" + LIKE_LABEL + "</a>");
		
		if (object.getLikes() > 0) {
			likeNo.setHTML(String.valueOf(object.getLikes()) + " people have " + LIKED_LABEL + " this.");
		}
			
		userName.setHTML(name);
		time.setHTML(object.getCreationDate());
		panel.setSize("400px", "150px");
	}
	
	public void setUsername(String name) {
		userName.setHTML(name);
		this.name = name;
	}
	
	public void alterImage(String image) {
		this.image.setUrl(image);
	}
	
	public PostObject getPost() {
		return post;
	}
	
	public HTMLPanel getPanel() {
		return panel;
	}
}
