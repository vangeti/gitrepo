package cgt.dop.alm.util;

import java.util.List;
import java.util.Map;

public class TestCoverageTO {
    
    private Map<String, Float> testCoverageMap;
    
    private List<RequirementTO> reqWithNoTestCases;
    
    Map<String, String> totalMap;
    
    private float meanTotal;
    
    public Map<String, Float> getTestCoverageMap() {
        return testCoverageMap;
    }

    public void setTestCoverageMap(Map<String, Float> testCoverageMap) {
        this.testCoverageMap = testCoverageMap;
    }

    public List<RequirementTO> getReqWithNoTestCases() {
        return reqWithNoTestCases;
    }

    public void setReqWithNoTestCases(List<RequirementTO> reqWithNoTestCases) {
        this.reqWithNoTestCases = reqWithNoTestCases;
    }

    public Map<String, String> getTotalMap() {
        return totalMap;
    }

    public void setTotalMap(Map<String, String> totalMap) {
        this.totalMap = totalMap;
    }

    public float getMeanTotal() {
        return meanTotal;
    }

    public void setMeanTotal(float meanTotal) {
        this.meanTotal = meanTotal;
    }

   

}
