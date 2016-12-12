package cgt.dop.bean;

public class ObservationsBean {
	
	
	private double error;
	
	private double tps;
	
	private double responseTime;
	
	public ObservationsBean(){
		
	}

	public double getError() {
		return error;
	}



	public void setError(double error) {
		this.error = error;
	}



	public double getTps() {
		return tps;
	}



	public void setTps(double tps) {
		this.tps = tps;
	}

	public double getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "ObservationsBean [error=" + error + ", tps=" + tps + ", responseTime=" + responseTime + "]";
	}



	}
