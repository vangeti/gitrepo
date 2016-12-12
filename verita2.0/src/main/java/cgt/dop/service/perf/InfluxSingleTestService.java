package cgt.dop.service.perf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import cgt.dop.bean.AggregateReportBean;
import cgt.dop.bean.InfluxAggregateReportBean;
import cgt.dop.bean.InfluxSummaryBean;
import cgt.dop.bean.InfluxUserProfileBean;
import cgt.dop.bean.KPIBean;
import cgt.dop.bean.ObservationsBean;
import cgt.dop.bean.ResponseCodeBean;
import cgt.dop.bean.ResponseTime;
import cgt.dop.model.LennoxUserData;
import cgt.dop.model.LennoxUserProfile;

@Repository
public class InfluxSingleTestService extends InfluxHelper {

	@PostConstruct
	public void init() {
		super.test1();
	}

	public String getUserProfile() throws Exception {

		LennoxUserProfile lennoxUserProfile = super.getUserProfileInfo();
		
		String json = new ObjectMapper().writeValueAsString(lennoxUserProfile);
		return json;
	}
	
	
	//Edited by Sankar
	public String getUserProfile(String testId) throws Exception {

		InfluxUserProfileBean lennoxUserData = (InfluxUserProfileBean) super.getUserProfileInfo(testId);
		String json = new ObjectMapper().writeValueAsString(lennoxUserData);
		return json;
	}
	
	
	public void getReport(String testId) throws Exception {

		//InfluxUserProfileBean lennoxUserData = (InfluxUserProfileBean) super.getReport(testId);
		super.getReport(testId);
		System.out.println("it is in method");
		//String json = new ObjectMapper().writeValueAsString(lennoxUserData);
		//return json;
	}
	
	
	public String getKPI() throws Exception {

		KPIBean kPIBean = new KPIBean();
		ObservationsBean observationsBean = super.getKPIInfo();
		kPIBean.setErrorForTest1(observationsBean.getError());
		kPIBean.setTpsForTest1(observationsBean.getTps());
		String json = new ObjectMapper().writeValueAsString(kPIBean);
		return json;
	}
	
