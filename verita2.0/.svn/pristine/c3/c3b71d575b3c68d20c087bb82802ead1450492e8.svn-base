package cgt.dop.service.perf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cgt.dop.bean.InfluxUserProfileBean;
import cgt.dop.dao.impl.JmeterDao;
import cgt.dop.model.JmeterAggrigateModel;
import cgt.dop.model.JmeterErrorsTimeModel;
//import cgt.dop.model.JmeterProjectDetails;
import cgt.dop.model.JmeterResponsetimesModel;
import cgt.dop.model.JmeterTransactionDetailsModel;
import cgt.dop.model.LennoxProjectDetails;
import cgt.dop.model.ProjectModel;

//import org.joda.time.DateTime;

@Repository
public class JmeterService {

	@Autowired
	JmeterDao jmeterDao;
	
	@Autowired
	private InfluxHelper influxHelper;

	public String getKPIImprovementForLastBuild() throws Exception {
		JSONObject parentObject = new JSONObject();

		JSONObject childObject1 = new JSONObject();
		JSONObject childObject2 = new JSONObject();
		JSONObject childObject3 = new JSONObject();

		List compare = jmeterDao.getlastTwoDetails();
		if (compare != null) {
			JmeterAggrigateModel object1 = (JmeterAggrigateModel) compare.get(0);
			JmeterAggrigateModel object2 = (JmeterAggrigateModel) compare.get(1);

			childObject1.put("Prevvalue", String.valueOf(round((object2.getAvg_time() / (1000)) % 60, 3)));
			childObject1.put("currentvalue", String.valueOf(round((object1.getAvg_time() / (1000)) % 60, 3)));

			childObject2.put("Prevvalue", String.valueOf(round((object2.getAvg_throughput() / (1000)) % 60, 3)));
			childObject2.put("currentvalue", String.valueOf(round((object1.getAvg_throughput() / (1000)) % 60, 3)));

			childObject3.put("Prevvalue", String.valueOf(round(object2.getErrorpercentage(), 3)));
			childObject3.put("currentvalue", String.valueOf(round(object1.getErrorpercentage(), 3)));

			parentObject.append("ReponseTime", childObject1);
			parentObject.append("TPS", childObject2);
			parentObject.append("Errors", childObject3);

		}
		System.out.println(parentObject.toString());
		return parentObject.toString();

	}

	public String getTransactionDetailsForLastestBuild() throws Exception {
		JSONObject jsonObject = new JSONObject();
		List transaction = jmeterDao.getTransactionDetailsForLastestBuild();
		for (int i = 0; i < transaction.size(); i++) {
			JSONObject child = new JSONObject();
			JmeterTransactionDetailsModel loadTransactionDetails = (JmeterTransactionDetailsModel) transaction.get(i);
			child.put("transactionName", loadTransactionDetails.getTransactionName());
			child.put("min", round((loadTransactionDetails.getMin_time() / 1000) % 60, 3));
			child.put("avg", round((loadTransactionDetails.getAvg_time() / 1000) % 60, 3));
			child.put("max", round((loadTransactionDetails.getMax_time() / 1000) % 60, 3));

			jsonObject.append(loadTransactionDetails.getTransactionName(), child);
		}
		System.out.println(jsonObject.toString());
		return jsonObject.toString();

	}

	public List<LennoxProjectDetails> getTestIdInfoFromLennoxDB(String testId)
			throws Exception {
		List<LennoxProjectDetails> testDetails = jmeterDao.getTestIdInfoFromLennoxDB(testId);
		return testDetails;

	}

	public List<ProjectModel> getProjectNameFromLennoxDB(String projectId)
			throws Exception {
		List<ProjectModel> projectDetails = jmeterDao
				.getProjectNameFromLennoxDB(projectId);
		// Above list should contain only 1 record...
		System.out.println(projectDetails);
		return projectDetails;

	}

