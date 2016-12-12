package cgt.dop.alm;

import java.util.List;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.util.DefectSeverityIndexTO;
import cgt.dop.alm.util.FeatureReadynessTO;
import cgt.dop.alm.util.MeanTimeTO;
import cgt.dop.alm.util.OpenDefectSeverityTO;
import cgt.dop.alm.util.PrescriptiveAnalysisTO;
import cgt.dop.alm.util.ReleaseReadynessTO;
import cgt.dop.alm.util.RequirementStabilityIndexTO;
import cgt.dop.alm.util.TestCaseYieldTO;
import cgt.dop.alm.util.TestCoverageTO;
import cgt.dop.alm.util.TestExecutionProgressTO;

public interface DashBoardKPI {
	
	public ReleaseReadynessTO getReleaseReadynessKPI(String releaseNo) throws ConstraintViolatedException;
	
	public List<PrescriptiveAnalysisTO> getPrescriptiveAnalysis(String releaseNo) throws ConstraintViolatedException;
		
	public DefectSeverityIndexTO getScrumMasterDefectSeverityIndex(String releaseNo) throws ConstraintViolatedException;
	
	public TestExecutionProgressTO getScrumMasterTestExecutionProgress(String releaseNo) throws ConstraintViolatedException;
	
	public TestCoverageTO getScrumMasterTestCoverage(String releaseNo) throws ConstraintViolatedException;	
	
	public OpenDefectSeverityTO getScrumMasterOpenDefectSeverity(String releaseNo) throws ConstraintViolatedException;	
	
	public RequirementStabilityIndexTO getScrumMasterRequirementStabilityIndex(String releaseNo) throws ConstraintViolatedException;
	
	public FeatureReadynessTO getScrumMasterFeatureReadyness(String releaseNo) throws ConstraintViolatedException;	
	
	public MeanTimeTO getScrumMasterMeanTime(String releaseNo) throws ConstraintViolatedException;	
	
	public TestCaseYieldTO getScrumMasterTestCaseYield(String releaseNo) throws ConstraintViolatedException;
	
	public String getDefectDetails();
	

}
