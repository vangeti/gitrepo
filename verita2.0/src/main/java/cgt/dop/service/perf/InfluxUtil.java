package cgt.dop.service.perf;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cgt.dop.bean.InfluxAggregateReportBean;
import cgt.dop.bean.InfluxSummaryBean;
import cgt.dop.bean.InfluxUserProfileBean;
import cgt.dop.bean.UserProfileDTO;

public class InfluxUtil {

	public static ResourceBundle bundle = ResourceBundle.getBundle("influxdb", Locale.getDefault());

	public static String url = bundle.getObject("influxurl").toString();
	public static String url1 = bundle.getObject("influxurl1").toString();

	public static String database = bundle.getObject("influxDb").toString();
	public static String database1 = bundle.getObject("influxDb1").toString();

	public static String userName = bundle.getObject("influxadmin").toString();

public static String password = bundle.getObject("influxpassword").toString();

	private UserProfileDTO userProfileDTO;

	private ArrayList<InfluxAggregateReportBean> influxAggregateReportList;

	private ArrayList<InfluxSummaryBean> influxSummaryList;
	
	//private ArrayList<InfluxUserProfileBean> influxSummaryList1;
	
	private UserProfileDTO userProfileDTOForTest2;

	private ArrayList<InfluxAggregateReportBean> influxAggregateReportListForTest2;

	private ArrayList<InfluxSummaryBean> influxSummaryListForTest2;

