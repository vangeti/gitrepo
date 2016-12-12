/*package cgt.dop.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
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

	@Autowired
	public DashBoardFactory dashBoardFactory;

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
	public @ResponseBody List getLogin(@PathVariable("id") String id, @PathVariable("pwd") String pwd) {
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
	 

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.POST_LOGIN, method = RequestMethod.POST, headers = {
			"Content-type=application/json" })
	@ResponseBody
	public String postLogin(@RequestBody UserLoginBean loginBean) throws Exception {

		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		int project_id = loginBean.getProjectID();
		System.out.println("prj:   " + project_id);
		String userName = loginBean.getUserName();
		String password = loginBean.getPassword();
		String jsonResp;
		String resultLogin = loginDao.isValidUser(userName, password);

		if (resultLogin.equals("true")) {
			System.out.println(resultLogin + " if called");
			Object roleId = loginDao.getUserRole(userName, password);
			Object userId = loginDao.getUserid(userName, password);

			JSONObject totalWidgetdetails = dashboardWidgetRoleMappingDao.getDashboardWidgetsByRole(roleId, project_id);
			JSONObject hideWidgetdetails = dashboardUserWidgetMappingDao.getDashboardWidgetsByUser(userId);

			jsonResp = new JSONObject().put("validUser", resultLogin).put("userName", userName)
					.put("graphList", totalWidgetdetails).put("hidegraphList", hideWidgetdetails).put("role", roleId)
					.put("user_id", userId).toString();
			return jsonResp;
		} else {

			jsonResp = new JSONObject().put("validUser", resultLogin).toString();
			return jsonResp;
		}

	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Get_Domains, method = RequestMethod.GET)
	@ResponseBody
	public String getDomains() throws JsonGenerationException, JsonMappingException, Exception {

		return new ObjectMapper().writeValueAsString(domainDao.getDomains());

	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Get_Projects, method = RequestMethod.GET)
	@ResponseBody
	public String getProjects() throws JsonGenerationException, JsonMappingException, Exception {

		return new ObjectMapper().writeValueAsString(projectDao.getProjects());

	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Add_widget, method = RequestMethod.GET)
	public @ResponseBody String addWidgetForHide(@PathVariable("user_id") String user_id,
			@PathVariable("widget_id") String widget_id) {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		String jsonResp = null;
		dashboardUserWidgetMappingDao.addWidgetForHide(Integer.parseInt(user_id), widget_id);
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
	public @ResponseBody String deleteWidgetForDisplay(@PathVariable("user_id") String user_id,
			@PathVariable("widget_id") String widget_id) {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		String jsonResp = null;
		dashboardUserWidgetMappingDao.deleteWidgetForDisplay(Integer.parseInt(user_id), widget_id);
		try {
			jsonResp = new JSONObject().put("Status", "Success").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResp;
	}

	 Performance 
	 ----------- NeoLoad start ------------ 

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.KPI_Improvement_For_Last_Build, method = RequestMethod.GET)
	public @ResponseBody String getKPIImprovementForLastBuild() throws Exception {
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
	public @ResponseBody String getTransactionPerSecondWithBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return neoLoadService.getTransactionPerSecondWithBuild();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Response_Time_For_Previous_Builds, method = RequestMethod.GET)
	public @ResponseBody String getResponseTimeForPreviousBuilds() throws Exception {
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
	 ----------- NeoLoad End ------------ 

	 ----------- Jmeter start ------------ 

	
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_KPI_Improvement_For_Last_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterKPIImprovementForLastBuild() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*");
	 * 
	 * return jmeterService.getKPIImprovementForLastBuild(); }
	 
	
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_KPI_Improvement_For_Last_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterKPIImprovementForLastBuild() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*");
	 * 
	 * return jmeterService.getKPIImprovementForLastBuildFromInflux(); }
	 * 
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Transaction_Details, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterTransactionDetails() throws Exception { HttpHeaders header = new
	 * HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getTransactionDetailsForLastestBuild(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Transaction_Details, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterTransactionDetails() throws Exception { HttpHeaders header = new
	 * HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getTransactionDetailsForLastestBuildFromInflux(); }
	 * 
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Transaction_Per_Second_With_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterTransactionPerSecondWithBuild() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*");
	 * 
	 * return jmeterService.getTransactionPerSecondWithBuild(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Transaction_Per_Second_With_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterTransactionPerSecondWithBuild() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*");
	 * 
	 * return jmeterService.getTransactionPerSecondWithBuildFromInflux(); }
	 * 
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Response_Time_For_Previous_Builds, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterResponseTimeForPreviousBuilds() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*");
	 * 
	 * return jmeterService.getResponseTimeForPreviousBuilds(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Response_Time_For_Previous_Builds, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterResponseTimeForPreviousBuilds() throws Exception { HttpHeaders
	 * header = new HttpHeaders(); header.add("Access-Control-Allow-Origin",
	 * "*"); return jmeterService.getResponseTimeForPreviousBuildsFromInflux();
	 * }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Error_Percentile_Trend, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterErrorPercentileTrend() throws Exception { HttpHeaders header =
	 * new HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getErrorPercentileTrend(); }
	 * 
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Error_Percentile_Trend, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterErrorPercentileTrend() throws Exception { HttpHeaders header =
	 * new HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getErrorPercentileTrendFromInflux(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Response_Time_Lastest_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterResponseTimeLastestBuild() throws Exception { HttpHeaders header
	 * = new HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getResponseTimeLastestBuild(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value =
	 * DevOpsRestURIConstants.Jmeter_Response_Time_Lastest_Build, method =
	 * RequestMethod.GET) public @ResponseBody String
	 * getJmeterResponseTimeLastestBuild() throws Exception { HttpHeaders header
	 * = new HttpHeaders(); header.add("Access-Control-Allow-Origin", "*");
	 * 
	 * return jmeterService.getResponseTimeLastestBuildfromInflux(); }
	

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Time_For_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorTimeForLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getErrorTimeForLastestBuild();
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Test_Execution_Summary, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTestExecutionSummary() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTestExecutionSummary();
	}

	/* ----------- Jmeter End ------------ 

	 Scrum Master 

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.JIRA_SERVICES, method = RequestMethod.GET)
	public @ResponseBody FeatureBean getJiraService() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return new FeatureFactory().getFeatureWidget(monitorDao.getWidgetDetail("Features"));
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.JIRA_VELOCITY, method = RequestMethod.GET)
	public @ResponseBody List<FeatureVelocityBean> getSprintVelocityDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return new FeatureFactory().getSprintVelocityDetails(monitorDao.getWidgetDetail("SprintDetilas"));
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

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterDefectSeverityIndex, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterDefectSeverityIndex() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterDefectSeverityIndex(releaseNo));
		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestExecutionProgress, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestExecutionProgress() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterTestExecutionProgress(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestCoverage, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestCoverage() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterTestCoverage(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterOpenDefectsBySeverity, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterOpenDefectSeverity() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterOpenDefectSeverity(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterRequirementStabilityIndex, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterRequirementStabilityIndex() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterRequirementStabilityIndex(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterFeatureReadiness, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterFeatureReadyness() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterFeatureReadyness(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterMeanTime, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterMeanTime() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterMeanTime(releaseNo));

		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ScrumMasterTestCaseYield, method = RequestMethod.GET)
	public @ResponseBody String getScrumMasterTestCaseYield() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getScrumMasterTestCaseYield(releaseNo));

		return mapAsJson;
	}

	 QC(ALM) 

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.ReleaseReadynessKPI, method = RequestMethod.GET)
	public @ResponseBody String getReleaseReadynessKPI() throws Exception {

		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getReleaseReadynessKPI(releaseNo));
		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.PrescriptiveAnalysis, method = RequestMethod.GET)
	public @ResponseBody String getPrescriptiveAnalysis() throws Exception {
		String releaseNo = "Rel 1.1";
		String mapAsJson = new ObjectMapper()
				.writeValueAsString(dashBoardFactory.getObject().getPrescriptiveAnalysis(releaseNo));
		return mapAsJson;
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.RESPTIME, method = RequestMethod.GET)
	public @ResponseBody String getPredictvieRespTime(@PathVariable("rTime") String rTime) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		final String uri = "https://ussouthcentral.services.azureml.net/workspaces/d731a5210cd84b7ba7f5f13673645c83/services/2d4298b5ce184529ac92c2fb791647f4/execute?api-version=2.0&details=true";

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		String postBody = "{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"calls_per_minute\"],\"Values\": [[\"" + rTime
				+ "\"]]}},\"GlobalParameters\": {}}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization",
				"Bearer Ldq12cRFAX8K682HjX6i+w4suJbd1R6XzdvU7l0ylPpTkiRRjKMArGdTXysQPeg3Pgwe7UX2eqD0/06A36jXng==");
		HttpEntity request = new HttpEntity(postBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
		System.out.println(response.getStatusCode());
		if (response.getStatusCode().toString().equals("200")) {
			System.out.println("**Response:" + response.getBody());
			return response.getBody();
		} else {
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

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.CUSTOMDEFECTWIDGET, method = RequestMethod.GET)
	public @ResponseBody String getCustomDefectDetails() throws Exception {
		return dashBoardFactory.getObject().getDefectDetails();
	}

	// Lennox

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_ACTIVE_THREADS, method = RequestMethod.GET)
	public @ResponseBody String getLennoxJmeterActiveThreadsForLastBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getLennoxJmeterActiveThreadsForLastBuildFromInflux();

	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_RESPONSE_CODES_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getLennoxJmeterResponseCodesPerSecondForLastBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getLennoxJmeterResponseCodesPerSecondForLastBuildFromInflux();
		
	}

}*/


