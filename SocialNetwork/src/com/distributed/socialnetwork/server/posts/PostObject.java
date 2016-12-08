package com.distributed.socialnetwork.server.posts;

import java.io.Serializable;
import java.sql.Date;

public class PostObject implements Serializable {

	private static final long serialVersionUID = 6191883700216273374L;
	
	private int id;
	private Date dateTime;
	private String images;
	private String posts;
	
	private boolean imagePost = true;
	
	public static PostObject create() {
		return new PostObject();
	}
	
	public static PostObject create(int id, Date time, String images, String posts) {
		return new PostObject(id, time, images, posts);
	}
	
	private PostObject() {}
	
	private PostObject(int id, Date time, String images, String posts) {
		if (images.length() == 0 || images == null) imagePost = false;
		
		this.id = id;
		this.dateTime = time;
		this.images = images;
		this.posts = posts;
	}
	
	public int getID() {
		return id;
	}
	
	public Date getCreationDate() {
		return dateTime;
	}
	
	public String getImages() {
		return images;
	}
	
	public String getPosts() {
		return posts;
	}
	
	public boolean isImagePost() {
		return imagePost; 
	}
}
