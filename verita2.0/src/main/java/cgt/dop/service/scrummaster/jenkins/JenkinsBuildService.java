package cgt.dop.service.scrummaster.jenkins;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Repository;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cgt.dop.bean.BuildDetailsBean;
import cgt.dop.bean.BuildInfoBean;

@Repository
public class JenkinsBuildService {

	JSONArray builddetails;
	JSONArray deploydetails;
	JSONObject seleniumdetails;
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public JenkinsBuildService() {
	}

	public static void main(String[] args) throws JSONException {
		try {

			JenkinsBuildService ser = new JenkinsBuildService();
			//String res=ser.getWeekNumberByDate("1/07/2016");
			ser.test();
			//System.out.println(res);

			// JenkinsBuildService ser = new JenkinsBuildService();
			// JSONObject data=ser.getTotoalBuildDetails(5);
			// System.out.println(data);
			// ser.getBuildDetails();
			/*
			 * System.out.println(ser.getLastNDaysBuildDetails(2));
			 * System.out.println(ser.getnumberofBuildsInLastNDays(7));
			 * System.out.println(ser.getLastNDaysBuildStatus(2));
			 * System.out.println(ser.getLastNDaysBuildStatusPerDay(1));
			 * System.out.println(ser.getLastNDaysBuildCountPerDay(1));
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JSONArray getBuildDetailsForJob(String URL, String jobname) {
		JSONArray jsonArray = null;
		try {
			jobname = jobname.replaceAll("\\s+", "%20");
			Client client = Client.create();
			WebResource webResource = client
					.resource(URL + "/job/" + jobname + "/api/json?tree=allBuilds[duration,number,timestamp,result]");
			ClientResponse response = webResource.header("Authorization", "Basic ").type("application/json")
					.accept("application/json").get(ClientResponse.class);
			jsonArray = new JSONObject(response.getEntity(String.class)).getJSONArray("allBuilds");
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonArray;
		}
	}

	public JSONObject getTotoalBuildDetails(int Days) throws Exception {
		Map<String, List<Integer>> totalMap = getLastNDaysBuildStatusPerDay(Days);
		System.out.println(totalMap);
		JSONObject totalJson = new JSONObject();
		JSONArray array = new JSONArray();
		int j = 0;
		for (int i = 1; i <= 2; i++) {

			for (Entry<String, List<Integer>> entry : totalMap.entrySet()) {
				JSONObject chiObject1 = new JSONObject();
				List<Integer> list = entry.getValue();
				chiObject1.put("build_status", i);
				chiObject1.put("build_date", entry.getKey());
				chiObject1.put("build_count", list.get(j));
				chiObject1.put("filter_by", getWeekNumberByDate(entry.getKey()));
				

				totalJson.append("TotalBuildDetails", chiObject1);

			}
			j++;
		}

		return totalJson;

	}

	public String getWeekNumberByDate(String input) throws Exception {

		String format = "dd/MM/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(input);

		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date);
		int dateWeek = calendar1.get(Calendar.WEEK_OF_YEAR);
		String weekNo = null;

		switch (currentWeek - dateWeek) {
		case 0:
			weekNo = "week1";
			break;
		case 1:
			weekNo = "week2";
			break;
		case 2:
			weekNo = "week3";
			break;
		case 3:
			weekNo = "week4";
			break;

		case 4:
			weekNo = "week5";
			break;
		default:
			weekNo = "No Data";
			break;
		}

		return weekNo;
	}

	public List<BuildInfoBean> getLastNDaysBuildDetails(int days) throws IOException {
		List<BuildInfoBean> buildbean = new ArrayList<BuildInfoBean>();
		builddetails = getDeployDetails();
		try {
			for (int i = 0; i < builddetails.length(); i++) {
				if (inTimePeriod(getPastDate(days),
						Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString()))) {
					BuildInfoBean bean = new BuildInfoBean();
					bean.setNumber(builddetails.getJSONObject(i).get("number").toString());
					bean.setResult(builddetails.getJSONObject(i).get("result").toString());
					bean.setDuration(builddetails.getJSONObject(i).get("duration").toString());
					bean.setExecutedtime(
							getTimeAgo(Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString())));
					bean.setTimestamp(builddetails.getJSONObject(i).get("timestamp").toString());
					buildbean.add(bean);
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buildbean;
	}

	private Map<String, Integer> getnumberofBuilds() {
		Map<String, Integer> builds = new HashMap<String, Integer>();
		builds.put("today", getnumberofBuildsInLastNDays(1));
		builds.put("last7Days", getnumberofBuildsInLastNDays(7));
		builds.put("last14Days", getnumberofBuildsInLastNDays(14));
		return builds;
	}

	private int getnumberofBuildsInLastNDays(int days) {
		int count = 0;
		try {
			String pastdate = getPastDate(days);
			System.out.println(pastdate);
			for (int i = 0; i < builddetails.length(); i++) {
				if (inTimePeriod(pastdate, Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString()))) {
					count++;
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	private Map<String, Integer> getLastNDaysBuildStatus(int days) throws IOException {
		builddetails = getBuildDetails();
		Map<String, Integer> lastndaysbuild = new LinkedHashMap<String, Integer>();
		int successCount = 0, failureCount = 0;
		try {
			String pastdate = getPastDate(days);
			for (int i = 0; i < builddetails.length(); i++) {
				if (inTimePeriod(pastdate, Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString()))) {
					if (builddetails.getJSONObject(i).get("result").toString().equalsIgnoreCase("SUCCESS"))
						successCount++;
					else if (builddetails.getJSONObject(i).get("result").toString().equalsIgnoreCase("FAILURE"))
						failureCount++;
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lastndaysbuild.put("SUCCESS", successCount);
		lastndaysbuild.put("FAILURE", failureCount);
		return lastndaysbuild;
	}

	private Map<String, List<Integer>> getLastNDaysBuildStatusPerDay(int days) throws IOException {
		builddetails = getBuildDetails();
		Map<String, List<Integer>> buildsstatus = new LinkedHashMap<String, List<Integer>>();
		for (int i = 1; i <= days; i++) {
			String date = getPastDate(i);
			buildsstatus.put(date, getBuildsStatusPerDay(date));
		}
		return buildsstatus;
	}

	public String getLastNDaysBuildCountPerDay(int days) throws Exception {
		// Map<String, Integer> buildscount = new LinkedHashMap<String,
		// Integer>();
		JSONObject jsonObject = new JSONObject();

		for (int i = 1; i <= days; i++) {
			JSONObject childObject = new JSONObject();
			String date = getPastDate(i);
			childObject.put("date", date);
			childObject.put("build no", getnumberofBuildsInaDay(date));
			jsonObject.append("buildDetails ", childObject);

		}
		return jsonObject.toString();
	}

	private List<Integer> getBuildsStatusPerDay(String date) {
		List<Integer> daybuildstatus = new ArrayList<Integer>();
		int successCount = 0, failureCount = 0;
		try {
			for (int i = 0; i < builddetails.length(); i++) {
				long timestamp = Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString());
				if (inTimePeriod(date, timestamp)) {
					if (date.equals(dateFormat.format(timestamp))) {
						if (builddetails.getJSONObject(i).get("result").toString().equalsIgnoreCase("SUCCESS"))
							successCount++;
						else if (builddetails.getJSONObject(i).get("result").toString().equalsIgnoreCase("FAILURE"))
							failureCount++;
					}
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		daybuildstatus.add(successCount);
		daybuildstatus.add(failureCount);
		return daybuildstatus;
	}

	private int getnumberofBuildsInaDay(String date) throws IOException {
		builddetails = getBuildDetails();
		int count = 0;
		try {
			for (int i = 0; i < builddetails.length(); i++) {
				long timestamp = Long.parseLong(builddetails.getJSONObject(i).get("timestamp").toString());
				if (inTimePeriod(date, timestamp)) {
					if (date.equals(dateFormat.format(timestamp))) {
						count++;
					}
				} else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	private String getPastDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days + 1);
		return dateFormat.format(cal.getTime());
	}

	private boolean inTimePeriod(String pastdate, long timestamp) throws ParseException {
		if (dateFormat.parse(dateFormat.format(timestamp)).compareTo(dateFormat.parse(pastdate)) >= 0)
			return true;
		else
			return false;
	}

	private String getTimeAgo(long timestamp) {
		long time = (new Date().getTime() - timestamp) / 1000;

		String timeago = "";
		if (time < 1) {
			timeago = "0 secs ago";
		} else if (time < 60) {
			if (time == 1)
				timeago = "1 sec ago";
			else
				timeago = time + " secs ago";
		} else if ((time / 60) < 60) {
			time = time / 60;
			if (time == 1)
				timeago = "1 min ago";
			else
				timeago = time + " mins ago";
		} else if ((time / 3600) < 60) {
			time = time / 3600;
			if (time == 1)
				timeago = "1 hour ago";
			else
				timeago = time + " hours ago";
		} else if ((time / 86400) < 24) {
			time = time / 86400;
			if (time == 1)
				timeago = "1 day ago";
			else
				timeago = time + " days ago";

		} else if ((time / 2592000) < 30) {
			time = time / 2592000;
			if (time == 1)
				timeago = "1 month ago";
			else
				timeago = time + " months ago";

		} else {
			time = time / 31536000;
			if (time == 1)
				timeago = "1 year ago";
			else
				timeago = time + " years ago";

		}
		return timeago;
	}
	
	private JSONObject getSeleniumDetailsForJob(String URL, String jobname){
		System.out.println("selenium URL:  "+URL + "/job/" + jobname + "/lastBuild/consoleText");
		JSONObject jsonObject=new JSONObject();
		try {
			jobname = jobname.replaceAll("\\s+", "%20");
			
			String response= getUrlContents(URL + "job/" + jobname + "/lastBuild/consoleText");
			
			String startTime=response.substring(response.indexOf("Starting Time : ")+16, response.indexOf("Starting Time : ")+44);
			String totalTestsRun=response.substring(response.indexOf("Total tests run: ")+17, response.indexOf("Total tests run: ")+18);
			System.out.println("Total tests run: "+totalTestsRun+", Failures: ");
			String reg1="Total tests run: "+totalTestsRun+", Failures: ";
			String failureTests=response.substring(response.indexOf(reg1)+30, response.indexOf(reg1)+31);
			String reg2="Total tests run: "+totalTestsRun+", Failures: "+failureTests+", Skips: ";
			System.out.println(reg2);
			String skipTests=response.substring(response.indexOf(reg2)+40, response.indexOf(reg2)+41);
			String testDuration=response.substring(response.indexOf("Duration : ")+11, response.indexOf("Duration : ")+19);
			String buildNo=response.substring(response.indexOf("Latest_BuildNO : ")+17, response.indexOf("Latest_BuildNO : ")+19);
			String browserName=response.substring(response.indexOf("Browser Type : ")+15, response.indexOf("Browser Type : ")+30);
			String os=response.substring(response.indexOf("Operating System : ")+19, response.indexOf("Operating System : ")+28);

			
			
			int succesFullTests=Integer.parseInt(totalTestsRun)-Integer.parseInt(failureTests);
			jsonObject.put("startTime", startTime);
			jsonObject.put("testDuration", testDuration);
			jsonObject.put("buildNo", buildNo);
			jsonObject.put("browserName", browserName);
			jsonObject.put("os", os);
			
			jsonObject.put("totalTestsRun", totalTestsRun);
			jsonObject.put("succesFullTests", String.valueOf(succesFullTests));
			jsonObject.put("failureTests", failureTests);
			jsonObject.put("skipTests", skipTests);
			
			
			/*System.out.println(response);*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonObject;
			
	}
	
	private void test(){
		String response= getUrlContents("http://172.16.3.42:8080/jenkins//job/Demo_Selenium%20Unit%20Test/lastBuild/consoleText");
		String startTime=response.substring(response.indexOf("Starting Time : ")+16, response.indexOf("Starting Time : ")+44);
		String totalTestsRun=response.substring(response.indexOf("Total tests run: ")+17, response.indexOf("Total tests run: ")+18);
		System.out.println("Total tests run: "+totalTestsRun+", Failures: ");
		String reg1="Total tests run: "+totalTestsRun+", Failures: ";
		String failureTests=response.substring(response.indexOf(reg1)+30, response.indexOf(reg1)+31);
		String reg2="Total tests run: "+totalTestsRun+", Failures: "+failureTests+", Skips: ";
		System.out.println(reg2);
		String skipTests=response.substring(response.indexOf(reg2)+40, response.indexOf(reg2)+41);
		
		String testDuration=response.substring(response.indexOf("Duration : ")+11, response.indexOf("Duration : ")+19);
		
		String buildNo=response.substring(response.indexOf("Latest_BuildNO : ")+17, response.indexOf("Latest_BuildNO : ")+19);
		String browserName=response.substring(response.indexOf("Browser Type : ")+15, response.indexOf("Browser Type : ")+30);
		String os=response.substring(response.indexOf("Operating System : ")+19, response.indexOf("Operating System : ")+28);


		
		System.out.println(startTime);
		System.out.println(totalTestsRun);
		System.out.println(failureTests);
		System.out.println(skipTests);
		System.out.println(testDuration);
		System.out.println(buildNo);
		System.out.println(browserName);
		System.out.println(os);
		/*Duration : 00:02:07*/
		/*Total tests run: 9, Failures: 9, Skips: 0*/
		
		//System.out.println(totalTestsRun);
	}
	public JSONObject getSeleniumTestDetails() throws IOException {
		BuildDetailsBean bean = new BuildDetailsBean();
		String jobname = getPropValues("SeleniumJob");//"Build Project";
		jobname = jobname.replaceAll("\\s+", "%20");
		String url = getPropValues("jenkinsurl");//"http://172.16.3.42:8080/jenkins/";
		seleniumdetails = getSeleniumDetailsForJob(url, jobname);
		
		return seleniumdetails;
	}

	
	public JSONArray getBuildDetails() throws IOException {
		BuildDetailsBean bean = new BuildDetailsBean();
		String jobname = getPropValues("buildjobdetails");//"Build Project";
		jobname = jobname.replaceAll("\\s+", "%20");
		String url = getPropValues("jenkinsurl");//"http://172.16.3.42:8080/jenkins/";
		builddetails = getBuildDetailsForJob(url, jobname);

		return builddetails;
	}

	public JSONArray getDeployDetails() throws IOException {
		BuildDetailsBean bean = new BuildDetailsBean();
		String jobname =getPropValues("deployjobdetails");// "Deploy Project";
		jobname = jobname.replaceAll("\\s+", "%20");
		String url = getPropValues("jenkinsurl");//"http://172.16.3.42:8080/jenkins/";
		deploydetails = getBuildDetailsForJob(url, jobname);

		return deploydetails;
	}
	public String getPropValues(String values) throws IOException {
		String result = "";
		InputStream inputStream = null;
		try {
			
			Properties prop = new Properties();
			String propFileName = "jenkins.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			/*String user = prop.getProperty("user");
			String company1 = prop.getProperty("company1");
			String company2 = prop.getProperty("company2");
			String company3 = prop.getProperty("company3");*/
 
			result =prop.getProperty(values);// "Company List = " + company1 + ", " + company2 + ", " + company3;
			System.out.println("Fetching value:"+result);
			//System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
	
	
	private static String getUrlContents(String Url)
	  {
	    StringBuilder content = new StringBuilder();

	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	      // create a url object
	      URL url = new URL(Url);;

	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();

	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

	      String line;

	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return content.toString();
	  }
	
	
}
