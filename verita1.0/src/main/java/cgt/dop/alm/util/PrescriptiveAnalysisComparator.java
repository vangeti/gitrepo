package cgt.dop.alm.util;

import java.util.Comparator;

public class PrescriptiveAnalysisComparator implements Comparator<PrescriptiveAnalysisTO> {
	
	public int compare(PrescriptiveAnalysisTO obj1, PrescriptiveAnalysisTO obj2) {		
	    int result = obj1.getSeverity().compareTo(obj2.getSeverity());	
	    if (result == 0)
		result = obj2.getCount().compareTo(obj1.getCount());	    
	    
	    return result;

	}

}
