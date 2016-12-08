package com.distributed.socialnetwork.shared;

public class ImageObject {

	public static ImageObject create(long id, String date) {
		return new ImageObject(id, date);
	}
	
	private long id;
	private String creation;
	
	private ImageObject(long id, String creation) {
		this.id = id;
		this.creation = creation;
	}
	
	public long getID() {
		return id;
	}
	
	public String getCreationDate() {
		return creation;
	}
}
