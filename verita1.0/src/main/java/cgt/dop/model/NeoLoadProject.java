package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "neoload_project")
public class NeoLoadProject {

	@Id
	@Column(name="buildNo")
	private int buildNo;
	@Column(name="projectId")
	private int projectId;
	@Column(name="apdex")
	private double apdex;
	
	
	public int getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public double getApdex() {
		return apdex;
	}
	public void setApdex(double apdex) {
		this.apdex = apdex;
	}
	
	
}