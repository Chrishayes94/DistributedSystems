package com.distributed.networksocial.handlers;

/**
 * Login Validation template, this class will oversee all Login attempts, errors and successful attempts.
 * Possibly handle validation of new accounts, password resets and lockouts for accounts?
 * @author Chris
 *
 */
public class LoginDao {

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
		/**
		 * Check the datastore for the username and passsword, do they matcH?
		 * If so return true, else return false.
		 */
		} catch (Exception e) {
		}
		return status;
	}
}
