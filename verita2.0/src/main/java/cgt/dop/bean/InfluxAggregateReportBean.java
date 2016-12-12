package cgt.dop.bean;

public class InfluxAggregateReportBean {

	String transactionName;
	
	String count;
	
	String avg;
	
	String aggregate_report_90_line;
	
	String aggregate_report_min;
	
	String aggregate_report_max;
	
	String aggregate_report_error;


	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getAggregate_report_90_line() {
		return aggregate_report_90_line;
	}

	public void setAggregate_report_90_line(String aggregate_report_90_line) {
		this.aggregate_report_90_line = aggregate_report_90_line;
	}

	public String getAggregate_report_min() {
		return aggregate_report_min;
	}

	public void setAggregate_report_min(String aggregate_report_min) {
		this.aggregate_report_min = aggregate_report_min;
	}

	public String getAggregate_report_max() {
		return aggregate_report_max;
	}

	public void setAggregate_report_max(String aggregate_report_max) {
		this.aggregate_report_max = aggregate_report_max;
	}

	public String getAggregate_report_error() {
		return aggregate_report_error;
	}

	public void setAggregate_report_error(String aggregate_report_error) {
		this.aggregate_report_error = aggregate_report_error;
	}


}
