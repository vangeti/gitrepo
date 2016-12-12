package cgt.dop.bean;

import java.util.List;
import java.util.Map;

public class BuildDetailsBean {

	private String jobname;
	

	private List<BuildInfoBean> builddetails;
	private Map<String,Integer> totalnumberofBuilds;
	private Map<String, Integer> buildstatus;
	private Map<String, List<Integer>> eachDayBuildStatus;
	private Map<String, Integer> eachDayBuildCount;
	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public List<BuildInfoBean> getBuilddetails() {
		return builddetails;
	}

	public void setBuilddetails(List<BuildInfoBean> builddetails) {
		this.builddetails = builddetails;
	}

	public Map<String,Integer> getTotalnumberofBuilds() {
		return totalnumberofBuilds;
	}

	public void setTotalnumberofBuilds(Map<String,Integer> totalnumberofBuilds) {
		this.totalnumberofBuilds = totalnumberofBuilds;
	}

	public Map<String, Integer> getBuildstatus() {
		return buildstatus;
	}

	public void setBuildstatus(Map<String, Integer> buildstatus) {
		this.buildstatus = buildstatus;
	}

	public Map<String, List<Integer>> getEachDayBuildStatus() {
		return eachDayBuildStatus;
	}

	public void setEachDayBuildStatus(Map<String, List<Integer>> eachDayBuildStatus) {
		this.eachDayBuildStatus = eachDayBuildStatus;
	}

	public Map<String, Integer> getEachDayBuildCount() {
		return eachDayBuildCount;
	}

	public void setEachDayBuildCount(Map<String, Integer> eachDayBuildCount) {
		this.eachDayBuildCount = eachDayBuildCount;
	}

}
