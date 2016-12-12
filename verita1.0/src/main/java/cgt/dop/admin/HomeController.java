package cgt.dop.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cgt.dop.alm.DashBoardFactory;
import cgt.dop.bean.BuildInfoBean;
import cgt.dop.bean.FeatureBean;
import cgt.dop.bean.FeatureVelocityBean;
import cgt.dop.bean.PredictionBean;
import cgt.dop.bean.UserLoginBean;
import cgt.dop.dao.impl.DashboardUserWidgetMappingDao;
import cgt.dop.dao.impl.DashboardWidgetRoleMappingDao;
import cgt.dop.dao.impl.DomainDao;
import cgt.dop.dao.impl.JmeterDao;
import cgt.dop.dao.impl.LoginDao;
import cgt.dop.dao.impl.MonitorDao;
import cgt.dop.dao.impl.ProjectDao;
import cgt.dop.rest.uri.constants.DevOpsRestURIConstants;
import cgt.dop.scrummaster.monitor.MonitorBean;
import cgt.dop.scrummaster.monitor.MonitorService;
import cgt.dop.scrummaster.sonar.SonarService;
import cgt.dop.service.perf.JmeterService;
import cgt.dop.service.perf.NeoLoadService;
import cgt.dop.service.perf.predictive.PerfPredictiveAnalysisService;
import cgt.dop.service.scrummaster.feature.FeatureFactory;
import cgt.dop.service.scrummaster.jenkins.JenkinsBuildService;

@Controller
public class HomeController {

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private DomainDao domainDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private DashboardWidgetRoleMappingDao dashboardWidgetRoleMappingDao;
	@Autowired
	private DashboardUserWidgetMappingDao dashboardUserWidgetMappingDao;

	@Autowired
	private NeoLoadService neoLoadService;
	
	@Autowired
	private JmeterService jmeterService;

	@Autowired
	private JenkinsBuildService jenkinsBuildService;
	
	/*@Autowired
	public DashBoardFactory dashBoardFactory;*/
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private SonarService sonarService;
	
