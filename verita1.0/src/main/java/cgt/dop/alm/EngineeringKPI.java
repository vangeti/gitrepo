package cgt.dop.alm;


import java.util.List;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.util.DefectSeverityIndexTO;
import cgt.dop.alm.util.OpenDefectSeverityTO;
import cgt.dop.alm.util.PrescriptiveAnalysisTO;
import cgt.dop.alm.util.TestCoverageTO;
import cgt.dop.alm.util.TestExecutionProgressTO;


/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */

public interface EngineeringKPI {


	public DefectSeverityIndexTO getDefectSeverityIndex(String releaseNo,RestConnector conn,String defectListAsXML) throws ConstraintViolatedException;
	

	public TestExecutionProgressTO getTestExecutionProgress(String releaseNo,RestConnector conn,String runListAsXML) throws ConstraintViolatedException;
	

	public TestCoverageTO getTestCoverage(String releaseNo,RestConnector conn,String requirementListAsXML) throws ConstraintViolatedException;
	

	public OpenDefectSeverityTO getOpenDefectSeverity(String releaseNo,RestConnector conn,String defectListAsXML) throws ConstraintViolatedException;
	
	public List<PrescriptiveAnalysisTO> getPrescriptiveAnalysis(String releaseNo, RestConnector conn,String defectListAsXML) throws ConstraintViolatedException;

}
