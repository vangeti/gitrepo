package cgt.dop.rest.uri.constants;

public class DevOpsRestURIConstants {

	public static final String GET_LOGIN = "devops/rest/login/{id}/{pwd}";
	public static final String POST_LOGIN = "devops/rest/logincheck";
	public static final String Get_Domains = "devops/rest/getDomains";
	public static final String Get_Projects = "devops/rest/getProjects";
	public static final String Delete_Widget = "devops/rest/delWidget/{user_id}/{widget_id}";
	public static final String Add_widget = "devops/rest/addWidget/{user_id}/{widget_id}";

	/* Performance */
	public static final String KPI_Improvement_For_Last_Build = "devops/rest/kpflb";
	public static final String Test_Execution_Summary = "devops/rest/tes";
	public static final String Transaction_Details = "devops/rest/td";
	public static final String Transaction_Per_Second_With_Build = "devops/rest/tpswb";
	public static final String Response_Time_For_Previous_Builds = "devops/rest/rtfpb";
	public static final String Error_Percentile_Trend = "devops/rest/ept";

	public static final String Jmeter_KPI_Improvement_For_Last_Build = "devops/rest/jmkpflb";
	public static final String Jmeter_Transaction_Details = "devops/rest/jmtd";
	public static final String Jmeter_Transaction_Per_Second_With_Build = "devops/rest/jmtpswb";
	public static final String Jmeter_Response_Time_For_Previous_Builds = "devops/rest/jmrtfpb";
	public static final String Jmeter_Error_Percentile_Trend = "devops/rest/jmept";
	public static final String Jmeter_Error_Time_For_Lastest_Build = "devops/rest/jmetflb";
	public static final String Jmeter_Test_Execution_Summary = "devops/rest/jmtes";
	public static final String Jmeter_Response_Time_Lastest_Build = "devops/rest/jmrtlb";

	public static final String Perf_Avg_Resp_Time_Predictive = "devops/rest/perfavgresptimepred";
	public static final String Perf_CPU_Utilization_Predictive = "devops/rest/perfcpuutilization";
	public static final String Perf_Scalability_Factor_Predictive = "devops/rest/scalabilityfactorpred";

	/* QC(ALM) */
	public static final String ReleaseReadynessKPI = "devops/rest/releaseReadynessKPI";
	public static final String PrescriptiveAnalysis = "devops/rest/prescriptiveAnalysis";

	/* Scrum Master */
	public static final String ScrumMasterDefectSeverityIndex = "devops/rest/smdsi";
	public static final String ScrumMasterTestExecutionProgress = "devops/rest/smtep";
	public static final String ScrumMasterTestCoverage = "devops/rest/smtc";
	public static final String ScrumMasterOpenDefectsBySeverity = "devops/rest/smods";
	public static final String ScrumMasterRequirementStabilityIndex = "devops/rest/smrsi";
	public static final String ScrumMasterFeatureReadiness = "devops/rest/smfr";
	public static final String ScrumMasterMeanTime = "devops/rest/smmt";
	public static final String ScrumMasterTestCaseYield = "devops/rest/smtcy";

	public static final String JIRA_SERVICES = "devops/rest/jira";
	public static final String JIRA_VELOCITY = "devops/rest/jiravelocity";

	public static final String LastNDaysBuildCountPerDay = "devops/rest/lndbcpd";
	public static final String GET_TOTAL_BUILDS = "devops/rest/gtb";
	public static final String ScrumMasterMonitor = "devops/rest/scrummastermonitor";
	public static final String ScrumMasterDeploy = "devops/rest/scrummasterdeploy";
	public static final String ScrumMasterSonar = "devops/rest/scrummastersonar";

	public static final String Selenium_Test_Details = "devops/rest/std";

	public static final String RESPTIME = "devops/rest/respTime/{rTime}";

	public static final String CUSTOMDEFECTWIDGET = "devops/rest/customdefect";

}
