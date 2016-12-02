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
	
	public static ClientInfo createClient(String fullname, String email, String password) {
		return new ClientInfo(fullname, email, password);
	}
	
	public static ClientInfo createClient(long id, String fullname, String email, String password) {
		return new ClientInfo(id, fullname, email, password);
	}
	
	public static long encode(String email) {
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
	private String fullname;
	
	private long ownerId;
	
	public ClientInfo() {}
	
	private ClientInfo(String fullname, String email, String password) {
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.ownerId = encode(email);
	}
	
	private ClientInfo(long id, String fullname, String email, String password) {
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.ownerId = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFullname() {
		return fullname;
	}

	public long getOwnerId() {
		return ownerId;
	}
	
	@Override
	public String toString() {
		return "(" + String.valueOf(ownerId) + ", " + 
				email + ", " +
				password + ", " + 
				fullname + ")";
	}
}
