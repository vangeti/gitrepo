package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jmeter_errorstime")
public class JmeterErrorsTimeModel {

	@Id
	@Column(name="sno")
	private int sno;
	
	@Column(name="projectId")
	private int projectId;
	
	@Column(name="buildNo")
	private int buildNo;
	
	@Column(name="elapsedTime")
	private String elapsedTime;
	
	@Column(name="responseTime")
	private double responseTime;

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

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}
	
	
	
}
