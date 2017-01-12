package com.distributed.socialnetwork.shared;

import java.io.Serializable;
import java.sql.Date;

import com.distributed.socialnetwork.server.UploadContentServlet;

public class PostObject implements Serializable {

	private static final long serialVersionUID = 6191883700216273374L;
	
	public static PostObject create() {
		return new PostObject();
	}
	
	public static PostObject create(int postId, long id, String time, String images, String posts) {
		return new PostObject(postId, id, time, images, posts, "");
	}
	
	public static PostObject create(int postId, long id, String time, String images, String posts, String likes)  {
		return new PostObject(postId, id, time, images, posts, likes);
	}
	
	public static String getServingUrl() {
		return UploadContentServlet.REMOTE_DIRECTORY;
	}
	
	private int postId;
	private long userid;
	private String dateTime;
	private String images;
	private String posts;
	
	private String likeIds;
	private int likes = 0;
	
	private boolean imagePost = true;
	
	private PostObject() {}
	
	private PostObject(int postId, long id, String time, String images, String posts, String likeIds) {
		if (images.length() == 0 || images == null) imagePost = false;
		
		this.postId = postId;
		this.userid = id;
		this.dateTime = time;
		this.images = images;
		this.posts = posts;
		this.likeIds = likeIds;
		
		if (!images.equals("")) imagePost = true;
		else imagePost = false;
		
		if (likeIds.equals("")) likes = 0;
		else {
			likes = this.likeIds.split(",").length;
		}
	}
	
	public int getPostID() {
		return postId;
	}
	
	public long getID() {
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
	
	public int getLikes() {
		return likes;
	}
	
	public String getIDS() {
		return likeIds;
	}
	
	public void addIDS(long id) {
		String temp = String.valueOf(id);
		temp += "," + likeIds;
		
		likeIds = temp;
	}
}
