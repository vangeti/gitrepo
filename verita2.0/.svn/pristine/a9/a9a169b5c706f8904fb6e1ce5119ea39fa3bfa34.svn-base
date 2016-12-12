package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="lennox_user_profile")
public class LennoxUserProfile {
	
	@Id
	@Column(name="testNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long testNo;
	
	@Column(name="testName")
	private String testName;
	@Column(name="testEnvUrl")
	private String testEnvUrl;
	@Column(name="startTime")
	private long dbStartTime;
	@Column(name="endTime")
	private long dbEndTime;
	@Column(name="scenarios")
	private String scenarios;
	@Column(name="testDate")
	private String testDate;
	
	@Transient
	private String rampUpDuration;
	@Transient
	private String rampDownDuration;
	@Transient
	private String steadyPeriod;
	@Transient
	private String duration;
	@Transient
	private String startTime;
	@Transient
	private String endTime;
	@Transient
	private long totalUsers;
	
	
	public long getTestNo() {
		return testNo;
	}
	public void setTestNo(long testNo) {
		this.testNo = testNo;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public long getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
	public String getTestEnvUrl() {
		return testEnvUrl;
	}
	public void setTestEnvUrl(String testEnvUrl) {
		this.testEnvUrl = testEnvUrl;
	}
	
	public String getScenarios() {
		return scenarios;
	}
	public void setScenarios(String scenarios) {
		this.scenarios = scenarios;
	}
	public String getRampUpDuration() {
		return rampUpDuration;
	}
	public void setRampUpDuration(String rampUpDuration) {
		this.rampUpDuration = rampUpDuration;
	}
	public String getRampDownDuration() {
		return rampDownDuration;
	}
	public void setRampDownDuration(String rampDownDuration) {
		this.rampDownDuration = rampDownDuration;
	}
	public String getSteadyPeriod() {
		return steadyPeriod;
	}
	public void setSteadyPeriod(String steadyPeriod) {
		this.steadyPeriod = steadyPeriod;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public long getDbStartTime() {
		return dbStartTime;
	}
	public void setDbStartTime(long dbStartTime) {
		this.dbStartTime = dbStartTime;
	}
	public long getDbEndTime() {
		return dbEndTime;
	}
	public void setDbEndTime(long dbEndTime) {
		this.dbEndTime = dbEndTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	
}
