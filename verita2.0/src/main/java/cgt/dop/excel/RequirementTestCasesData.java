package cgt.dop.excel;

public class RequirementTestCasesData 
{
	private String reqStatus;
	private String applicationName;
	private String releaseName;
	private String requirementId;
	
	public RequirementTestCasesData()
	{
		super();
	}
	
	public String toString()
	{
		return String.format("%s - %s - %s - %s\n", reqStatus, applicationName, releaseName);
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public String setReqStatus(String reqStatus) {
		return this.reqStatus = reqStatus;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String setApplicationName(String applicationName) {
		return this.applicationName = applicationName;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public String setReleaseName(String releaseName) {
		return this.releaseName = releaseName;
	}

	public String getRequirementId() {
		return requirementId;
	}

	public String setRequirementId(String requirementId) {
		return this.requirementId = requirementId;
	}
}
