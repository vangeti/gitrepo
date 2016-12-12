package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_details")
public class TestDetails {
	@Id
	@Column(name="projectId")
	private int projectId;
	@Column(name="projectName")
	private String projectName;
	
	//@OneToOne(cascade = { CascadeType.ALL })
	//@JoinColumn(name = "buildNo")
	//private NeoLoadProject buildNo;

	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/*public NeoLoadProject getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(NeoLoadProject buildNo) {
		this.buildNo = buildNo;
	}*/
	
	
	
	
}