	public String getTransactionPerSecondWithBuild() throws Exception {

		List list = jmeterDao.getTransactionPerSecondWithBuild();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {

			JmeterAggrigateModel obje = (JmeterAggrigateModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()),
				/* obje.getAvgHits()+ */(obje.getAvg_throughput() / (1000)) % 60);

			} else {
				jsonObject.put(String.valueOf(obje.getBuildNo()),
				/* obje.getAvgHits() */(obje.getAvg_throughput() / (1000)) % 60);

			}

		}
		System.out.println("jmeter bar graph: " + jsonObject);
		return jsonObject.toString();

	}

	public String getResponseTimeForPreviousBuilds() throws Exception {

		List list = jmeterDao.getTransactionPerSecondWithBuild();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {

			JmeterAggrigateModel obje = (JmeterAggrigateModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()), round((obje.getAvg_time() / (1000)) % 60, 3));

			} else {
				jsonObject.put(String.valueOf(obje.getBuildNo()), round((obje.getAvg_time() / (1000)) % 60, 3));

			}

		}
		System.out.println("jmeter trans per sec : " + jsonObject);
		return jsonObject.toString();

	}

	public String getErrorPercentileTrend() throws Exception {

		List list = jmeterDao.getTransactionPerSecondWithBuild();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {

			JmeterAggrigateModel obje = (JmeterAggrigateModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getErrorpercentage());

			} else {
				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getErrorpercentage());

			}

		}
		System.out.println("jmeter trans per sec : " + jsonObject);
		return jsonObject.toString();

	}

	public String getErrorTimeForLastestBuild() throws Exception {

		List list = jmeterDao.getErrorTimeForLastestBuild();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {

			JmeterErrorsTimeModel obje = (JmeterErrorsTimeModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getElapsedTime()), obje.getResponseTime());

			} else {
				jsonObject.put(String.valueOf(obje.getElapsedTime()), obje.getResponseTime());

			}

		}
		System.out.println("jmeter trans per sec : " + jsonObject);
		return jsonObject.toString();

	}

	public String getResponseTimeLastestBuild() throws Exception {

		List list = jmeterDao.getResponseTimeLastestBuild();
		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {

			JmeterResponsetimesModel obje = (JmeterResponsetimesModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getElapsedTime()), obje.getResponseTime());

			} else {
				jsonObject.put(String.valueOf(obje.getElapsedTime()), obje.getResponseTime());

			}

		}
		System.out.println("jmeter trans per sec : " + jsonObject);
		return jsonObject.toString();

	}

	public String getTestExecutionSummary() throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONObject child1 = new JSONObject();
		JSONObject child2 = new JSONObject();
		JSONObject child3 = new JSONObject();

		List tableList = jmeterDao.getlastTwoDetails();
		JmeterAggrigateModel graphModel = (JmeterAggrigateModel) tableList.get(0);
		//
		child1.put("projectName", "Konakart");
		child1.put("date", graphModel.getTestdate());
		child1.put("duration", graphModel.getTestDuration());
		child1.put("users", graphModel.getVuser());
		jsonObject.append("Header", child1);

		child2.put("hits", graphModel.getAvghits());
		child2.put("samples", graphModel.getAvgsamples());
		child2.put("response", round((graphModel.getAvg_time() / (1000)) % 60, 3));
		child2.put("throughput", round((graphModel.getAvg_throughput() / (1000)) % 60, 3));
		jsonObject.append("Average", child2);

		child3.put("hits", graphModel.getTotalhits());
		child3.put("samples", graphModel.getTotalsamples());
		child3.put("percentileError", round(graphModel.getErrorpercentage(), 3));
		child3.put("throughput", graphModel.getTotalThroughput());
		jsonObject.append("Total", child3);

		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<LennoxProjectDetails>  lastFivebuild() throws org.codehaus.jackson.JsonGenerationException, org.codehaus.jackson.map.JsonMappingException, IOException {
		ArrayList<LennoxProjectDetails> lastFiveBuilds = null;
		ArrayList<LennoxProjectDetails> lastFiveBuildsWithMaxUsers = new ArrayList<LennoxProjectDetails>();
		try {
			lastFiveBuilds = (ArrayList<LennoxProjectDetails>) jmeterDao.getLastSixBuildsForInflux();
			for(LennoxProjectDetails lpd : lastFiveBuilds) {
				InfluxUserProfileBean iupb = influxHelper.getUserProfileInfo(lpd.getTestname());
				lpd.setNoOfUsers(iupb.getMaxThreads());
				lastFiveBuildsWithMaxUsers.add(lpd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastFiveBuildsWithMaxUsers;
	}

	public String getAllTestNos() throws org.codehaus.jackson.JsonGenerationException, org.codehaus.jackson.map.JsonMappingException, IOException {
		ArrayList<LennoxProjectDetails> allTestBuilds = null;
		ArrayList<LennoxProjectDetails> allTestBuildsUpdated = new ArrayList<LennoxProjectDetails>();
		try {
			allTestBuilds = (ArrayList<LennoxProjectDetails>) jmeterDao.getAllTestForInflux();
			for(int i=0;i<allTestBuilds.size();i++) {
				LennoxProjectDetails obj=new LennoxProjectDetails();
				obj=allTestBuilds.get(i);
				if(null!=obj.getTestupdatedname())	{
					obj.setTestsuite(obj.getTestupdatedname());
				}
				String date=obj.getTeststarttime();
				obj.setTeststarttime(date);
				allTestBuildsUpdated.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String json = new ObjectMapper().writeValueAsString(allTestBuildsUpdated);
		return json;
	}
	
	public String getinfluxProjectDetails()
	{
		Properties prop = new Properties();
		RestTemplate restTemplate=new RestTemplate();
		InputStream input = null;
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");
		try {
			prop.load(input);
			String query="select avg,project from "+prop.getProperty("delta")+" group By \"suite\" limit 1";
			UriComponents uriComponents1 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl1"))
					.queryParam("q", query).queryParam("db", prop.getProperty("influxDb1")).build();
		ResponseEntity<String> result = restTemplate.getForEntity(uriComponents1.toUri(), String.class);
		//System.out.println("Response Recevied :"+result.getBody());
		String res= result.getBody();
		try {
			JSONObject resp=new JSONObject(res);
			JSONArray arr=resp.getJSONArray("results");
			resp=(JSONObject) arr.get(0);
			arr=(JSONArray) resp.get("series");
		//	System.out.println("values:"+arr);
			//arr=(JSONArray)arr.get;
			
			JSONArray array=new JSONArray();
			for(int i=0;i<arr.length();i++)
			{
			//	System.out.println("Values In Loop:"+arr.get(i));
				JSONObject object=(JSONObject) arr.get(i);
			//	
				
				JSONArray values=object.getJSONArray("values");
			///	System.out.println("****Values:"+values);
				values=(JSONArray) values.get(0);
				String projectName=(String) values.get(2);
				JSONObject suiteObject=object.getJSONObject("tags");
				String suiteName=(String) suiteObject.get("suite");
				JSONObject json=new JSONObject();
				json.put("project", projectName);
				json.put("suite", suiteName);
				array.put(json);
				
			}
			System.out.println("array:"+array);
			return array.toString();
			//arr=arr.toJSONObject("series");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("no Project Found under table delta");
			e.printStackTrace();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
