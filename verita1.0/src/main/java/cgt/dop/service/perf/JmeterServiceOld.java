package cgt.dop.service.perf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cgt.dop.dao.impl.JmeterDaoOld;
import cgt.dop.model.JmeterAggrigateModel;
import cgt.dop.model.JmeterErrorsTimeModel;
import cgt.dop.model.JmeterProjectDetails;
import cgt.dop.model.JmeterResponsetimesModel;
import cgt.dop.model.JmeterTransactionDetailsModel;

@Repository
public class JmeterServiceOld {

	@Autowired
	JmeterDaoOld jmeterDao;

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
	
	
	public String getKPIImprovementForLastBuildFromInflux() throws Exception {
		
		Properties prop = new Properties();
		String pre_query = "";
		InputStream input = null;
		ResponseEntity<String> result = null;
		Map map = new HashMap();
		
		JSONObject parentObject = new JSONObject();

		JSONObject childObject1 = new JSONObject();
		JSONObject childObject2 = new JSONObject();
		JSONObject childObject3 = new JSONObject();
		
		
		try {
			input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");// new
																							// FileInputStream("influxdb.properties");
			prop.load(input);
			List transaction = jmeterDao.getKPIDetailsFromInflux();
			RestTemplate restTemplate = new RestTemplate();
			
			/*for (int i = 0; i < transaction.size(); i++) {*/
				
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(0);
				
				String query1 = "SELECT Mean(\"duration\") FROM \"samples\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents1 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents1.toUri(), String.class);
				
				JSONObject respTimeJson = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(respTimeJson.getJSONArray("results").toString());
				respTimeJson = (JSONObject) obj1.get(0);
				respTimeJson = (JSONObject) respTimeJson.getJSONArray("series").get(0);
				childObject1.put("currentvalue", respTimeJson.getJSONArray("values").getJSONArray(0).get(1));
				
				
				
				String query = "SELECT Sum(\"err\") FROM \"delta\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject errorjson = new JSONObject(result.getBody());
				
				JSONArray obj2 = new JSONArray(errorjson.getJSONArray("results").toString());
				errorjson = (JSONObject) obj2.get(0);
				errorjson = (JSONObject) errorjson.getJSONArray("series").get(0);
					
				childObject2.put("currentvalue", errorjson.getJSONArray("values").getJSONArray(0).get(1));
				
				
            JmeterProjectDetails projectDetails2 = (JmeterProjectDetails) transaction.get(1);
				
				String query2 = "SELECT Mean(\"duration\") FROM \"samples\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents2 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query2).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents2.toUri(), String.class);
				
				JSONObject respTimeJson1 = new JSONObject(result.getBody());
				JSONArray obj3 = new JSONArray(respTimeJson1.getJSONArray("results").toString());
				respTimeJson1 = (JSONObject) obj3.get(0);
				respTimeJson1 = (JSONObject) respTimeJson1.getJSONArray("series").get(0);
				childObject1.put("Prevvalue", respTimeJson1.getJSONArray("values").getJSONArray(0).get(1));
				
				
				
				String query3 = "SELECT Sum(\"err\") FROM \"delta\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents3 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query3).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents3.toUri(), String.class);
				JSONObject errorjson1 = new JSONObject(result.getBody());
				
				JSONArray obj4 = new JSONArray(errorjson1.getJSONArray("results").toString());
				errorjson1 = (JSONObject) obj4.get(0);
				errorjson1 = (JSONObject) errorjson1.getJSONArray("series").get(0);
					
				childObject2.put("Prevvalue", errorjson1.getJSONArray("values").getJSONArray(0).get(1));
				
				parentObject.append("ReponseTime", childObject1);
				parentObject.append("Errors", childObject2);
				
				
			/*}*/
		}catch(Exception e)
		{
			e.printStackTrace();
		}
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

	
	public String getTransactionDetailsForLastestBuildFromInflux() throws Exception {
		  JSONObject jsonObject = new JSONObject();
		  Properties prop=new Properties();
		  InputStream input = null;
		  try{
		  //output = new FileOutputStream("influxdb.properties");
		  input =getClass().getClassLoader().getResourceAsStream("influxdb.properties");

		  // load a properties file
		  prop.load(input);
		  List transaction = jmeterDao.getTransactionDetailsForLastestBuildFromInflux();
		  JmeterProjectDetails projectDetails=(JmeterProjectDetails) transaction.get(0);
		  RestTemplate restTemplate = new RestTemplate();
		     input.close();
		     //UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = 'Konakart' AND time>=1471851590000ms AND time<=1471851650000ms").queryParam("db", "jmeter").build();//queryParam("", "").build();//.queryParams(params).build();
		//  UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms").queryParam("db", prop.getProperty("influxDb")).build();
		     UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "select min(\"duration\"),max(\"duration\"),mean(\"duration\"), count(\"duration\") from samples where \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms group by \"label\"").queryParam("db", prop.getProperty("influxDb")).build();
		     
		     System.out.println(uriComponents.toUri());
		     ResponseEntity<String> result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
		     System.out.println(result.getBody());
		     JSONObject obj = new JSONObject(result.getBody());
		     JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
		 	obj = (JSONObject) obj1.get(0);
		 	
		 	JSONArray array= obj.getJSONArray("series");
		 	for (int i = 0; i < array.length(); i++) {
		 		JSONObject child = new JSONObject();
				//System.out.println(array.get(i));
				obj = (JSONObject) array.get(i);
				System.out.println(obj);
				
				JSONArray array1= obj.getJSONArray("values").getJSONArray(0);
				System.out.println(array1);
				
				child.put("transactionName", obj.getJSONObject("tags").getString("label"));
				child.put("min",array1.get(1));			
				child.put("max", array1.get(2));
				double db = Math.round((double)array1.get(3));
				child.put("avg", db);
				
				jsonObject.append(obj.getJSONObject("tags").getString("label"), child);
				
				//JSONArray array1= obj.getJSONArray("values").getJSONArray(0);
				/*for (int j = 0; j < array1.length(); j++) {
					System.out.println(array1.get(i));
				}*/
			}
		 	/*obj = (JSONObject) obj.getJSONArray("series").get(0);
			
			System.out.println(obj.getJSONArray("values").getJSONArray(0).get(1));*/
		
		  }catch(Exception e)
		  {
		   e.printStackTrace();
		  }
		  return jsonObject.toString();

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

	
	public String getTransactionPerSecondWithBuildFromInflux() throws Exception {
		  
		  JSONObject jsonObject = new JSONObject();
		  Properties prop=new Properties();
		  String pre_query="";
		  InputStream input = null;
		  ResponseEntity<String> result=null;
		  JSONObject maap;
		  Map map=new HashMap();
		  try{
		  input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		  prop.load(input);
		  List transaction = jmeterDao.getTransactionPerSecondWithBuildInflux();
		  String query="SELECT SUM(\"count\") from delta WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		  RestTemplate restTemplate = new RestTemplate();
		  
		  for(int i=0;i<transaction.size();i++)
		  {
		   JmeterProjectDetails projectDetails1=(JmeterProjectDetails) transaction.get(i);
		   long testtime=Long.valueOf(projectDetails1.getTestendtime())-Long.valueOf(projectDetails1.getTeststarttime());
		   testtime=testtime/1000;
		   String query1=query+"AND time>="+projectDetails1.getTeststarttime()+"ms AND time<="+projectDetails1.getTestendtime()+"ms;";
		   pre_query="";
		   pre_query= pre_query+query1;
		   UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
		   result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
		   JSONObject obj= new JSONObject(result.getBody());
		   JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
		   obj=(JSONObject) obj1.get(0);
		   obj=(JSONObject) obj.getJSONArray("series").get(0);
		   String count=obj.getJSONArray("values").getJSONArray(0).get(1).toString();
		   
		   map.put(projectDetails1.getBuildNo(), /*obj.getJSONArray("values").getJSONArray(0).get(1)*/Long.valueOf(count)/testtime);
		    
		  }
		  
		     input.close();
		     //UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = 'Konakart' AND time>=1471851590000ms AND time<=1471851650000ms").queryParam("db", "jmeter").build();//queryParam("", "").build();//.queryParams(params).build();
		  //UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms").queryParam("db", prop.getProperty("influxDb")).build();
		    
		     System.out.println("****map:"+map);
		  }catch(Exception e)
		  {
		   e.printStackTrace();
		  }
		  //return jsonObject.toString();
		  return new JSONObject(map).toString();
		  //return pre_query;

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

	public String getResponseTimeForPreviousBuildsFromInflux() throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		Properties prop = new Properties();
		String pre_query = "";
		InputStream input = null;
		ResponseEntity<String> result = null;
		JSONObject maap;
		Map map = new HashMap();
		try {

			input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");
			prop.load(input);
			List transaction = jmeterDao.getTransactionPerSecondWithBuildInflux();

			String query = "SELECT Mean(\"duration\") FROM \"samples\" WHERE \"project\" = '"
					+ prop.getProperty("projectname") + "'";
			RestTemplate restTemplate = new RestTemplate();

			for (int i = 0; i < transaction.size(); i++) {
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(i);
				String query1 = query + "AND time>=" + projectDetails1.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				pre_query = "";
				pre_query = pre_query + query1;
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject obj = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
				obj = (JSONObject) obj1.get(0);
				obj = (JSONObject) obj.getJSONArray("series").get(0);
				map.put(projectDetails1.getBuildNo(), obj.getJSONArray("values").getJSONArray(0).get(1));

			}

			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject(map).toString();

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

	/*public String getErrorPercentileTrendFromInflux() throws Exception {

		Properties prop = new Properties();
		String pre_query = "";
		InputStream input = null;
		ResponseEntity<String> result = null;
		Map map = new HashMap();
		try {
			input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");// new
																							// FileInputStream("influxdb.properties");
			prop.load(input);
			List transaction = jmeterDao.getTransactionPerSecondWithBuildInflux();
			pre_query = "";

			String query = "select sum(\"err\") from \"delta\" WHERE \"project\" = '" + prop.getProperty("projectname")
					+ "'";
			RestTemplate restTemplate = new RestTemplate();
			for (int i = 0; i < transaction.size(); i++) {
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(i);
				String query1 = query + "AND time>=" + projectDetails1.getTeststarttime() + " AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				pre_query = "";
				pre_query = pre_query + query1;
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
				System.out.println("uriComponents.toUri():" + uriComponents.toUri());
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject obj = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
				obj = (JSONObject) obj1.get(0);
				obj = (JSONObject) obj.getJSONArray("series").get(0);
				map.put(projectDetails1.getBuildNo(), obj.getJSONArray("values").getJSONArray(0).get(1));

			}

			input.close();

			System.out.println("****map:" + map);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject(map).toString();

	}*/
	
	public String getErrorPercentileTrendFromInflux() throws Exception {
		  Properties prop=new Properties();
		  List list = jmeterDao.getLatestBuildFromInflux();
		  JSONObject jsonObject = new JSONObject();
		  InputStream input = null;
		  ResponseEntity<String> result=null;
		  Map map=new HashMap();
		  
		  input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		  prop.load(input);
		  String query="select \"err\" from delta WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		  RestTemplate restTemplate = new RestTemplate();
		  if (list.size() > 0) {
		   
		   JmeterProjectDetails protectDetails=new JmeterProjectDetails();
		   protectDetails=(JmeterProjectDetails) list.get(0);
		   String query1=query+"AND time>="+protectDetails.getTeststarttime()+"ms AND time<="+protectDetails.getTestendtime()+"ms;";
		   UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
		    result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
		    JSONObject obj= new JSONObject(result.getBody());
		    JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
		   obj=(JSONObject) obj1.get(0);
		   obj=(JSONObject) obj.getJSONArray("series").get(0);
		   obj1= obj.getJSONArray("values");
		   /*System.out.println("jmeter trans per sec : " + obj1);
		   System.out.println("Size of array:"+obj1.length());
		   System.out.println(obj1.get(0));*/
		   
		   Map object=new LinkedHashMap();
		   
		   for(int i=0;i<obj1.length();i++)
		   {
		    JSONArray aar=new JSONArray(obj1.get(i).toString());
		    DateTime elasedTime = new DateTime(aar.getString(0));
		    long elapsed=elasedTime.getMillis()-Long.valueOf(protectDetails.getTeststarttime());//startdate.getTime();
		    object.put(elapsed, aar.getString(1));
		   }
		   return new JSONObject(object).toString();
		   //return obj1.toString();
		  }
		 
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
	
	
	public String getResponseTimeLastestBuildfromInflux() throws Exception {
		  Properties prop=new Properties();
		  List list = jmeterDao.getLatestBuildFromInflux();
		  JSONObject jsonObject = new JSONObject();
		  InputStream input = null;
		  ResponseEntity<String> result=null;
		  Map map=new HashMap();
		  
		  input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		  prop.load(input);
		  String query="select \"duration\" from \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		  RestTemplate restTemplate = new RestTemplate();
		  if (list.size() > 0) {
		   
		   JmeterProjectDetails protectDetails=new JmeterProjectDetails();
		   protectDetails=(JmeterProjectDetails) list.get(0);
		   String query1=query+"AND time>="+protectDetails.getTeststarttime()+"ms AND time<="+protectDetails.getTestendtime()+"ms;";
		   UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
		    result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
		    JSONObject obj= new JSONObject(result.getBody());
		    JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
		   obj=(JSONObject) obj1.get(0);
		   obj=(JSONObject) obj.getJSONArray("series").get(0);
		   obj1= obj.getJSONArray("values");
		   System.out.println("jmeter trans per sec : " + obj1);
		   System.out.println("Size of array:"+obj1.length());
		   System.out.println(obj1.get(0));
		   
		   Map object=new LinkedHashMap();
		   
		   for(int i=0;i<obj1.length();i++)
		   {
		    JSONArray aar=new JSONArray(obj1.get(i).toString());
		    DateTime elasedTime = new DateTime(aar.getString(0));
		    long elapsed=elasedTime.getMillis()-Long.valueOf(protectDetails.getTeststarttime());//startdate.getTime();
		    object.put(elapsed, aar.getString(1));
		   }
		   return new JSONObject(object).toString();
		   //return obj1.toString();
		  }
		 
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

}
