package com.distributed.socialnetwork.shared;

public class ImageObject {

	public static ImageObject create(long id, String time, String date) {
		return new ImageObject(id, time, date);
	}
	
	private long id;
	private String creationTime;
	private String creationDate;
	
	private ImageObject(long id, String time, String date) {
		this.id = id;
		this.creationTime = time;
		this.creationDate = date;
	}
	
	public long getID() {
		return id;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public String getCreationTime() {
		return creationTime;
	}
}
