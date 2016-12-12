package cgt.dop.bean;

public class InfluxUserProfileBean {
	private String maxThreads;
	private String timeStamp;
	private String teststarttime;
	private String testendtime;
	private String rampup;
	private String rampdown;
	private String steadyperiod;
	private String testduration;
	private String scenario_name;
	private String testName; 
	private String testdatetimezone;
	private String testenvurl;
	private String testscenarios;

	private double error;
	private double tps;
	private double responseTime;
	
	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double getTps() {
		return tps;
	}

	public void setTps(double tps) {
		this.tps = tps;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(String maxThreads) {
		this.maxThreads = maxThreads;
	}

	public String getTeststarttime() {
		return teststarttime;
	}

	public void setTeststarttime(String teststarttime) {
		this.teststarttime = teststarttime;
	}

	public String getTestendtime() {
		return testendtime;
	}

	public void setTestendtime(String testendtime) {
		this.testendtime = testendtime;
	}

	public String getRampup() {
		return rampup;
	}

	public void setRampup(String rampup) {
		this.rampup = rampup;
	}

	public String getRampdown() {
		return rampdown;
	}

	public void setRampdown(String rampdown) {
		this.rampdown = rampdown;
	}

	public String getSteadyperiod() {
		return steadyperiod;
	}

	public void setSteadyperiod(String steadyperiod) {
		this.steadyperiod = steadyperiod;
	}

	public String getScenario_name() {
		return scenario_name;
	}

	public void setScenario_name(String scenario_name) {
		this.scenario_name = scenario_name;
	}

	public String getTestduration() {
		return testduration;
	}

	public void setTestduration(String testduration) {
		this.testduration = testduration;
	}

	public String getTestdatetimezone() {
		return testdatetimezone;
	}

	public void setTestdatetimezone(String testdatetimezone) {
		this.testdatetimezone = testdatetimezone;
	}

	public String getTestenvurl() {
		return testenvurl;
	}

	public void setTestenvurl(String testenvurl) {
		this.testenvurl = testenvurl;
	}

	public String getTestscenarios() {
		return testscenarios;
	}

	public void setTestscenarios(String testscenarios) {
		this.testscenarios = testscenarios;
	}

	
	

}
