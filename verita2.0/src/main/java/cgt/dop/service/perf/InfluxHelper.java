package cgt.dop.service.perf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.export.Exporter;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cgt.dop.admin.HomeController;
import cgt.dop.bean.ActiveThreadsBean;
import cgt.dop.bean.AggregateReportBean;
import cgt.dop.bean.HitsBean;
import cgt.dop.bean.InfluxAggregateReportBean;
import cgt.dop.bean.InfluxSummaryBean;
import cgt.dop.bean.InfluxUserProfileBean;
import cgt.dop.bean.ObservationsBean;
import cgt.dop.bean.ResponseCodeBean;
import cgt.dop.bean.ResponseTime;
import cgt.dop.bean.ResponseTimeBean;
import cgt.dop.bean.ResponseTimeDTO;
import cgt.dop.dao.TestUpdateDao;
import cgt.dop.dao.UserProfileDao;
import cgt.dop.model.LennoxUserData;
import cgt.dop.model.LennoxUserProfile;
import cgt.dop.model.TestUpdateModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;




@Repository
public class InfluxHelper extends InfluxUtil {

	@Autowired
	private UserProfileDao userProfileDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired 
	private TestUpdateDao testUpdateDao;
	
	@Autowired
	private HomeController homeController;
	
	
	public LennoxUserProfile getUserProfileInfo() throws Exception {

		LennoxUserProfile lennoxUserProfileBean = null;
		LennoxUserProfile lennoxUserProfileDbBean = null;

			
		
		try {

			// Calculation of RAMPUP,RAMPDOWN,STEADY PERIOD

			long rampUpDuration = (getUserProfileDTO().getRampUpTimeStamp() - getUserProfileDTO().getStartDateTimeStamp());
			long rampDownDuration = (getUserProfileDTO().getEndDateTimeStamp() - getUserProfileDTO().getRampDownTimeStamp());
			long steadyPeriod = (getUserProfileDTO().getRampDownTimeStamp() - getUserProfileDTO().getRampUpTimeStamp());
			long duration = getUserProfileDTO().getEndDateTimeStamp() - getUserProfileDTO().getStartDateTimeStamp();

			lennoxUserProfileBean = new LennoxUserProfile();
			lennoxUserProfileBean.setStartTime(getTimeString(getUserProfileDTO().getStartDateTimeStamp()));
			lennoxUserProfileBean.setEndTime(getTimeString(getUserProfileDTO().getEndDateTimeStamp()));
			lennoxUserProfileBean.setRampUpDuration((getMilliSecToTimeFormat(rampUpDuration)));
			lennoxUserProfileBean.setRampDownDuration(getMilliSecToTimeFormat(rampDownDuration));
			lennoxUserProfileBean.setSteadyPeriod(getMilliSecToTimeFormat(steadyPeriod));
			lennoxUserProfileBean.setDuration(getMilliSecToTimeFormat(duration));

			lennoxUserProfileBean.setDbStartTime(getUserProfileDTO().getStartDateTimeStamp());
			lennoxUserProfileBean.setDbEndTime(getUserProfileDTO().getEndDateTimeStamp());
			lennoxUserProfileBean.setTotalUsers(getUserProfileDTO().getMaxAllThreads());

			// setting db values to bean
			lennoxUserProfileDbBean = userProfileDao.getUserProfileData(getUserProfileDTO().getStartDateTimeStamp(), getUserProfileDTO().getEndDateTimeStamp());
			if (lennoxUserProfileDbBean != null) {

				lennoxUserProfileBean.setTestNo(lennoxUserProfileDbBean.getTestNo());

				if (lennoxUserProfileDbBean.getTestName() != null)
					lennoxUserProfileBean.setTestName(lennoxUserProfileDbBean.getTestName());
				else
					lennoxUserProfileBean.setTestName("");

				/*
				 * if(lennoxUserProfileDbBean.getTotalUsers()!= 0)
				 * lennoxUserProfileBean
				 * .setTotalUsers(lennoxUserProfileDbBean.getTotalUsers()); else
				 * lennoxUserProfileBean.setTotalUsers(0);
				 */

				if (lennoxUserProfileDbBean.getScenarios() != null)
					lennoxUserProfileBean.setScenarios(lennoxUserProfileDbBean.getScenarios());
				else
					lennoxUserProfileBean.setScenarios("");
				if (lennoxUserProfileDbBean.getTestEnvUrl() != null)
					lennoxUserProfileBean.setTestEnvUrl(lennoxUserProfileDbBean.getTestEnvUrl());
				else
					lennoxUserProfileBean.setTestEnvUrl("");

				if (lennoxUserProfileDbBean.getTestDate() != null)
					lennoxUserProfileBean.setTestDate(lennoxUserProfileDbBean.getTestDate());
				else
					lennoxUserProfileBean.setTestDate("");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lennoxUserProfileBean;
	}
	
	//Edited by Sankar
	
	public InfluxUserProfileBean getUserProfileInfo(String testId) throws Exception {
		
			List<LennoxUserData> list = new ArrayList<LennoxUserData>();
			System.out.println("*********testId:"+testId);
			Session session = sessionFactory.openSession();
			String SQL_Query = "SELECT * FROM lennox_projectdetails where testname=?";
			Query query = session.createSQLQuery(SQL_Query);
			query.setParameter(0, testId);
			((SQLQuery) query).addEntity(LennoxUserData.class);
			list = query.list();
			
			String datetimezone=list.get(0).getTestdatetimezone();	
			String scenarios=list.get(0).getTestscenarios();
			String qaenvurl=list.get(0).getTestenvurl();
			
			//Test start and end Time from MySql
			String testStartTime=list.get(0).getTeststarttime();
			String testEndTime=list.get(0).getTestendtime();
			String scenarioName=list.get(0).getTestsuite();
			String testName=list.get(0).getTestname();
			
			//start and end time to this format yyyy-MM-dd HH:mm:ss
			SimpleDateFormat sdfu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd HH:mm:ss,SSS
			GregorianCalendar calendar = new GregorianCalendar();
			GregorianCalendar calendar1 = new GregorianCalendar();
			calendar.setTimeInMillis(Long.parseLong(testStartTime));
			calendar1.setTimeInMillis(Long.parseLong(testEndTime));
				
			String startTime = sdfu.format(calendar.getTime());
			String endTime = sdfu.format(calendar1.getTime());

			String query1 = "select max(\"ath\") from " +bundle.getObject("delta").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
					+ "ms order by time asc limit 1"; 
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query1).queryParam("db", database1).queryParam("u", userName)
 					.queryParam("p", password).build();
			
			ResponseEntity<String> results = new RestTemplate().getForEntity(uriComponents.toUri(), String.class);
// 			System.out.println("Result From Influx:"+results);
			
			ArrayList<InfluxUserProfileBean> influxSummaryList1= this.getInfluxSummaryResults(results);
		
		    //Total users
			String totalUsers=influxSummaryList1.get(0).getMaxThreads();
			
			//Rampup calculation
			String ramUpEndTime=influxSummaryList1.get(0).getTimeStamp();
			DateTime rampUpdateTime = new DateTime(ramUpEndTime);
			long ramUpEndTimeMillis=rampUpdateTime.getMillis();
			
			
			
			System.out.println("ramUpEndTime:"+ramUpEndTime);
			System.out.println("rampUpdateTime:"+rampUpdateTime);
			System.out.println("ramUpEndTimeMillis:"+ramUpEndTimeMillis);

			
            String query2 = "select max(\"eth\") from " +bundle.getObject("delta").toString()  + " where time >= " +testStartTime  +"ms and time <= " +testEndTime
			+ "ms order by time desc limit 1"; 
 					
 			System.out.println("Query1:"+query1);
 			System.out.println("Query2:"+query2);
 			
 			UriComponents uriComponents1 = UriComponentsBuilder.fromHttpUrl(url1).queryParam("q", query2).queryParam("db", database1).queryParam("u", userName)
 					.queryParam("p", password).build();
 			ResponseEntity<String> results1 = new RestTemplate().getForEntity(uriComponents1.toUri(), String.class);
 			
			ArrayList<InfluxUserProfileBean> influxSummaryList2= this.getInfluxSummaryResults1(results1);

			
			//RampDownCalculation
			String ramDownStartTime=influxSummaryList2.get(0).getTimeStamp(); //
			DateTime rampDownDuration = new DateTime(ramDownStartTime); //2016-10-29T05:31:46.927+05:30
			long ramDownStartTimeinMillis=rampDownDuration.getMillis();
			
			System.out.println("ramDownStartTime:"+ramDownStartTime);
			System.out.println("rampDownDuration:"+rampDownDuration);
			System.out.println("ramDownStartTimeinMillis:"+ramDownStartTimeinMillis);


			//TestDuration
			String testdu=getDurationInHHMMSSFormat(testStartTime,testEndTime);
			
			
			/*String rampupdur=getDurationInHHMMSSFormat(testStartTime,Long.toString(ramUpEndTimeMillis));
			String ramdownpdur= getDurationInHHMMSSFormat(Long.toString(ramDownStartTimeinMillis),Long.toString(ramUpEndTimeMillis));
			String Steadyprd=getDurationInHHMMSSFormat(Long.toString(ramDownStartTimeinMillis),testEndTime);*/
			String rampupdur=getDurationInHHMMSSFormat(testStartTime,Long.toString(ramUpEndTimeMillis));
			String ramdownpdur= getDurationInHHMMSSFormat(Long.toString(ramDownStartTimeinMillis),/*Long.toString(ramUpEndTimeMillis)*/testEndTime);
			String Steadyprd=getDurationInHHMMSSFormat(Long.toString(ramUpEndTimeMillis),Long.toString(ramDownStartTimeinMillis));
			
			System.out.println("testdutime is========"+testdu);
			System.out.println("rampupdur is========"+rampupdur);
			System.out.println("ramdownpdur is========"+ramdownpdur);
			System.out.println("Steadyprd is========"+Steadyprd);
			System.out.println("ramDownStartTimeinMillis is========"+ramDownStartTimeinMillis);
			System.out.println("ramUpEndTimeMillis is========"+ramUpEndTimeMillis);
			
			InfluxUserProfileBean influxUserProfileBean=new InfluxUserProfileBean();
//			influxUserProfileBean.setTestduration(testdu);
			influxUserProfileBean.setTestduration(getTestDurInHrMinSecFormat(testdu));
			influxUserProfileBean.setSteadyperiod(Steadyprd);
			influxUserProfileBean.setRampdown(ramdownpdur);
			influxUserProfileBean.setRampup(rampupdur);
			influxUserProfileBean.setTeststarttime(startTime);
			influxUserProfileBean.setTestendtime(endTime);
			influxUserProfileBean.setMaxThreads(totalUsers);
			influxUserProfileBean.setScenario_name(scenarioName);
			influxUserProfileBean.setTestName(testName);
			influxUserProfileBean.setTestdatetimezone(datetimezone);
			influxUserProfileBean.setTestscenarios(scenarios);
			influxUserProfileBean.setTestenvurl(qaenvurl);
			
			if(null!=list.get(0).getTestupdatedname())
			{
				influxUserProfileBean.setScenario_name(list.get(0).getTestupdatedname());	
			}
			else
				influxUserProfileBean.setScenario_name(scenarioName);
			session.close();
			
		if (list.isEmpty())
			return new InfluxUserProfileBean();
		else
			return influxUserProfileBean;

	}
	
	public String getTestDurInHrMinSecFormat(String testDurationHHMMSS) {
		StringBuffer sb = new StringBuffer();
		if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(0, 2)))	{
			sb.append(testDurationHHMMSS.substring(0, 2));
        	sb.append("Hr ");	
		}
		if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(3, 5)))	{
			sb.append(testDurationHHMMSS.substring(3, 5));
        	sb.append("Min ");	
		}
		if(!"00".equalsIgnoreCase(testDurationHHMMSS.substring(6)))	{
			sb.append(testDurationHHMMSS.substring(6));
        	sb.append("Sec");	
		}
		return sb.toString();
	}
	
	public long timeInMillis(String timeinl){
		
		String[] tokens = timeinl.split(":");
		int secondsToMs = Integer.parseInt(tokens[2]) * 1000;
		int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
		int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
		long timeinmills = secondsToMs + minutesToMs + hoursToMs;
	     return timeinmills  ;
	}
	
	// Jasper Reports
	
		public void getReport(String testId) throws Exception { //,String test_type 
			try {

				// User home directory location
				String userHomeDirectory = System.getProperty("user.home");
				// Output file location
				String outputFile = userHomeDirectory + File.separatorChar + "Jasper_Test_Report_05.pdf";
				String outputFile1 = userHomeDirectory + File.separatorChar + "JasperReport_19.docx";
				
				//TestSummary
				InfluxUserProfileBean userProfileInfo = getUserProfileInfo(testId);

				//KPI Widget
				HashMap<String, String> testInfo = homeController.getTestInfoCommon(testId);
				ObservationsBean kpiList=getKPIInfoLive(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
				
				
				//TestUpdates
				List<TestUpdateModel> testUpdtDetWithCommts = testUpdateDao.findTesterUpdatesByTestType("SINGLE_TEST",testId);
				
				//Response Times
				List<AggregateReportBean> responseTimeGraphDetails= getAggregateReportInfo(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
				
				
				//Response Time Graph
				String responseTimeGraphResults=getInfluxSummaryListForResponseTimesGraph(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
				JSONObject jsonObject =new JSONObject(responseTimeGraphResults);
				Iterator jsonObjkeys = jsonObject.keys();
				
				List<ResponseTimeBean> ResponseTimeBeanList = new ArrayList<ResponseTimeBean>();
				while(jsonObjkeys.hasNext() ) {
					ResponseTimeBean respTimeBean = new ResponseTimeBean();
					String jsonObjkey = (String)jsonObjkeys.next();
					JSONArray temp = (JSONArray) jsonObject.get(jsonObjkey); 
					for (int i = 0; i < temp.length(); i++) {
						JSONArray tempElTimeRespTime = (JSONArray) temp.get(i);
						
						//Elapsed time 
						Date elaspedDate=getDateFromJODATimeStamp((String) tempElTimeRespTime.get(0));
						@SuppressWarnings("deprecation")
						int elaspedDateSeconds=elaspedDate.getMinutes();
						Integer intObj = new Integer(elaspedDateSeconds);
						SimpleDateFormat originalFormat = new SimpleDateFormat("mm");
						Date date = originalFormat.parse(intObj.toString());
						
						//long seconds = intObj.longValue();
						respTimeBean.setElapsedTimeGraph(date);
						
						//response time
						//int tempTime = (int) tempElTimeRespTime.get(1);
						//int resTime=tempTime/1000;
						//Long responseTimeInSeconds = Long.parseLong(String.valueOf(resTime));
						
						int tempTime = (int) tempElTimeRespTime.get(1);
						Integer obj = new Integer(tempTime);
						double d = obj.doubleValue();
						double resTime = (d / 1000);
						double resTimeGraph=Double.parseDouble(new DecimalFormat("##.##").format(resTime));
						
						//Long responseTimeInSeconds = Long.parseLong(String.valueOf(resTime));
						//respTimeBean.setResponseTimeGraph(responseTimeInSeconds);
						
						respTimeBean.setResponseTimeGraph(resTimeGraph);
						System.out.println("ResponseTime====="+resTimeGraph);
						System.out.println("ElapsedTime====="+elaspedDate);
						if(jsonObjkey.length() > 20)	{
							jsonObjkey = jsonObjkey.substring(0,18);
						}
						
						//transaction names
						respTimeBean.setTransactionNameGraph(jsonObjkey);
						ResponseTimeBeanList.add(respTimeBean);
						System.out.println("transactions====="+jsonObjkey);
						
					}
				}
				
				
				//Convert TestSummary Object to List
				ArrayList<InfluxUserProfileBean> userProfileList=new ArrayList<InfluxUserProfileBean>();
				userProfileList.add(userProfileInfo);
				
				//Convert KPI Widget Object to List Object
				ArrayList<ObservationsBean> kpiInfoList=new ArrayList<ObservationsBean>();
				kpiInfoList.add(kpiList);
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("Test_Summary", new JRBeanCollectionDataSource(userProfileList));
				paramMap.put("Test_Summary1", new JRBeanCollectionDataSource(kpiInfoList));
				paramMap.put("Test_Update", new JRBeanCollectionDataSource(testUpdtDetWithCommts));
				paramMap.put("Response_Time_Details", new JRBeanCollectionDataSource(responseTimeGraphDetails));
				paramMap.put("ResponseTime_Graph", new JRBeanCollectionDataSource(ResponseTimeBeanList));
				paramMap.put("ResponseTime_Graph1", new JRBeanCollectionDataSource(ResponseTimeBeanList));
				paramMap.put("Transactions_Graph", new JRBeanCollectionDataSource(ResponseTimeBeanList));
				
				

				JasperPrint jasperPrint = JasperFillManager.fillReport(
				"C:\\cgt-dop-admin\\src\\main\\resources\\Blank_A4.jasper",paramMap, new JREmptyDataSource());

				// outputStream to create PDF 
				FileOutputStream outputStream = new FileOutputStream(new File(outputFile));
				// Write content to PDF file 
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				System.out.println("File Generateddddddd"+userHomeDirectory);

				// Write content to Word Document
				Exporter exporter = new JRDocxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				File exportReportFile = new File(outputFile1);
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
				exporter.exportReport();
				System.out.println("Document got generatedddddd"+userHomeDirectory);

			} catch (JRException ex) {
				ex.printStackTrace();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}

		}
	
	public String getDurationInHHMMSSFormat(String startTime, String endTime) {
		 
        long diffSeconds = 0L;
        long diffMinutes = 0L;
        long diffHours = 0L;
        String duration = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(startTime));
        Date startTimeDate = new Date(Long.valueOf(startTime));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(Long.valueOf(endTime));
        Date endTimeDate = new Date(Long.valueOf(endTime));
        try {
               long diff = endTimeDate.getTime() - startTimeDate.getTime();
               diffSeconds = diff / 1000 % 60;
               diffMinutes = diff / (60 * 1000) % 60;
               diffHours = diff / (60 * 60 * 1000) % 24;

			if (diffHours == 0) {
				duration += "0" + String.valueOf(diffHours) + ":";
			} else if (String.valueOf(diffHours).length() == 1) {
						duration += "0" + String.valueOf(diffHours) + ":";
					}	else	{
							duration += String.valueOf(diffHours) + ":";
						}
			
			if (diffMinutes == 0) {
				duration += "0" + String.valueOf(diffMinutes) + ":";
			} else if (String.valueOf(diffMinutes).length() == 1) {
						duration += "0" + String.valueOf(diffMinutes) + ":";
					}	else	{
							duration += String.valueOf(diffMinutes) + ":";
						}
			
			if (diffSeconds == 0) {
				duration += "0" + String.valueOf(diffSeconds);
			} else if (String.valueOf(diffSeconds).length() == 1) {
						duration += "0" + String.valueOf(diffSeconds);
					}	else	{
							duration += String.valueOf(diffSeconds);
						}
        } catch (Exception e) {
               e.printStackTrace();
        }
        return duration;
 }
	
	public ArrayList<InfluxUserProfileBean> getInfluxSummaryResults(ResponseEntity<String> results) throws Exception {

		JSONObject responseBody = new JSONObject(results.getBody());
		JSONArray resultsArray = new JSONArray(responseBody.getJSONArray("results").toString());
		JSONObject resultJSONObject = (JSONObject) resultsArray.get(0);
		
		JSONArray jsonValues = new JSONArray();
		if(resultJSONObject.length() > 0)	{
			jsonValues = getJSONArrayList(resultJSONObject);
		}

		ArrayList<InfluxUserProfileBean> influxSummaryList = new ArrayList<InfluxUserProfileBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxUserProfileBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				String timeStamp = jsonValue.get(0).toString();
				String maxThreads = jsonValue.get(1).toString();
				influxSummary = new InfluxUserProfileBean();
				influxSummary.setMaxThreads(maxThreads);
				influxSummary.setTimeStamp(timeStamp);
				
//				System.out.println("summary addeding in the list is to be"+influxSummary);
			}
			influxSummaryList.add(influxSummary);
			
//			System.out.println("InfluxSummaryList is========="+influxSummaryList);
			
		}
		return influxSummaryList;
	}
	
	public ArrayList<InfluxUserProfileBean> getInfluxSummaryResults1(ResponseEntity<String> results) throws Exception {
		JSONObject responseBody = new JSONObject(results.getBody());
		JSONArray resultsArray = new JSONArray(responseBody.getJSONArray("results").toString());
		JSONObject resultJSONObject = (JSONObject) resultsArray.get(0);
		
		JSONArray jsonValues = new JSONArray();
		if(resultJSONObject.length() > 0)	{
			jsonValues = getJSONArrayList1(resultJSONObject);
		}
		
		ArrayList<InfluxUserProfileBean> influxSummaryList = new ArrayList<InfluxUserProfileBean>();

		for (int i = 0; i < jsonValues.length(); i++) {
			JSONArray jsonValue = (JSONArray) jsonValues.get(i);
			InfluxUserProfileBean influxSummary = null;
			for (int j = 1; j < jsonValue.length(); j++) {
				//String maxThreads = jsonValue.get(1).toString();
				String rampDownTimeStamp = jsonValue.get(0).toString();
				influxSummary = new InfluxUserProfileBean();
				//influxSummary.setMaxThreads(maxThreads);
				influxSummary.setTimeStamp(rampDownTimeStamp);
				
			}
			influxSummaryList.add(influxSummary);
			
//			System.out.println("InfluxSummaryList2 is========="+influxSummaryList);
			
		}
		return influxSummaryList;
	}
	
	
	private JSONArray getJSONArrayList(JSONObject resultJSONObject) throws JSONException {
		JSONArray results= (JSONArray) resultJSONObject.get("series");
		resultJSONObject=(JSONObject) results.get(0);
		results=(JSONArray) resultJSONObject.get("values");
		System.out.println("******values :"+((JSONArray) results.get(0)).get(0));
		//JSONObject resultSeries = (JSONObject) resultJSONObject.getJSONArray("time").get(0);
	//	JSONArray jsonValues = resultSeries.getJSONArray("values");
		return results;
	}
	
	private JSONArray getJSONArrayList1(JSONObject resultJSONObject) throws JSONException {
		JSONArray results= (JSONArray) resultJSONObject.get("series");
		resultJSONObject=(JSONObject) results.get(0);
		results=(JSONArray) resultJSONObject.get("values");
		System.out.println("******values for rampdown:"+((JSONArray) results.get(0)).get(0));
		//JSONObject resultSeries = (JSONObject) resultJSONObject.getJSONArray("time").get(0);
	//	JSONArray jsonValues = resultSeries.getJSONArray("values");
		return results;
	}
	
	
	
	public ObservationsBean getKPIInfo() throws Exception {

		ObservationsBean observationsBean = null;
		try {
			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.HALF_UP);

			double aggregateReportError = 0;
			double tps = 0;

			for (InfluxAggregateReportBean influxAggregateReportBean : getInfluxAggregateReportList()) {

				if ("TOTAL".equals(influxAggregateReportBean.getTransactionName())) {
					double d = Double.parseDouble(influxAggregateReportBean.getAggregate_report_error());

					aggregateReportError = Double.parseDouble(df.format(d));
					double testDuration = getTestDuration(getUserProfileDTO().getStartDateTimeStamp(), getUserProfileDTO().getEndDateTimeStamp());

					double tpsVal = Double.parseDouble(influxAggregateReportBean.getCount()) / testDuration;

					tps = Double.parseDouble(df.format(tpsVal));

					break;
				}

			}

			observationsBean = new ObservationsBean();
			observationsBean.setError(aggregateReportError);
			observationsBean.setTps(tps);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return observationsBean;
	}
	
	public ObservationsBean getKPIInfoLive(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		ObservationsBean observationsBean = null;
		try {
			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.HALF_UP);
			double aggregateReportError = 0;
			double tps = 0;
			for (InfluxAggregateReportBean influxAggregateReportBean : getInfluxAggregateReportListForLiveKPI(testStartTime, testEndTime, testSuite, projectName)) {
				double d = Double.parseDouble(influxAggregateReportBean.getAggregate_report_error());
				aggregateReportError = Double.parseDouble(df.format(d));
				
				double testDuration = (Long.parseLong(testEndTime) - Long.parseLong(testStartTime))/1000;
				double tpsVal = Double.parseDouble(influxAggregateReportBean.getCount()) / testDuration;
				tps = Double.parseDouble(df.format(tpsVal));
			}
			observationsBean = new ObservationsBean();
			observationsBean.setError(aggregateReportError); 
			observationsBean.setTps(tps);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return observationsBean;
	}

	public Map<Date, Long> getActiveThreadsInfo() throws Exception {
		Map<Date, Long> resultMap = new TreeMap<Date, Long>();
		ArrayList<ActiveThreadsBean> activeThreads = new ArrayList<ActiveThreadsBean>();
		Long timeStamp = null;
		Long allThreads = null;
		Date date = null;
		for (InfluxSummaryBean influxSummaryBean : getInfluxSummaryList()) {

			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				timeStamp = Long.valueOf(influxSummaryBean.getTimeStamp());
				allThreads = Long.valueOf(influxSummaryBean.getAllThreads());
				ActiveThreadsBean activeThreadsBean = new ActiveThreadsBean();
				date = new Date(timeStamp);
				activeThreadsBean.setActiveThreads(allThreads);
				activeThreadsBean.setElapsedTime(date);
				activeThreads.add(activeThreadsBean);
			}
		}
		Collections.sort(activeThreads);
		Map<Date, List<Long>> tempMap = new HashMap<Date, List<Long>>();

		for (ActiveThreadsBean at : activeThreads) {
			if (!tempMap.containsKey(at.getElapsedTime())) {
				List<Long> list = new ArrayList<Long>();
				list.add(at.getActiveThreads());
				tempMap.put(at.getElapsedTime(), list);
			} else {
				tempMap.get(at.getElapsedTime()).add(at.getActiveThreads());
			}

		}

		for (Map.Entry<Date, List<Long>> entrySet : tempMap.entrySet()) {

			long activeThread = 0;
			for (Long at : entrySet.getValue()) {
				activeThread = activeThread + at;
			}
			Date dte = entrySet.getKey();
			/*
			 * SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm:ss");
			 * System.out.println("Duration: " + timeFormat.format(dte)); String
			 * key = dte.getHours() + ":" + dte.getMinutes() + ":" +
			 * dte.getSeconds();
			 */
			long val = activeThread / entrySet.getValue().size();
			resultMap.put(dte, val);
		}

		return resultMap;

	}

	//Refactoring with lennox_projectdetails table...
	public Map<String, Long> getActiveThreadsInfo(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		Map<Date, Long> resultMap = new TreeMap<Date, Long>();
		ArrayList<ActiveThreadsBean> activeThreads = new ArrayList<ActiveThreadsBean>();
		Long timeStamp = null;
		Long allThreads = null;
		Date date = null;
		for (InfluxSummaryBean influxSummaryBean : getInfluxSummaryList(testStartTime, testEndTime, testSuite, projectName)) {

			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				//As we get the timestamp in JODA timezone, need to convert it to date object...
				date = getDateFromJODATimeStamp(influxSummaryBean.getTimeStamp());				
				allThreads = Long.valueOf(influxSummaryBean.getAllThreads());
				
				ActiveThreadsBean activeThreadsBean = new ActiveThreadsBean();
				activeThreadsBean.setElapsedTime(date);
				activeThreadsBean.setActiveThreads(allThreads);				
				activeThreads.add(activeThreadsBean);
			}
		}
		Collections.sort(activeThreads);
		Map<Date, List<Long>> tempMap = new HashMap<Date, List<Long>>();

		for (ActiveThreadsBean at : activeThreads) {
			if (!tempMap.containsKey(at.getElapsedTime())) {
				List<Long> list = new ArrayList<Long>();
				list.add(at.getActiveThreads());
				tempMap.put(at.getElapsedTime(), list);
			} else {
				tempMap.get(at.getElapsedTime()).add(at.getActiveThreads());
			}

		}

		for (Map.Entry<Date, List<Long>> entrySet : tempMap.entrySet()) {

			long activeThread = 0;
			for (Long at : entrySet.getValue()) {
				activeThread = activeThread + at;
			}
			Date dte = entrySet.getKey();
			
			 /** SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm:ss");
			 * System.out.println("Duration: " + timeFormat.format(dte)); String
			 * key = dte.getHours() + ":" + dte.getMinutes() + ":" +
			 * dte.getSeconds();*/
			 
			long val = activeThread / entrySet.getValue().size();
			resultMap.put(dte, val);
		}

		Map<String, Long> evaluatedActiveThreads = evaluateActiveThreads(resultMap, testStartTime);
		return evaluatedActiveThreads;
	}
	
	
	public String getActiveThreadsInfoGraph(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		Map<Date, Long> resultMap = new TreeMap<Date, Long>();
		ArrayList<ActiveThreadsBean> activeThreads = new ArrayList<ActiveThreadsBean>();
		Long timeStamp = null;
		Long allThreads = null;
		Date date = null;
		/*for (InfluxSummaryBean influxSummaryBean : getInfluxSummaryListGraph(testStartTime, testEndTime, testSuite, projectName)) {

			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				//As we get the timestamp in JODA timezone, need to convert it to date object...
				date = getDateFromJODATimeStamp(influxSummaryBean.getTimeStamp());				
				allThreads = Long.valueOf(influxSummaryBean.getAllThreads());
				
				ActiveThreadsBean activeThreadsBean = new ActiveThreadsBean();
				activeThreadsBean.setElapsedTime(date);
				activeThreadsBean.setActiveThreads(allThreads);				
				activeThreads.add(activeThreadsBean);
			}
		}
		Collections.sort(activeThreads);
		Map<Date, List<Long>> tempMap = new HashMap<Date, List<Long>>();

		for (ActiveThreadsBean at : activeThreads) {
			if (!tempMap.containsKey(at.getElapsedTime())) {
				List<Long> list = new ArrayList<Long>();
				list.add(at.getActiveThreads());
				tempMap.put(at.getElapsedTime(), list);
			} else {
				tempMap.get(at.getElapsedTime()).add(at.getActiveThreads());
			}

		}

		for (Map.Entry<Date, List<Long>> entrySet : tempMap.entrySet()) {

			long activeThread = 0;
			for (Long at : entrySet.getValue()) {
				activeThread = activeThread + at;
			}
			Date dte = entrySet.getKey();
			
			 *//** SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm:ss");
			 * System.out.println("Duration: " + timeFormat.format(dte)); String
			 * key = dte.getHours() + ":" + dte.getMinutes() + ":" +
			 * dte.getSeconds();*//*
			 
			long val = activeThread / entrySet.getValue().size();
			resultMap.put(dte, val);
		}

		Map<String, Long> evaluatedActiveThreads = evaluateActiveThreads(resultMap, testStartTime);
		return evaluatedActiveThreads;*/
		return getInfluxSummaryListGraph(testStartTime, testEndTime, testSuite, projectName);
	}
	
	

	private Map<String, Long> evaluateActiveThreads(Map<Date, Long> resultMap, String testStartTime) {
		//logic to identify the time differences & update activeThreadsMap with those keys...
		List<Long> tempActiveThreadsTimeList = new ArrayList<Long>();
		List<Date> tempActiveThreadsTimeDateList = new ArrayList<Date>();
		for(Date entrySet : resultMap.keySet())	{
			tempActiveThreadsTimeDateList.add(entrySet);
			tempActiveThreadsTimeList.add(entrySet.getTime());
		}
		
		Map<String, Long> finalActiveThreads = new LinkedHashMap<String, Long>();
		Map<String, List<Long>> activeThreadsAtSameTime = new LinkedHashMap<String, List<Long>>();
		if (testStartTime != null) {
			for (int i = 0; i <= tempActiveThreadsTimeList.size(); i++) {
				if (i == 0) {
					finalActiveThreads.put("00:00:00", 0L);
				} else {
					//for first user hit, the difference should be firstElapsedTime - testStartTime...
					if (i == 1) {
//						long l = tempActiveThreadsTimeList.get(0) - Long.parseLong(testStartTime);
						String elapsedTimeDuration = getDurationInHHMMSSFormat(testStartTime, Long.toString(tempActiveThreadsTimeList.get(0)));
//						finalActiveThreads.put(((double)l/1000), resultMap.get(tempActiveThreadsTimeDateList.get(0)));
						finalActiveThreads.put(elapsedTimeDuration, resultMap.get(tempActiveThreadsTimeDateList.get(0)));
					}	else	{
//						long l = tempActiveThreadsTimeList.get(i-1) - Long.parseLong(testStartTime);
						String elapsedTimeDuration = getDurationInHHMMSSFormat(testStartTime, Long.toString(tempActiveThreadsTimeList.get(i-1)));
//						finalActiveThreads.put(((double)l/1000), resultMap.get(tempActiveThreadsTimeDateList.get(i-1)));
//						finalActiveThreads.put(elapsedTimeDuration, resultMap.get(tempActiveThreadsTimeDateList.get(i-1)));
						
						if(!finalActiveThreads.containsKey(elapsedTimeDuration)){
							finalActiveThreads.put(elapsedTimeDuration, resultMap.get(tempActiveThreadsTimeDateList.get(i-1)));	
						}	else	{
							if(!activeThreadsAtSameTime.containsKey(elapsedTimeDuration))	{
								List<Long> temp = new ArrayList<Long>();
								temp.add(resultMap.get(tempActiveThreadsTimeDateList.get(i-1)));
								activeThreadsAtSameTime.put(elapsedTimeDuration, temp);
							}	else	{
								List<Long> l = activeThreadsAtSameTime.get(elapsedTimeDuration);
								l.add(resultMap.get(tempActiveThreadsTimeDateList.get(i-1)));
								activeThreadsAtSameTime.put(elapsedTimeDuration, l);
							}
						}
					}
				}
			}
		}
		//Now evaluate the values of activeThreadsAtSameTime & finalActiveThreads for aggregate ath & return them.
		for(Entry<String, List<Long>> me : activeThreadsAtSameTime.entrySet()){
			long athInFinalActiveThreads = finalActiveThreads.get(me.getKey());
			List<Long> tempList = me.getValue();
			long finalATH = athInFinalActiveThreads;
			for(Long tempATH : tempList)	{
				finalATH = finalATH+tempATH;
			}
			finalATH = finalATH/(tempList.size()+1);
			finalActiveThreads.put(me.getKey(), finalATH);
		}
		return finalActiveThreads;
	}

	private Date getDateFromJODATimeStamp(String timeStampJODA) {
		DateTime dateTime = new DateTime(timeStampJODA);
		Timestamp timeStamp = new Timestamp(dateTime.getMillis());
		Date date = new Date(timeStamp.getTime());
		//System.out.println(date);
		return date;
	}
	public List<AggregateReportBean> getAggregateReportInfo(ArrayList<InfluxAggregateReportBean> influxAggregateReportList) throws Exception {

		List<AggregateReportBean> aggregateReportBeanList = null;
		try {

			aggregateReportBeanList = new ArrayList<AggregateReportBean>();
			for (InfluxAggregateReportBean influxAggregateReportBean : influxAggregateReportList) {
				AggregateReportBean aggregateReportBean = new AggregateReportBean();
				aggregateReportBean.setCount(Long.parseLong(influxAggregateReportBean.getCount()));
				aggregateReportBean.setAvg(Long.parseLong(influxAggregateReportBean.getAvg()));

				aggregateReportBean.setAggregate_report_90_line(Long.parseLong(influxAggregateReportBean.getAggregate_report_90_line()));

				aggregateReportBean.setAggregate_report_min(Long.parseLong(influxAggregateReportBean.getAggregate_report_min()));
				aggregateReportBean.setAggregate_report_max(Long.parseLong(influxAggregateReportBean.getAggregate_report_max()));

				double d = Double.parseDouble(influxAggregateReportBean.getAggregate_report_error());

				DecimalFormat df = new DecimalFormat("#.00");
				df.setRoundingMode(RoundingMode.HALF_UP);
				String str = df.format(d);

				aggregateReportBean.setAggregate_report_error(Double.parseDouble(str));

				String trnName = influxAggregateReportBean.getTransactionName().trim();

				aggregateReportBean.setTransactionName(trnName);
				aggregateReportBeanList.add(aggregateReportBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return aggregateReportBeanList;
	}
	
	public List<AggregateReportBean> getAggregateReportInfo(String testStartTime, String testEndTime, String testSuite, String projectName) {

		List<AggregateReportBean> aggregateReportBeanList = null;
		try {

			aggregateReportBeanList = new ArrayList<AggregateReportBean>();
			for (InfluxAggregateReportBean influxAggregateReportBean : getInfluxAggregateReportListLive(testStartTime, testEndTime, testSuite, projectName)) {
				AggregateReportBean aggregateReportBean = new AggregateReportBean();
				aggregateReportBean.setCount(Long.parseLong(influxAggregateReportBean.getCount()));
				aggregateReportBean.setAvg((long)Double.parseDouble(influxAggregateReportBean.getAvg()));

				aggregateReportBean.setAggregate_report_90_line(influxAggregateReportBean.getAggregate_report_90_line() != null ? Long.parseLong(influxAggregateReportBean.getAggregate_report_90_line()) : 0L);

				aggregateReportBean.setAggregate_report_min(Long.parseLong(influxAggregateReportBean.getAggregate_report_min()));
				aggregateReportBean.setAggregate_report_max(Long.parseLong(influxAggregateReportBean.getAggregate_report_max()));

				double d = Double.parseDouble(influxAggregateReportBean.getAggregate_report_error() != null ? influxAggregateReportBean.getAggregate_report_error() : "0");

				DecimalFormat df = new DecimalFormat("#.00");
				df.setRoundingMode(RoundingMode.HALF_UP);
				String str = df.format(d);

				aggregateReportBean.setAggregate_report_error(Double.parseDouble(str));

				String trnName = influxAggregateReportBean.getTransactionName().trim();

				aggregateReportBean.setTransactionName(trnName);
				aggregateReportBeanList.add(aggregateReportBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return aggregateReportBeanList;
	}

	public Map<Long, Long> getHitsPerSecondsInfo(ArrayList<InfluxSummaryBean> influxSummaryList) throws Exception {
		Map<Date, Long> resultMap = new TreeMap<Date, Long>();
		ArrayList<HitsBean> hitsList = new ArrayList<HitsBean>();
		Long timeStamp = null;
		Long userHits = null;
		Date date = null;
		Date starttime=null;
		int counter=1;
		for (InfluxSummaryBean influxSummaryBean : influxSummaryList) {
			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				if(counter==1) {
					timeStamp = Long.valueOf(influxSummaryBean.getTimeStamp());
					userHits = Long.valueOf(influxSummaryBean.getBytes());
					HitsBean hitsBean = new HitsBean();
					starttime = new Date(timeStamp);
					hitsBean.setHits(userHits);
					hitsBean.setElapsedTime(0L);
					hitsList.add(hitsBean);	
				} else{

					timeStamp = Long.valueOf(influxSummaryBean.getTimeStamp());
					userHits = Long.valueOf(influxSummaryBean.getBytes());
					HitsBean hitsBean = new HitsBean();
					date = new Date(timeStamp);
					hitsBean.setHits(userHits);
					hitsBean.setElapsedTime(Long.parseLong(getRelativeTime(starttime,date)));
					hitsList.add(hitsBean);
				}
			counter=counter+1;
			}
		}
		Map<Long, Long> tempMap = evaluateAggregateHitsPerTime(hitsList);
		return tempMap;

	}

	//Edited by Sankar	
	public String getHitsPerSecondsInfo(String testStartTime, String testEndTime, String testSuite, String projectName) {
		return getInfluxSummaryListGraphForHitsPerSeconds(testStartTime, testEndTime, testSuite, projectName);
	}


	private Map<Long, Long> evaluateAggregateHitsPerTime(ArrayList<HitsBean> hitsList) {
		Collections.sort(hitsList);

		Map<Long, Long> tempMap = new HashMap<Long, Long>();
		Map<Long, ArrayList<Long>> hitsAtSameTime = new HashMap<Long, ArrayList<Long>>();

		for (HitsBean hitsBean : hitsList) {
			if (!tempMap.containsKey(hitsBean.getElapsedTime())) {
				tempMap.put(hitsBean.getElapsedTime(), hitsBean.getHits());
				ArrayList<Long> hitsAtTime = new ArrayList<Long>();
				hitsAtTime.add(hitsBean.getHits());
				hitsAtSameTime.put(hitsBean.getElapsedTime(), hitsAtTime);
			} else {
				hitsAtSameTime.get(hitsBean.getElapsedTime()).add( hitsBean.getHits());
			}
		}

		//Logic to find the aggregate hits at same time & update the tempMap...
		for (Entry<Long, ArrayList<Long>> entrySet : hitsAtSameTime.entrySet()) {
			ArrayList<Long> hitsAtTime = entrySet.getValue();
			if(hitsAtTime.size() > 1){
				long temp = 0;
				for (Long l : hitsAtTime) {
					temp += l;
				}
				temp = temp / hitsAtTime.size();
				tempMap.put(entrySet.getKey(), temp);
			}
		}
		return tempMap;
	}
	
	private Map<Double, Long> evaluateAggregateHitsPerTime(ArrayList<HitsBean> hitsList, String testStartTime) {

		Map<Long, Long> tempMap = new LinkedHashMap<Long, Long>();
		Map<Long, ArrayList<Long>> hitsAtSameTime = new HashMap<Long, ArrayList<Long>>();

		for (HitsBean hitsBean : hitsList) {
			if (!tempMap.containsKey(hitsBean.getElapsedTime())) {
				tempMap.put(hitsBean.getElapsedTime(), hitsBean.getHits());
				ArrayList<Long> hitsAtTime = new ArrayList<Long>();
				hitsAtTime.add(hitsBean.getHits());
				hitsAtSameTime.put(hitsBean.getElapsedTime(), hitsAtTime);
			} else {
				hitsAtSameTime.get(hitsBean.getElapsedTime()).add( hitsBean.getHits());
			}
		}

		//Logic to find the aggregate hits at same time & update the tempMap...
		for (Entry<Long, ArrayList<Long>> entrySet : hitsAtSameTime.entrySet()) {
			ArrayList<Long> hitsAtTime = entrySet.getValue();
			if(hitsAtTime.size() > 1){
				long tempTotalHits = 0;
				for (Long l : hitsAtTime) {
					tempTotalHits += l;
				}
				tempTotalHits = tempTotalHits / hitsAtTime.size();
				tempMap.put(entrySet.getKey(), tempTotalHits);
			}
		}
		
		//logic to identify the time differences & update userHitsMap with those keys...
		List<Long> tempTimeList = new ArrayList<Long>();
		for(Long entrySet : tempMap.keySet())	{
			tempTimeList.add(entrySet);
		}
		Map<Double, Long> finalUserHits = new LinkedHashMap<Double, Long>();
		if (testStartTime != null) {
			for (int i = 0; i <= tempMap.size(); i++) {
				if (i == 0) {
					finalUserHits.put((double)0L, 0L);
				} else {
					//for first user hit, the difference should be firstElapsedTime - testStartTime...
					if (i == 1) {
						long l = tempTimeList.get(0) - Long.parseLong(testStartTime);
						finalUserHits.put(((double)l/1000), tempMap.get(tempTimeList.get(0)));
					}	else	{
						/*long l = tempTimeList.get(1) - tempTimeList.get(0);
						finalUserHits.put(l, tempMap.get(tempTimeList.get(1)));*/
						long l = tempTimeList.get(i-1) - Long.parseLong(testStartTime);
						finalUserHits.put(((double)l/1000), tempMap.get(tempTimeList.get(i-1)));
					}
				}
			}
		}
		return finalUserHits;
	}

	public ResponseTime getResponseTimesInfo(ArrayList<InfluxSummaryBean> influxSummaryList) throws Exception {
		ResponseTime resultResponseTime = new ResponseTime();
		ArrayList<ResponseTimeBean> responseTimeBeanList = new ArrayList<ResponseTimeBean>();
		Long timeStamp = null;
		Long responseTime = null;
		String transactionName = null;
		Date date = null;
		for (InfluxSummaryBean influxSummaryBean : influxSummaryList) {

			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				timeStamp = Long.valueOf(influxSummaryBean.getTimeStamp());
				responseTime = Long.valueOf(influxSummaryBean.getLatency());
				transactionName = influxSummaryBean.getLabel();
				ResponseTimeBean responseTimeBean = new ResponseTimeBean();
				date = new Date(timeStamp);
				responseTimeBean.setResponseTime(responseTime);
				responseTimeBean.setTransactionName(transactionName);
				responseTimeBean.setElapsedTime(date);
				String key = date.getHours() + ":" + date.getMinutes();// + ":"
																		// +
																		// dte.getSeconds();
				responseTimeBean.setElapsedTimeString(key);
				responseTimeBeanList.add(responseTimeBean);
			}
		}
		Collections.sort(responseTimeBeanList);
		Map<String, String> trnNameMap = new TreeMap<String, String>();
		Map<String, String> elapseTimeMap = new TreeMap<String, String>();
		Map<String, List<ResponseTimeBean>> map = new HashMap<String, List<ResponseTimeBean>>();
		for (ResponseTimeBean rtb : responseTimeBeanList) {

			if (!map.containsKey(rtb.getElapsedTimeString())) {

				map.put(rtb.getElapsedTimeString(), new ArrayList<ResponseTimeBean>());

			}
			map.get(rtb.getElapsedTimeString()).add(rtb);
			trnNameMap.put(rtb.getTransactionName(), rtb.getTransactionName());
			elapseTimeMap.put(rtb.getElapsedTimeString(), rtb.getElapsedTimeString());
		}

		String split = "_SPLIT_";
		Map<String, List<Long>> map2 = new HashMap<String, List<Long>>();
		for (Map.Entry<String, List<ResponseTimeBean>> entrySet : map.entrySet()) {

			for (ResponseTimeBean rtb : entrySet.getValue()) {

				if (!map2.containsKey(rtb.getTransactionName() + split + rtb.getElapsedTimeString())) {
					List<Long> list = new ArrayList<Long>();
					list.add(rtb.getResponseTime());
					map2.put(rtb.getTransactionName() + split + rtb.getElapsedTimeString(), list);
				} else {
					map2.get(rtb.getTransactionName() + split + rtb.getElapsedTimeString()).add(rtb.getResponseTime());
				}

			}

		}

		Map<String, List<ResponseTimeDTO>> resultMap = new TreeMap<String, List<ResponseTimeDTO>>();
		for (Map.Entry<String, List<Long>> entrySet : map2.entrySet()) {
			long rt = 0;
			for (Long at : entrySet.getValue()) {
				rt = rt + at;
			}

			String[] keys = entrySet.getKey().split(split);
			String trnName = keys[0];
			String elapseTime = keys[1];
			long val = rt / entrySet.getValue().size();

			if (!resultMap.containsKey(elapseTime)) {
				List<ResponseTimeDTO> list = new ArrayList<ResponseTimeDTO>();
				ResponseTimeDTO rtb = new ResponseTimeDTO(trnName, val);
				list.add(rtb);
				resultMap.put(elapseTime, list);
			} else {

				ResponseTimeDTO rtb = new ResponseTimeDTO(trnName, val);
				resultMap.get(elapseTime).add(rtb);
			}

		}

		resultResponseTime.setResultMap(resultMap);
		resultResponseTime.setTrnNames(trnNameMap.keySet().toArray());
		resultResponseTime.setElapseTimes(elapseTimeMap.keySet().toArray());
		return resultResponseTime;

	}
	
	public ResponseTime getResponseTimesInfoLive(ArrayList<InfluxSummaryBean> influxSummaryList) throws Exception {
		ResponseTime resultResponseTime = new ResponseTime();
		ArrayList<ResponseTimeBean> responseTimeBeanList = new ArrayList<ResponseTimeBean>();
		Long timeStamp = null;
		Long responseTime = null;
		String transactionName = null;
		Date date = null;
		for (InfluxSummaryBean influxSummaryBean : influxSummaryList) {

			if ((null != influxSummaryBean.getTimeStamp() && !"null".equals(influxSummaryBean.getTimeStamp()))) {
				date = getDateFromJODATimeStamp(influxSummaryBean.getTimeStamp());
				responseTime = Long.valueOf(influxSummaryBean.getLatency());
				transactionName = influxSummaryBean.getLabel();
				ResponseTimeBean responseTimeBean = new ResponseTimeBean();
				
				responseTimeBean.setResponseTime(responseTime);
				responseTimeBean.setTransactionName(transactionName);
//				responseTimeBean.setElapsedTime(date);
				String key = date.getHours() + ":" + date.getMinutes() + ":"+date.getSeconds();
				
				responseTimeBean.setElapsedTimeString(String.valueOf(date.getTime()));
			//	responseTimeBean.setElapsedTimeString(key);
				responseTimeBeanList.add(responseTimeBean);
			}
		}
		Collections.sort(responseTimeBeanList);
		Map<String, String> trnNameMap = new TreeMap<String, String>();
		Map<String, String> elapseTimeMap = new TreeMap<String, String>();
		Map<String, List<ResponseTimeBean>> map = new HashMap<String, List<ResponseTimeBean>>();
		for (ResponseTimeBean rtb : responseTimeBeanList) {

			if (!map.containsKey(rtb.getElapsedTimeString())) {

				map.put(rtb.getElapsedTimeString(), new ArrayList<ResponseTimeBean>());

			}
			map.get(rtb.getElapsedTimeString()).add(rtb);
			trnNameMap.put(rtb.getTransactionName(), rtb.getTransactionName());
			elapseTimeMap.put(rtb.getElapsedTimeString(), rtb.getElapsedTimeString());
		}

		String split = "_SPLIT_";
		Map<String, List<Long>> map2 = new HashMap<String, List<Long>>();
		for (Map.Entry<String, List<ResponseTimeBean>> entrySet : map.entrySet()) {

			for (ResponseTimeBean rtb : entrySet.getValue()) {

				if (!map2.containsKey(rtb.getTransactionName() + split + rtb.getElapsedTimeString())) {
					List<Long> list = new ArrayList<Long>();
					list.add(rtb.getResponseTime());
					map2.put(rtb.getTransactionName() + split + rtb.getElapsedTimeString(), list);
				} else {
					map2.get(rtb.getTransactionName() + split + rtb.getElapsedTimeString()).add(rtb.getResponseTime());
				}

			}

		}

		Map<String, List<ResponseTimeDTO>> resultMap = new TreeMap<String, List<ResponseTimeDTO>>();
		for (Map.Entry<String, List<Long>> entrySet : map2.entrySet()) {
			long rt = 0;
			for (Long at : entrySet.getValue()) {
				rt = rt + at;
			}

			String[] keys = entrySet.getKey().split(split);
			String trnName = keys[0];
			String elapseTime = keys[1];
			long val = rt / entrySet.getValue().size();

			if (!resultMap.containsKey(elapseTime)) {
				List<ResponseTimeDTO> list = new ArrayList<ResponseTimeDTO>();
				ResponseTimeDTO rtb = new ResponseTimeDTO(trnName, val);
				list.add(rtb);
				resultMap.put(elapseTime, list);
			} else {

				ResponseTimeDTO rtb = new ResponseTimeDTO(trnName, val);
				resultMap.get(elapseTime).add(rtb);
			}

		}

		resultResponseTime.setResultMap(resultMap);
		resultResponseTime.setTrnNames(trnNameMap.keySet().toArray());
		resultResponseTime.setElapseTimes(elapseTimeMap.keySet().toArray());
		
		//plotty graphs
		
		
		
		return resultResponseTime;

	}
	
	public ArrayList<ResponseCodeBean> getResponseCodesInfo(ArrayList<InfluxSummaryBean> influxSummaryList) throws Exception {
		ArrayList<ResponseCodeBean> responseCodes = null;
		ArrayList<ResponseCodeBean> responseCodeBeanList = new ArrayList<ResponseCodeBean>();

		ResponseCodeBean rcb = null;
		Date date = null;
		Date startDate=null;
		int counter=1;
		for (InfluxSummaryBean influxSummaryBean : influxSummaryList) {
	//Commented by SAI 
			/*	Long timeStamp = Long.valueOf(influxSummaryBean.getTimeStamp());
			date = new Date(timeStamp);
			String val = influxSummaryBean.getResponseCode();
			Long responseCode;
			if (onlyContainsNumbers(val) && val.length() > 2) {
				responseCode = Long.valueOf(val);
			} else {
				responseCode = 0L;
			}

			rcb = new ResponseCodeBean();
			rcb.setResponseCode(responseCode);
			rcb.setElapsedTime(date);
			responseCodeBeanList.add(rcb);*/
			String val = influxSummaryBean.getResponseCode();
			Long responseCode;
			if (onlyContainsNumbers(val) && val.length() > 2) {
				responseCode = Long.valueOf(val);
			} else {
				responseCode = 0L;
			}
			//Edited by SAI
			if(counter==1)	{
				startDate= getDateFromJODATimeStamp(influxSummaryBean.getTimeStamp());
				rcb = new ResponseCodeBean();
				rcb.setElapsedTime("00");
				rcb.setResponseCode(responseCode);
				responseCodeBeanList.add(rcb);
			}
			else
			{
				date = getDateFromJODATimeStamp(influxSummaryBean.getTimeStamp());	
				rcb = new ResponseCodeBean();
				rcb.setElapsedTime(getRelativeTime(startDate,date));
				rcb.setResponseCode(responseCode);
				responseCodeBeanList.add(rcb);
			}
			counter=counter+1;
			//Edited SAI
		}

		

		try {
			//Commented by sai
			/*responseCodes = new ArrayList<ResponseCodeBean>();
			//Date startTime = responseCodeBeanList.get(0).getElapsedTime();
			for (int i = 1; i < responseCodeBeanList.size(); i++) {
				long responseCode = responseCodeBeanList.get(i).getResponseCode();
				Date time = responseCodeBeanList.get(i).getElapsedTime();
				//long elapsedTime = (time.getTime() - startTime.getTime());
				//Date dte = new Date(elapsedTime);				
				ResponseCodeBean responseCodeBean = new ResponseCodeBean();
				responseCodeBean.setResponseCode(responseCode);
				responseCodeBean.setElapsedTime(time);
				responseCodes.add(responseCodeBean); */

			responseCodes = new ArrayList<ResponseCodeBean>();
			//Date startTime = responseCodeBeanList.get(0).getElapsedTime();
			for (int i = 1; i < responseCodeBeanList.size(); i++) {
				long responseCode = responseCodeBeanList.get(i)
						.getResponseCode();
				//Date dte = responseCodeBeanList.get(i).getElapsedTime();
				//long elapsedTime = (endTime.getTime() - startTime.getTime());

				//Date dte = new Date(elapsedTime);

				ResponseCodeBean responseCodeBean = new ResponseCodeBean();
				responseCodeBean.setResponseCode(responseCode);
				//commented by sai
				//responseCodeBean.setElapsedTime(dte);
				//Edited by SAI
				//responseCodeBeanList.get(i).getElapsedTime()
				responseCodeBean.setElapsedTime(responseCodeBeanList.get(i).getElapsedTime());
				// End of the Editing
				responseCodes.add(responseCodeBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseCodes;
	}
	

	
	public ObservationsBean getKPIInfoForComparisionTest() throws Exception {

		ObservationsBean observationsBean = null;
		try {

			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.HALF_UP);

			double aggregateReportError = 0;
			double tps = 0;

			for (InfluxAggregateReportBean influxAggregateReportBean : getInfluxAggregateReportListForTest2()) {

				if ("TOTAL".equals(influxAggregateReportBean.getTransactionName())) {
					double d = Double.parseDouble(influxAggregateReportBean.getAggregate_report_error());

					aggregateReportError = Double.parseDouble(df.format(d));
					double testDuration = getTestDuration(getUserProfileDTO().getStartDateTimeStamp(), getUserProfileDTO().getEndDateTimeStamp());

					double tpsVal = Double.parseDouble(influxAggregateReportBean.getCount()) / testDuration;

					tps = Double.parseDouble(df.format(tpsVal));

					break;
				}

			}

			observationsBean = new ObservationsBean();
			observationsBean.setError(aggregateReportError);
			observationsBean.setTps(tps);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return observationsBean;
	}
	

	public ArrayList<ResponseCodeBean> getResponseCodesInfo(String testStartTime, String testEndTime, String testSuite,String projectName) {
		ArrayList<ResponseCodeBean> responseCodeBeanList = new ArrayList<ResponseCodeBean>();
		ResponseCodeBean rcb = null;
		int counter = 1;
		for (InfluxSummaryBean influxSummaryBean : getInfluxSummaryListForResponseCodeInfo(testStartTime, testEndTime, testSuite, projectName)) {
			String val = influxSummaryBean.getResponseCode();
			Long responseCode;
			if (onlyContainsNumbers(val) && val.length() > 2) {
				responseCode = Long.valueOf(val);
			} else {
				responseCode = 0L;
			}
			if (counter == 1) {
				rcb = new ResponseCodeBean();
				rcb.setElapsedTime(testStartTime);
				rcb.setResponseCode(responseCode);
				responseCodeBeanList.add(rcb);
			} else {
				rcb = new ResponseCodeBean();
				rcb.setElapsedTime(influxSummaryBean.getTimeStamp());
				rcb.setResponseCode(responseCode);
				responseCodeBeanList.add(rcb);
			}
			counter = counter + 1;
		}
		return responseCodeBeanList;
	}
	
	public Map<String, LinkedHashMap<Long, Integer>> evaluateResponseCodeOccurances(ArrayList<ResponseCodeBean> responseCodes) {
		Map<String, LinkedHashMap<Long, Integer>> respCodeOccurancesAtTime = new LinkedHashMap<String, LinkedHashMap<Long,Integer>>();
		for(ResponseCodeBean responseCode : responseCodes){
			if(!respCodeOccurancesAtTime.containsKey(responseCode.getElapsedTime())){
				LinkedHashMap<Long, Integer> codeOccurances = new LinkedHashMap<Long, Integer>();
				codeOccurances.put(responseCode.getResponseCode(), 1);
				respCodeOccurancesAtTime.put(responseCode.getElapsedTime(), codeOccurances);
			}	else	{
				//get the value, update it & put in same respCodeOccurancesAtTime
				LinkedHashMap<Long, Integer> tempCodeOccurances = respCodeOccurancesAtTime.get(responseCode.getElapsedTime());
				if(tempCodeOccurances.containsKey(responseCode.getResponseCode()))	{
					int respCodeOccrnce = tempCodeOccurances.get(responseCode.getResponseCode())+1;
					tempCodeOccurances.put(responseCode.getResponseCode(), respCodeOccrnce);
				}else{
					tempCodeOccurances.put(responseCode.getResponseCode(), 1);
				}
				respCodeOccurancesAtTime.put(responseCode.getElapsedTime(), tempCodeOccurances);
			}
		}
		return respCodeOccurancesAtTime;
	}
	
	public String getRelativeTime(Date startTime,Date secondDate)
	{
		Long milliseconds=secondDate.getTime()-startTime.getTime();
	//	System.out.println("StartTime:"+startTime   +" End Time:"+secondDate);
		Double seconds=Double.valueOf(milliseconds/1000);
		return String.valueOf(seconds);
	}
}