	public String getKPILive(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {

		KPIBean kPIBean = new KPIBean();
		ObservationsBean observationsBean = super.getKPIInfoLive(testStartTime, testEndTime, testSuite, projectName);
		kPIBean.setErrorForTest1(observationsBean.getError());
		kPIBean.setTpsForTest1(observationsBean.getTps());
		String json = new ObjectMapper().writeValueAsString(kPIBean);
		return json;
	}


	public String getActiveThreads() throws Exception {

		Map<Date, Long> resultMap = super.getActiveThreadsInfo();
		String json = new ObjectMapper().writeValueAsString(resultMap);
		return json;

	}
	
	//Refactoring with lennox_projectdetails table...
	public String getActiveThreads(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {

		Map<String, Long> resultMap = super.getActiveThreadsInfo(testStartTime, testEndTime, testSuite, projectName);
		String json = new ObjectMapper().writeValueAsString(resultMap);
		return json;

	}
	
	public String getActiveThreadsGraphs(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {

		String json/*Map<String, Long> resultMap*/ = super.getActiveThreadsInfoGraph(testStartTime, testEndTime, testSuite, projectName);
		//String json = new ObjectMapper().writeValueAsString(resultMap);
		return json;

	}

	public String getResponseCodes() throws Exception {
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryList();
		ArrayList<ResponseCodeBean> responseCodes = super.getResponseCodesInfo(influxSummaryList);
		String json = new ObjectMapper().writeValueAsString(responseCodes);
		return json;
	}

public String getResponseCodes(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {

		ArrayList<ResponseCodeBean> responseCodes = super.getResponseCodesInfo(testStartTime, testEndTime, testSuite, projectName);
		Map<String, LinkedHashMap<Long, Integer>> responseCodeOccurances = super.evaluateResponseCodeOccurances(responseCodes);
//		ArrayList<ResponseCodeBean> respCodesWithTimeAndOccurances = respCodeAndOccrns(responseCodeOccurances);
//		String json = new ObjectMapper().writeValueAsString(respCodesWithTimeAndOccurances);
		String json = new ObjectMapper().writeValueAsString(responseCodeOccurances);
		return json;
	}

	private ArrayList<ResponseCodeBean> respCodeAndOccrns(Map<String, LinkedHashMap<Long, Integer>> responseCodeOccurances) {
		ArrayList<ResponseCodeBean> resCodBeanLstWithOutRespCodes = new ArrayList<ResponseCodeBean>();
		for(Entry<String, LinkedHashMap<Long, Integer>> ent : responseCodeOccurances.entrySet())	{
			ResponseCodeBean responseCodeBean = new ResponseCodeBean();
			responseCodeBean.setElapsedTime((String) ent.getKey());
			LinkedHashMap<Long, Integer> respCodesAndCounts = ent.getValue();
			ArrayList<Integer> respCodeCountsList = new ArrayList<Integer>();
			ArrayList<Long> respCodesList = new ArrayList<Long>();
			for(Entry<Long, Integer> entRespCodeCount : respCodesAndCounts.entrySet())	{
				respCodesList.add(entRespCodeCount.getKey());
				respCodeCountsList.add(entRespCodeCount.getValue());
			}
			responseCodeBean.setRespCodesList(respCodesList);
			responseCodeBean.setRespCodeCounts(respCodeCountsList);
			resCodBeanLstWithOutRespCodes.add(responseCodeBean);
		}
		return resCodBeanLstWithOutRespCodes;
	}

	public String getHitsPerSeconds() throws Exception {
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryList();
		Map<Long, Long> resultMap = super.getHitsPerSecondsInfo(influxSummaryList);
		String json = new ObjectMapper().writeValueAsString(resultMap);
		return json;

	}
	
	// Edited by Sankar
	public String getHitsPerSeconds(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		String json = super.getHitsPerSecondsInfo(testStartTime, testEndTime, testSuite, projectName);
		return json;
	}
	
	public String getResponseTimes() throws Exception {
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryList();
		ResponseTime resultResponseTime = super.getResponseTimesInfo(influxSummaryList);
		String json = new ObjectMapper().writeValueAsString(resultResponseTime);
		return json;

	}

	public String getResponseTimesLive(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryListForResponseTimes(testStartTime, testEndTime, testSuite, projectName);
		ResponseTime resultResponseTime = super.getResponseTimesInfoLive(influxSummaryList);
		String json = new ObjectMapper().writeValueAsString(resultResponseTime);
		return json;
	}
	
	public String getResponseTimesLiveGraph(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		/*ArrayList<InfluxSummaryBean> influxSummaryList*/String json = getInfluxSummaryListForResponseTimesGraph(testStartTime, testEndTime, testSuite, projectName);
		//ResponseTime resultResponseTime = super.getResponseTimesInfoLive(influxSummaryList);
		
		//String json = new ObjectMapper().writeValueAsString(influxSummaryList);
		return json;
	}

	public String getAggregateReport() throws Exception {
		
		this.init(); // to be deleted
		
		ArrayList<InfluxAggregateReportBean> influxAggregateReportList = getInfluxAggregateReportList();		
		List<AggregateReportBean> aggregateReportBeanList = super.getAggregateReportInfo(influxAggregateReportList);
		
		Map<String, List<AggregateReportBean>> map = new HashMap<String, List<AggregateReportBean>>();		
		for(AggregateReportBean aggregateReportBean : aggregateReportBeanList)
		{
			if(aggregateReportBean.getTransactionName().contains("Transaction"))
			{
				if (!map.containsKey("Transaction")) {
					ArrayList<AggregateReportBean> arb = new ArrayList<AggregateReportBean>();
					arb.add(aggregateReportBean);
					map.put("Transaction", arb);
				}
				map.get("Transaction").add(aggregateReportBean);				
			}
			else
			{
				if (!map.containsKey("Request")) {
					ArrayList<AggregateReportBean> arb = new ArrayList<AggregateReportBean>();
					arb.add(aggregateReportBean);
					map.put("Request", arb);
				}
				map.get("Request").add(aggregateReportBean);				
			}
			
		}		
		
		System.out.println("map == "+map);
		
		String json = new ObjectMapper().writeValueAsString(aggregateReportBeanList);
		return json;
	}

	public String getAggregateReport(String testStartTime, String testEndTime, String testSuite, String projectName) throws Exception {
		List<AggregateReportBean> aggregateReportBeanList = super.getAggregateReportInfo(testStartTime, testEndTime, testSuite, projectName);
		String json = new ObjectMapper().writeValueAsString(aggregateReportBeanList);
		return json;
	}
}
