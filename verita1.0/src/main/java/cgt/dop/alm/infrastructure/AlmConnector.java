package cgt.dop.alm.infrastructure;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * AlmConnector is only a thin layer around the HP class RestConnector, which
 * resides in the package infrastructure. AlmConnector provides only methods
 * around logging into ALM.
 * </p>
 * <p>
 * HP designed the class RestConnector in a static way. Once initialized, the
 * class holds an instance, which can be referenced by any other class using the
 * method <code>RestConnector.getInstance()</code>.
 * </p>
 * <p>
 * Operations like reading from and writing to HP ALM have to be applied through
 * the class RestConnector directly ignoring the AlmConnector.
 * </p>
 */

public class AlmConnector {

	
        private AlmConnector(){
    	
        }
	private static AlmConnector instance = new AlmConnector();

	public static AlmConnector getInstance() {
		return instance;
	}

	/**
	 * <p>
	 * Attempts to log a user into an ALM project. If a user is already
	 * authenticated, no action is applied but true will be returned.
	 * </p>
	 * <p>
	 * Calling <code>login</code> after being already authenticated will not
	 * logout the currently logged in user. You specifically have to call
	 * <code>logout</code> before logging in with other user credentials.
	 * </p>
	 * <p>
	 * To check if a user is authenticated call method
	 * <code>isAuthenticated()</code>.
	 * </p>
	 * 
	 * @param username
	 *            - a String providing the name of a user in HP ALM.
	 * @param password
	 *            - the HP ALM password corresponding a provided user name.
	 * @return true if user is successfully authenticated else false.
	 * @throws Exception
	 */
	public boolean login(String username, String password) throws Exception {
		/**
		 * Get the current authentication status.
		 */
		String authenticationPoint = this.isAuthenticated();

		/**
		 * If the authenticationPoint is null, the user is already
		 * authenticated. In this case no login necessary.
		 */
		if (authenticationPoint != null) {
			return this.login(authenticationPoint, username, password);
		}

		return true;
	}

	/**
	 * <p>
	 * Logging into HP ALM is standard HTTP login (basic authentication), where
	 * one must store the returned cookies for further use.
	 * <p>
	 * 
	 * @param loginUrl
	 *            - a String providing an URL to authenticate at.
	 * @param username
	 *            - an HP ALM user name.
	 * @param password
	 *            - an HP ALM user password corresponding username.
	 * @return true if login is successful, else false.
	 * @throws Exception
	 */
	private boolean login(String loginUrl, String username, String password)
			throws Exception {
/**
		 * create a string that looks like:
		 * "Basic ((username:password)<as bytes>)<64encoded>"
		 */
		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);

		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", credEncodedString);
		RestConnector con = RestConnector.getInstance();
		Response response = con.httpGet(loginUrl, null, map);

		boolean ret = response.getStatusCode() == HttpURLConnection.HTTP_OK;

		return ret;
	}

	/**
	 * Closes a session on a server and cleans session cookies on a client.
	 * 
	 * @return true if logout was successful.
	 * @throws Exception
	 */
	public boolean logout() throws Exception {
	    	RestConnector con = RestConnector.getInstance();
		/**
		 * note the get operation logs us out by setting authentication cookies
		 * to: LWSSO_COOKIE_KEY="" via server response header Set-Cookie
		 */
		Response response = con.httpGet(
				con.buildUrl("authentication-point/logout"), null, null);

		return (response.getStatusCode() == HttpURLConnection.HTTP_OK);
	}

	/**
	 * Indicates if a user is already authenticated and returns an URL to
	 * authenticate against if the user is not authenticated yet. Having this
	 * said the returned URL is always as follows.
	 * https://{host}/qcbin/authentication-point/authenticate
	 * 
	 * @return null if a user is already authenticated.<br>
	 *         else an URL to authenticate against.
	 * @throws Exception
	 *             - an Exception occurs, if HTTP errors like 404 or 500 occur
	 *             and the thrown Exception should reflect those errors.
	 */
	public String isAuthenticated() throws Exception {
	    	RestConnector con = RestConnector.getInstance();
		String isAuthenticateUrl = con.buildUrl("rest/is-authenticated");
		String ret;

		Response response = con.httpGet(isAuthenticateUrl, null, null);
		int responseCode = response.getStatusCode();

		/**
		 * If a user is already authenticated, the return value is set to null
		 * and the current connection is kept open.
		 */
		if (responseCode == HttpURLConnection.HTTP_OK) {
			ret = HttpURLConnection.HTTP_OK + "";
		}
		/**
		 * If a user is not authenticated yet, return an URL at which he can
		 * authenticate himself via www-authenticate.
		 */
		else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
			ret = con.buildUrl("authentication-point/authenticate");
		}
		/**
		 * If an error occurred during login, the function throws an Exception.
		 */
		else {
			throw response.getFailure();
		}

		return ret;
	}


}
