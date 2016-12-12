package cgt.dop.bean;

import java.util.Date;

public class ResponseTimeBean implements Comparable<ResponseTimeBean> {

	private long responseTime;

	private Date elapsedTime;

	private String elapsedTimeString;

	private Date elapsedTimeGraph;

	public Date getElapsedTimeGraph() {
		return elapsedTimeGraph;
	}

	private String transactionName;
	
	private String transactionNameGraph;
	
	private double responseTimeGraph;
	


	public double getResponseTimeGraph() {
		return responseTimeGraph;
	}

	public void setResponseTimeGraph(double responseTimeGraph) {
		this.responseTimeGraph = responseTimeGraph;
	}

	public void setElapsedTimeGraph(Date elapsedTimeGraph) {
		this.elapsedTimeGraph = elapsedTimeGraph;
	}

	public String getTransactionNameGraph() {
		return transactionNameGraph;
	}

	public void setTransactionNameGraph(String transactionNameGraph) {
		this.transactionNameGraph = transactionNameGraph;
	}


	public ResponseTimeBean() {

	}

	public ResponseTimeBean(long responseTime, Date elapsedTime, String transactionName) {
		super();
		this.responseTime = responseTime;
		this.elapsedTime = elapsedTime;
		this.transactionName = transactionName;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public Date getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Date elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getElapsedTimeString() {
		return elapsedTimeString;
	}

	public void setElapsedTimeString(String elapsedTimeString) {
		this.elapsedTimeString = elapsedTimeString;
	}

	@Override
	public String toString() {
		return "ResponseTimeBean [responseTime=" + responseTime + ", transactionName=" + transactionName + "]";
	}

	@Override
	public int compareTo(ResponseTimeBean other) {
		int result = this.getTransactionName().compareTo(other.getTransactionName());

		return result;
	}

}
