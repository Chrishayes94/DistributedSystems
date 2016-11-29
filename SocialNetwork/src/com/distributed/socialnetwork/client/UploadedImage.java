package com.distributed.socialnetwork.client;

import java.io.Serializable;
import java.util.Date;

/**
 * Uploaded images 
 * @author Alex
 */

@SuppressWarnings("serial")
public class UploadedImage implements Serializable {

	public static final String IMAGE = "image";
	public static final String CREATED_AT = "createdAt";
	public static final String OWNER_ID = "ownerId";

	String key;
	String image;
	Date createdAt;
	String ownerId; // Refers to the User that uploaded this

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String servingUrl) {
		this.image = servingUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
