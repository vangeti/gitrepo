package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jmeter_transactiondetails")
public class JmeterTransactionDetailsModel {

	@Id
	@Column(name="sno")
	private int sno;
	@Column(name="projectId")
	private int projectId;
	@Column(name="buildNo")
	private int buildNo;
	@Column(name="transactionName")
	private String transactionName;
	@Column(name="avg_time")
	private double avg_time;
	@Column(name="min_time")
	private double min_time;
	@Column(name="max_time")
	private double max_time;
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
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public double getAvg_time() {
		return avg_time;
	}
	public void setAvg_time(double avg_time) {
		this.avg_time = avg_time;
	}
	public double getMin_time() {
		return min_time;
	}
	public void setMin_time(double min_time) {
		this.min_time = min_time;
	}
	public double getMax_time() {
		return max_time;
	}
	public void setMax_time(double max_time) {
		this.max_time = max_time;
	}
	
	
	
	
}
