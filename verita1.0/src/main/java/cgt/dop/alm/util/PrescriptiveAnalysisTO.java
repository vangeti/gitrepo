package cgt.dop.alm.util;

import java.util.List;

public class PrescriptiveAnalysisTO {
    
	private String defectId;
	
	private String applicationName;
	
	private String severity;
	
	private String count;
	
	private List<TCBlockedTO> tCBlockedList;
	
	private int defectPriority;
	
	public int getDefectPriority() {
	    return defectPriority;
	}

	public void setDefectPriority(int defectPriority) {
	    this.defectPriority = defectPriority;
	}

	public PrescriptiveAnalysisTO()
	{
		
	}

	public String getDefectId() {
		return defectId;
	}

	public PrescriptiveAnalysisTO(String defectId, String applicationName, String severity, String count,List<TCBlockedTO> tCBlockedList) {
		super();
		this.defectId = defectId;
		this.applicationName = applicationName;
		this.severity = severity;
		this.count = count;
		this.tCBlockedList = tCBlockedList;
	}

	public void setDefectId(String defectId) {
		this.defectId = defectId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}


	public List<TCBlockedTO> gettCBlockedList() {
		return tCBlockedList;
	}

	public void settCBlockedList(List<TCBlockedTO> tCBlockedList) {
		this.tCBlockedList = tCBlockedList;
	}

	@Override
	public String toString() {
		return "PrescriptiveAnalysisTO [defectId=" + defectId + ", applicationName=" + applicationName + ", defectPriority=" + defectPriority + ", severity=" + severity
				+ ", count=" + count + ", tCBlockedList=" + tCBlockedList + "]";
	}
    
}
