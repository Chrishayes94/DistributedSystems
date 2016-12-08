package com.distributed.socialnetwork.shared;

import java.io.Serializable;
import java.sql.Date;

import com.distributed.socialnetwork.server.UploadContentServlet;

public class PostObject implements Serializable {

	private static final long serialVersionUID = 6191883700216273374L;
	
	public static PostObject create() {
		return new PostObject();
	}
	
	public static PostObject create(int id, String time, String images, String posts) {
		return new PostObject(id, time, images, posts);
	}
	
	public static String getServingUrl() {
		return UploadContentServlet.REMOTE_DIRECTORY;
	}
	
	private int userid;
	private String dateTime;
	private String images;
	private String posts;
	
	private boolean imagePost = true;
	
	private PostObject() {}
	
	private PostObject(int id, String time, String images, String posts) {
		if (images.length() == 0 || images == null) imagePost = false;
		
		this.userid = id;
		this.dateTime = time;
		this.images = images;
		this.posts = posts;
	}
	
	public int getID() {
		return userid;
	}
	
	public String getCreationDate() {
		return dateTime;
	}
	
	public String[] getImages() {
		return images.split(",");
	}
	
	public String getImagesUnsplit() {
		return images;
	}
	
	public String getPosts() {
		return posts;
	}
	
	public boolean isImagePost() {
		return imagePost; 
	}
}
