package cgt.dop.bean;

public class PredictionBean 
{
	private int callsPerminute;
	private String actual;
	private double prediction;

	public int getCallsPerminute() {
		return callsPerminute;
	}

	public void setCallsPerminute(int callsPerminute) {
		this.callsPerminute = callsPerminute;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public double getPrediction() {
		return prediction;
	}

	public void setPrediction(double prediction) {
		this.prediction = prediction;
	}

}

