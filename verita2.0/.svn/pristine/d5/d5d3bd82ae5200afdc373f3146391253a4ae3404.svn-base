package cgt.dop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="lennox_jmeter_project")
public class LennoxJmeterProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sno;
	
	@Column(name="projectId")
	private int projectId;
	@Column(name="testNo")
	private long testNo;
	@Column(name="startDate")
	private Date startDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(name="singleTestComments")
	private String singleTestComments;
	
	@Column(name="testName")
	private String testName;
	@Column(name="noOfHybrisUsers")
	private int noOfHybrisUsers;
	@Column(name="noOfLegacyUsers")
	private int noOfLegacyUsers;
	@Column(name="scenarios")
	private String scenarios;
	@Column(name="qaEnvUrl")
	private String qaEnvUrl;
	
	@Transient
	private double duration;
	@Transient
	private double rampUp;
	@Transient
	private double rampdown;
	@Transient
	private double steadyPeriod;
	
	
	@Column(name="fileName")
	private String fileName;
	
	@Column(name="startDateString")
	private String startDateString;
	
	@Column(name="endDateString")
	private String endDateString;
	
	public long getSno() {
		return sno;
	}
	public void setSno(long sno) {
		this.sno = sno;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public long getTestNo() {
		return testNo;
	}
	public void setTestNo(long testNo) {
		this.testNo = testNo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSingleTestComments() {
		return singleTestComments;
	}
	public void setSingleTestComments(String singleTestComments) {
		this.singleTestComments = singleTestComments;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public int getNoOfHybrisUsers() {
		return noOfHybrisUsers;
	}
	public void setNoOfHybrisUsers(int noOfHybrisUsers) {
		this.noOfHybrisUsers = noOfHybrisUsers;
	}
	public int getNoOfLegacyUsers() {
		return noOfLegacyUsers;
	}
	public void setNoOfLegacyUsers(int noOfLegacyUsers) {
		this.noOfLegacyUsers = noOfLegacyUsers;
	}
	public String getScenarios() {
		return scenarios;
	}
	public void setScenarios(String scenarios) {
		this.scenarios = scenarios;
	}
	public String getQaEnvUrl() {
		return qaEnvUrl;
	}
	public void setQaEnvUrl(String qaEnvUrl) {
		this.qaEnvUrl = qaEnvUrl;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public double getRampUp() {
		return rampUp;
	}
	public void setRampUp(double rampUp) {
		this.rampUp = rampUp;
	}
	public double getRampdown() {
		return rampdown;
	}
	public void setRampdown(double rampdown) {
		this.rampdown = rampdown;
	}
	public double getSteadyPeriod() {
		return steadyPeriod;
	}
	public void setSteadyPeriod(double steadyPeriod) {
		this.steadyPeriod = steadyPeriod;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
}
