package com.distributed.socialnetwork.shared;

public class ImageObject {

	public static ImageObject create(String id, String time, String date) {
		return new ImageObject(id, time, date);
	}
	
	private String id;
	private String creationTime;
	private String creationDate;
	
	private ImageObject(String id, String time, String date) {
		this.id = id;
		this.creationTime = time;
		this.creationDate = date;
	}
	
	public String getID() {
		return id;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public String getCreationTime() {
		return creationTime;
	}
}
