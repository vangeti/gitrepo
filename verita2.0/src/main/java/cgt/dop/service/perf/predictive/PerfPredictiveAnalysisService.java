package cgt.dop.service.perf.predictive;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVReader;

import cgt.dop.bean.PredictionBean;

@Repository
public class PerfPredictiveAnalysisService {

	/*@Autowired
	private ServiceUtils service;*/
	
	public List<PredictionBean> responseTimes() throws Exception 
	{
		String filepath = "C://CIODashboardWorkspace//cgt-dop-admin//src//main//webapp//WEB-INF//inputFiles//response_time_vs_load.csv"; //path + "response_time_vs_load.csv";// C:\\Users\\IN01071\\Downloads\\June2016\\Jacobs_data_and_web_service_api\\response_time_vs_load.csv";
		CSVReader reader = new CSVReader(new FileReader(filepath));
		List list = reader.readAll();
		System.out.println("list Size;" + list.size());
		
//		JSONObject responseJson = new JSONObject();
		
		ArrayList responseCallList = new ArrayList();
		
		PredictionBean responsecalls = new PredictionBean();
		
		ServiceUtils service = new ServiceUtils();
		
		for (int i = 1; i < list.size(); i++) {
			responsecalls = new PredictionBean();
			String[] array = (String[]) list.get(i);
			String calls = array[0];
			responsecalls.setCallsPerminute(Integer.parseInt(array[0]
					.toString()));
			String responsetime = "";
			try {
				responsetime = array[1];
				System.out.println("Actual Value:" + responsetime);
				responsecalls.setActual(responsetime);
				// cpuActualList.add(cpuusage);
			} catch (Exception e) {
				responsetime = "0.0";
				responsecalls.setActual(responsetime);
				// cpuActualList.add(cpuusage);
			}
			String cpuString = "";
			try {
				cpuString = service.getResponsePredictions(calls, responsetime);
				JSONObject cpu = new JSONObject(cpuString);
				cpu = (JSONObject) cpu.get("Results");
				cpu = (JSONObject) cpu.get("predicted_response_time");
				cpu = (JSONObject) cpu.get("value");
				JSONArray finalobj = (JSONArray) cpu.getJSONArray("Values");
				finalobj = (JSONArray) finalobj.get(0);
				System.out.println("JSON FInal OBject :" + finalobj.get(0));
				// cpuPrediction = cpuPrediction.concat((String)
				// cpu.get("Results"));
				responsecalls.setPrediction(Double.valueOf(finalobj.get(0)
						.toString()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!responsetime.equals("0.0"))
				responseCallList.add(responsecalls);
				

		}
		
		System.out.println("call: "+responsecalls.getCallsPerminute());
		System.out.println("actual: "+responsecalls.getActual());
		System.out.println("pred: "+responsecalls.getPrediction());
		
		responseCallList.remove(responseCallList.size() - 1);
		responseCallList.remove(responseCallList.size() - 2);
		
		System.out.println("ghhghg: "+responseCallList);
		
		
		reader.close();
		try {
			service.getPercentile();
		} catch (Exception e) {
			System.out.println("Valus CPU and diskk");
			e.printStackTrace();
		}
		
		System.out.println("response time: "+responseCallList);
		
		return responseCallList;
		
	}
	
	public List<PredictionBean> cpuUtilization() throws Exception 
	{
		ServiceUtils service = new ServiceUtils();
		String filepath = "C://CIODashboardWorkspace//cgt-dop-admin//src//main//webapp//WEB-INF//inputFiles//cpu_vs_load.csv";
		
		CSVReader reader = new CSVReader(new FileReader(filepath));
		List list = reader.readAll();
		System.out.println("list Size;" + list.size());
		
		ArrayList cpuCallList = new ArrayList();
		PredictionBean cpucalls = new PredictionBean();
		String cpuPrediction = "";
		
		for (int i = 1; i < list.size(); i++) {
			cpucalls = new PredictionBean();
			String[] array = (String[]) list.get(i);
			String calls = array[0];
			cpucalls.setCallsPerminute(Integer.parseInt(array[0].toString()));
			String cpuusage = "";
			try {
				cpuusage = array[1];
				System.out.println("Actual Value:" + cpuusage);
				cpucalls.setActual(cpuusage);
				// cpuActualList.add(cpuusage);
			} catch (Exception e) {
				cpuusage = "0.0";
				cpucalls.setActual(cpuusage);
				// cpuActualList.add(cpuusage);
			}
			String cpuString = "";
			try {
				cpuString = service.getCPUPredictions(calls, cpuusage);
				JSONObject cpu = new JSONObject(cpuString);
				cpu = (JSONObject) cpu.get("Results");
				cpu = (JSONObject) cpu.get("predicted_cpu_usage");
				cpu = (JSONObject) cpu.get("value");
				JSONArray finalobj = (JSONArray) cpu.getJSONArray("Values");
				finalobj = (JSONArray) finalobj.get(0);
				System.out.println("JSON FInal OBjec t :" + finalobj.get(0));
				// cpuPrediction = cpuPrediction.concat((String)
				// cpu.get("Results"));
				cpucalls.setPrediction(Double.valueOf(finalobj.get(0)
						.toString()));

			} catch (Exception e) {
				// e.printStackTrace();
			}
			if (!cpuusage.equals("0.0"))
				cpuCallList.add(cpucalls);
		}
		cpuCallList.remove(cpuCallList.size() - 1);
		cpuCallList.remove(cpuCallList.size() - 2);
		// cpuCallList.remove(cpuCallList.size());
		reader.close();
		
		return cpuCallList;
	}
	
	public JSONObject scalabilityFactor() throws Exception 
	{

		ServiceUtils service = new ServiceUtils();
		JSONObject totalJson = new JSONObject();
		JSONObject chiObject1 = new JSONObject();
		try {
			String result = service.getPercentile();
			JSONObject cpu = new JSONObject(result);
			cpu = cpu.getJSONObject("Results");
			cpu = cpu.getJSONObject("feature dependency");
			cpu = cpu.getJSONObject("value");
			JSONArray oldpercents = cpu.getJSONArray("Values");
			// System.out.println("**** Array"+oldpercents);

			
			
			JSONArray percents = (JSONArray) oldpercents.get(0);
			chiObject1.put((String)percents.get(0),percents.get(1));
			
			percents = (JSONArray) oldpercents.get(1);
			chiObject1.put((String)percents.get(0),percents.get(1));
			
			percents = (JSONArray) oldpercents.get(2);
			chiObject1.put((String)percents.get(0),percents.get(1));
			
			percents = (JSONArray) oldpercents.get(3);
			chiObject1.put((String)percents.get(0),percents.get(1));
			
	
			
			System.out.println("check json: "+totalJson);

		} catch (Exception e) {
			// System.out.println("Values CPU and diskk");
			e.printStackTrace();
		}
		
		return chiObject1; // "redirect:/index.html";
	}
	
	/*public static void main(String args[]) throws Exception
	{
		PerfPredictiveAnalysisService perf = new PerfPredictiveAnalysisService();
		perf.scalabilityFactor();
		perf.responseTimes();
	}*/
}