package cgt.dop.admin;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
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

import cgt.dop.alm.DashBoardFactory;
import cgt.dop.bean.BuildInfoBean;
import cgt.dop.bean.FeatureBean;
import cgt.dop.bean.ObservationsBean;
import cgt.dop.bean.PredictionBean;
import cgt.dop.bean.TestUpdateBean;
import cgt.dop.bean.TesterObservationBean;
import cgt.dop.bean.UserLoginBean;
import cgt.dop.bean.UserProfileBean;
import cgt.dop.dao.impl.DashboardUserWidgetMappingDao;
import cgt.dop.dao.impl.DashboardWidgetRoleMappingDao;
import cgt.dop.dao.impl.DomainDao;
import cgt.dop.dao.impl.LoginDao;
import cgt.dop.dao.impl.MonitorDao;
import cgt.dop.dao.impl.ProjectDao;
import cgt.dop.model.LennoxProjectDetails;
import cgt.dop.model.LennoxTestDetails;
import cgt.dop.model.LennoxUserProfile;
import cgt.dop.model.ProjectModel;
import cgt.dop.model.TestUpdateModel;
import cgt.dop.rest.uri.constants.DevOpsRestURIConstants;
import cgt.dop.scrummaster.monitor.MonitorBean;
import cgt.dop.scrummaster.monitor.MonitorService;
import cgt.dop.scrummaster.sonar.SonarService;
import cgt.dop.service.TestUpdateService;
import cgt.dop.service.TesterObservationsService;
import cgt.dop.service.UserProfileService;
import cgt.dop.service.perf.InfluxComparisionTestService;
import cgt.dop.service.perf.InfluxHelper;
import cgt.dop.service.perf.InfluxSingleTestService;
import cgt.dop.service.perf.JmeterService;
import cgt.dop.service.perf.NeoLoadService;
import cgt.dop.service.perf.predictive.PerfPredictiveAnalysisService;
import cgt.dop.service.scrummaster.feature.FeatureFactory;
import cgt.dop.service.scrummaster.jenkins.JenkinsBuildService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	private InfluxSingleTestService influxSingleTestService;
	
	@Autowired
	private InfluxComparisionTestService influxComparisionTestService;

	@Autowired
	private JenkinsBuildService jenkinsBuildService;
	
	@Autowired
	public DashBoardFactory dashBoardFactory;
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private MonitorService monitorService;
	
	@Autowired
	private SonarService sonarService;
	
	@Autowired
	private PerfPredictiveAnalysisService perfService;
	
	@Autowired
	private TestUpdateService testUpdateService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private TesterObservationsService testerObservationsService;

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
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_KPI_Improvement_For_Last_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterKPIImprovementForLastBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getKPIImprovementForLastBuild();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Details, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionDetails() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTransactionDetailsForLastestBuild();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Transaction_Per_Second_With_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTransactionPerSecondWithBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTransactionPerSecondWithBuild();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_For_Previous_Builds, method = RequestMethod.GET)
	public @ResponseBody String getJmeterResponseTimeForPreviousBuilds() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getResponseTimeForPreviousBuilds();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Percentile_Trend, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorPercentileTrend() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getErrorPercentileTrend();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Response_Time_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterResponseTimeLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getResponseTimeLastestBuild();
	}
	

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Error_Time_For_Lastest_Build, method = RequestMethod.GET)
	public @ResponseBody String getJmeterErrorTimeForLastestBuild() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getErrorTimeForLastestBuild();
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Jmeter_Test_Execution_Summary, method = RequestMethod.GET)
	public @ResponseBody String getJmeterTestExecutionSummary() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return jmeterService.getTestExecutionSummary();
	}
	
	/* ----------- Jmeter End ------------ */
	

	
	/* Scrum Master*/
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.JIRA_SERVICES, method = RequestMethod.GET)
	public @ResponseBody FeatureBean getJiraService() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		
		return  new FeatureFactory()
				.getFeatureWidget(monitorDao.getWidgetDetail("Features"));
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
	
	
	@CrossOrigin
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


	/* QC(ALM)*/

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
	
	/*LENNOX PROJECT SERVICES  */
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE, method = RequestMethod.GET)
	public @ResponseBody String getUserProfile() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return influxSingleTestService.getUserProfile();
	}
	
	
	//Edited by Sankar
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getUserProfile(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxSingleTestService.getUserProfile(testId);	
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Get_Report, method = RequestMethod.GET)
	public @ResponseBody String getJasperReport(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		influxSingleTestService.getReport(testId);
		return "";
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_ACTIVE_THREADS, method = RequestMethod.GET)
	public @ResponseBody String getActiveThreads() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");

		return influxSingleTestService.getActiveThreads();
		
	}
	
	//Refactoring with lennox_projectdetails table...
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_ACTIVE_THREADS_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getActiveThreadsLive(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		System.out.println("===========inside getActiveThreads() for a given test===============");
		if(testId.equalsIgnoreCase("1") ||  testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined"))	{
			return influxSingleTestService.getActiveThreads();
		} else	{
				HashMap<String, String> testInfo = getTestInfoCommon(testId);
				return influxSingleTestService.getActiveThreads(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
			}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_ACTIVE_THREADS_LIVE_GRAPH, method = RequestMethod.GET)
	public @ResponseBody String getActiveThreadsLiveGraph(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		System.out.println("===========inside getActiveThreads() for a given test===============");
		if(testId.equalsIgnoreCase("1") ||  testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined"))	{
			return influxSingleTestService.getActiveThreads();
		} else	{
				HashMap<String, String> testInfo = getTestInfoCommon(testId);
				return influxSingleTestService.getActiveThreadsGraphs(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
			}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_LIVE_TEST_INFO, method = RequestMethod.GET)
	public @ResponseBody String getLiveTestInfo(@RequestParam("testId") String testId) throws Exception {
		HashMap<String, String> testInfo = getTestInfoCommon(testId);
		return testInfo.get("testStartTime");
	}
	
	public HashMap<String, String> getTestInfoCommon(String testId) throws Exception {
		HashMap<String, String> hm = new HashMap<String, String>();
		//hit the lennox_projectdetails table of lennox_new DB to fetch testStartTime, testEndTime, testSuite and projectId based on the testId.
		List<LennoxProjectDetails> testDetails = jmeterService.getTestIdInfoFromLennoxDB(testId);
		
		//get the below values from above object testDetails...
		hm.put("testStartTime", testDetails.get(0).getTeststarttime());
		hm.put("testEndTime", testDetails.get(0).getTestendtime());
		hm.put("testSuite", testDetails.get(0).getTestsuite());
		
		//get the project name based on the above project Id & then hit the Influx DB.		
		List<ProjectModel> projectInfoList = jmeterService.getProjectNameFromLennoxDB(testDetails.get(0).getProjectId());
		//
		//hm.put("projectName", projectInfoList.get(0).getProject_Name());
		hm.put("projectName", testDetails.get(0).getProjectId());
		return hm;
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_OBSERVATIONS, method = RequestMethod.GET)
	public @ResponseBody String getKPI() throws Exception {
		/*HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");*/
		System.out.println("===========inside single test===============");
		return influxSingleTestService.getKPI();
		
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_OBSERVATIONS_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getKPILive(@RequestParam("testId") String testId) throws Exception {
		System.out.println("===========KPI test for test ID===============" +testId);
		if(testId.equalsIgnoreCase("1") ||  testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined"))	{
			return influxSingleTestService.getKPI();
		} else	{
			System.out.println("===========inside getKPILive() for a given test===============");
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getKPILive(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));	
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_AGGREGATE_REPORT, method = RequestMethod.GET)
	public @ResponseBody String getAggregateReport() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxSingleTestService.getAggregateReport();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_AGGREGATE_REPORT_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getAggregateReportLive(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		if(testId.equalsIgnoreCase("1") ||  testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined"))	{
			return influxSingleTestService.getAggregateReport();
		} else	{
			System.out.println("===========inside getAggregateReportLive() for a given test===============");
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getAggregateReport(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));	
			}
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_HITS_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getHitsPerSeconds() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxSingleTestService.getHitsPerSeconds();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_HITS_PERSECOND_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getHitsPerSecondsLive(@RequestParam("testId") String testId)throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		if (testId.equalsIgnoreCase("1") || testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined")) {
			return influxSingleTestService.getHitsPerSeconds();
		} else {
			System.out.println("===========inside getHitsPerSeconds() for a given test===============");
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getHitsPerSeconds(testInfo.get("testStartTime"), testInfo.get("testEndTime"),testInfo.get("testSuite"), testInfo.get("projectName"));
		}

	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_RESPONSE_CODES_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getResponseCodes() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxSingleTestService.getResponseCodes();
		
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_RESPONSE_CODES_PERSECOND_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getResponseCodesLive(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		System.out.println("===========inside getResponseCodes() for a given test===============");
		if (testId.equals("1") || testId.equals("2") || testId.equalsIgnoreCase("undefined")) {
			return influxSingleTestService.getResponseCodes();
		} else {
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getResponseCodes(testInfo.get("testStartTime"), testInfo.get("testEndTime"),testInfo.get("testSuite"), testInfo.get("projectName"));
		}

	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_RESPONSE_TIMES_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getResponseTimes() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxSingleTestService.getResponseTimes();
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_RESPONSE_TIMES_PERSECOND_LIVE, method = RequestMethod.GET)
	public @ResponseBody String getResponseTimesLive(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		if (testId.equalsIgnoreCase("1") || testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined")) {
			return influxSingleTestService.getResponseTimes();
		} else {
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getResponseTimesLive(testInfo.get("testStartTime"), testInfo.get("testEndTime"),testInfo.get("testSuite"), testInfo.get("projectName"));
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.Lennox_JMETER_RESPONSE_TIMES_PLOTYGRAPH, method = RequestMethod.GET)
	public @ResponseBody String getResponseTimesLiveGraph(@RequestParam("testId") String testId) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		if (testId.equalsIgnoreCase("1") || testId.equalsIgnoreCase("2") || testId.equalsIgnoreCase("undefined")) {
			return influxSingleTestService.getResponseTimes();
		} else {
			HashMap<String, String> testInfo = getTestInfoCommon(testId);
			return influxSingleTestService.getResponseTimesLiveGraph(testInfo.get("testStartTime"), testInfo.get("testEndTime"),testInfo.get("testSuite"), testInfo.get("projectName"));
		}
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_ADD_TEST_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String addTestUpdate(@RequestBody  TestUpdateBean testUpdatBean	
			) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		TestUpdateModel testUpdateModel = new TestUpdateModel();
		testUpdateModel.setComment_name(testUpdatBean.getComment_name());
		testUpdateModel.setUser_name(testUpdatBean.getUser_name());
		testUpdateModel.setTest_type(testUpdatBean.getTest_type());
		testUpdateModel.setTest_num(testUpdatBean.getTest_num());
		Date date = new Date();
	    String DATE_FORMAT = "MM/dd/yyyy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		testUpdateModel.setComment_date(sdf.format(date));
		return testUpdateService.addTestUpdate(testUpdateModel); 
		//return "test";
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_ADD_TEST_UPDATEDETAILS_COMPARE, method = RequestMethod.POST)
	public @ResponseBody String addTestUpdateDetailsCompare(@RequestBody  TestUpdateBean testUpdatBean	
			) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		TestUpdateModel testUpdateModel = new TestUpdateModel();
		testUpdateModel.setComment_name(testUpdatBean.getComment_name());
		testUpdateModel.setUser_name(testUpdatBean.getUser_name());
		testUpdateModel.setTest_type(testUpdatBean.getTest_type());
		testUpdateModel.setTestwith(testUpdatBean.getTestwith());
		testUpdateModel.setTestto(testUpdatBean.getTestto());
		System.out.println("test with is"+testUpdatBean.getTestwith()+"testto is=="+testUpdatBean.getTestto());
		Date date = new Date();
	    String DATE_FORMAT = "MM/dd/yyyy";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		testUpdateModel.setComment_date(sdf.format(date));
		return testUpdateService.addTestUpdateDetailsCompare(testUpdateModel); 
		//return "test";
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_SAVE_TEST_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String saveTestUpdate(@RequestBody  TestUpdateBean testUpdatBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		boolean flag = testUpdateService.updateTestUpdateDetails(testUpdatBean.getId(), testUpdatBean.getComment_name(), testUpdatBean.getUser_name(), testUpdatBean.getComment_date());
		if(flag = true){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_SAVE_TEST_NAME_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String saveTestNameUpdateDetails(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		boolean flag = testUpdateService.updateTestNameDetails(userProfileBean.getTestName(), userProfileBean.getScenarios());
		if(flag){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_SAVE_DATE_TIME_ZONE_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String saveDateTimeZonedateDetails(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		boolean flag = testUpdateService.updateDateTimeZone(userProfileBean.getTestName(), userProfileBean.getTestdatetimezone());
		if(flag){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_SAVE_SCENARIO_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String updateScenariosDetails(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		boolean flag = testUpdateService.updateScenarioDetails(userProfileBean.getTestName(), userProfileBean.getTestscenarios());
		if(flag){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_SAVE_ENV_URL_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String updateQaEnvDetails(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		boolean flag = testUpdateService.updateQaEnvUrlDetails(userProfileBean.getTestName(), userProfileBean.getTestenvurl());
		if(flag){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_DEL_TEST_UPDATE, method = RequestMethod.POST)
	public @ResponseBody String deleteTestUpdate(@RequestBody  TestUpdateBean testUpdatBean	
			) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		TestUpdateModel testUpdateModel = new TestUpdateModel();
		testUpdateModel.setId(testUpdatBean.getId());
		
		String result = testUpdateService.deleteTestUpdate(testUpdateModel); 
		if("success".equals(result)){
			return "{\"result\":\"success\"}";
		}else{
			return "{\"result\":\"fail\"}";
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_RETREIVE_TOP_FIVE_TEST_UPDATE_DETAILS, method = RequestMethod.POST)
	public @ResponseBody String retreiveTopFiveTestUpdateDetails(@RequestBody  TestUpdateBean testUpdatBean) throws Exception {
		
		System.out.println("testUpdatBean.getTest_type() ==============="+testUpdatBean.getTest_type());
		
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return new ObjectMapper().writeValueAsString(testUpdateService.findTesterUpdatesByTestType(testUpdatBean.getTest_type(),testUpdatBean.getTest_num()));	
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_RETREIVE_TOP_FIVE_TEST_UPDATE_DETAILS_COMPARE, method = RequestMethod.POST)
	public @ResponseBody String retreiveTopFiveTestUpdateDetailsCompare(@RequestBody  TestUpdateBean testUpdatBean) throws Exception {
		
		System.out.println("testUpdatBean.getTest_type() ==============="+testUpdatBean.getTest_type());
		
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return new ObjectMapper().writeValueAsString(testUpdateService.findTesterUpdatesCompareByTestType(testUpdatBean.getTest_type(),testUpdatBean.getTestwith(),testUpdatBean.getTestto()));	
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE_SAVE_TEST_NAME, method = RequestMethod.POST)
	public @ResponseBody String addTestName(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		LennoxUserProfile lennoxUserProfile = new LennoxUserProfile();
		lennoxUserProfile.setDbStartTime(userProfileBean.getStartTime());
		lennoxUserProfile.setDbEndTime(userProfileBean.getEndTime());
		lennoxUserProfile.setTestName(userProfileBean.getTestName());
		/*lennoxUserProfile.setTestEnvUrl(userProfileBean.getTestEnvUrl());
		lennoxUserProfile.setTotalUsers(userProfileBean.getTotalUsers());
		lennoxUserProfile.setScenarios(userProfileBean.getScenarios());*/
				
		return userProfileService.saveUserProfile(lennoxUserProfile); 
		//return "test";
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE_SAVE_SCENARIOS, method = RequestMethod.POST)
	public @ResponseBody String addScenarios(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		LennoxUserProfile lennoxUserProfile = new LennoxUserProfile();
		lennoxUserProfile.setTestName(userProfileBean.getTestName());
		lennoxUserProfile.setDbStartTime(userProfileBean.getStartTime());
		lennoxUserProfile.setDbEndTime(userProfileBean.getEndTime());
		lennoxUserProfile.setScenarios(userProfileBean.getTestscenarios());
		/*lennoxUserProfile.setTestEnvUrl(userProfileBean.getTestEnvUrl());
		lennoxUserProfile.setTotalUsers(userProfileBean.getTotalUsers());
		lennoxUserProfile.setScenarios(userProfileBean.getScenarios());*/
				
		return userProfileService.saveUserProfileScenarios(lennoxUserProfile); 
		//return "test";
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE_SAVE_TEST_URL, method = RequestMethod.POST)
	public @ResponseBody String addTestUrl(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		LennoxUserProfile lennoxUserProfile = new LennoxUserProfile();
		lennoxUserProfile.setTestName(userProfileBean.getTestName());
		lennoxUserProfile.setDbStartTime(userProfileBean.getStartTime());
		lennoxUserProfile.setDbEndTime(userProfileBean.getEndTime());
		lennoxUserProfile.setTestEnvUrl(userProfileBean.getTestenvurl());
		
				
		return userProfileService.saveUserProfileTestUrl(lennoxUserProfile); 
		//return "test";
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_USER_PROFILE_SAVE_TEST_DATE, method = RequestMethod.POST)
	public @ResponseBody String addTestDate(@RequestBody  UserProfileBean userProfileBean) throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		LennoxUserProfile lennoxUserProfile = new LennoxUserProfile();
		lennoxUserProfile.setTestName(userProfileBean.getTestName());
		lennoxUserProfile.setDbStartTime(userProfileBean.getStartTime());
		lennoxUserProfile.setDbEndTime(userProfileBean.getEndTime());
		lennoxUserProfile.setTestDate(userProfileBean.getTestdatetimezone());
		
				
		return userProfileService.saveUserProfileTestDate(lennoxUserProfile); 
		//return "test";
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_ADD_TESTER_OBSERVATION, method = RequestMethod.POST)
	public @ResponseBody String addTesterObservations(@RequestBody  TesterObservationBean testerObservationBean) throws Exception {
		return testerObservationsService.addTesterObservations(testerObservationBean);
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_GET_TESTER_OBSERVATION_COMMENT, method = RequestMethod.GET)
	public @ResponseBody String getTesterObservationsByWidgetFlag(@RequestParam  String widgetFlag) throws Exception {
		String comment = testerObservationsService.getTesterObservationsByWidgetFlag(widgetFlag);
		String json = new ObjectMapper().writeValueAsString(comment);
		return json;
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_GET_TESTER_OBSERVATION, method = RequestMethod.GET)
	public @ResponseBody String getTesterObservations() throws Exception {
		List<TesterObservationBean> comments = testerObservationsService.getTesterObservations();
		String json = new ObjectMapper().writeValueAsString(comments);
		return json;
	}
	
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_COMP_KPI, method = RequestMethod.GET)
	public @ResponseBody String getKPIForComparisionTest() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxComparisionTestService.getKPIForComparisionTest();
		
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_COMP_AGGREGATE_REPORT, method = RequestMethod.GET)
	public @ResponseBody String getAggregateReportForComparisionTest() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxComparisionTestService.getAggregateReportForComparisionTest();
		
	}

	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_COMP_HITS_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getHitsPerSecondsForComparisionTest() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxComparisionTestService.getHitsPerSecondsForComparisionTest();
		
	}
	
	@CrossOrigin
	@RequestMapping(value = DevOpsRestURIConstants.LENNOX_JMETER_COMP_RESPONSE_CODES_PERSECOND, method = RequestMethod.GET)
	public @ResponseBody String getResponseCodesForComparisionTest() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.add("Access-Control-Allow-Origin", "*");
		return influxComparisionTestService.getResponseCodesForComparisionTest();
		
	}
 @CrossOrigin
    @RequestMapping(value = DevOpsRestURIConstants.LastFiveBuilds, method = RequestMethod.GET)
    public @ResponseBody String getLastFiveBuilds() throws Exception {
           HttpHeaders header = new HttpHeaders();
           header.add("Access-Control-Allow-Origin", "*");
           System.out.println("===========inside Last 5 Test details===============");
           ArrayList<LennoxProjectDetails> lastFiveBuildsWithMaxUsers = jmeterService.lastFivebuild();
           ArrayList<LennoxProjectDetails> lastFiveBuildsWithKPISignal = new ArrayList<LennoxProjectDetails>(); 
           for(LennoxProjectDetails lpd : lastFiveBuildsWithMaxUsers){
        	   HashMap<String, String> testInfo = getTestInfoCommon(lpd.getTestname());
        	   ObservationsBean observationsBean = influxSingleTestService.getKPIInfoLive(testInfo.get("testStartTime"), testInfo.get("testEndTime"), testInfo.get("testSuite"), testInfo.get("projectName"));
        	   if((120.0 - observationsBean.getTps()) > 0 || (0.0 - observationsBean.getError() < 0))	{
        		   //Set KPI signal of lpd as Red....
        		   lpd.setKpiSignalDanger("true");
        		   lpd.setKpiSignalSuccess("false");
        	   }else	{
        		   lpd.setKpiSignalDanger("false");
        		   lpd.setKpiSignalSuccess("true");
        	   }
        	   //adding updated test name by user
        	   if(null!=lpd.getTestupdatedname())  {
        		   lpd.setTestsuite(lpd.getTestupdatedname());  
        	   }
        	   //To add the testDuration time in HrMinSec format...
        	   Long testDurationLong = Long.parseLong(testInfo.get("testEndTime")) - Long.parseLong(testInfo.get("testStartTime"));
           	   LocalTime testDurationInHHMMSS = LocalTime.ofSecondOfDay(testDurationLong/1000);
           	   lpd.setTestDurationInHrMinSecFormat(influxSingleTestService.getTestDurInHrMinSecFormat(testDurationInHHMMSS.toString()));
           	   
        	   lastFiveBuildsWithKPISignal.add(lpd);
           }
           return new ObjectMapper().writeValueAsString(lastFiveBuildsWithKPISignal);
    }
    
    @CrossOrigin
    @RequestMapping(value = DevOpsRestURIConstants.AllTestNos, method = RequestMethod.GET)
    public @ResponseBody String getAllTestNos() throws Exception {
           HttpHeaders header = new HttpHeaders();
           header.add("Access-Control-Allow-Origin", "*");
           System.out.println("===========inside All test numbers===============");
           return jmeterService.getAllTestNos();
           
    }
    //
    @CrossOrigin
    @RequestMapping(value = DevOpsRestURIConstants.SAVE_TESTDETAILSFROMGRAFANA, method = RequestMethod.POST)
    public @ResponseBody String saveTestDetailsFromGrafana(@RequestParam String project,@RequestParam String scenario,@RequestParam String testname,@RequestParam String startTime,@RequestParam String endTime ) throws Exception {
           HttpHeaders header = new HttpHeaders();
           header.add("Access-Control-Allow-Origin", "*");
           System.out.println("===========inside Save test details===============");
           
           LennoxTestDetails lennoxUserProfile = new LennoxTestDetails();
           lennoxUserProfile.setBuildno("10");
           lennoxUserProfile.setProjectId(project);
           lennoxUserProfile.setTestsuite(scenario);
           lennoxUserProfile.setTestName(testname);
           lennoxUserProfile.setStartTime(startTime);
           lennoxUserProfile.setEndTime(endTime);
           
          return userProfileService.saveGraphanaTestDetails(lennoxUserProfile); 
//           if(flag = true){
//   			return "success";
//   		   }else{
//   			return "fail";
//   		    }
           
    }
    
    @CrossOrigin
    @RequestMapping(value = DevOpsRestURIConstants.GETINFLUX_PROJECTS, method = RequestMethod.GET)
    public @ResponseBody String getProjectDetailsFromInflux() throws Exception {
           HttpHeaders header = new HttpHeaders();
           header.add("Access-Control-Allow-Origin", "*");
           System.out.println("===========inside Project details from Influx===============");

          return jmeterService.getinfluxProjectDetails();            
    }
    
}