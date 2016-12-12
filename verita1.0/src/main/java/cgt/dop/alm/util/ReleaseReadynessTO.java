package cgt.dop.alm.util;


public class ReleaseReadynessTO {

	private Float engKPI;

	private Float anlyKPI;

	private Float relReady;

	private Integer engKPIPercentage;

	private Integer analyKPIPercentage;

	public DefectSeverityIndexTO defectSeverityIndex;

	private TestExecutionProgressTO testExecutionProgress;

	public TestCoverageTO testCoverage;

	public OpenDefectSeverityTO openDefectSeverity;

	public TestCaseYieldTO testCaseYield;

	public RequirementStabilityIndexTO requirementStabilityIndex;

	public MeanTimeTO meanTime;

	public FeatureReadynessTO featureReadyness;

	public Float getEngKPI() {
		return engKPI;
	}

	public void setEngKPI(Float engKPI) {
		this.engKPI = engKPI;
	}

	public Float getAnlyKPI() {
		return anlyKPI;
	}

	public void setAnlyKPI(Float anlyKPI) {
		this.anlyKPI = anlyKPI;
	}

	public Float getRelReady() {
		return relReady;
	}

	public void setRelReady(Float relReady) {
		this.relReady = relReady;
	}

	public Integer getEngKPIPercentage() {
		return engKPIPercentage;
	}

	public void setEngKPIPercentage(Integer engKPIPercentage) {
		this.engKPIPercentage = engKPIPercentage;
	}

	public Integer getAnalyKPIPercentage() {
		return analyKPIPercentage;
	}

	public void setAnalyKPIPercentage(Integer analyKPIPercentage) {
		this.analyKPIPercentage = analyKPIPercentage;
	}

	public DefectSeverityIndexTO getDefectSeverityIndex() {
		return defectSeverityIndex;
	}

	public void setDefectSeverityIndex(DefectSeverityIndexTO defectSeverityIndex) {
		this.defectSeverityIndex = defectSeverityIndex;
	}

	public TestExecutionProgressTO getTestExecutionProgress() {
		return testExecutionProgress;
	}

	public void setTestExecutionProgress(TestExecutionProgressTO testExecutionProgress) {
		this.testExecutionProgress = testExecutionProgress;
	}

	public TestCoverageTO getTestCoverage() {
		return testCoverage;
	}

	public void setTestCoverage(TestCoverageTO testCoverage) {
		this.testCoverage = testCoverage;
	}

	public OpenDefectSeverityTO getOpenDefectSeverity() {
		return openDefectSeverity;
	}

	public void setOpenDefectSeverity(OpenDefectSeverityTO openDefectSeverity) {
		this.openDefectSeverity = openDefectSeverity;
	}

	public TestCaseYieldTO getTestCaseYield() {
		return testCaseYield;
	}

	public void setTestCaseYield(TestCaseYieldTO testCaseYield) {
		this.testCaseYield = testCaseYield;
	}

	public RequirementStabilityIndexTO getRequirementStabilityIndex() {
		return requirementStabilityIndex;
	}

	public void setRequirementStabilityIndex(RequirementStabilityIndexTO requirementStabilityIndex) {
		this.requirementStabilityIndex = requirementStabilityIndex;
	}

	public MeanTimeTO getMeanTime() {
		return meanTime;
	}

	public void setMeanTime(MeanTimeTO meanTime) {
		this.meanTime = meanTime;
	}

	public FeatureReadynessTO getFeatureReadyness() {
		return featureReadyness;
	}

	public void setFeatureReadyness(FeatureReadynessTO featureReadyness) {
		this.featureReadyness = featureReadyness;
	}

	@Override
	public String toString() {
		return "ReleaseReadynessTO [engKPI=" + engKPI + ", anlyKPI=" + anlyKPI + ", relReady=" + relReady + ", engKPIPercentage=" + engKPIPercentage + ", analyKPIPercentage="
				+ analyKPIPercentage + ", defectSeverityIndex=" + defectSeverityIndex + ", testExecutionProgress=" + testExecutionProgress + ", testCoverage=" + testCoverage
				+ ", openDefectSeverity=" + openDefectSeverity + ", testCaseYield=" + testCaseYield + ", requirementStabilityIndex=" + requirementStabilityIndex + ", meanTime="
				+ meanTime + ", featureReadyness=" + featureReadyness + "]";
	}

	

}