	@Autowired
	private PerfPredictiveAnalysisService perfService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.GET_LOGIN, method = RequestMethod.GET)
	public @ResponseBody List getLogin(@PathVariable("id") String id,
			@PathVariable("pwd") String pwd) {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		System.out.println(id);
		System.out.println(pwd);
		ArrayList<String> list = new ArrayList<String>();
		list.add(id);
		list.add(pwd);
		list.add("9");
		list.add("success");
		return list;
	}

	/*
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value = EmpRestURIConstants.POST_LOGIN, method =
	 * RequestMethod.POST, headers = { "Content-type=application/json" })
	 * 
	 * @ResponseBody public boolean postLogin(@RequestBody UserInfo userInfo) {
	 * HttpHeaders header=new HttpHeaders();
	 * header.add("Access-Control-Allow-Origin", "*"); System.out.println(
	 * "name "+userInfo.getId()); System.out.println("pwd "+userInfo.getPwd());
	 * System.out.println("role "+userInfo.getRole()); return true; }
	 */

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.POST_LOGIN, method = RequestMethod.POST, headers = { "Content-type=application/json" })
	@ResponseBody
	public String postLogin(@RequestBody UserLoginBean loginBean)
			throws Exception {

		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		int project_id=loginBean.getProjectID();
		System.out.println("prj:   "+project_id);
		String userName = loginBean.getUserName();
		String password = loginBean.getPassword();
		String jsonResp;
		String resultLogin = loginDao.isValidUser(userName, password);

		if (resultLogin.equals("true")) {
			System.out.println(resultLogin + " if called");
			Object roleId = loginDao.getUserRole(userName, password);
			Object userId = loginDao.getUserid(userName, password);

			JSONObject totalWidgetdetails = dashboardWidgetRoleMappingDao
					.getDashboardWidgetsByRole(roleId,project_id);
			JSONObject hideWidgetdetails = dashboardUserWidgetMappingDao
					.getDashboardWidgetsByUser(userId);

			jsonResp = new JSONObject().put("validUser", resultLogin)
					.put("userName", userName)
					.put("graphList", totalWidgetdetails)
					.put("hidegraphList", hideWidgetdetails)
					.put("role", roleId).put("user_id", userId).toString();
			return jsonResp;
		} else {

			jsonResp = new JSONObject().put("validUser", resultLogin)
					.toString();
			return jsonResp;
		}

	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Get_Domains, method = RequestMethod.GET)
	@ResponseBody
	public String getDomains()
			throws JsonGenerationException, JsonMappingException, Exception {

		return new ObjectMapper().writeValueAsString(domainDao.getDomains());

	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Get_Projects, method = RequestMethod.GET)
	@ResponseBody
	public String getProjects()
			throws JsonGenerationException, JsonMappingException, Exception {

		return new ObjectMapper().writeValueAsString(projectDao.getProjects());

	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Add_widget, method = RequestMethod.GET)
	public @ResponseBody String addWidgetForHide(
			@PathVariable("user_id") String user_id,
			@PathVariable("widget_id") String widget_id) {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		String jsonResp = null;
		dashboardUserWidgetMappingDao.addWidgetForHide(
				Integer.parseInt(user_id), widget_id);
		try {
			jsonResp = new JSONObject().put("Status", "Success").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResp;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Delete_Widget, method = RequestMethod.GET)
	public @ResponseBody String deleteWidgetForDisplay(
			@PathVariable("user_id") String user_id,
			@PathVariable("widget_id") String widget_id) {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		String jsonResp = null;
		dashboardUserWidgetMappingDao.deleteWidgetForDisplay(
				Integer.parseInt(user_id), widget_id);
		try {
			jsonResp = new JSONObject().put("Status", "Success").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResp;
	}

	/* Performance*/
	/* ----------- NeoLoad start ------------ */

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.KPI_Improvement_For_Last_Build, method = RequestMethod.GET)
	public @ResponseBody String getKPIImprovementForLastBuild()
			throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getKPIImprovementForLastBuild();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Test_Execution_Summary, method = RequestMethod.GET)
	public @ResponseBody String getTestExecutionSummary() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getTestDetails();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Transaction_Details, method = RequestMethod.GET)
	public @ResponseBody String getTransactionDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		// neoLoadService.getTransactionPerSecondWithBuild();
		return neoLoadService.getTransactionDetails();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Transaction_Per_Second_With_Build, method = RequestMethod.GET)
	public @ResponseBody String getTransactionPerSecondWithBuild()
			throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getTransactionPerSecondWithBuild();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Response_Time_For_Previous_Builds, method = RequestMethod.GET)
	public @ResponseBody String getResponseTimeForPreviousBuilds()
			throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getResponseTimeForPreviousBuilds();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Error_Percentile_Trend, method = RequestMethod.GET)
	public @ResponseBody String getErrorPercentileTrend() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getErrorPercentileTrend();
	}
	/* ----------- NeoLoad End ------------ */
	
	/* ----------- Jmeter start ------------ */
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_KPI_Improvement_For_Last_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterKPIImprovementForLastBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		//jmeterService.
		return jmeterService.getKPIImprovementForLastBuild();
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_KPI_Improvement_For_Last_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterKPIImprovementForLastBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getKPIImprovementForLastBuildFromInflux();
	}
	
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Details, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
//		jmeterService.
//		return jmeterService.getTransactionDetailsForLastestBuild();
		return jmeterService.getTransactionDetailsForLastestBuildFromInflux();
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Details, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTransactionDetailsForLastestBuildFromInflux();
	}
	
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Per_Second_With_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionPerSecondWithBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTransactionPerSecondWithBuild();
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Per_Second_With_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionPerSecondWithBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTransactionPerSecondWithBuildFromInflux();
	}
	
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_For_Previous_Builds, method = RequestMethod.GET)
	public @ResponseBody String getJmeterResponseTimeForPreviousBuilds() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

	//	return jmeterService.getResponseTimeForPreviousBuilds();
		return jmeterService.getResponseTimeForPreviousBuildsFromInflux();
		//return getResponseTimeForPreviousBuildsFromInflux();
	}*/
	
	@CrossOrigin
	 @RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_For_Previous_Builds, method = RequestMethod.GET)
	 public @ResponseBody String getJmeterResponseTimeForPreviousBuilds() throws Exception {
	  HttpHeaders header = new HttpHeaders();
	  header.add("Access-Control-Allow-Origin", "*"); 
	  return jmeterService.getResponseTimeForPreviousBuildsFromInflux(); 
	 }
	
