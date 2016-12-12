package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jmeter_projectdetails")
public class JmeterProjectDetails {
	@Id
	@Column(name="sno")
	private int sno;
	@Column(name="testname")
	private String testname;
	@Column(name="buildNo")
	private int buildNo;
	@Column(name="teststarttime")
	private String teststarttime;
	@Column(name="testendtime")
	private String testendtime;
	
	@Column(name="projectId")
	private String projectId;
	
	@Column(name="transactionTrend")
	private String transactionTrend;
	@Column(name="responseTrend")
	private String responseTrend;
	@Column(name="errorTrend")
	private String errorTrend;
	
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public int getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
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
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getTransactionTrend() {
		return transactionTrend;
	}
	public void setTransactionTrend(String transactionTrend) {
		this.transactionTrend = transactionTrend;
	}
	public String getResponseTrend() {
		return responseTrend;
	}
	public void setResponseTrend(String responseTrend) {
		this.responseTrend = responseTrend;
	}
	public String getErrorTrend() {
		return errorTrend;
	}
	public void setErrorTrend(String errorTrend) {
		this.errorTrend = errorTrend;
	}
	
}