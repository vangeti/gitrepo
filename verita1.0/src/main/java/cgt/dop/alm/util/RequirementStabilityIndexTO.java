package cgt.dop.alm.util;

import java.util.Map;

public class RequirementStabilityIndexTO {
    
    private Map<String, Float> requirementStabilityIndexMap;
    
    private float meanTotal;

    public Map<String, Float> getRequirementStabilityIndexMap() {
        return requirementStabilityIndexMap;
    }

    public void setRequirementStabilityIndexMap(
    	Map<String, Float> requirementStabilityIndexMap) {
        this.requirementStabilityIndexMap = requirementStabilityIndexMap;
    }

    public float getMeanTotal() {
        return meanTotal;
    }

    public void setMeanTotal(float meanTotal) {
        this.meanTotal = meanTotal;
    }

}
