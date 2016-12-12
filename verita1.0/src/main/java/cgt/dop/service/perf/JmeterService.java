package cgt.dop.service.perf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import cgt.dop.alm.log.Debug;
import cgt.dop.alm.log.Error;
import cgt.dop.dao.impl.JmeterDao;
import cgt.dop.dao.impl.ProjectDao;
import cgt.dop.model.JmeterAggrigateModel;
import cgt.dop.model.JmeterErrorsTimeModel;
import cgt.dop.model.JmeterProjectDetails;
import cgt.dop.model.JmeterResponsetimesModel;
import cgt.dop.model.JmeterTransactionDetailsModel;

import org.joda.time.DateTime;

@Repository
public class JmeterService {

	@Autowired
	JmeterDao jmeterDao;

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
	
	/*public String getKPIImprovementForLastBuildFromInflux() throws Exception {
		JSONObject parentObject = new JSONObject();

		JSONObject childObject1 = new JSONObject();
		JSONObject childObject2 = new JSONObject();
		JSONObject childObject3 = new JSONObject();

		List compare = jmeterDao.getlastTwoDetails();
		if (compare != null) {
			JmeterAggrigateModel object1 = (JmeterAggrigateModel) compare.get(0);
			JmeterAggrigateModel object2 = (JmeterAggrigateModel) compare.get(1);

			childObject1.put("Prevvalue", String.valueOf(round((object2.getAvg_time() / (1000)) % 60,3)));
			childObject1.put("currentvalue", String.valueOf(round((object1.getAvg_time() / (1000)) % 60,3)));

			childObject2.put("Prevvalue", String.valueOf(round((object2.getAvg_throughput() / (1000)) % 60,3)));
			childObject2.put("currentvalue", String.valueOf(round((object1.getAvg_throughput() / (1000)) % 60,3)));

			childObject3.put("Prevvalue", String.valueOf(round(object2.getErrorpercentage(),3)));
			childObject3.put("currentvalue", String.valueOf(round(object1.getErrorpercentage(),3)));

			parentObject.append("ReponseTime", childObject1);
			parentObject.append("TPS", childObject2);
			parentObject.append("Errors", childObject3);

		}
		System.out.println(parentObject.toString());
		return parentObject.toString();

	}
	*/
	
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
				
				String query1 = "SELECT Mean(\"avg\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + "ms AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				
				UriComponents uriComponents1 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
				
				System.out.println("KPI Query:"+uriComponents1.toUri());
				result = restTemplate.getForEntity(uriComponents1.toUri(), String.class);
				
