package com.distributed.socialnetwork.shared;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * ClientInfo handles information regarding new clients connecting to the social network,
 * through previously created accounts. Any new registrations are past here after.
 * </p>
 * @author Chris
 *
 */
public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 8439894809734599593L;

	public static ClientInfo createClient(String email, String password) {
		return new ClientInfo(email, password);
	}
	
	private String email;
	private String password;
	private String loginTime;
	private String key;
	private String ownerId;
	private Date createdAt;
	private String servingUrl;
	
	public ClientInfo() {}
	
	private ClientInfo(String email, String password) {
		this.email = email;
		this.password = password;
		
		//Calendar cal = Calendar.getInstance();
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//this.loginTime = sdf.format(cal.getTime());
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getLoginTime() {
		return loginTime;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getServingUrl() {
		return servingUrl;
	}
	
	public void setServingUrl(String servingUrl) {
		this.servingUrl = servingUrl;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
