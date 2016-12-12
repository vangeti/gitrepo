package cgt.dop.excel;

public class DefectsData 
{
	private String defect_ID;
	private String defectSeverity;
	private String defectStatus;
	private String defectApplicationName;
	private String defectReleaseName;
	private String defectPhase;
	private String defectCreatedDate;
	private String defectDetectedDate;
	
	
	public DefectsData()
	{
		super();
	}
	
	public String toString()
	{
		return String.format("%s - %s - %s - %s - %s - %s\n", defect_ID, defectSeverity, defectStatus, defectApplicationName, defectReleaseName, defectPhase);
	}

	public String getDefect_ID() {
		return defect_ID;
	}

	public String setDefect_ID(String defect_ID) {
		return this.defect_ID = defect_ID;
	}

	public String getDefectSeverity() {
		return defectSeverity;
	}

	public String setDefectSeverity(String defectSeverity) {
		return this.defectSeverity = defectSeverity;
	}

	public String getDefectStatus() {
		return defectStatus;
	}

	public String setDefectStatus(String defectStatus) {
		return this.defectStatus = defectStatus;
	}

	public String getDefectApplicationName() {
		return defectApplicationName;
	}

	public String setDefectApplicationName(String defectApplicationName) {
		return this.defectApplicationName = defectApplicationName;
	}

	public String getDefectReleaseName() {
		return defectReleaseName;
	}

	public String setDefectReleaseName(String defectReleaseName) {
		return this.defectReleaseName = defectReleaseName;
	}

	public String getDefectPhase() {
		return defectPhase;
	}

	public String setDefectPhase(String defectPhase) {
		return this.defectPhase = defectPhase;
	}

	public String getDefectCreatedDate() {
		return defectCreatedDate;
	}

	public void setDefectCreatedDate(String defectCreatedDate) {
		this.defectCreatedDate = defectCreatedDate;
	}

	public String getDefectDetectedDate() {
		return defectDetectedDate;
	}

	public void setDefectDetectedDate(String defectDetectedDate) {
		this.defectDetectedDate = defectDetectedDate;
	}


	
}
