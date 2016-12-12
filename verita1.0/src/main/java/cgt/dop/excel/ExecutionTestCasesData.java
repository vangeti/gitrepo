package cgt.dop.excel;

public class ExecutionTestCasesData 
{
	private String testExecutionStatus;
	private String testApplicationName;
	private String testReleaseName;
	private String testRequirementId;
	
	public ExecutionTestCasesData()
	{
		super();
	}
	
	public String toString()
	{
		return String.format("%s - %s - %s - %s\n", testExecutionStatus, testApplicationName, testReleaseName, testRequirementId);
	}

	public String getTestExecutionStatus() {
		return testExecutionStatus;
	}

	public String setTestExecutionStatus(String testExecutionStatus) {
		return this.testExecutionStatus = testExecutionStatus;
	}

	public String getTestApplicationName() {
		return testApplicationName;
	}

	public String setTestApplicationName(String testApplicationName) {
		return this.testApplicationName = testApplicationName;
	}

	public String getTestReleaseName() {
		return testReleaseName;
	}

	public String setTestReleaseName(String testReleaseName) {
		return this.testReleaseName = testReleaseName;
	}

	public String getTestRequirementId() {
		return testRequirementId;
	}

	public String setTestRequirementId(String testRequirementId) {
		return this.testRequirementId = testRequirementId;
	}

}
