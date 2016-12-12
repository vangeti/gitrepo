package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="project_master")
public class ProjectModel {
	@Id
	@Column(name="project_id")
	private int project_id;
	@Column(name="domain_id")
	private int domain_id;
	
	@Column(name="project_Name")
	private String project_Name;
	@Column(name="project_Client")
	private String project_Client;
	@Column(name="project_startDate")
	private String project_startDate;
	@Column(name="project_endDate")
	private String project_endDate;
	@Column(name="project_Description")
	private String project_Description;
	
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_Name() {
		return project_Name;
	}
	public void setProject_Name(String project_Name) {
		this.project_Name = project_Name;
	}
	public String getProject_Client() {
		return project_Client;
	}
	public void setProject_Client(String project_Client) {
		this.project_Client = project_Client;
	}
	public String getProject_startDate() {
		return project_startDate;
	}
	public void setProject_startDate(String project_startDate) {
		this.project_startDate = project_startDate;
	}
	public String getProject_endDate() {
		return project_endDate;
	}
	public void setProject_endDate(String project_endDate) {
		this.project_endDate = project_endDate;
	}
	public String getProject_Description() {
		return project_Description;
	}
	public void setProject_Description(String project_Description) {
		this.project_Description = project_Description;
	}
	public int getDomain_id() {
		return domain_id;
	}
	public void setDomain_id(int domain_id) {
		this.domain_id = domain_id;
	}
	
	
}
