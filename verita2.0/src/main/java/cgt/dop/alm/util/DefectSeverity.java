package cgt.dop.alm.util;

public enum DefectSeverity {

	ONE("1"),
	
	TWO("2"),
	
	THREE("3"),
	
	FOUR("4");
	
	private String val;

	DefectSeverity(String val) {
		this.val = val;
	}

	public String val() {
		return val;
	}
}
