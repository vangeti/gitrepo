package cgt.dop.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="lennox_projectdetails")
public class LennoxTestDetails {
	@Id
	
	@Column(name="testname")
	private String testName;
	@Column(name="teststarttime")
	private String startTime;
	@Column(name="testendtime")
	private String endTime;
	@Column(name="testsuite")
	private String testsuite;
	@Column(name="projectId")
	private String projectId;
	@Column(name="buildNo")
	private String buildno;
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
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
	public String getTestsuite() {
		return testsuite;
	}
	public void setTestsuite(String testsuite) {
		this.testsuite = testsuite;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getBuildno() {
		return buildno;
	}
	public void setBuildno(String buildno) {
		this.buildno = buildno;
	}
	
	
}
