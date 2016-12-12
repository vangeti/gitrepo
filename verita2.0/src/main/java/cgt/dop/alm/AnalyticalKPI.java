package cgt.dop.alm;


import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.util.FeatureReadynessTO;
import cgt.dop.alm.util.MeanTimeTO;
import cgt.dop.alm.util.RequirementStabilityIndexTO;
import cgt.dop.alm.util.TestCaseYieldTO;

/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */
public interface AnalyticalKPI {

	
	public TestCaseYieldTO getTestCaseYield(String releaseNo,RestConnector conn, String defectListAsXML) throws ConstraintViolatedException;	
	
	public RequirementStabilityIndexTO getRequirementStabilityIndex(String releaseNo,RestConnector conn,String groupedReqListAsXML) throws ConstraintViolatedException;
	
	public FeatureReadynessTO getFeatureReadyness(String releaseNo, RestConnector conn,String runListAsXML,String defectListAsXML) throws ConstraintViolatedException;
	
	public MeanTimeTO getMeanTime(String releaseNo,RestConnector conn,String defectListAsXML) throws ConstraintViolatedException;
	
}
