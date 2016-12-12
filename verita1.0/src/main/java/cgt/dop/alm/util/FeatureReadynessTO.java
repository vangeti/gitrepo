package cgt.dop.alm.util;

import java.util.Map;

public class FeatureReadynessTO {
    
    private Map<String, Float> featureReadynessMap;
    
    private Map<String, String> allDefects;
    
    private float meanTotal;

    public Map<String, Float> getFeatureReadynessMap() {
        return featureReadynessMap;
    }

    public void setFeatureReadynessMap(Map<String, Float> featureReadynessMap) {
        this.featureReadynessMap = featureReadynessMap;
    }

    public Map<String, String> getAllDefects() {
        return allDefects;
    }

    public void setAllDefects(Map<String, String> allDefects) {
        this.allDefects = allDefects;
    }

    public float getMeanTotal() {
        return meanTotal;
    }

    public void setMeanTotal(float meanTotal) {
        this.meanTotal = meanTotal;
    }

}
