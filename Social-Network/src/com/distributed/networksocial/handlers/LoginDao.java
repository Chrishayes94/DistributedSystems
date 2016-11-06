package com.distributed.networksocial.handlers;

import java.util.Collections;

import org.datanucleus.util.Base64;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * Login Validation template, this class will oversee all Login attempts, errors and successful attempts.
 * Possibly handle validation of new accounts, password resets and lockouts for accounts?
 * @author Chris
 *
 */
public class LoginDao {

	protected static Entity currentRequestedUser = null;
	
	/**
	 * This class will detect if the supplied username and password are a valid combination,
	 *	after this we can call our new method {@code} retriveUniqueID. Which will be used for
	 *	all other communications
	 * @param name - The username supplied from the Login form (Email/username)?
	 * @param pass - The password that was supplied via the login form.
	 * @return a boolean statement depending on the success of the datastore query.
	 */
	public static boolean validate(String name, String pass) {
		boolean status = false;
		
		try {
			final DatastoreService store = DatastoreServiceFactory.getDatastoreService();
			Entity user = null;

			if (entityExists(name)) {
				user = currentRequestedUser;
				currentRequestedUser = null;
				
				System.out.println(Base64.decodeString((String) user.getProperty("password")));
				
				if (((String)user.getProperty("password")).equals(encryptPassword(pass))) {
					status = true;
				}
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		return status;
	}
	
	protected static boolean entityExists(String name) {
		final Query q = new Query("Users");
		final PreparedQuery pq = DatastoreServiceFactory.getDatastoreService().prepare(q);
		for (final Entity e : pq.asIterable()) {
			if (((String)e.getProperty("email")).equals(name)) {
				currentRequestedUser = e;
				return true; 
			}
		}
		return false;
	}
	
	protected static String encryptPassword(String pass) {
		return Base64.encodeString(pass);
	}
}