/*	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Percentile_Trend, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorPercentileTrend() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		//return jmeterService.getErrorPercentileTrend();
		return jmeterService.getErrorPercentileTrendFromInflux();
	}*/
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Percentile_Trend, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorPercentileTrend() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getErrorPercentileTrendFromInflux();
	}
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterResponseTimeLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		//return jmeterService.getResponseTimeLastestBuild();
		return jmeterService.getResponseTimeLastestBuildfromInflux();
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterResponseTimeLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getResponseTimeLastestBuildfromInflux();
	}
	

	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Time_For_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorTimeForLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getErrorTimeForLastestBuild();
	
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Time_For_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorTimeForLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

//		return jmeterService.getErrorTimeForLastestBuild();
		return jmeterService.getErrorTimeForLastestBuildFromInflux();
	}
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Test_Execution_Summary, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTestExecutionSummary() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTestExecutionSummary();
	}*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Test_Execution_Summary, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTestExecutionSummary() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return jmeterService.getTestExecutionSummaryFromInflux();
	}
	
	/* ----------- Jmeter End ------------ */
	

	
	/* Scrum Master*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.JIRA_SERVICES, method = RequestMethod.GET)
	public @ResponseBody FeatureBean getJiraService() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		
		return  new FeatureFactory()
				.getFeatureWidget(monitorDao.getWidgetDetail("SprintDetilas"));
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.JIRA_VELOCITY, method = RequestMethod.GET)
	public @ResponseBody List<FeatureVelocityBean> getSprintVelocityDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		
		return  new FeatureFactory()
				.getSprintVelocityDetails(monitorDao.getWidgetDetail("SprintDetilas"));
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LastNDaysBuildCountPerDay, method = RequestMethod.GET)
	public @ResponseBody String getLastNDaysBuildCountPerDay() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return jenkinsBuildService.getLastNDaysBuildCountPerDay(5);
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.GET_TOTAL_BUILDS, method = RequestMethod.GET)
	public @ResponseBody String getTotoalBuildDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return jenkinsBuildService.getTotoalBuildDetails(30).toString();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Selenium_Test_Details, method = RequestMethod.GET)
	public @ResponseBody String getSeleniumTestDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return jenkinsBuildService.getSeleniumTestDetails().toString();
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterMonitor, method = RequestMethod.GET)
	public @ResponseBody List<MonitorBean> getScrumMasterMonitor() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		List<MonitorBean> mbean = monitorService.getMonitorWidgetDetails(monitorDao.getWidgetToolMapping());
		
		return mbean;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterDeploy, method = RequestMethod.GET)
	public @ResponseBody List<BuildInfoBean> getScrumMasterDeploy() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		List<BuildInfoBean> buildbean = jenkinsBuildService.getLastNDaysBuildDetails(10);
		
		return buildbean;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterSonar, method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getScrumMasterSonar() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		Map<String, String> sonar = sonarService.getSonarData();
		
		return sonar;
	}
	
	
	/*@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterDefectSeverityIndex, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterDefectSeverityIndex() throws Exception {
	
		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterDefectSeverityIndex(releaseNo));		
		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestExecutionProgress, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestExecutionProgress() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterTestExecutionProgress(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestCoverage, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestCoverage() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterTestCoverage(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterOpenDefectsBySeverity, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterOpenDefectSeverity() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterOpenDefectSeverity(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterRequirementStabilityIndex, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterRequirementStabilityIndex() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterRequirementStabilityIndex(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterFeatureReadiness, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterFeatureReadyness() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterFeatureReadyness(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterMeanTime, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterMeanTime() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterMeanTime(releaseNo));

		return mapAsJson;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestCaseYield, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestCaseYield() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
				.getScrumMasterTestCaseYield(releaseNo));

		return mapAsJson;
	}


	 QC(ALM)

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ReleaseReadynessKPI, method = RequestMethod.GET)
	public @ResponseBody String getReleaseReadynessKPI() throws Exception {

	    String releaseNo = "Rel 1.1";
	    String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject()
		    .getReleaseReadynessKPI(releaseNo));		
	    return mapAsJson;
	}
	

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.PrescriptiveAnalysis, method = RequestMethod.GET)
	public @ResponseBody String getPrescriptiveAnalysis() throws Exception {
	    String releaseNo = "Rel 1.1";
	    String mapAsJson = new ObjectMapper().writeValueAsString(dashBoardFactory.getObject().getPrescriptiveAnalysis(releaseNo));
	    return mapAsJson;
	}
	*/
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.RESPTIME, method = RequestMethod.GET)
	public @ResponseBody String getPredictvieRespTime(@PathVariable("rTime") String rTime) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		 final String uri = "https://ussouthcentral.services.azureml.net/workspaces/d731a5210cd84b7ba7f5f13673645c83/services/2d4298b5ce184529ac92c2fb791647f4/execute?api-version=2.0&details=true";
		
		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		   String postBody="{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"calls_per_minute\"],\"Values\": [[\""+rTime+"\"]]}},\"GlobalParameters\": {}}";
		   HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.add("Authorization", "Bearer Ldq12cRFAX8K682HjX6i+w4suJbd1R6XzdvU7l0ylPpTkiRRjKMArGdTXysQPeg3Pgwe7UX2eqD0/06A36jXng==");
		    HttpEntity request = new HttpEntity(postBody, headers);
		    ResponseEntity<String> response = restTemplate
		    		  .exchange(uri, HttpMethod.POST, request, String.class);
		    System.out.println(response.getStatusCode());
		    if(response.getStatusCode().toString().equals("200"))
		    {
		    	System.out.println("**Response:"+response.getBody());
		    	return response.getBody();
		    }
		    else{
		    	return "";
		    }

	}
	
	// Performance predictive
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Perf_Avg_Resp_Time_Predictive, method = RequestMethod.GET)
	public @ResponseBody List<PredictionBean> getPerfAvgRespTimePred() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		List<PredictionBean> predBean = perfService.responseTimes();
		
		return predBean;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Perf_CPU_Utilization_Predictive, method = RequestMethod.GET)
	public @ResponseBody List<PredictionBean> getPerfCpuUtilization() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		List<PredictionBean> predBean = perfService.cpuUtilization();
		
		return predBean;
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Perf_Scalability_Factor_Predictive, method = RequestMethod.GET)
	public @ResponseBody String getPerfScalabilityFactor() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		
		JSONObject jsonScalability = perfService.scalabilityFactor();
		String scale = jsonScalability.toString();
		return scale;
	}
	
	@RequestMapping(value="/devops/jmeter/testcompare",method = RequestMethod.GET)
	public String getCompareTestPage(@RequestParam(required=false) String testwith,@RequestParam(required=false) String testto) throws Exception
	{
		//String object=jmeterService.getTestCompareDetailsFromInflux();
		System.out.println("welcome:");
	//	System.out.println("welocomw:"+testwith +" testwith"+testwith.length());
		ArrayList list=new ArrayList();
		list=jmeterService.getListOfTests();
		if(null!=testwith && null!=testto)
		{
			System.out.println("****************");
						
		}
		else{
			
			System.out.println("***if values are null");
		}	
		return "comparetests";
	}
	
	@RequestMapping(value="/devops/jmeter/testNames",method = RequestMethod.GET)
	public @ResponseBody String getTestNames() throws JSONException
	{
		ArrayList list=new ArrayList();
		list=jmeterService.getListOfTests();
		System.out.println("list"+list);
		JSONObject object=new JSONObject();
		System.out.println("JSON OBject:"+object);
		JSONArray mJSONArray = new JSONArray(Arrays.asList(list.toArray()));
		object.put("testnames", mJSONArray);
		return object.toString();
		
	}
	
	@RequestMapping(value="/devops/jmeter/gettestresults",method = RequestMethod.GET)
	public @ResponseBody String getCompareTestResults(@RequestParam String testto,@RequestParam String testwith) throws Exception
	{
		//String object=jmeterService.getTestCompareDetailsFromInflux();
		System.out.println("welcome:");
	//	System.out.println("welocomw:"+testwith +" testwith"+testwith.length());
		ArrayList list=new ArrayList();
		list=jmeterService.getListOfTests();
		JSONObject response=new JSONObject();
		if(null!=testwith && null!=testto)
		{
			System.out.println("****************");
			JSONObject object= new JSONObject(jmeterService.getTestCompareResults(testwith));
			JSONObject object2=new JSONObject(jmeterService.getTestCompareResults(testto));
			
			Iterator<?> keys = object.keys();

			while( keys.hasNext() ) {
			    String key = (String)keys.next();
			   
			    
			    if(object2.has(key))
			    {
			    	JSONArray array=new JSONArray();
			    	//array=object2.getJSONArray(key);
			    	JSONArray arrtestWith=object.getJSONArray(key);
			    	JSONObject temp=arrtestWith.getJSONObject(0);
			    	JSONObject setValues=new JSONObject();
			    	setValues.put("testwithmin", temp.get("min").toString());//put("testwithmin",temp.get("min"));
			    	setValues.put("testwithavg", temp.get("avg").toString());
			    	setValues.put("testwithmax", temp.get("max").toString());
			    	setValues.put("min", object2.getJSONArray(key).getJSONObject(0).get("min").toString());//get("max").toString());
			    	setValues.put("avg", object2.getJSONArray(key).getJSONObject(0).get("avg").toString());
			    	setValues.put("max", object2.getJSONArray(key).getJSONObject(0).get("max").toString());
			    	setValues.put("diff", jmeterService.round(Double.valueOf(temp.get("avg").toString())-Double.valueOf(object2.getJSONArray(key).getJSONObject(0).get("avg").toString()),1));
			    	if(Double.valueOf(temp.get("avg").toString())>Double.valueOf(object2.getJSONArray(key).getJSONObject(0).get("avg").toString()))
			    	{
			    		setValues.put("class", "red");	
			    	}
			    	else
			    	{
			    		setValues.put("class", "green");
			    	}
			    	setValues.put("transactionName", key);
			    	//array.putmin
			    	System.out.println("****"+setValues);
			    	array.put(setValues);
			    	response.put(key,array);
			    }
			    else
			    {
			    	JSONArray array=new JSONArray();
			    	//array=object2.getJSONArray(key);
			    	JSONArray arrtestWith=object.getJSONArray(key);
			    	JSONObject temp=arrtestWith.getJSONObject(0);
			    	JSONObject setValues=new JSONObject();
			    	setValues.put("testwithmin", temp.get("min").toString());//put("testwithmin",temp.get("min"));
			    	setValues.put("testwithavg", temp.get("avg").toString());
			    	setValues.put("testwithmax", temp.get("max").toString());
			    	setValues.put("min", 0.0);//get("max").toString());
			    	setValues.put("avg", 0.0);
			    	setValues.put("max", 0.0);
			    	setValues.put("diff", "0.0");
			    	setValues.put("transactionName", key);
			    	//array.putmin
			    	System.out.println("****"+setValues);
			    	array.put(setValues);
			    	response.put(key,array);
			    }
//			    if ( jObject.get(key) instanceof JSONObject ) {}
			}
			return response.toString();
			
		}
		else{
			System.out.println("***if values are null");
		}	
		return "";
	}
	
	@RequestMapping(value="/devops/jmeter/gettestresultsKPI",method = RequestMethod.GET)
	public @ResponseBody String getCompareTestResultsKPI(@RequestParam String testto,@RequestParam String testwith) throws Exception
	{
		//String object=jmeterService.getTestCompareDetailsFromInflux();
		System.out.println("welcome:");
	//	System.out.println("welocomw:"+testwith +" testwith"+testwith.length());
		ArrayList list=new ArrayList();
		list=jmeterService.getListOfTests();
		if(null!=testwith)
		{
			System.out.println("****************");
		//	return jmeterService.getTestCompareResults(testwith);
			return jmeterService.getKPICompareTest(testto, testwith);
		}
		else{
			
			System.out.println("***if values are null");
		}	
		return "ee";
	}
	
	@RequestMapping(value="/devops/jmeter/sampleCompare",method = RequestMethod.GET)
	public  String getSampleCompare() throws Exception
	{
		return "compare";
	}
	
	@RequestMapping(value="/devops/admin/influx",method = RequestMethod.GET)
	public String sendAdminPageforInflux() throws Exception
	{
		//return "compare";
		
		return "influx";
	}
	
	@RequestMapping(value="/devops/admin/influx/insert",method = RequestMethod.POST)
	public @ResponseBody String insertintoDB(@RequestParam String ip,@RequestParam String port,@RequestParam String database,@RequestParam String projectname) throws Exception
	{		
		System.out.println("********");
		jmeterService.insertintoDB(ip,port,database,projectname);
		return "";
	}
}