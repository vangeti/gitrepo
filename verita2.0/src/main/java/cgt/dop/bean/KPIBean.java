package cgt.dop.bean;

public class KPIBean {

	private double errorForTest1;

	private double tpsForTest1;

	private double errorForTest2;

	private double tpsForTest2;
	
	private double errorDiff;
	
	private double errorDiffPercentage;
	
	private double tpsDiff;
	
	private double tpsDiffPercentage;

	public KPIBean() {

	}

	public double getErrorForTest1() {
		return errorForTest1;
	}

	public void setErrorForTest1(double errorForTest1) {
		this.errorForTest1 = errorForTest1;
	}

	public double getTpsForTest1() {
		return tpsForTest1;
	}

	public void setTpsForTest1(double tpsForTest1) {
		this.tpsForTest1 = tpsForTest1;
	}

	public double getErrorForTest2() {
		return errorForTest2;
	}

	public void setErrorForTest2(double errorForTest2) {
		this.errorForTest2 = errorForTest2;
	}

	public double getTpsForTest2() {
		return tpsForTest2;
	}

	public void setTpsForTest2(double tpsForTest2) {
		this.tpsForTest2 = tpsForTest2;
	}

	
	

	public double getErrorDiff() {
		return errorDiff;
	}

	public void setErrorDiff(double errorDiff) {
		this.errorDiff = errorDiff;
	}

	public double getErrorDiffPercentage() {
		return errorDiffPercentage;
	}

	public void setErrorDiffPercentage(double errorDiffPercentage) {
		this.errorDiffPercentage = errorDiffPercentage;
	}

	public double getTpsDiff() {
		return tpsDiff;
	}

	public void setTpsDiff(double tpsDiff) {
		this.tpsDiff = tpsDiff;
	}

	public double getTpsDiffPercentage() {
		return tpsDiffPercentage;
	}

	public void setTpsDiffPercentage(double tpsDiffPercentage) {
		this.tpsDiffPercentage = tpsDiffPercentage;
	}

	@Override
	public String toString() {
		return "KPIBean [errorForTest1=" + errorForTest1 + ", tpsForTest1=" + tpsForTest1 + ", errorForTest2=" + errorForTest2 + ", tpsForTest2=" + tpsForTest2
				+ ", errorDiff=" + errorDiff + ", errorDiffPercentage=" + errorDiffPercentage + ", tpsDiff=" + tpsDiff + ", tpsDiffPercentage="
				+ tpsDiffPercentage + "]";
	}


}
