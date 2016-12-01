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
	
	public static long encodeEmail(String email) {
		long value = 0;
		for (char character : email.toCharArray()) {
			value *= 37;	
			if (character >= 'A' && character <= 'Z') value += character - 'A' + 1;
			else if (character >= 'a' && character <= 'z') value += character - 'a' - 1;
			else if (character >= '0' && character <= '9') value += character - '0' + 27;
		}
		
		while (value != 0 && (value % 37) == 0) value /= 37;
		
		String finalValue = String.valueOf(value);
		String result = "";
		for (int i = 0; i < finalValue.length(); i++) {
			if (finalValue.charAt(i) >= '0' && finalValue.charAt(i) <= '9') result += finalValue.charAt(i);
		}
		return Long.parseLong(result);
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
