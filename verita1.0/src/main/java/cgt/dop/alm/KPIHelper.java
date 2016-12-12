package cgt.dop.alm;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.AlmConnector;
import cgt.dop.alm.infrastructure.EntityMarshallingUtils;
import cgt.dop.alm.infrastructure.GroupByHeader;
import cgt.dop.alm.infrastructure.GroupByHeaders;
import cgt.dop.alm.infrastructure.Response;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.log.Debug;
import cgt.dop.alm.log.Error;
import cgt.dop.model.QcDetails;
import cgt.dop.service.QcDetailsService;

/**
 * Helper class for the project.
 * 
 */
@Service("kPIHelper")
public class KPIHelper {

	private static final Logger logger = Logger.getLogger(KPIHelper.class);

	private static final String QCDETAILS_CONFIG = "qcdetails-config";

	private ResourceBundle resourceBundle = ResourceBundle.getBundle(QCDETAILS_CONFIG, Locale.getDefault());

	@Autowired
	private QcDetailsService qcDetailsService;

	public RestConnector getConnection() throws ConstraintViolatedException {
		RestConnector conn = RestConnector.getInstance();
		AlmConnector alm = AlmConnector.getInstance();
		if (null == resourceBundle.getObject("qc.username")) {
			Error.DEFAULT.log("Exception Occured : username null");
			throw new ConstraintViolatedException("Exception Occured : Exception Occured : username null");
		}

		QcDetails qcDetails = qcDetailsService.getQcDetailsByProjectAndUserName(
				resourceBundle.getObject("qc.username").toString(), resourceBundle.getObject("qc.project").toString());

		conn.init(new HashMap<String, String>(), qcDetails.getServiceURL(), qcDetails.getDomain(),
				qcDetails.getProject());

		String isAuthenticated = null;
		try {
			Debug.DEFAULT.log("alm.isAuthenticated()===" + alm.isAuthenticated());
			if ((qcDetails.getServiceURL() + "/authentication-point/authenticate").equals(alm.isAuthenticated())) {
				alm.login(qcDetails.getUserName(), qcDetails.getPassword());
			}
			isAuthenticated = alm.isAuthenticated();
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While login to ALM is attempted");
			throw new ConstraintViolatedException("Exception Occured : While login to ALM is attempted", e);
		}

		/*
		 * Connect to for HP ALM
		 */
		conn.getQCSession();

		Debug.DEFAULT.log("isAuthenticated :" + isAuthenticated);

		return conn;
	}

	public void close() throws ConstraintViolatedException {
		try {
			AlmConnector alm = AlmConnector.getInstance();
			alm.logout();
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : during logout");
			throw new ConstraintViolatedException("Exception Occured : during logout", e);
		}
	}

	public String getResponseAsXMLForDefects(RestConnector conn, String entityType) throws ConstraintViolatedException {

		/*
		 * Get the entity
		 */

		String url = conn.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();

		try {
			String id = resourceBundle.getObject("defect.id").toString();
			String name = resourceBundle.getObject("defect.name").toString();
			String status = resourceBundle.getObject("defect.status").toString();
			String severity = resourceBundle.getObject("defect.severity").toString();
			String priority = resourceBundle.getObject("defect.priority").toString();
			String mttd = resourceBundle.getObject("defect.mttd").toString();
			String detectedDate = resourceBundle.getObject("defect.detecteddate").toString();
			String detectedBy = resourceBundle.getObject("defect.detectedby").toString();
			String detectedStage = resourceBundle.getObject("defect.detectedstage").toString();
			String appName = resourceBundle.getObject("defect.application.name").toString();
			String releaseId = resourceBundle.getObject("defect.release.name").toString();

			String releaseName = "'Rel 1.1'";
			releaseName = URLEncoder.encode(releaseName, "UTF-8");

			url = url + "?query={" + releaseId + "[" + releaseName + "]}&fields=" + id + "," + name + "," + appName
					+ "," + status + "," + severity + "," + mttd + "," + detectedDate + "," + detectedBy + ","
					+ detectedStage + "," + priority + "&order-by={" + appName + "[ASC]}&page-size=max";

			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}

		// xml -> class instance
		String listAsXML = responseAsXML.toString();

		return listAsXML;
	}