	public void test1() {

		try {

			System.out.println("test1Table ==================="+bundle.getObject("test1Table").toString());
			String query = "select \"allThreads\",\"timeStamp\",\"responseCode\",\"Latency\",\"bytes\",\"label\" from "
					+ bundle.getObject("test1Table").toString();
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParam("q", query).queryParam("db", database).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			influxSummaryList = this.getInfluxSummary(results);

			String query2 = "select \"sampler_label\",\"aggregate_report_count\",\"average\",\"aggregate_report_90%_line\",\"aggregate_report_min\",\"aggregate_report_max\",\"aggregate_report_error%\" from "
					+ bundle.getObject("test1AggregateTable").toString();

			UriComponents uriComponents2 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", query2)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> avgResults = new RestTemplate().getForEntity(uriComponents2.toUri(), String.class);

			influxAggregateReportList = this.getInfluxAggregateReport(avgResults);

			String dateQuery = "select min(timeStamp), max(timeStamp)  from " + bundle.getObject("test1Table").toString();

			UriComponents dateUriComponents = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", dateQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result = new RestTemplate().getForEntity(dateUriComponents.toUri(), String.class);
			JSONArray jsonValues = getJSONArray(result);
			JSONArray jsonValue = (JSONArray) jsonValues.get(0);
			long startDateTimeStamp = jsonValue.getLong(1);
			long endDateTimeStamp = jsonValue.getLong(2);

			// Query to get maxNo.ofThreads
			String maxNoOfThreadsQuery = "select max(allThreads) from " + bundle.getObject("test1Table").toString();

			UriComponents uriComponents3 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", maxNoOfThreadsQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result2 = new RestTemplate().getForEntity(uriComponents3.toUri(), String.class);
			JSONArray jsonValues2 = getJSONArray(result2);
			JSONArray jsonValue2 = (JSONArray) jsonValues2.get(0);
			long maxAllThreads = jsonValue2.getLong(1);

			// Query RAMP UP
			String rampUpQuery = "select \"allThreads\",\"timeStamp\",\"value\"  from " + bundle.getObject("test1Table").toString() + " where allThreads="
					+ maxAllThreads + " group by value limit 1";
			UriComponents uriComponents4 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", rampUpQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result3 = new RestTemplate().getForEntity(uriComponents4.toUri(), String.class);
			JSONArray jsonValues3 = getJSONArray(result3);
			JSONArray jsonValue3 = (JSONArray) jsonValues3.get(0);
			long rampUpTimeStamp = jsonValue3.getLong(2);

			// RAMP DOWN QUERY
			String rampDownQuery = "select \"allThreads\",\"timeStamp\",\"value\"  from " + bundle.getObject("test1Table").toString() + " where allThreads="
					+ maxAllThreads + " group by value";
			UriComponents uriComponents5 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", rampDownQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result4 = new RestTemplate().getForEntity(uriComponents5.toUri(), String.class);
			JSONArray jsonValues4 = getJSONArray(result4);
			JSONArray jsonValue4 = (JSONArray) jsonValues4.get((jsonValues4.length() - 1));
			long rampDownTimeStamp = jsonValue4.getLong(2);

			userProfileDTO = new UserProfileDTO(maxAllThreads, startDateTimeStamp, endDateTimeStamp, rampUpTimeStamp, rampDownTimeStamp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void test2() {

		try {

			String query = "select \"allThreads\",\"timeStamp\",\"responseCode\",\"Latency\",\"bytes\",\"label\" from "
					+ bundle.getObject("test2Table").toString();
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParam("q", query).queryParam("db", database).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			influxSummaryListForTest2 = this.getInfluxSummary(results);

			String query2 = "select \"sampler_label\",\"aggregate_report_count\",\"average\",\"aggregate_report_90%_line\",\"aggregate_report_min\",\"aggregate_report_max\",\"aggregate_report_error%\" from "
					+ bundle.getObject("test2AggregateTable").toString();

			UriComponents uriComponents2 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", query2)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> avgResults = new RestTemplate().getForEntity(uriComponents2.toUri(), String.class);

			influxAggregateReportListForTest2 = this.getInfluxAggregateReport(avgResults);

			String dateQuery = "select min(timeStamp), max(timeStamp)  from " + bundle.getObject("test2Table").toString();

			UriComponents dateUriComponents = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", dateQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result = new RestTemplate().getForEntity(dateUriComponents.toUri(), String.class);
			JSONArray jsonValues = getJSONArray(result);
			JSONArray jsonValue = (JSONArray) jsonValues.get(0);
			long startDateTimeStamp = jsonValue.getLong(1);
			long endDateTimeStamp = jsonValue.getLong(2);

			// Query to get maxNo.ofThreads
			String maxNoOfThreadsQuery = "select max(allThreads) from " + bundle.getObject("test2Table").toString();

			UriComponents uriComponents3 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", maxNoOfThreadsQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result2 = new RestTemplate().getForEntity(uriComponents3.toUri(), String.class);
			JSONArray jsonValues2 = getJSONArray(result2);
			JSONArray jsonValue2 = (JSONArray) jsonValues2.get(0);
			long maxAllThreads = jsonValue2.getLong(1);

			// Query RAMP UP
			String rampUpQuery = "select \"allThreads\",\"timeStamp\",\"value\"  from " + bundle.getObject("test2Table").toString() + " where allThreads="
					+ maxAllThreads + " group by value limit 1";
			UriComponents uriComponents4 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", rampUpQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result3 = new RestTemplate().getForEntity(uriComponents4.toUri(), String.class);
			JSONArray jsonValues3 = getJSONArray(result3);
			JSONArray jsonValue3 = (JSONArray) jsonValues3.get(0);
			long rampUpTimeStamp = jsonValue3.getLong(2);

			// RAMP DOWN QUERY
			String rampDownQuery = "select \"allThreads\",\"timeStamp\",\"value\"  from " + bundle.getObject("test2Table").toString() + " where allThreads="
					+ maxAllThreads + " group by value";
			UriComponents uriComponents5 = UriComponentsBuilder.fromHttpUrl(bundle.getObject("influxurl").toString()).queryParam("q", rampDownQuery)
					.queryParam("db", bundle.getObject("influxDb").toString()).queryParam("u", bundle.getObject("influxadmin").toString())
					.queryParam("p", bundle.getObject("influxpassword").toString()).build();

			ResponseEntity<String> result4 = new RestTemplate().getForEntity(uriComponents5.toUri(), String.class);
			JSONArray jsonValues4 = getJSONArray(result4);
			JSONArray jsonValue4 = (JSONArray) jsonValues4.get((jsonValues4.length() - 1));
			long rampDownTimeStamp = jsonValue4.getLong(2);

			userProfileDTOForTest2 = new UserProfileDTO(maxAllThreads, startDateTimeStamp, endDateTimeStamp, rampUpTimeStamp, rampDownTimeStamp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public ArrayList<InfluxSummaryBean> getInfluxSummary(ResponseEntity<String> results) throws Exception {

		JSONArray jsonValues = getJSONArray(results);

		ArrayList<InfluxSummaryBean> influxSummaryList = new ArrayList<InfluxSummaryBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxSummaryBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				String allThreads = jsonValue.get(1).toString();
				String timeStamp = jsonValue.get(2).toString();
				String responseCode = jsonValue.get(3).toString();
				String latency = jsonValue.get(4).toString();
				String bytes = jsonValue.get(5).toString();
				String label = jsonValue.get(6).toString();
				influxSummary = new InfluxSummaryBean();
				influxSummary.setAllThreads(allThreads);
				influxSummary.setResponseCode(responseCode);
				influxSummary.setTimeStamp(timeStamp);
				influxSummary.setLatency(latency);
				influxSummary.setBytes(bytes);
				influxSummary.setLabel(label);
			}
			influxSummaryList.add(influxSummary);
		}
		return influxSummaryList;
	}

	
	public ArrayList<InfluxSummaryBean> getInfluxSummaryForActiveThreads(ResponseEntity<String> results) throws Exception {

		JSONArray jsonValues = getJSONArray(results);

		ArrayList<InfluxSummaryBean> influxSummaryListTemp = new ArrayList<InfluxSummaryBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxSummaryBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				String timeStamp = jsonValue.get(0).toString();
				String allThreads = jsonValue.get(1).toString();
				influxSummary = new InfluxSummaryBean();
				influxSummary.setTimeStamp(timeStamp);
				influxSummary.setAllThreads(allThreads);
			}
			influxSummaryListTemp.add(influxSummary);
		}
		return influxSummaryListTemp;
	}
	
	public String getInfluxSummaryForActiveThreadsGraph(ResponseEntity<String> results) throws Exception {

		JSONArray jsonValues = getJSONArray(results);
		
		System.out.println("jsonValues active threads:"+jsonValues);
		ArrayList<InfluxSummaryBean> influxSummaryListTemp = new ArrayList<InfluxSummaryBean>();
/*
		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxSummaryBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				String timeStamp = jsonValue.get(0).toString();
				String allThreads = jsonValue.get(1).toString();
				influxSummary = new InfluxSummaryBean();
				influxSummary.setTimeStamp(timeStamp);
				influxSummary.setAllThreads(allThreads);
			}
			influxSummaryListTemp.add(influxSummary);
		}*/
		return jsonValues.toString();
	}
	
	//Edited by Sankar
	public String getInfluxSummaryForHitsForSeconds(ResponseEntity<String> results) throws Exception {
		JSONArray jsonValues = getJSONArray(results);
		System.out.println("jsonValues active threads:"+jsonValues);
		ArrayList<InfluxSummaryBean> influxSummaryListTemp = new ArrayList<InfluxSummaryBean>();
		return jsonValues.toString();
	}
	
	public ArrayList<InfluxSummaryBean> getInfluxSummaryForResponseCodes(ResponseEntity<String> results) throws Exception {

		JSONArray jsonValues = getJSONArray(results);
		ArrayList<InfluxSummaryBean> influxSummaryListTemp = new ArrayList<InfluxSummaryBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxSummaryBean influxSummary = new InfluxSummaryBean();
			influxSummary.setTimeStamp(jsonValue.get(0).toString());
			influxSummary.setResponseCode(jsonValue.get(1).toString());
			influxSummaryListTemp.add(influxSummary);
		}
		System.out.println("Size of influxSummaryListTemp:"+influxSummaryListTemp.size());
		return influxSummaryListTemp;
	}
	
	public ArrayList<InfluxSummaryBean> getInfluxSummaryForHitsPerSecond(ResponseEntity<String> results) throws Exception {

		JSONArray jsonValues = getJSONArray(results);
		System.out.println("jsonValues:"+jsonValues);
		ArrayList<InfluxSummaryBean> influxSummaryListTemp = new ArrayList<InfluxSummaryBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxSummaryBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				String timeStamp = jsonValue.get(0).toString();
				String rate = jsonValue.get(1).toString();
				influxSummary = new InfluxSummaryBean();
				influxSummary.setTimeStamp(timeStamp);
				influxSummary.setBytes(rate);
			}
			influxSummaryListTemp.add(influxSummary);
		}
		return influxSummaryListTemp;
	}
	
	public ArrayList<InfluxSummaryBean> getInfluxSummaryForResponseTimes(ResponseEntity<String> results) throws Exception {
		JSONObject obj = new JSONObject(results.getBody());
		JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
		obj = (JSONObject) obj1.get(0);
		
		ArrayList<InfluxSummaryBean> influxSummaryListLiveTemp = new ArrayList<InfluxSummaryBean>();

		JSONArray array = obj.getJSONArray("series");
		for (int i = 0; i < array.length(); i++) {
			obj = (JSONObject) array.get(i);
			JSONArray array1 = obj.getJSONArray("values");
			for(int j=0; j<array1.length(); j++){
				InfluxSummaryBean influxSummary = new InfluxSummaryBean();
				influxSummary.setTimeStamp(((JSONArray) array1.get(j)).get(0).toString());
				influxSummary.setLatency(((JSONArray) array1.get(j)).get(1).toString());
				String transactionName = obj.getJSONObject("tags").get("label").toString();
				influxSummary.setLabel(transactionName);
				influxSummaryListLiveTemp.add(influxSummary);
			}
		}
		return influxSummaryListLiveTemp;
	}
	
	
	public String getInfluxSummaryForResponseTimesGraph(ResponseEntity<String> results) throws Exception {
		JSONObject response=new JSONObject();
		JSONObject obj = new JSONObject(results.getBody());
		JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
		obj = (JSONObject) obj1.get(0);
		ArrayList<InfluxSummaryBean> influxSummaryListLiveTemp = new ArrayList<InfluxSummaryBean>();
		JSONArray array = obj.getJSONArray("series");
		System.out.println("array:"+array.length());
		System.out.println("array:");
		
		
		//System.out.println("******"+arr);
		
		for(int i=0;i<array.length();i++)
		{
			JSONObject arr=array.getJSONObject(i);	
			JSONArray values=arr.getJSONArray("values");
			JSONObject transaction=arr.getJSONObject("tags");
			response.put(transaction.getString("label"), values);
		}
		
	/*	for (int i = 0; i < array.length(); i++) {
			obj = (JSONObject) array.get(i);
			JSONArray array1 = obj.getJSONArray("values");
			for(int j=0; j<array1.length(); j++){
				InfluxSummaryBean influxSummary = new InfluxSummaryBean();
				influxSummary.setTimeStamp(((JSONArray) array1.get(j)).get(0).toString());
				influxSummary.setLatency(((JSONArray) array1.get(j)).get(1).toString());
				String transactionName = obj.getJSONObject("tags").get("label").toString();
				influxSummary.setLabel(transactionName);
				influxSummaryListLiveTemp.add(influxSummary);
			}
		}*/
		return response.toString();
	}
	
	
	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReport(ResponseEntity<String> avgResults) throws Exception {

		JSONArray jsonValues = getJSONArray(avgResults);
		List<JSONArray> jsonList = new ArrayList<JSONArray>();
		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			jsonList.add(jsonValue);
		}
		ArrayList<InfluxAggregateReportBean> influxAggregateReportBeanList = new ArrayList<InfluxAggregateReportBean>();
		InfluxAggregateReportBean influxAggregateReportBean = null;
		for (JSONArray jsonValue : jsonList) {

			influxAggregateReportBean = new InfluxAggregateReportBean();
			influxAggregateReportBean.setCount(jsonValue.get(2).toString());
			influxAggregateReportBean.setAvg(jsonValue.get(3).toString());
			influxAggregateReportBean.setAggregate_report_90_line(jsonValue.get(4).toString());
			influxAggregateReportBean.setAggregate_report_min(jsonValue.get(5).toString());
			influxAggregateReportBean.setAggregate_report_max(jsonValue.get(6).toString());
			influxAggregateReportBean.setAggregate_report_error(jsonValue.get(7).toString());
			influxAggregateReportBean.setTransactionName(jsonValue.get(1).toString());
			influxAggregateReportBeanList.add(influxAggregateReportBean);
		}
		return influxAggregateReportBeanList;
	}
	
	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReportLive(ResponseEntity<String> avgResults) throws Exception {

		JSONObject obj = new JSONObject(avgResults.getBody());
		JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
		obj = (JSONObject) obj1.get(0);

		ArrayList<InfluxAggregateReportBean> influxAggregateReportBeanList = new ArrayList<InfluxAggregateReportBean>();

		JSONArray array = obj.getJSONArray("series");
		for (int i = 0; i < array.length(); i++) {
			InfluxAggregateReportBean influxAggregateReportBean = new InfluxAggregateReportBean();
			obj = (JSONObject) array.get(i);
			JSONArray array1 = obj.getJSONArray("values").getJSONArray(0);
			influxAggregateReportBean.setCount(array1.get(4).toString());
			influxAggregateReportBean.setAvg(array1.get(3).toString());
			influxAggregateReportBean.setAggregate_report_min(array1.get(1).toString());
			influxAggregateReportBean.setAggregate_report_max(array1.get(2).toString());
			String transactionName = obj.getJSONObject("tags").get("label").toString();
			influxAggregateReportBean.setTransactionName(transactionName);
			influxAggregateReportBeanList.add(influxAggregateReportBean);
		}
		return influxAggregateReportBeanList;
	}

	private JSONArray getJSONArray(ResponseEntity<String> result) throws JSONException {

		JSONObject responseBody = new JSONObject(result.getBody());
		JSONArray results = new JSONArray(responseBody.getJSONArray("results").toString());
		JSONObject resultJSONObject = (JSONObject) results.get(0);
		JSONArray jsonValues = new JSONArray();
		if(resultJSONObject.length() > 0)	{
			JSONObject resultSeries = (JSONObject) resultJSONObject.getJSONArray("series").get(0);
			jsonValues = resultSeries.getJSONArray("values");
		}
		// JSONArray columns = resultSeries.getJSONArray("columns");

		return jsonValues;
	}

	public static String getTimeString(long tmp) {
		java.util.Date date = toDate(new java.sql.Timestamp(tmp));
		Format format = new SimpleDateFormat("HH:mm:ss");
		String strFormat = format.format(date);
		// timeStamp to Date conversion
		return (strFormat);
	}

	public static String getMilliSecToTimeFormat(long duration) {

		long time = duration / 1000;
		String seconds = Integer.toString((int) (time % 60));
		String minutes = Integer.toString((int) ((time % 3600) / 60));
		String hours = Integer.toString((int) (time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}

		return (hours + ":" + minutes + ":" + seconds);
	}

	public long getTestDuration(long startTimesStamp, long endTimesStamp) {

		long seconds = (endTimesStamp - startTimesStamp);
		return seconds;

	}

	public boolean onlyContainsNumbers(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static java.util.Date toDate(java.sql.Timestamp timestamp) {
		long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
		return new java.util.Date(milliseconds);
	}

	public UserProfileDTO getUserProfileDTO() {
		return userProfileDTO;
	}

	public void setUserProfileDTO(UserProfileDTO userProfileDTO) {
		this.userProfileDTO = userProfileDTO;
	}

	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReportList() {
		return influxAggregateReportList;
	}
	
	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReportListForLiveKPI(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxAggregateReportBean> influxAggregateReportBeanList = new ArrayList<InfluxAggregateReportBean>();
		try {
			String queryForErrPct = "select mean(\"errpct\") from "
				+ bundle.getObject("delta").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																+ "ms and suite='" +testSuite + "' and project='" +projectName +"'";
			UriComponents uriComponentsForErrPct = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", queryForErrPct).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> avgResultsForErrPct = new RestTemplate().getForEntity(uriComponentsForErrPct.toUri(), String.class);
			
			String queryForCount = "select sum(\"count\") from "
					+ bundle.getObject("total").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"'";
			UriComponents uriComponentsForCount = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", queryForCount).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> avgResultsForCount= new RestTemplate().getForEntity(uriComponentsForCount.toUri(), String.class);
			
			JSONArray jsonValuesErrPct = getJSONArray(avgResultsForErrPct);
			JSONArray jsonValuesCount= getJSONArray(avgResultsForCount);
			
			InfluxAggregateReportBean influxAggregateReportBean = new InfluxAggregateReportBean();
			influxAggregateReportBean.setCount(((JSONArray) jsonValuesCount.get(0)).get(1).toString());
			influxAggregateReportBean.setAggregate_report_error(((JSONArray) jsonValuesErrPct.get(0)).get(1).toString());
			influxAggregateReportBeanList.add(influxAggregateReportBean);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return influxAggregateReportBeanList;
	}

	public void setInfluxAggregateReportList(ArrayList<InfluxAggregateReportBean> influxAggregateReportList) {
		this.influxAggregateReportList = influxAggregateReportList;
	}

	public ArrayList<InfluxSummaryBean> getInfluxSummaryList() {
		return influxSummaryList;
	}


	//Refactoring with lennox_projectdetails table...
	public ArrayList<InfluxSummaryBean> getInfluxSummaryList(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
		try {
			String query = "select ath from "
					+ bundle.getObject("total").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"'";
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			System.out.println("query:"+uriComponents.toString());
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			
			influxSummaryListRefactored = this.getInfluxSummaryForActiveThreads(results);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return influxSummaryListRefactored;
	}
	
	
	
	public String getInfluxSummaryListGraph(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
		String response="";
		try {
			String query = "select ath from "
					+ bundle.getObject("total").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"'";
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			System.out.println("query:"+uriComponents.toString());
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			
			 response = this.getInfluxSummaryForActiveThreadsGraph(results);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	
	//Edited By Sankar
	public String getInfluxSummaryListGraphForHitsPerSeconds(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
		String response="";
		try {
			String query = "select count(\"responsecode\") from "
					+ bundle.getObject("samples").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"'group by time(1s)";
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			System.out.println("query:"+uriComponents.toString());
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			response = this.getInfluxSummaryForHitsForSeconds(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	//Refactoring with lennox_projectdetails table for response code info...
	public ArrayList<InfluxSummaryBean> getInfluxSummaryListForResponseCodeInfo(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
		try {
			String query = "select responsecode from "
					+ bundle.getObject("samples").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"'";
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			System.out.println("results:"+results);
			influxSummaryListRefactored = this.getInfluxSummaryForResponseCodes(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return influxSummaryListRefactored;
	}
	
	//Refactoring with lennox_projectdetails table for hits per seconds info...
	public ArrayList<InfluxSummaryBean> getInfluxSummaryListForResponseTimes(String testStartTime, String testEndTime, String testSuite, String projectName) {
		ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
		try {
			String query = "select \"duration\" from "
					+ bundle.getObject("samples").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																	+ "ms and suite='" +testSuite + "' and project='" +projectName +"' group by label";
			System.out.println(" The Response Time Live Query @@@@@ " +query);
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
					.queryParam("p", password).build();
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
			
			influxSummaryListRefactored = this.getInfluxSummaryForResponseTimes(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return influxSummaryListRefactored;
	}
	
	
	//Refactoring with lennox_projectdetails table for hits per seconds info...
		public String getInfluxSummaryListForResponseTimesGraph(String testStartTime, String testEndTime, String testSuite, String projectName) {
			String response="";
			ArrayList<InfluxSummaryBean> influxSummaryListRefactored = new ArrayList<InfluxSummaryBean>();
			try {
				String query = "select \"duration\" from "
						+ bundle.getObject("samples").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
																		+ "ms and suite='" +testSuite + "' and project='" +projectName+"' group by label";
				System.out.println(" The Response Time Live Query @@@@@ " +query);
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query).queryParam("db", database1).queryParam("u", userName)
						.queryParam("p", password).build();
				ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
				
				//influxSummaryListRefactored = this.getInfluxSummaryForResponseTimesGraph(results);
				 response = this.getInfluxSummaryForResponseTimesGraph(results);
				 System.out.println("the response is..............."+results);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}
	
	
	public void setInfluxSummaryList(ArrayList<InfluxSummaryBean> influxSummaryList) {
		this.influxSummaryList = influxSummaryList;
	}


	public UserProfileDTO getUserProfileDTOForTest2() {
		return userProfileDTOForTest2;
	}


	public void setUserProfileDTOForTest2(UserProfileDTO userProfileDTOForTest2) {
		this.userProfileDTOForTest2 = userProfileDTOForTest2;
	}


	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReportListForTest2() {
		return influxAggregateReportListForTest2;
	}


	public void setInfluxAggregateReportListForTest2(ArrayList<InfluxAggregateReportBean> influxAggregateReportListForTest2) {
		this.influxAggregateReportListForTest2 = influxAggregateReportListForTest2;
	}


	public ArrayList<InfluxSummaryBean> getInfluxSummaryListForTest2() {
		return influxSummaryListForTest2;
	}


	public void setInfluxSummaryListForTest2(ArrayList<InfluxSummaryBean> influxSummaryListForTest2) {
		this.influxSummaryListForTest2 = influxSummaryListForTest2;
	}

	// Refactoring with lennox_projectdetails table for aggregate reports live info...
	public ArrayList<InfluxAggregateReportBean> getInfluxAggregateReportListLive(String testStartTime, String testEndTime, String testSuite,String projectName) {
		ArrayList<InfluxAggregateReportBean> influxAggregateReportList = new ArrayList<InfluxAggregateReportBean>();
		try {
			String query2 = "select min(\"duration\"),max(\"duration\"),mean(\"duration\"), count(\"duration\") from "
					+ bundle.getObject("samples").toString() + " where time >= " + testStartTime
					+ "ms and time <= " 	+ testEndTime  + "ms and suite='" + testSuite
					+ "' and project='" + projectName + "' group by \"label\"";
			;
			UriComponents uriComponents2 = UriComponentsBuilder
					.fromHttpUrl(bundle.getObject("influxurl1").toString())
					.queryParam("q", query2).queryParam("db", database1)
					.queryParam("u", userName).queryParam("p", password)
					.build();
			System.out.println("Aggrigate report :"+uriComponents2.toUri());
			ResponseEntity<String> transactions = new RestTemplate().getForEntity(uriComponents2.toUri(), String.class);
			influxAggregateReportList = this.getInfluxAggregateReportLive(transactions);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return influxAggregateReportList;
	}
}