				JSONObject respTimeJson = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(respTimeJson.getJSONArray("results").toString());
				respTimeJson = (JSONObject) obj1.get(0);
				respTimeJson = (JSONObject) respTimeJson.getJSONArray("series").get(0);
				childObject1.put("currentvalue", round(Double.valueOf(respTimeJson.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,1));
				
				String query = "SELECT Sum(\"err\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + "ms AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject errorjson = new JSONObject(result.getBody());
				JSONArray obj2 = new JSONArray(errorjson.getJSONArray("results").toString());
				errorjson = (JSONObject) obj2.get(0);
				errorjson = (JSONObject) errorjson.getJSONArray("series").get(0);
				childObject2.put("currentvalue", round(Double.valueOf(errorjson.getJSONArray("values").getJSONArray(0).get(1).toString()),2));
				System.out.println("**********"+round(Double.valueOf(errorjson.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				
				
				JmeterProjectDetails projectDetails2 = (JmeterProjectDetails) transaction.get(1);
				
				String query2 = "SELECT Mean(\"avg\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + "ms AND time<="
						+ projectDetails2.getTestendtime() + "ms;";
				
				UriComponents uriComponents2 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query2).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents2.toUri(), String.class);
				System.out.println("uriComponents2.toUri():"+uriComponents2.toUri());
				JSONObject respTimeJson1 = new JSONObject(result.getBody());
				JSONArray obj3 = new JSONArray(respTimeJson1.getJSONArray("results").toString());
				respTimeJson1 = (JSONObject) obj3.get(0);
				respTimeJson1 = (JSONObject)  respTimeJson1.getJSONArray("series").get(0);
				childObject1.put("Prevvalue", round(Double.valueOf(respTimeJson1.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				
				
				String query3 = "SELECT Sum(\"err\") FROM \"total\" WHERE \"project\" = '"
				+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + "ms AND time<="
				+ projectDetails2.getTestendtime() + "ms;";
				
				UriComponents uriComponents3 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query3).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents3.toUri(), String.class);
				JSONObject errorjson1 = new JSONObject(result.getBody());
				
				JSONArray obj4 = new JSONArray(errorjson1.getJSONArray("results").toString());
				errorjson1 = (JSONObject) obj4.get(0);
				errorjson1 = (JSONObject) errorjson1.getJSONArray("series").get(0);
					
				childObject2.put("Prevvalue", round(Double.valueOf(errorjson1.getJSONArray("values").getJSONArray(0).get(1).toString()),2));
				
				JSONObject tpsJson=new JSONObject(getKPITPSForLastTwoBuilds());
				System.out.println("tpsJson: "+ tpsJson);
				
				int count=0;
				for (final Iterator<String> iter = tpsJson.keys(); iter.hasNext();) {
					count++;
						if(count==0){
				    final String key = iter.next();

				    try {
				    	System.out.println("****tpsJson"+tpsJson.get(key));
				        final Object value = tpsJson.get(key);
				        childObject3.put("currentvalue", round(Double.valueOf(value.toString()), 1));
				      /*  final JSONArray error = (JSONArray) value;
				        System.out.println(key);
				        System.out.println(error.get(0).toString());*/
				    } catch (final JSONException e) {
				        // Something went wrong!
				    }
						}
						if(count==1){
							
						    final String key = iter.next();

						    try {
						    	System.out.println("****tpsJson 6"+tpsJson.get(key));
						        final Object value = tpsJson.get(key);
						       
						        childObject3.put("Prevvalue", round(Double.valueOf(value.toString()), 1));
						      /*  final JSONArray error = (JSONArray) value;
						        System.out.println(key);
						        System.out.println(error.get(0).toString());*/

						    } catch (final JSONException e) {
						        // Something went wrong!
						    }
								}
					}
				
				System.out.println("**********"+childObject3);
				parentObject.append("ReponseTime", childObject1);
				parentObject.append("Errors", childObject2);
				parentObject.append("TPS", childObject3);
				
				
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
		  /*RestTemplate restTemplate = new RestTemplate();
	      String url = "http://172.16.3.30:8086/query";
	      
	      UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(url).queryParam("q", "SELECT mean(\"duration\") FROM \"samples\" WHERE \"project\" ='Konakart' AND time>=1471851590000ms AND time<=1471851650000ms").queryParam("db", "jmeter").build();//queryParam("", "").build();//.queryParams(params).build();
	      System.out.println(uriComponents.toUri());
	      ResponseEntity<String> result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
	      System.out.println(result.getBody());*/

		System.out.println(jsonObject.toString());
		return jsonObject.toString();

	}
	
/*	public String getTransactionDetailsForLastestBuildFromInflux() throws Exception {
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
//		UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms").queryParam("db", prop.getProperty("influxDb")).build();
	    UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "select min(\"duration\"),max(\"duration\"),mean(\"duration\"), count(\"duration\") from samples where \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms group by \"label\"").queryParam("db", prop.getProperty("influxDb")).build();
	    
	    System.out.println(uriComponents.toUri());
	    ResponseEntity<String> result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
	    System.out.println(result.getBody());
		System.out.println(jsonObject.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return jsonObject.toString();
	}*/

	

	
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
		    // System.out.println("Transaction details:"+);
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
				child.put("min",round(Double.valueOf(array1.get(1).toString())/1000,1));			
				child.put("max", round(Double.valueOf(array1.get(2).toString())/1000,1));
				child.put("avg", round(Double.valueOf(array1.get(3).toString())/1000,1));
				
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
		input.close();
		List transaction = jmeterDao.getTransactionPerSecondWithBuildInflux();
		String query="SELECT SUM(\"count\") from total WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		RestTemplate restTemplate = new RestTemplate();
		for(int i=0;i<transaction.size();i++)
		{
			JmeterProjectDetails projectDetails1=(JmeterProjectDetails) transaction.get(i);
			System.out.println("projectDetails1.getTransactionTrend(): "+projectDetails1.getSno()+"  "+ projectDetails1.getTransactionTrend());
			
			if(null!=projectDetails1.getTransactionTrend())
			{
				map.put(projectDetails1.getTestname(), projectDetails1.getTransactionTrend());
			}
			else{
			long testtime=Long.valueOf(projectDetails1.getTestendtime())-Long.valueOf(projectDetails1.getTeststarttime());
			testtime=testtime/1000;
			String query1=query+"AND time>="+projectDetails1.getTeststarttime()+"ms AND time<="+projectDetails1.getTestendtime()+"ms;";
			pre_query="";
			pre_query=	pre_query+query1;
			UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			System.out.println("uriComponents.toUri():"+uriComponents.toUri());
			JSONObject obj= new JSONObject(result.getBody());
			System.out.println("Transaction:"+obj);
			JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			//System.out.println("*****"+obj.getJSONArray("values").getJSONArray(0).get(1));
			String count= obj.getJSONArray("values").getJSONArray(0).get(1).toString();
			map.put(projectDetails1.getTestname(), /*obj.getJSONArray("values").getJSONArray(0).get(1)*/Long.valueOf(count)/testtime);
			projectDetails1.setTransactionTrend(String.valueOf(Double.valueOf(count)/testtime));
			jmeterDao.setTransactionTrendvaluetoMysql(projectDetails1);
			}
			 
		}
		
	 
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

	
	/*public String getTransactionPerSecondWithBuildFromInflux() throws Exception {
		  
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
		   String count=(String) obj.getJSONArray("values").getJSONArray(0).get(1);
		   
		   map.put(projectDetails1.getBuildNo(), obj.getJSONArray("values").getJSONArray(0).get(1)Long.valueOf(count)/testtime);
		    
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

		 }*/
	
	
	
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
	/*public String getResponseTimeForPreviousBuildsFromInflux() throws Exception {
		JSONObject jsonObject = new JSONObject();
		Properties prop=new Properties();
		String pre_query="";
		InputStream input = null;
		ResponseEntity<String> result=null;
		JSONObject maap;
		Map map=new HashMap();
		try{
		//output = new FileOutputStream("influxdb.properties");
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		prop.load(input);
		List transaction = jmeterDao.getTransactionPerSecondWithBuildInflux();
		
		String query="SELECT Mean(\"duration\") FROM \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		RestTemplate restTemplate = new RestTemplate();
//		DateTimeParser d1 = new DateTimeParser();
	//	DateTime d2 = new DateTime();

		//long diffInMillis = d2.getMillis() - d1.getMillis();
		for(int i=0;i<transaction.size();i++)
		{
			JmeterProjectDetails projectDetails1=(JmeterProjectDetails) transaction.get(i);
			String query1=query+"AND time>="+projectDetails1.getTeststarttime()+"ms AND time<="+projectDetails1.getTestendtime()+"ms;";
			 pre_query="";
			 pre_query=	pre_query+query1;
			 UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
			 result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			 JSONObject obj= new JSONObject(result.getBody());
			 JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			 obj=(JSONObject) obj1.get(0);
			 obj=(JSONObject) obj.getJSONArray("series").get(0);
			 map.put(projectDetails1.getBuildNo(), obj.getJSONArray("values").getJSONArray(0).get(1));
			 
		}
		
	    input.close();
	    //UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = 'Konakart' AND time>=1471851590000ms AND time<=1471851650000ms").queryParam("db", "jmeter").build();//queryParam("", "").build();//.queryParams(params).build();
		//UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", "SELECT max(\"duration\"),Mean(\"duration\"),min(\"duration\") FROM \"samples\" WHERE \"project\" = '"+prop.getProperty("projectname")+"' AND time>="+projectDetails.getTeststarttime()+"ms AND time<="+projectDetails.getTestendtime()+"ms").queryParam("db", prop.getProperty("influxDb")).build();
	   
	    System.out.println("****map:"+map);
		//System.out.println(jsonObject.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//return jsonObject.toString();
		return new JSONObject(map).toString();
		//return pre_query;

	}*/

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

			String query = "SELECT mean(\"avg\") FROM \"total\" WHERE \"project\" = '"
					+ prop.getProperty("projectname") + "'";
			RestTemplate restTemplate = new RestTemplate();

			for (int i = 0; i < transaction.size(); i++) {
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(i);
				if(null!=projectDetails1.getResponseTrend())
				{
				map.put(projectDetails1.getTestname(), Double.valueOf(projectDetails1.getResponseTrend())/1000);
				}
				else{
				String query1 = query + "AND time>=" + projectDetails1.getTeststarttime() + "ms AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				pre_query = "";
				pre_query = pre_query + query1;
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
				System.out.println("Query fired to response Time:"+uriComponents.toUri());
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject obj = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(obj.getJSONArray("results").toString());
				obj = (JSONObject) obj1.get(0);
				obj = (JSONObject) obj.getJSONArray("series").get(0);
				System.out.println("**** Respomse Time"+obj);
				
				map.put(projectDetails1.getTestname(), Double.valueOf(obj.getJSONArray("values").getJSONArray(0).get(1).toString())/1000);
				projectDetails1.setResponseTrend(obj.getJSONArray("values").getJSONArray(0).get(1).toString());
				jmeterDao.setResponseTrendvaluetoMysql(projectDetails1);
				
				}
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
	*/

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

			String query = "SELECT MAX(\"err\") FROM \"total\" WHERE \"project\" = '"
					+ prop.getProperty("projectname") + "'";
			RestTemplate restTemplate = new RestTemplate();

			for (int i = 0; i < transaction.size(); i++) {
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(i);
				
				if(null!=projectDetails1.getErrorTrend())
				{
				map.put(projectDetails1.getTestname(), projectDetails1.getErrorTrend());
				}
				else{
				String query1 = query + "AND time>=" + projectDetails1.getTeststarttime() + "ms AND time<="
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
				map.put(projectDetails1.getTestname(), obj.getJSONArray("values").getJSONArray(0).get(1));
				projectDetails1.setErrorTrend(obj.getJSONArray("values").getJSONArray(0).get(1).toString());
				jmeterDao.setErrorTrendvaluetoMysql(projectDetails1);
				}
			}

			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject(map).toString();

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
	
	
	public String getErrorTimeForLastestBuildFromInflux() throws Exception {
		  Properties prop=new Properties();
		  List list = jmeterDao.getLatestBuildFromInflux();
		  JSONObject jsonObject = new JSONObject();
		  InputStream input = null;
		  ResponseEntity<String> result=null;
		  Map map=new HashMap();
		  
		  input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		  prop.load(input);
		  String query="select \"errpct\" from total WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		  RestTemplate restTemplate = new RestTemplate();
		  if (list.size() > 0) {
		  // System.out.println(query);
		   JmeterProjectDetails protectDetails=new JmeterProjectDetails();
		   protectDetails=(JmeterProjectDetails) list.get(0);
		   String query1=query+"AND time>="+protectDetails.getTeststarttime()+"ms AND time<="+protectDetails.getTestendtime()+"ms;";
		   UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
		   System.out.println(uriComponents.toUri()); 
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
		    object.put(elapsed/1000, aar.getString(1));
		   }
		   return new JSONObject(object).toString();
		   //return obj1.toString();
		  }		 
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
	//getTransactionPerSecondWithBuildInflux
	
	/*public String getResponseTimeLastestBuildfromInflux() throws Exception {
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

	}*/

	public String getResponseTimeLastestBuildfromInflux() throws Exception {
		  Properties prop=new Properties();
		  List list = jmeterDao.getLatestBuildFromInflux();
		  JSONObject jsonObject = new JSONObject();
		  InputStream input = null;
		  ResponseEntity<String> result=null;
		  Map map=new HashMap();
		  
		  input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		  prop.load(input);
		  String query="select \"avg\" from \"total\" WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
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
		   System.out.println("jmeter response second per second : " + obj1);
		   System.out.println("Size of array:"+obj1.length());
		   System.out.println(obj1.get(0));
		   DateTime firstTime=new DateTime();
		   Map object=new LinkedHashMap();
		   object.put("0","0");
		   for(int i=0;i<obj1.length();i++)
		   {
			 /*  if(i==0)
			   {
				   JSONArray aar=new JSONArray(obj1.get(i).toString());
				   firstTime = new DateTime(aar.getString(0));
				   System.out.println("Time stamps: "+protectDetails.getTeststarttime()+ "     "+firstTime.getMillis());
				   object.put(Long.valueOf(protectDetails.getTeststarttime())-firstTime.getMillis(), aar.getString(1));
			   }
			   else{*/
		    JSONArray aar=new JSONArray(obj1.get(i).toString());
		    DateTime elasedTime = new DateTime(aar.getString(0));
		    long elapsed=elasedTime.getMillis()-/*-firstTime.getMillis();*/Long.valueOf(protectDetails.getTeststarttime());//startdate.getTime();
		   object.put(elapsed/1000, Double.valueOf(aar.getString(1))/1000);
		   // object.put(elapsed/1000, Double.valueOf(aar.getString(1))/1000f);
			  // }
		   }
		   System.out.println("Response time per second:"+new JSONObject(object).toString());
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
		child1.put("projectName", "Konakart");
		child1.put("date", graphModel.getTestdate());
		child1.put("duration", graphModel.getTestDuration());
		child1.put("users", graphModel.getVuser());
		jsonObject.append("Header", child1);

		child2.put("hits", graphModel.getAvghits());
		child2.put("samples", graphModel.getAvgsamples());
		child2.put("response", round((graphModel.getAvg_time()/ (1000)) % 60,3));
		child2.put("throughput", round((graphModel.getAvg_throughput()/ (1000)) % 60,3));
		jsonObject.append("Average", child2);

		child3.put("hits", graphModel.getTotalhits());
		child3.put("samples", graphModel.getTotalsamples());
		child3.put("percentileError", round(graphModel.getErrorpercentage(),3));
		child3.put("throughput", graphModel.getTotalThroughput());
		jsonObject.append("Total", child3);

		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}
	
	public String getTestExecutionSummaryFromInflux() throws Exception {
		
		Properties prop=new Properties();
		List list = jmeterDao.getLatestBuildFromInflux();
		InputStream input = null;
		ResponseEntity<String> result=null;
		Map map=new HashMap();
		
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		prop.load(input);
		input.close();
		
		JSONObject jsonObject = new JSONObject();
		JSONObject child1 = new JSONObject();
		JSONObject child2 = new JSONObject();
		JSONObject child3 = new JSONObject();
		
		//for success samples
		String query="select sum(\"err\"),MEAN(\"err\"),Max(\"errpct\"),Mean(\"avg\") from \"total\" where ";
		//failures
		String error="select sum(\"err\"),MEAN(\"err\") from \"total\" where ";
		//total samples
		String total="select sum(\"count\"),MEAN(\"count\") from \"total\" where ";
		String totalactiveUsers="SELECT Max(\"ath\") FROM \"total\" WHERE ";
		String avgTPSForCurrent="SELECT SUM(\"count\") from total WHERE \"project\" = '"+prop.getProperty("projectname")+"' AND";
		
		String totalerrorCount="",totalSampleCount="",totalSuccessCount="",testname="",buildNo="";
		String testDate=""; String testDuration="",avgerrorCount="",avgsampleCount="",avgsuccess="",errpct="",userCount="",avgresponse="",avgTPS=""; 
		RestTemplate restTemplate = new RestTemplate();
		if (list.size() > 0) {
			
			JmeterProjectDetails protectDetails=new JmeterProjectDetails();
			
			protectDetails=(JmeterProjectDetails) list.get(0);
			buildNo=String.valueOf(protectDetails.getBuildNo());
			//for testing
			/*protectDetails.setTeststarttime("1473249227000");
			protectDetails.setTestendtime("1473249240000");*/
			
			Date TestDate = new Date(Long.valueOf(protectDetails.getTeststarttime()));

			DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss");

			// to convert Date to String, use format method of SimpleDateFormat
			// class.
			 testDate = dateFormat.format(TestDate);

			System.out.println("Date converted to String: " + testDate);
	        
	        testDuration= getTestDuration(protectDetails.getTeststarttime(), protectDetails.getTestendtime());
	        System.out.println("testDuration: " +testDuration );
			
			String constant=" time>="+protectDetails.getTeststarttime()+"ms AND time<="+protectDetails.getTestendtime()+"ms;";
			//error starts here
			String query1=query+constant;
			UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			System.out.println("URL:"+uriComponents.toUri());
			 result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			JSONObject obj= new JSONObject(result.getBody());
			JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			System.out.println(obj1.get(0));
			/*JSONArray avg= new JSONArray(obj1.get(1).toString());*/
			//System.out.println(obj1.get(1));
			obj1= new JSONArray(obj1.get(0).toString());
			
			totalerrorCount=obj1.get(1).toString();
			avgerrorCount=obj1.get(2).toString();//avg.get(1).toString();
			System.out.println("avgerrorCount:"+avgerrorCount);
			errpct=obj1.get(3).toString();
			avgresponse=obj1.get(4).toString();
			//wrong code starts here
			query1=error+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			//System.out.println(obj1.get(0));
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println(obj1.get(1));
			totalSuccessCount=obj1.get(1).toString();
			avgsuccess=obj1.get(2).toString();
			
			//total
			query1=total+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println(obj1.get(1));
			totalSampleCount=obj1.get(1).toString();
			avgsampleCount=obj1.get(2).toString();
			
			//for Total Users
			query1=totalactiveUsers+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			System.out.println("URL:"+uriComponents.toUri());
			//System.out.println("Faling uri;"+);
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println("total Count:"+obj1.get(1));
			userCount=obj1.get(1).toString();
				
			//for avg TPS
			
			query1=avgTPSForCurrent+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println("total Count:"+obj1.get(1));
			avgTPS=obj1.get(1).toString();
			long testtime=Long.valueOf(protectDetails.getTestendtime())-Long.valueOf(protectDetails.getTeststarttime());
			testtime=testtime/1000;
			avgTPS=String.valueOf(Double.valueOf(avgTPS)/testtime);
			//return obj1.toString();
			
			testname=protectDetails.getTestname();
		}
		
		System.out.println("Total count:"+totalSampleCount +"  errorCOunt:"+totalerrorCount +"  total success:"+totalSuccessCount);
		/*List tableList = jmeterDao.getlastTwoDetails();
		JmeterAggrigateModel graphModel = (JmeterAggrigateModel) tableList.get(0);*/
		child1.put("projectName", prop.getProperty("projectname"));
		child1.put("date", testDate);
		child1.put("duration", testDuration);
		child1.put("users", userCount);
		child1.put("testname", testname);
		child1.put("buildno", buildNo);
		jsonObject.append("Header", child1);

		child2.put("hits", "");
		child2.put("samples", round(Double.valueOf(avgTPS),1));
		child2.put("response",/* round((graphModel.getAvg_time() / (1000)) % 60, 3)*/round(Double.valueOf(avgresponse)/1000,1));
		child2.put("errorcount",/*(graphModel.getAvg_throughput() / (1000)) % 60, 3)*/round(Double.valueOf(avgerrorCount),1));
		jsonObject.append("Average", child2);

		child3.put("hits", "");
		child3.put("samples", totalSampleCount);
		child3.put("percentileError", errpct);
		child3.put("errorcount", totalerrorCount);
		jsonObject.append("Total", child3);

		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}
	
public ArrayList getListOfTests()
 {
		ArrayList<JmeterProjectDetails> list = new ArrayList<JmeterProjectDetails>();
		list=(ArrayList) jmeterDao.getListOfTests();
		ArrayList testNo=new ArrayList();
		for(int i=0;i<list.size();i++)
		{
			testNo.add(list.get(i).getTestname());
		}
		
		return testNo;

	}
	
	
public String getTestCompareDetailsFromInflux() throws Exception {
		
		Properties prop=new Properties();
		List list = jmeterDao.getLatestBuildFromInflux();
		InputStream input = null;
		ResponseEntity<String> result=null;
		Map map=new HashMap();
		
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		prop.load(input);
		input.close();
		
		JSONObject jsonObject = new JSONObject();
		JSONObject child1 = new JSONObject();
		JSONObject child2 = new JSONObject();
		JSONObject child3 = new JSONObject();
		
		//for success samples
		String query="select sum(\"err\"),MEAN(\"err\"),Max(\"errpct\") from \"total\" where ";
		//failures
		String error="select sum(\"err\"),MEAN(\"err\") from \"total\" where ";
		//total samples
		String total="select sum(\"count\"),MEAN(\"count\") from \"total\" where ";
		
		String totalactiveUsers="SELECT Max(\"value\") FROM \"jmeterkctest.maxAT\" WHERE ";
		
		String totalerrorCount="",totalSampleCount="",totalSuccessCount="",buildNo="";
		String testDate=""; String testDuration="",avgerrorCount="",avgsampleCount="",avgsuccess="",errpct="",userCount="",avgTPS=""; 
		RestTemplate restTemplate = new RestTemplate();
		if (list.size() > 0) {
			
			JmeterProjectDetails protectDetails=new JmeterProjectDetails();
			
			protectDetails=(JmeterProjectDetails) list.get(0);
			buildNo=String.valueOf(protectDetails.getBuildNo());
			//for testing
			/*protectDetails.setTeststarttime("1473249227000");
			protectDetails.setTestendtime("1473249240000");*/
			
			Date TestDate = new Date(Long.valueOf(protectDetails.getTeststarttime()));
			DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss");
			// to convert Date to String, use format method of SimpleDateFormat
			// class.
			 testDate = dateFormat.format(TestDate);
			System.out.println("Date converted to String: " + testDate);
	        testDuration= getTestDuration(protectDetails.getTeststarttime(), protectDetails.getTestendtime());
	        System.out.println("testDuration: " +testDuration );
			String constant=" time>="+protectDetails.getTeststarttime()+"ms AND time<="+protectDetails.getTestendtime()+"ms;";
			//error starts here
			String query1=query+constant;
			UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			 result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			JSONObject obj= new JSONObject(result.getBody());
			JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			System.out.println(obj1.get(0));
			/*JSONArray avg= new JSONArray(obj1.get(1).toString());*/
			//System.out.println(obj1.get(1));
			obj1= new JSONArray(obj1.get(0).toString());
			
			totalerrorCount=obj1.get(1).toString();
			avgerrorCount=obj1.get(2).toString();//avg.get(1).toString();
			System.out.println("avgerrorCount:"+avgerrorCount);
			errpct=obj1.get(3).toString();
			//wrong code starts here
			query1=error+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			//System.out.println(obj1.get(0));
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println(obj1.get(1));
			totalSuccessCount=obj1.get(1).toString();
			avgsuccess=obj1.get(2).toString();
			
			//total
			query1=total+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println(obj1.get(1));
			totalSampleCount=obj1.get(1).toString();
			avgsampleCount=obj1.get(2).toString();
			
			
			query1=totalactiveUsers+constant;
			uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
			//System.out.println("URL:"+uriComponents.toUri());
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
			obj= new JSONObject(result.getBody());
			obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			obj1= obj.getJSONArray("values");
			//System.out.println("jmeter trans per sec : " + obj1);
			//System.out.println("Size of array:"+obj1.length());
			obj1= new JSONArray(obj1.get(0).toString());
			System.out.println("total Count:"+obj1.get(1));
			userCount=obj1.get(1).toString();
			
			//return obj1.toString();
		}
		
		
		System.out.println("Total count:"+totalSampleCount +"  errorCOunt:"+totalerrorCount +"  total success:"+totalSuccessCount);
		/*List tableList = jmeterDao.getlastTwoDetails();
		JmeterAggrigateModel graphModel = (JmeterAggrigateModel) tableList.get(0);*/
		child1.put("projectName", prop.getProperty("projectname"));
		child1.put("date", testDate);
		child1.put("duration", testDuration);
		child1.put("users", userCount);
		
		jsonObject.append("Header", child1);

		child2.put("hits", "");
		child2.put("samples", round(Double.valueOf(avgsampleCount),3));
		child2.put("response",/* round((graphModel.getAvg_time() / (1000)) % 60, 3)*/round(Double.valueOf(avgsuccess),3));
		child2.put("throughput",/*(graphModel.getAvg_throughput() / (1000)) % 60, 3)*/round(Double.valueOf(avgerrorCount),3));
		jsonObject.append("Average", child2);
		

		child3.put("hits", "");
		child3.put("samples", totalSampleCount);
		child3.put("percentileError", errpct);
		child3.put("throughput", totalerrorCount);
		jsonObject.append("Total", child3);

		System.out.println(jsonObject.toString());
		return jsonObject.toString();
	}
	
	public String getKPITPSForLastTwoBuilds(){
		
		JSONObject jsonObject = new JSONObject();
		Properties prop=new Properties();
		String pre_query="";
		InputStream input = null;
		ResponseEntity<String> result=null;
		JSONObject maap;
		Map map=new LinkedHashMap();
		try{
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		prop.load(input);
		input.close();
		List transaction =jmeterDao.getKPIDetailsFromInflux(); //jmeterDao.getTransactionPerSecondWithBuildInflux();
		String query="SELECT SUM(\"count\") from total WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		RestTemplate restTemplate = new RestTemplate();
		if(transaction.size()>0)
		for(int i=0;i<2;i++)
		{
			JmeterProjectDetails projectDetails1=(JmeterProjectDetails) transaction.get(i);
			System.out.println("Values:"+projectDetails1.getTransactionTrend());
			if(null!=projectDetails1.getTransactionTrend())
			{
				map.put(projectDetails1.getTestname(), projectDetails1.getTransactionTrend());
			}
			else{
			long testtime=Long.valueOf(projectDetails1.getTestendtime())-Long.valueOf(projectDetails1.getTeststarttime());
			testtime=testtime/1000;
			String query1=query+"AND time>="+projectDetails1.getTeststarttime()+"ms AND time<="+projectDetails1.getTestendtime()+"ms;";
			pre_query="";
			pre_query=	pre_query+query1;
			UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
//			System.out.println("uriComponents.toUri():"+uriComponents.toUri());
			JSONObject obj= new JSONObject(result.getBody());
//			System.out.println("Transaction:"+obj);
			JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			//System.out.println("*****"+obj.getJSONArray("values").getJSONArray(0).get(1));
			String count= obj.getJSONArray("values").getJSONArray(0).get(1).toString();
			map.put(projectDetails1.getTestname(), /*obj.getJSONArray("values").getJSONArray(0).get(1)*/Long.valueOf(count)/testtime);
			projectDetails1.setTransactionTrend(String.valueOf(Double.valueOf(count)/testtime));
			jmeterDao.setTransactionTrendvaluetoMysql(projectDetails1);
			}
			 
		}
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
	
	
	public String getKPITPSForLastTwoBuilds(String testto,String testwith)
	{
		
		JSONObject jsonObject = new JSONObject();
		Properties prop=new Properties();
		String pre_query="";
		InputStream input = null;
		ResponseEntity<String> result=null;
		JSONObject maap;
		Map map=new LinkedHashMap();
		try{
		input = getClass().getClassLoader().getResourceAsStream("influxdb.properties");//new FileInputStream("influxdb.properties");
		prop.load(input);
		input.close();
		List transaction =jmeterDao.getKPIDetailsFromInflux(testto,testwith); //jmeterDao.getTransactionPerSecondWithBuildInflux();
		String query="SELECT SUM(\"count\") from total WHERE \"project\" = '"+prop.getProperty("projectname")+"'";
		RestTemplate restTemplate = new RestTemplate();
		if(transaction.size()>0)
		for(int i=0;i<2;i++)
		{
			JmeterProjectDetails projectDetails1=(JmeterProjectDetails) transaction.get(i);
			System.out.println("Values:"+projectDetails1.getTransactionTrend());
			if(null!=projectDetails1.getTransactionTrend())
			{
				map.put(projectDetails1.getTestname(), projectDetails1.getTransactionTrend());
			}
			else{
			long testtime=Long.valueOf(projectDetails1.getTestendtime())-Long.valueOf(projectDetails1.getTeststarttime());
			testtime=testtime/1000;
			String query1=query+"AND time>="+projectDetails1.getTeststarttime()+"ms AND time<="+projectDetails1.getTestendtime()+"ms;";
			pre_query="";
			pre_query=	pre_query+query1;
			UriComponents uriComponents =  UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl")).queryParam("q", pre_query).queryParam("db", prop.getProperty("influxDb")).build();
			result=restTemplate.getForEntity(uriComponents.toUri(), String.class);
//			System.out.println("uriComponents.toUri():"+uriComponents.toUri());
			JSONObject obj= new JSONObject(result.getBody());
//			System.out.println("Transaction:"+obj);
			JSONArray obj1=new JSONArray(obj.getJSONArray("results").toString());
			obj=(JSONObject) obj1.get(0);
			obj=(JSONObject) obj.getJSONArray("series").get(0);
			//System.out.println("*****"+obj.getJSONArray("values").getJSONArray(0).get(1));
			String count= obj.getJSONArray("values").getJSONArray(0).get(1).toString();
			map.put(projectDetails1.getTestname(), /*obj.getJSONArray("values").getJSONArray(0).get(1)*/Long.valueOf(count)/testtime);
			projectDetails1.setTransactionTrend(String.valueOf(Double.valueOf(count)/testtime));
			jmeterDao.setTransactionTrendvaluetoMysql(projectDetails1);
			}
			 
		}
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


	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public String getTestDuration(String startTime, String endTime) {

		Date d1 = null;
		Date d2 = null;
		long diffSeconds = 0L;
		long diffMinutes = 0L;
		String duration = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(startTime));
		d1 = new Date(Long.valueOf(startTime));
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(Long.valueOf(endTime));
		d2 = new Date(Long.valueOf(endTime));
		try {
			long diff = d2.getTime() - d1.getTime();

			diffSeconds = diff / 1000 % 60;
			diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;

			if (diffHours == 0) {
				duration += "0" + String.valueOf(diffHours) + ":";
			}
			if (String.valueOf(diffMinutes).length() == 1) {
				duration += "0" + String.valueOf(diffMinutes) + ":"
						+ String.valueOf(diffSeconds);
			} else
				duration = String.valueOf(diffHours) + ":"
						+ String.valueOf(diffMinutes) + ":"
						+ String.valueOf(diffSeconds);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(duration);

	}
	
	public String getTestCompareResults(String testname)
	{
		
		ArrayList<JmeterProjectDetails> list=(ArrayList<JmeterProjectDetails>) jmeterDao.getTestsCompareResults(testname);

		  JSONObject jsonObject = new JSONObject();
		  Properties prop=new Properties();
		  InputStream input = null;
		  try{
		  //output = new FileOutputStream("influxdb.properties");
		  input =getClass().getClassLoader().getResourceAsStream("influxdb.properties");

		  // load a properties file
		  prop.load(input);
		 // List transaction = jmeterDao.getTransactionDetailsForLastestBuildFromInflux();
		  JmeterProjectDetails projectDetails=(JmeterProjectDetails) list.get(0);
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
				child.put("min",round(Double.valueOf(array1.get(1).toString())/1000,1));			
				child.put("max", round(Double.valueOf(array1.get(2).toString())/1000,1));
				child.put("avg", round(Double.valueOf(array1.get(3).toString())/1000,1));
				
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

		 
		//return "";
	}
	
	public String getKPICompareTest(String testto,String testwith)
	{
		
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
			List transaction = jmeterDao.getKPICompare(testto,testwith);
			RestTemplate restTemplate = new RestTemplate();
			
			/*for (int i = 0; i < transaction.size(); i++) {*/
				
				JmeterProjectDetails projectDetails1 = (JmeterProjectDetails) transaction.get(0);
				
				String query1 = "SELECT Mean(\"avg\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + "ms AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				
				UriComponents uriComponents1 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query1).queryParam("db", prop.getProperty("influxDb")).build();
				result = restTemplate.getForEntity(uriComponents1.toUri(), String.class);
				
				JSONObject respTimeJson = new JSONObject(result.getBody());
				JSONArray obj1 = new JSONArray(respTimeJson.getJSONArray("results").toString());
				respTimeJson = (JSONObject) obj1.get(0);
				respTimeJson = (JSONObject) respTimeJson.getJSONArray("series").get(0);
				childObject1.put("currentvalue", round(Double.valueOf(respTimeJson.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,1));
				
				String query = "SELECT Sum(\"err\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails1.getTeststarttime() + "ms AND time<="
						+ projectDetails1.getTestendtime() + "ms;";
				
				UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents.toUri(), String.class);
				JSONObject errorjson = new JSONObject(result.getBody());
				JSONArray obj2 = new JSONArray(errorjson.getJSONArray("results").toString());
				errorjson = (JSONObject) obj2.get(0);
				errorjson = (JSONObject) errorjson.getJSONArray("series").get(0);
				childObject2.put("currentvalue", round(Double.valueOf(errorjson.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				System.out.println("**********"+round(Double.valueOf(errorjson.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				
				
				JmeterProjectDetails projectDetails2 = (JmeterProjectDetails) transaction.get(1);
				
				String query2 = "SELECT Mean(\"avg\") FROM \"total\" WHERE \"project\" = '"
						+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + "ms AND time<="
						+ projectDetails2.getTestendtime() + "ms;";
				
				UriComponents uriComponents2 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query2).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents2.toUri(), String.class);
				System.out.println("uriComponents2.toUri():"+uriComponents2.toUri());
				JSONObject respTimeJson1 = new JSONObject(result.getBody());
				JSONArray obj3 = new JSONArray(respTimeJson1.getJSONArray("results").toString());
				respTimeJson1 = (JSONObject) obj3.get(0);
				respTimeJson1 = (JSONObject)  respTimeJson1.getJSONArray("series").get(0);
				childObject1.put("Prevvalue", round(Double.valueOf(respTimeJson1.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				
				
				String query3 = "SELECT Sum(\"err\") FROM \"total\" WHERE \"project\" = '"
				+ prop.getProperty("projectname") + "' AND time>="+ projectDetails2.getTeststarttime() + "ms AND time<="
				+ projectDetails2.getTestendtime() + "ms;";
				
				UriComponents uriComponents3 = UriComponentsBuilder.fromHttpUrl(prop.getProperty("influxurl"))
						.queryParam("q", query3).queryParam("db", prop.getProperty("influxDb")).build();
				
				result = restTemplate.getForEntity(uriComponents3.toUri(), String.class);
				JSONObject errorjson1 = new JSONObject(result.getBody());
				
				JSONArray obj4 = new JSONArray(errorjson1.getJSONArray("results").toString());
				errorjson1 = (JSONObject) obj4.get(0);
				errorjson1 = (JSONObject) errorjson1.getJSONArray("series").get(0);
					
				childObject2.put("Prevvalue", round(Double.valueOf(errorjson1.getJSONArray("values").getJSONArray(0).get(1).toString())/1000,2));
				
				
				JSONObject tpsJson=new JSONObject(getKPITPSForLastTwoBuilds(testto,testwith));
				System.out.println("tpsJson: "+ tpsJson);
				
				int count=0;
				for (final Iterator<String> iter = tpsJson.keys(); iter.hasNext();) {
					count++;
					
						if(count==5){
				    final String key = iter.next();

				    try {
				        final Object value = tpsJson.get(key);
				        childObject3.put("currentvalue", round(Double.valueOf(value.toString()), 1));
				      /*  final JSONArray error = (JSONArray) value;
				        System.out.println(key);
				        System.out.println(error.get(0).toString());*/
				    } catch (final JSONException e) {
				        // Something went wrong!
				    }
						}
						if(count==6){
						    final String key = iter.next();

						    try {
						        final Object value = tpsJson.get(key);
						       
						        childObject3.put("Prevvalue", round(Double.valueOf(value.toString()), 1));
						      /*  final JSONArray error = (JSONArray) value;
						        System.out.println(key);
						        System.out.println(error.get(0).toString());*/

						    } catch (final JSONException e) {
						        // Something went wrong!
						    }
								}
					}
				
				
				parentObject.append("ReponseTime", childObject1);
				parentObject.append("Errors", childObject2);
				parentObject.append("TPS", childObject3);
				
				
			/*}*/
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return parentObject.toString();
	
	}

	/*public ArrayList getListOfTests()
	 {
			ArrayList<JmeterProjectDetails> list = new ArrayList<JmeterProjectDetails>();
			list=(ArrayList) jmeterDao.getListOfTests();
			ArrayList testNo=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				testNo.add(list.get(i).getTestname());
			}
			
			return testNo;

		}*/
public boolean insertintoDB(String ip,String port,String db,String projectname)
{
	boolean returnvalue=true;
	
	
	return returnvalue;
}
	
}
