package com.distributed.socialnetwork.shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import com.ibm.icu.util.Calendar;

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
	
	private ClientInfo(String email, String password) {
		this.email = email;
		this.password = password;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		this.loginTime = sdf.format(cal.getTime());
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
}