	public String getResponseAsXMLForRequirements(RestConnector conn, String entityType)
			throws ConstraintViolatedException {

		/*
		 * Get the entity
		 */

		String url = conn.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();

		try {

			String id = resourceBundle.getObject("requirement.id").toString();
			String name = resourceBundle.getObject("requirement.name").toString();
			String appName = resourceBundle.getObject("requirement.application.name").toString();
			String releaseId = resourceBundle.getObject("requirement.release.name").toString();
			String status = resourceBundle.getObject("requirement.status").toString();

			String releaseName = "'Rel 1.1'";
			releaseName = URLEncoder.encode(releaseName, "UTF-8");

			url = url + "?query={" + releaseId + "[" + releaseName + "]}&fields=" + id + "," + appName + "," + name
					+ "," + status + "," + "&order-by={" + appName + "[ASC]}&page-size=max";

			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}

		// xml -> class instance
		String listAsXML = responseAsXML.toString();

		return listAsXML;
	}

	public String getResponseAsXMLForTest(RestConnector conn, String entityType) throws ConstraintViolatedException {

		/*
		 * Get the entity
		 */

		String url = conn.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();

		try {

			String id = resourceBundle.getObject("test.id").toString();
			String appName = resourceBundle.getObject("test.application.name").toString();
			String releaseId = resourceBundle.getObject("test.release.name").toString();

			String releaseName = "'Rel 1.1'";
			releaseName = URLEncoder.encode(releaseName, "UTF-8");

			url = url + "?query={" + releaseId + "[" + releaseName + "]}&fields=" + id + "," + appName + ","
					+ "&order-by={" + appName + "[ASC]}&page-size=max";

			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}

		// xml -> class instance
		String listAsXML = responseAsXML.toString();

		return listAsXML;
	}

	public String getResponseAsXMLForRun(RestConnector conn, String entityType) throws ConstraintViolatedException {

		String url = conn.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();

		try {
			String releaseName = "'Rel 1.1'";
			releaseName = URLEncoder.encode(releaseName, "UTF-8");

			String id = resourceBundle.getObject("run.id").toString();
			String status = resourceBundle.getObject("run.status").toString();
			String name = resourceBundle.getObject("run.name").toString();
			String type = resourceBundle.getObject("run.type").toString();
			String appName = resourceBundle.getObject("run.application.name").toString();
			String releaseId = resourceBundle.getObject("run.release.name").toString();

			url = url + "?query={" + releaseId + "[" + releaseName + "]}&fields=" + id + "," + appName + "," + status
					+ "," + name + "," + type + "," + "&order-by={" + appName + "[ASC]}&page-size=max";

			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}
		// xml -> class instance
		String listAsXML = responseAsXML.toString();

		return listAsXML;
	}

	public float getLimitedPrecision(float val, int maxDigitsAfterDecimal) {
		int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
		float truncated = (float) ((long) (val * multiplier)) / multiplier;
		return truncated;

	}

	public Map<String, Map<String, Integer>> getGroupByHeaderMapOfMap(String listAsXML, String releaseNo)
			throws ConstraintViolatedException {

		GroupByHeaders groupByHeaders = new GroupByHeaders();
		try {
			groupByHeaders = EntityMarshallingUtils.marshal(GroupByHeaders.class, listAsXML);
		} catch (JAXBException e) {
			Error.DEFAULT.log("Exception Occured : While Marshalling");
		}

		Map<String, Map<String, Integer>> groupByHeaderMap = new TreeMap<String, Map<String, Integer>>();

		if (null != groupByHeaders && null != groupByHeaders.getGroupByHeaders()) {
			List<String> releaseNos = new ArrayList<String>();
			for (GroupByHeader gh : groupByHeaders.getGroupByHeaders()) {
				if (null != gh && null != gh.getGroupByHeaders()) {
					releaseNos.add(gh.getValue());
				}
			}

			if (!releaseNos.contains(releaseNo)) {
				Error.DEFAULT.log("Exception Occured : While matching releaseNo");
			}

			for (GroupByHeader groupByHeader : groupByHeaders.getGroupByHeaders()) {

				if (null != groupByHeader && null != groupByHeader.getGroupByHeaders()) {
					if (releaseNo.equals(groupByHeader.getValue())) {

						for (GroupByHeader child1GroupByHeader : groupByHeader.getGroupByHeaders()) {

							if (null != child1GroupByHeader && null != child1GroupByHeader.getGroupByHeaders()) {
								String key = child1GroupByHeader.getValue();

								for (GroupByHeader child2GroupByHeader : child1GroupByHeader.getGroupByHeaders()) {
									if (null != child2GroupByHeader) {

										if (!groupByHeaderMap.containsKey(key)) {

											Map<String, Integer> value = new HashMap<String, Integer>();
											if (null != child2GroupByHeader.getValue()
													&& child2GroupByHeader.getValue().length() > 0)
												value.put(child2GroupByHeader.getValue(),
														child2GroupByHeader.getSize());
											groupByHeaderMap.put(key, value);
										} else {
											groupByHeaderMap.get(key).put(child2GroupByHeader.getValue(),
													child2GroupByHeader.getSize());
										}
									}

								}
							}

						}
					}

				}

			}
		}

		return groupByHeaderMap;

	}

