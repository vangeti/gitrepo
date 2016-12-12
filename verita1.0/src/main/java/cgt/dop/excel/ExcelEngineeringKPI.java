package cgt.dop.excel;


import java.io.IOException;
import java.util.HashMap;

import cgt.dop.alm.exception.ConstraintViolatedException;


/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */

public interface ExcelEngineeringKPI {

	
	 /**
     * Gets Defect Severity Index
     * 
     * @param property (<code>String</code>) : the property releaseNo
     * @return <code>Map<String, Double></code> : returns resultMap Object.
     * @throws ConstraintViolatedException
     */
	public HashMap<String, Double> getDefectSeverity(String excelFilePath) throws IOException;
	
	public HashMap<String, Double> getTestCoverageBarGraph(String excelFilePath) throws IOException;
	
	
}
