package cgt.dop.alm.util;

import java.util.List;
import java.util.Map;

public class TestExecutionProgressTO {

    private Map<String, Float> executionProgressMap;
    
    private Map<String, String> allTestCasesMap;

    private List<RunTO> failedCases;

    private float meanTotal;

    
    public Map<String, Float> getExecutionProgressMap() {
        return executionProgressMap;
    }

    public void setExecutionProgressMap(Map<String, Float> executionProgressMap) {
        this.executionProgressMap = executionProgressMap;
    }

    public List<RunTO> getFailedCases() {
        return failedCases;
    }

    public void setFailedCases(List<RunTO> failedCases) {
        this.failedCases = failedCases;
    }

    public float getMeanTotal() {
	return meanTotal;
    }

    public void setMeanTotal(float meanTotal) {
	this.meanTotal = meanTotal;
    }

    public Map<String, String> getAllTestCasesMap() {
        return allTestCasesMap;
    }

    public void setAllTestCasesMap(Map<String, String> allTestCasesMap) {
        this.allTestCasesMap = allTestCasesMap;
    }

}
