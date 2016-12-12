package cgt.dop.alm.infrastructure;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * These constants are used throughout the code to set the server to work with.
 * To execute this code, change these settings to fit those of your server.
 */
public class Constants {

	private Constants() {
	}

	public static final String HOST = "localhost";
	public static final String PORT = "8080";

	public static final String USERNAME = "admin";
	public static final String PASSWORD = "";

	public static final String DOMAIN = "DEFAULT";
	public static final String PROJECT = "version";

	/**
	 * Supports running tests correctly on both versioned and non-versioned
	 * projects.
	 * 
	 * @return true if entities of entityType support versioning
	 */
	public static boolean isVersioned(String entityType, final String domain,
			final String project) throws Exception {

		RestConnector con = RestConnector.getInstance();
		String descriptorUrl = con.buildUrl("rest/domains/" + domain
				+ "/projects/" + project + "/customization/entities/"
				+ entityType);

		String descriptorXml = con.httpGet(descriptorUrl, null, null)
				.toString();
		EntityDescriptor descriptor = EntityMarshallingUtils.marshal(
				EntityDescriptor.class, descriptorXml);

		boolean isVersioned = descriptor.getSupportsVC().getValue();

		return isVersioned;
	}

	public static String generateFieldXml(String field, String value) {
		return "<Field Name=\"" + field + "\"><Value>" + value
				+ "</Value></Field>";
	}

	/**
	 * This string used to create new "requirement" type entities.
	 */
	public static final String entityToPostName = "req"
			+ Double.toHexString(Math.random());
	public static final String entityToPostFieldName = "type-id";
	public static final String entityToPostFieldValue = "1";
	public static final String entityToPostFormat = "<Entity Type=\"requirement\">"
			+ "<Fields>"
			+ Constants.generateFieldXml("%s", "%s")
			+ Constants.generateFieldXml("%s", "%s")
			+ "</Fields>"
			+ "</Entity>";

	public static final String entityToPostXml = String.format(
			entityToPostFormat, "name", entityToPostName,
			entityToPostFieldName, entityToPostFieldValue);

	public static final CharSequence entityToPostFieldXml = generateFieldXml(
			Constants.entityToPostFieldName, Constants.entityToPostFieldValue);

	public static String uriEncoding(String add) throws MalformedURLException,
			URISyntaxException {
		URL url = new URL(add);

		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(),
				url.getQuery(), null);

		return uri.toASCIIString();
	}

}