	public String getResponseAsXML(RestConnector conn, String entityType, String releaseNo, String applicationName,
			String name) throws ConstraintViolatedException {
		/*
		 * Get the entity
		 */

		String url = conn.buildEntityCollectionUrl(entityType);

		url += "/groups/" + releaseNo + applicationName + name;

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();
		try {
			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}

		// xml -> class instance
		String listAsXML = responseAsXML.toString();
		Debug.DEFAULT.log("listAsXML : " + listAsXML);

		return listAsXML;
	}

	public String getRelatedEntities(RestConnector conn, String entityType, String id)
			throws ConstraintViolatedException {

		String url = conn.buildEntityCollectionUrl(entityType);

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");

		Response responseAsXML = new Response();

		try {

			String defectLinkId = resourceBundle.getObject("defect.link.id").toString();
			String defectLinkTestId = resourceBundle.getObject("defect.link.test.id").toString();
			String defectLinkTestName = resourceBundle.getObject("defect.link.test.name").toString();
			String defectLinkTestStatus = resourceBundle.getObject("defect.link.test.status").toString();
			String defectLinkDetectedBy = resourceBundle.getObject("defect.link.test.detectedby").toString();
			String defectLinkDetectedDate = resourceBundle.getObject("defect.link.test.detecteddate").toString();

			String releaseName = "'Rel 1.1'";
			releaseName = URLEncoder.encode(releaseName, "UTF-8");

			url = url + "?query={" + defectLinkId + "[" + id + "]}&fields=" + defectLinkId + "," + defectLinkTestId
					+ "," + defectLinkTestName + "," + defectLinkTestStatus + "," + defectLinkDetectedBy + ","
					+ defectLinkDetectedDate + ",&order-by={" + defectLinkTestId + "[ASC]}&page-size=max";

			responseAsXML = conn.httpGet(url, null, requestHeaders);
		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the responseAsXML");
		}

		if (null == responseAsXML) {
			Error.DEFAULT.log("Exception Occured : responseAsXML null");
		}

		// xml -> class instance
		String listAsXML = responseAsXML.toString();

		// Json o/p for debugging

		/*
		 * try { int PRETTY_PRINT_INDENT_FACTOR = 2; JSONObject xmlJSONObj =
		 * XML.toJSONObject(listAsXML); String jsonPrettyPrintString =
		 * xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		 * System.out.println(jsonPrettyPrintString); } catch (JSONException e)
		 * { Debug.DEFAULT.log(e.getMessage(), e); }
		 */

		// Json o/p for debugging

		return listAsXML;
	}

	public String getEntityDetails(RestConnector conn, String entityType) {

		String responseString = "";
		String url = conn.buildEntityCollectionUrl(entityType) + "?page-size=max";
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");

		Response response = new Response();

		try {
			conn.getQCSession();
			response = conn.httpGet(url, null, requestHeaders);
			responseString = response.toString();

		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the response");
		}

		if (null == response) {
			Error.DEFAULT.log("Exception Occured : response null");
		}

		return responseString;
	}

	public String getEntityFields(RestConnector conn, String entityType, String collectionname) {

		String responseString = "";
		String url = conn.buildcustomizationURL(entityType, collectionname) + "?page-size=max";

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");

		Response response = new Response();

		try {

			conn.getQCSession();
			response = conn.httpGet(url, null, requestHeaders);
			responseString = response.toString();

		} catch (Exception e) {
			Error.DEFAULT.log("Exception Occured : While getting the response");
		}

		if (null == response) {
			Error.DEFAULT.log("Exception Occured : response null");
		}

		return responseString;
	}

}
