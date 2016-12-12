package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "jmeter_aggrigate")
public class JmeterAggrigateModel {

	@Id
	@Column(name="sno")
	private int sno;
	
	@Column(name="projectId")
	private int projectId;
	
	@Column(name="buildNo")
	private int buildNo;
	
	@Column(name="avg_time")
	private double avg_time;
	
	@Column(name="avg_throughput")
	private double avg_throughput;
	
	@Column(name="errorpercentage")
	private double errorpercentage;
	
	@Column(name="testdate")
	private String testdate;
	
	@Column(name="testDuration")
	private String testDuration;
	
	@Column(name="Vuser")
	private String Vuser;
	
	@Column(name="totalThroughput")
	private double totalThroughput;
	
	@Column(name="avghits")
	private double avghits;
	
	@Column(name="totalhits")
	private double totalhits;
	
	@Column(name="avgsamples")
	private double avgsamples;
	
	@Column(name="totalsamples")
	private double totalsamples;


	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}

	public double getAvg_time() {
		return avg_time;
	}

	public void setAvg_time(double avg_time) {
		this.avg_time = avg_time;
	}

	public double getAvg_throughput() {
		return avg_throughput;
	}

	public void setAvg_throughput(double avg_throughput) {
		this.avg_throughput = avg_throughput;
	}

	public double getErrorpercentage() {
		return errorpercentage;
	}

	public void setErrorpercentage(double errorpercentage) {
		this.errorpercentage = errorpercentage;
	}

	public String getTestdate() {
		return testdate;
	}

	public void setTestdate(String testdate) {
		this.testdate = testdate;
	}

	public String getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(String testDuration) {
		this.testDuration = testDuration;
	}

	public String getVuser() {
		return Vuser;
	}

	public void setVuser(String vuser) {
		Vuser = vuser;
	}

	public double getTotalThroughput() {
		return totalThroughput;
	}

	public void setTotalThroughput(double totalThroughput) {
		this.totalThroughput = totalThroughput;
	}

	public double getAvghits() {
		return avghits;
	}

	public void setAvghits(double avghits) {
		this.avghits = avghits;
	}

	public double getTotalhits() {
		return totalhits;
	}

	public void setTotalhits(double totalhits) {
		this.totalhits = totalhits;
	}

	public double getAvgsamples() {
		return avgsamples;
	}

	public void setAvgsamples(double avgsamples) {
		this.avgsamples = avgsamples;
	}

	public double getTotalsamples() {
		return totalsamples;
	}

	public void setTotalsamples(double totalsamples) {
		this.totalsamples = totalsamples;
	}

	
	
}
